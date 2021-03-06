package org.quickbundle.project.login;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.quickbundle.base.beans.RmBeanFactory;
import org.quickbundle.base.service.RmService;
import org.quickbundle.config.RmClusterConfig;
import org.quickbundle.config.RmConfig;
import org.quickbundle.orgauth.rmuser.IRmUserConstants;
import org.quickbundle.orgauth.rmuser.IRmUserService;
import org.quickbundle.orgauth.rmuseronlinerecord.IRmUserOnlineRecordConstants;
import org.quickbundle.orgauth.rmuseronlinerecord.IRmUserOnlineRecordService;
import org.quickbundle.orgauth.rmuseronlinerecord.RmUserOnlineRecordVo;
import org.quickbundle.project.IGlobalConstants;
import org.quickbundle.project.RmGlobalMonitor;
import org.quickbundle.project.RmProjectHelper;
import org.quickbundle.project.RmRequestMonitor;
import org.quickbundle.project.listener.RmSessionListener;
import org.quickbundle.project.login.RmLoginVo.RmUserSessionVo;
import org.quickbundle.tools.helper.RmDateHelper;
import org.quickbundle.tools.helper.RmJspHelper;
import org.quickbundle.tools.helper.RmVoHelper;

public class RmLoginService extends RmService implements IRmLoginService {
    public IRmUserOnlineRecordService getUserOnlineRecordService() {
        return (IRmUserOnlineRecordService) RmBeanFactory.getBean(IRmUserOnlineRecordConstants.SERVICE_KEY);  //得到Service对象,受事务控制
    }
	
	/**
	 * 根据login_id查用户
	 * 
	 * @param login_id
	 * @return
	 */
	public List<IRmLoginVo> findUsersByLoginId(String login_id) {
		List lvo = null;
		//orgauth begin
		IRmUserService userService = (IRmUserService)RmBeanFactory.getBean(IRmUserConstants.SERVICE_KEY);
		lvo = userService.queryByCondition("login_id='" + login_id + "'", null, -1, -1, true);
		//orgauth end
		return lvo;
	}

	/**
	 * 验证用户登录的合法性; 登录用户公司判断并添加相关信息
	 * 
	 * @param request
	 * @param loginVo
	 * @return 校验异常时，返回异常反馈信息，正常返回null
	 */
	public String validateLogin(ServletRequest request, IRmLoginVo loginVo){
		RmLoginVo userVo = (RmLoginVo)loginVo;
		String loginStr = null;
		//orgauth validateLogin begin
		if(IOrgauthConstants.UserAdminType.ADMIN.value().equals(loginVo.getAdmin_type())) {
			//admin可直接登录
		} else{
			//扩展用户登录的合理性校验
			if(IGlobalConstants.RM_NO.equals(userVo.getLock_status())){
				loginStr="该用户被锁定，暂时不能登录，请联系管理员！";
			}
		} 
		//orgauth validateLogin end
		return loginStr;
	}

	/**
	 * 判断这个用户是否已经在线
	 * @param request
	 * @param loginVo
	 * @return 如果未登录，返回null；如果已经登录在线，返回vo信息
	 */
	public UserUniqueLoginVo checkUniqueLogin(ServletRequest request, IRmLoginVo loginVo) {
		RmLoginVo userVo = (RmLoginVo)loginVo;
		UserUniqueLoginVo uniqueLoginVo = new UserUniqueLoginVo();
		if(IGlobalConstants.RM_NO.equals(userVo.getLogin_status())) {
			return null;
		}
		//orgauth begin
		RmUserOnlineRecordVo onlineRecordVo = getUserOnlineRecordService().findLastLoginRecord(userVo.getId());
		if(onlineRecordVo != null) {
			//从RmUserOnlineRecord复制信息
			uniqueLoginVo.setSession_id(onlineRecordVo.getLogin_sign());
			uniqueLoginVo.setLogin_ip(onlineRecordVo.getLogin_ip());
			uniqueLoginVo.setLogin_date(onlineRecordVo.getLogin_time());
			//从session中继续复制
			HttpSession sessionToReplace = RmSessionListener.getSessionById(onlineRecordVo.getLogin_sign());
			if(sessionToReplace != null) {
				//从loginVo复制信息
				uniqueLoginVo.setName(userVo.getName());
				uniqueLoginVo.setLogin_id(userVo.getLogin_id());
				uniqueLoginVo.setId(userVo.getId());
			} else {
				// 集群模式下，直接从数据库读取用户信息
				if(!RmConfig.getSingleton().isClusterMode() || RmClusterConfig.getSingleton().getSelfId().equals(onlineRecordVo.getCluster_node_id())) {
					return null;
				}
				RmUserSessionVo sessionVo = null;
				if(sessionVo == null) {
					return null;
				}
				//从sessionVo复制信息
				uniqueLoginVo.setName(sessionVo.getName());
				uniqueLoginVo.setLogin_id(sessionVo.getLogin_id());
				uniqueLoginVo.setId(sessionVo.getId());
			}
			return uniqueLoginVo;
		}
		//orgauth end
		return null;
	}
	
