//代码生成时,文件路径: D:/rc/svn/fm/code/cu-tm/src/main/java/modules/log/rmlog/service/impl/RmLogService.java
//代码生成时,系统时间: 2010-11-30 22:31:36
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> cu-tm
 * 
 * 文件名称: org.quickbundle.modules.log.rmlog.service.impl --> RmLogService.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-30 22:31:36 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.modules.log.rmlog.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.quickbundle.base.service.RmService;
import org.quickbundle.modules.log.rmlog.dao.IRmLogDao;
import org.quickbundle.modules.log.rmlog.service.IRmLogService;
import org.quickbundle.modules.log.rmlog.util.IRmLogConstants;
import org.quickbundle.modules.log.rmlog.vo.RmLogVo;
import org.quickbundle.project.cache.RmSqlCountCache;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public class RmLogService extends RmService implements IRmLogService, IRmLogConstants {
    
    /**
     * dao 表示: 数据访问层的实例
     */
    private IRmLogDao dao = null;

    /**
     * 设置数据访问接口
     * 
     * @return
     */
    public IRmLogDao getDao() {
        return dao;
    }

    /**
     * 获取数据访问接口
     * 
     * @param dao
     */
    public void setDao(IRmLogDao dao) {
        this.dao = dao;
    }


    /**
     * 插入单条记录
     * 
     * @param vo 用于添加的VO对象
     * @return 若添加成功，返回新生成的Oid
     */
    public String insert(RmLogVo vo) {
        String id = getDao().insert(vo);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "插入了1条记录,id=" + String.valueOf(id));
        RmSqlCountCache.clearCount(TABLE_NAME);  //清除count记录数缓存
		return id;
    }
    
    /**
     * 插入多条记录
     *
     * @param vos 用于添加的VO对象数组
     * @return 返回新生成的id数组
     */
    public String[] insert(RmLogVo[] vos) {
        String[] aId = getDao().insert(vos);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "插入了" + vos.length + "条记录,id=" + RmStringHelper.ArrayToString(aId, ","));
        RmSqlCountCache.clearCount(TABLE_NAME);  //清除count记录数缓存
        return aId;
    }

    /**
     * 删除单条记录
     * 
     * @param id 用于删除的记录的id
     * @return 成功删除的记录数
     */
    public int delete(String id) {
		int sum = getDao().delete(id);
		//RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "删除了" + sum + "条记录,id=" + String.valueOf(id));
		RmSqlCountCache.clearCount(TABLE_NAME);  //清除count记录数缓存
		return sum;
    }

    /**
     * 删除多条记录
     * 
     * @param ids 用于删除的记录的ids
     * @return 成功删除的记录数
     */
    public int delete(String ids[]) {
		int sum = getDao().delete(ids);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "删除了" + sum + "条记录,id=" + RmStringHelper.ArrayToString(ids, ","));
        RmSqlCountCache.clearCount(TABLE_NAME);  //清除count记录数缓存
		return sum;
    }

    /**
     * 根据Id进行查询
     * 
     * @param id 用于查找的id
     * @return 查询到的VO对象
     */
    public RmLogVo find(String id) {
		RmLogVo vo = getDao().find(id);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "察看了1条记录,id=" + id);
		return vo;
    }

    /**
     * 更新单条记录
     * 
     * @param vo 用于更新的VO对象
     * @return 成功更新的记录数
     */
    public int update(RmLogVo vo) {
		int sum = getDao().update(vo);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "更新了" + sum + "条记录,id=" + String.valueOf(vo.getId()));
        RmSqlCountCache.clearCount(TABLE_NAME);  //清除count记录数缓存
		return sum;
    }

    /**
     * 批量更新修改多条记录
     * 
     * @param vos 更新的VO对象数组
     * @return 成功更新的记录最终数量
     */
	public int update(RmLogVo[] vos) {
		int[] sum = getDao().update(vos);
		int finalSum = 0;
		for (int i = 0; i < sum.length; i++) {
			finalSum += sum[i];
		}
		//RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "批量更新了" + finalSum + "条记录);
        RmSqlCountCache.clearCount(TABLE_NAME);  //清除count记录数缓存
		return finalSum;
	}
	
	/**
	 * 批量保存，没有主键的insert，有主键的update
	 * 
	 * @param vos 更新的VO对象数组
	 * @return new int[2]{insert的记录数, update的记录数}	
	 */
	public int[] insertUpdateBatch(RmLogVo[] vos) {
		int[] sum_insert_update = new int[2];
		List<RmLogVo> lInsert = new ArrayList<RmLogVo>();
		List<RmLogVo> lUpdate = new ArrayList<RmLogVo>();
		for (int i = 0; i < vos.length; i++) {
			if(vos[i].getId() != null && vos[i].getId().trim().length() > 0) {
				lUpdate.add(vos[i]);
			} else {
				lInsert.add(vos[i]);
			}
		}
		if(lInsert.size() > 0) {
			sum_insert_update[0] = insert(lInsert.toArray(new RmLogVo[0])).length;
		}
		if(lUpdate.size() > 0) {
			sum_insert_update[1] = update(lUpdate.toArray(new RmLogVo[0]));
		}
		return sum_insert_update;
	}

    /**
     * 查询总记录数，带查询条件
     * 
     * @param queryCondition 查询条件
     * @return 总记录数
     */
    public int getRecordCount(String queryCondition) {
		int sum = getDao().getRecordCount(queryCondition);
		//RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "查询到了总记录数,count=" + sum + ", queryCondition=" + queryCondition);
		return sum;
    }

    /**
     * 功能: 通过查询条件获得所有的VO对象列表，不带翻页查全部，只查询必要的字段
     *
     * @param queryCondition 查询条件, queryCondition等于null或""时查询全部
     * @param orderStr 排序字段
     * @return 查询到的VO列表
     */
    public List<RmLogVo> queryByCondition(String queryCondition, String orderStr) {
        return queryByCondition(queryCondition, orderStr, -1, -1);
    }

    /**
     * 功能: 通过查询条件获得所有的VO对象列表，带翻页，带排序字符，只查询必要的字段
     *
     * @param queryCondition 查询条件, queryCondition等于null或""时查询全部
     * @param orderStr 排序字符
     * @param startIndex 开始位置(第一条是1，第二条是2...)
     * @param size 查询多少条记录(size小于等于0时,忽略翻页查询全部)
     * @return 查询到的VO列表
     */
    public List<RmLogVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size) {
        return queryByCondition(queryCondition, orderStr, startIndex, size, false);
    }
    
    /**
     * 功能: 通过查询条件获得所有的VO对象列表，带翻页，带排序字符，根据selectAllClumn判断是否查询所有字段
     *
     * @param queryCondition 查询条件, queryCondition等于null或""时查询全部
     * @param orderStr 排序字符
     * @param startIndex 开始位置(第一条是1，第二条是2...)
     * @param size 查询多少条记录(size小于等于0时,忽略翻页查询全部)
     * @param selectAllClumn 是否查询所有列，即 SELECT * FROM ...(适用于导出)
     * @return 查询到的VO列表
     */
    public List<RmLogVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size, boolean selectAllClumn) {
        List<RmLogVo> lResult = getDao().queryByCondition(queryCondition, orderStr, startIndex, size, selectAllClumn);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "按条件查询了多条记录,recordCount=" + lResult.size() + ", startIndex=" + startIndex + ", size=" + size + ", queryCondition=" + queryCondition + ", orderStr=" + orderStr + ", selectAllClumn=" + selectAllClumn);
        return lResult;
    }
    
}