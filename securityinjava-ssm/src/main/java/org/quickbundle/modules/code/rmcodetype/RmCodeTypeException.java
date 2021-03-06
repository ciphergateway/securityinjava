//代码生成时,文件路径: E:/platform/myProject/qbrm/code/securityinjava-ssm/src.open/org/quickbundle/modules/code/rmcodetype/util/exception/RmCodeTypeException.java
//代码生成时,系统时间: 2010-04-08 21:15:45.906
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> securityinjava-ssm
 * 
 * 文件名称: org.quickbundle.modules.code.rmcodetype.util.exception --> RmCodeTypeException.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-04-08 21:15:45.906 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.modules.code.rmcodetype;

import org.quickbundle.base.RmRuntimeException;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public class RmCodeTypeException extends RmRuntimeException {

    /**
     * 构造函数:
     * 
     * @param msg
     */
    public RmCodeTypeException(String msg) {
        super(msg);
    }

    /**
     * 构造函数:
     * 
     * @param msg
     * @param ex
     */
    public RmCodeTypeException(String msg, Throwable ex) {
        super(msg, ex);
    }

    /**
     * 构造函数:
     * 
     * @param msg
     * @param ex
     * @param returnObj
     */
    public RmCodeTypeException(String msg, Throwable ex, Object returnObj) {
        super(msg, ex, returnObj);
    }
}
