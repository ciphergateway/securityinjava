package org.quickbundle.base.service;

import org.quickbundle.base.beans.RmBeanFactory;
import org.quickbundle.project.common.IRmCommonService;

public class RmService {
    /**
     * 获得通用的IRmCommonService
     * @return
     */
    protected IRmCommonService getCommonServiceInstance() {
        return (IRmCommonService) RmBeanFactory.getBean("IRmCommonService");
    }
}