	/**
	 * 执行强制登录（踢出已在线用户，清理在线记录）
	 * @param request
	 * @param loginVo
	 * @return 强制登录是否成功
	 */
	public boolean executeLoginForce(ServletRequest request, IRmLoginVo loginVo) {
		RmLoginVo userVo = (RmLoginVo)loginVo;
		//orgauth begin
		String message = "您被IP为" + RmProjectHelper.getIp(request) + "的用户取代登录了，请重新登录。如有帐号异常，请联系管理员。";
		if(userVo.getId() == null || userVo.getId().length() == 0) {
			return true;
		}
		//销毁集群下兄弟节点的session
		RmUserOnlineRecordVo onlineRecordVo = getUserOnlineRecordService().findLastLoginRecord(userVo.getId());
		if(onlineRecordVo != null) {
			String cluster_node_id = onlineRecordVo.getCluster_node_id();
			String sessionId = onlineRecordVo.getLogin_sign();
			HttpSession sessionToReplace = RmSessionListener.getSessionById(sessionId);
			if(sessionToReplace != null) {
				sessionToReplace.setAttribute(IRmLoginConstants.LOGOUT_TYPE, IRmLoginConstants.LogoutType.FORCE_REPLACE.value());
				executeDestroyUserInfo(sessionToReplace);
				sessionToReplace.setAttribute(IGlobalConstants.SystemPara.system_message.name(), message);
			}
		}
		//orgauth end
		return true;
	}

	/**
	 * 成功登录后，初始化登录session信息; 插入用户在线记录，并更新状态为--"已登录"、记录登录时间、IP、登录次数
	 * 查询出公司、部门、营业厅信息
	 * 
	 * @param request
	 * @param loginVo
	 */
	public void executeInitUserInfo(ServletRequest request, ServletResponse response, IRmLoginVo loginVo) {
    	//确保产生session，并往session放入loginVo
    	HttpSession session = RmJspHelper.getSession(request, response, true);
		RmLoginVo userVo = (RmLoginVo)loginVo;
		//orgauth initUserInfo begin

		//orgauth initUserInfo end
		
		//记住登录时间
		userVo.getMAttribute().put(RmLoginVo.A_LOGIN_TIME, String.valueOf(System.currentTimeMillis()));
		//往session放入loginVo
		session.setAttribute(IGlobalConstants.RM_USER_VO, userVo);
		//更新用户状态
		updateLoginStatus(request, loginVo);
	}
	

