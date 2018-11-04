package org.quickbundle.project.common;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.quickbundle.tools.helper.RmPopulateHelper;

public class RmCommonVoTest {
    @Test
    public void populate() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("a", "1");
        map.put("b", "2");
        
        RmCommonVo vo = new RmCommonVo();
        RmPopulateHelper.populate(vo, map);
        System.out.println(vo);
    }
}
