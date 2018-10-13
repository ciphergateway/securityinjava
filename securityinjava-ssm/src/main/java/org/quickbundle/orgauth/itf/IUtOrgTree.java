package org.quickbundle.orgauth.itf;

import javax.servlet.http.HttpServletRequest;

import org.quickbundle.project.DeepTreeVo;
import org.quickbundle.project.common.RmCommonVo;

public interface IUtOrgTree {
	/**
	 * 初始化每个树节点的值
	 * 
	 * @param request
	 * @param party_view_id
	 * @param cvo
	 * @return
	 */
	public DeepTreeVo getDeepTreeVo(HttpServletRequest request, String party_view_id, DeepTreeVo dtv, RmCommonVo cvo);
	
}