	/**
	 * 插入用户在线记录，并更新状态为--"已登录"、记录登录时间、IP、登录次数
	 * @param request
	 * @param loginVo
	 */
	private void updateLoginStatus(ServletRequest request, IRmLoginVo loginVo) {
		//orgauth updateLoginStatus begin 插入用户在线记录
		IRmUserOnlineRecordService uorService = (IRmUserOnlineRecordService)RmBeanFactory.getBean(IRmUserOnlineRecordConstants.SERVICE_KEY);
		RmUserOnlineRecordVo uorVo = new RmUserOnlineRecordVo();
		uorVo.setUser_id(loginVo.getId());
		uorVo.setCluster_node_id(RmClusterConfig.getSingleton().getSelfId());
		uorVo.setLogin_time(RmDateHelper.getSysTimestamp());
		uorVo.setLogin_ip(RmProjectHelper.getIp(request));
		uorVo.setLogin_uuid(RmGlobalMonitor.uniqueUUID.get());
		uorVo.setLogin_sign(RmJspHelper.getSession(request, null, false).getId());
		RmVoHelper.markCreateStamp((HttpServletRequest)request, uorVo);
		uorService.insert(uorVo);
		//更新用户状态
		Timestamp lastLoginDate = uorVo.getLogin_time();
		String lastLoginIp = uorVo.getLogin_ip();
		RmProjectHelper.getCommonServiceInstance().doUpdate("update RM_USER set LOGIN_STATUS='1', LAST_LOGIN_DATE=?, LAST_LOGIN_IP=?, LOGIN_SUM=LOGIN_SUM+1 where ID=?", new Object[]{lastLoginDate, lastLoginIp, loginVo.getId()});
		{
			RmLoginVo userVo = (RmLoginVo)loginVo;
			userVo.setLast_login_date(lastLoginDate);
			userVo.setLast_login_ip(lastLoginIp);
			userVo.setLogin_sum(userVo.getLogin_sum()+1);
		}
		//orgauth updateLoginStatus end
	}
	
	/**
	 * 清除登录session信息; 完成用户在线记录，并更新状态为--"未登录"
	 * 
	 * @param session 要清理的session
	 */
	public void executeDestroyUserInfo(HttpSession session) {
		if(session == null || session.getAttribute(IGlobalConstants.RM_USER_VO) == null
				|| IGlobalConstants.RM_YES.equals(session.getAttribute(IGlobalConstants.RM_SSO_TEMP)) ) {
			return;
		}
		RmLoginVo vo = (RmLoginVo)session.getAttribute(IGlobalConstants.RM_USER_VO);
		//orgauth destroy begin 完成用户在线记录
		IRmUserOnlineRecordService uorService = (IRmUserOnlineRecordService)RmBeanFactory.getBean(IRmUserOnlineRecordConstants.SERVICE_KEY);
		List<RmUserOnlineRecordVo> lUor = uorService.queryByCondition("USER_ID=" + vo.getId() + " and LOGIN_SIGN='" + session.getId() + "' and LOGOUT_TIME is null", "LOGIN_TIME DESC", -1, -1, true);
		if(lUor.size() > 0) {
			RmUserOnlineRecordVo uorVo = (RmUserOnlineRecordVo)lUor.get(0);
			uorVo.setLogout_time(RmDateHelper.getSysTimestamp());
			if(session.getAttribute(IRmLoginConstants.LOGOUT_TYPE) != null) { //如果设置了注销类型
				uorVo.setLogout_type(session.getAttribute(IRmLoginConstants.LOGOUT_TYPE).toString());
				session.removeAttribute(IRmLoginConstants.LOGOUT_TYPE);
			} else { //默认是超时退出
				uorVo.setLogout_type(IRmLoginConstants.LogoutType.TIMEOUT.value());
			}
			uorVo.setLast_operation(vo.getMAttribute().get(RmLoginVo.A_LAST_OPERATION));
			uorVo.setOnline_time(new BigDecimal(uorVo.getLogout_time().getTime() - uorVo.getLogin_time().getTime()));
			RmVoHelper.markModifyStamp(RmRequestMonitor.getCurrentThreadRequest(), uorVo);
			uorService.update(uorVo);
			//更新用户状态
			RmProjectHelper.getCommonServiceInstance().doUpdate("update RM_USER SET LOGIN_STATUS='0' where ID=" + vo.getId());
		}
		//清除登录session信息
		session.removeAttribute(IGlobalConstants.RM_USER_VO);
		//orgauth destroy end
		
		//system lockRelease begin
		//释放用户的所有业务锁
		org.quickbundle.modules.lock.RmLockHelper.releaseLock(vo.getId());
		//system lockRelease end
	}
}