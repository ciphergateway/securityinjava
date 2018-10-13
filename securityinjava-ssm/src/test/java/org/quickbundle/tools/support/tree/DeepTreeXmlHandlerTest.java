package org.quickbundle.tools.support.tree;

import static org.junit.Assert.*;

import org.junit.Test;
import org.quickbundle.project.DeepTreeVo;
import org.quickbundle.project.DeepTreeXmlHandler;

public class DeepTreeXmlHandlerTest {

	@Test
    public void build() {
        DeepTreeXmlHandler dt = new DeepTreeXmlHandler();
        dt.addTreeNode(new DeepTreeVo("1", "销售部", "1", "xmlData.xml"));
        dt.addTreeNode(new DeepTreeVo("2", "开发中心", "1", "xmlData.xml"));
        System.out.println(dt.getStringFromDocument());
        assertTrue(dt.getStringFromDocument().trim().endsWith("</Trees>"));
    }
}
