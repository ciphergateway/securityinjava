/*
 * 系统名称: QuickBundle --> rmdemo
 * 
 * 文件名称: org.quickbundle.tools.support.tree --> RmXmlHelper.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2005-11-19 19:16:49 创建1.0.0版 (baixiaoyong)
 *  
 */
package org.quickbundle.tools.support.tree;

import java.util.Iterator;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.quickbundle.tools.helper.RmXmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class DeepTreeXmlHandler {

    /**
     * document 表示: xml的母体
     */
    private Document document;
    
    /**
     * 功能: 获得xml的String表示
     *
     * @return
     */
    public String getStringFromDocument() {
        selfCheckHasChild4HardTree();
        try {
			return RmXmlHelper.getStringFromDocument(document);
		} catch (TransformerException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
    }

    /**
     * 构造函数: 初始化document,并加入根元素"Trees"
     *  
     */
    public DeepTreeXmlHandler() {
    	try {
			document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
    	Element root = document.createElement("Trees");
    	document.appendChild(root);
    }

    /**
     * 功能: 加DeepTreeVo节点到根元素
     *
     * @param dtv 要加入的树节点
     * @return
     */
    public Element addTreeNode(DeepTreeVo dtv) {
        Element root = document.getDocumentElement();
        
		Element treeNodeVo = document.createElement("TreeNode");
		root.appendChild(treeNodeVo);
        for(Iterator itMapAttribute = dtv.getAttributeMapIterator(); itMapAttribute.hasNext(); ) {
            String tempKey = itMapAttribute.next().toString();
            String tempValue = dtv.getAttribute(tempKey);
            treeNodeVo.setAttribute(tempKey, tempValue);
        }
        return treeNodeVo;
    }
    
    /**
     * 功能: 加DeepTreeVo节点到id为id值的元素,如果不存在返回空
     *
     * @param id 父节点id
     * @param dtv 要加入的树节点
     * @return 成功则返回这个树节点的Element，失败则返回null
     */
    public Element addTreeNode(String parentId, DeepTreeVo dtv) {
        if(parentId == null) {
            return null;
        } 
		try {
			XPathExpression expr = XPathFactory.newInstance().newXPath().compile("//TreeNode[@id='" + parentId + "']");
			Element thisParentTreeNode = (Element)expr.evaluate(document, XPathConstants.NODE);
			if(thisParentTreeNode == null) {
				return null;
			}
			Element thisTreeNode = document.createElement("TreeNode");
			thisParentTreeNode.appendChild(thisTreeNode);
			for(Iterator itMapAttribute = dtv.getAttributeMapIterator(); itMapAttribute.hasNext(); ) {
				String tempKey = itMapAttribute.next().toString();
				String tempValue = dtv.getAttribute(tempKey);
				thisTreeNode.setAttribute(tempKey, tempValue);
			}
			return thisTreeNode;
		} catch (XPathExpressionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

    }
    
    /**
     * 功能: 加DeepTreeVo节点到id为id值的元素,如果parentId为null或parentId为""则加到id为defaultRootId的元素
     *
     * @param id 父节点id
     * @param dtv 要加入的树节点
     * @param defaultRootId
     * @return 成功则返回这个树节点的Element，失败则返回null
     */
    public Element addTreeNode(String parentId, DeepTreeVo dtv, String defaultRootId) {
        Element rtElement = null;
        if(parentId != null && parentId.length() > 0) {
            rtElement = addTreeNode(parentId, dtv);
        } 
        if(rtElement == null) {
            rtElement = addTreeNode(defaultRootId, dtv);
        }
        return rtElement;
    }
    
    /**
     * 功能: 自我修复不良数据，把没有子节点，并且xmlSource等于tempTreeNode""，并且hasChild等于"1"的节点的hasChild="0"
     *
     * @return 返回修复的节点数
     */
    public int selfCheckHasChild4HardTree() {

		try {
			XPathExpression expr = XPathFactory.newInstance().newXPath().compile("//TreeNode[not(./TreeNode) and @xmlSource='' and @hasChild='1']");
			NodeList lTreeNode = (NodeList)expr.evaluate(document, XPathConstants.NODESET);
	        for(int i=0; i<lTreeNode.getLength(); i++) {
	            Node tempTreeNode = lTreeNode.item(0);
	            XPathExpression expr2 = XPathFactory.newInstance().newXPath().compile("@hasChild");
	            
	            Node hasChild = (Node)expr2.evaluate(tempTreeNode, XPathConstants.NODE);
	            hasChild.setTextContent("0");
	        }
	        return lTreeNode.getLength();
		} catch (XPathExpressionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
    }

}