package org.quickbundle.tools.support.helper;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.quickbundle.config.RmBaseConfig;
import org.quickbundle.tools.helper.RmXmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RmXmlHelperTest {
	@Rule
	public TestRule watcher = new TestWatcher() {
	   protected void starting(Description description) {
	      System.out.println("--------" + description.getDisplayName() + "--------");
	   }
	};
	
	Document getDoc() throws Exception {
		return RmXmlHelper.getDocumentFromString(getXmlStr());
	}
	
	String getXmlStr() throws Exception {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" + 
				"<rss version=\"2.0\">\n" + 
				"<channel>\n" + 
				"<title>a</title>\n" + 
				"<link>b</link>\n" + 
				"<description>c</description>\n" + 
				"</channel>\n" + 
				"</rss>";
	}

	@Test
    public void getStringFromDocument() throws Exception {
    	String str = RmXmlHelper.getStringFromDocument(getDoc());
    	System.out.println(str);
    	assertNotNull(str);
    }
	
	@Test
    public void getStringFromNode() throws Exception {
    	String str = RmXmlHelper.getStringFromNode(getDoc().getDocumentElement().getChildNodes().item(1));
    	System.out.println(str);
    	assertNotNull(str);
	}

	@Test
	public void getDocumentFromString() throws Exception {
    	Document doc = RmXmlHelper.getDocumentFromString(getXmlStr());
    	assertTrue("rss".equals(doc.getDocumentElement().getNodeName()));
    }
    
	@Test
    public void getRss20Document() throws Exception {
		Document doc = RmXmlHelper.getRss20Document("a", "b", "c");
		Node descriptionNode = doc.getDocumentElement().getFirstChild().getFirstChild();
		assertTrue("title".equals(descriptionNode.getNodeName()));
		assertTrue("a".equals(descriptionNode.getTextContent()));
	}
    
	@Test
    public void getRss20Item() throws Exception {
		Document doc = RmXmlHelper.getRss20Item("a", "b", "c", "123");
		Element pubDateEle = (Element)doc.getDocumentElement().getChildNodes().item(3);
		assertTrue("pubDate".equals(pubDateEle.getNodeName()));
		assertTrue("123".equals(pubDateEle.getTextContent()));
    }

	@Test
    public void parse() throws Exception {
    	String tmpXml = new File(System.getProperty("java.io.tmpdir") + File.separator + "test.parse.xml").toString();
    	RmXmlHelper.saveXmlToPath(getDoc(), tmpXml);
    	Document doc = RmXmlHelper.parse(tmpXml);
    	NodeList nodes = doc.getDocumentElement().getChildNodes();
    	Element channelEle = null;
    	for(int i=0; i<nodes.getLength(); i++) {
    		if(nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
    			channelEle = (Element)nodes.item(i);
    			break;
    		}
    	}
    	assertTrue("channel".equals(channelEle.getNodeName()));
    	new File(tmpXml).delete();
    	assertTrue(!new File(tmpXml).exists());
    }
	
	String getIdDocStr() {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
				"<RmIdFactory \n" + 
				"    xmlns=\"http://www.ciphergateway.com/schema\" \n" + 
				"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" + 
				"    xsi:schemaLocation=\"http://www.ciphergateway.com/schema http://www.ciphergateway.com/schema/id.xsd\">\n" + 
				"    <table table_code=\"2028\" table_name=\"RM_PARTY_ROLE\" id_name=\"ID\" wrapper_class=\"org.quickbundle.base.beans.idwrapper.NumberIncrementWrapper\"/>\n" + 
				"    <table table_code=\"2024\" table_name=\"RM_AUTHORIZE\" id_name=\"ID\"/>\n" + 
				"    <table table_code=\"2018\" table_name=\"RM_AUTHORIZE_RESOURCE\" id_name=\"ID\"/>\n" + 
				"    <table table_code=\"2017\" table_name=\"RM_AUTHORIZE_RESOURCE_RECORD\" id_name=\"ID\"/>\n" + 
				"    <table table_code=\"2014\" table_name=\"RM_FUNCTION_NODE\" id_name=\"ID\"/>\n" + 
				"    <table table_code=\"2011\" table_name=\"RM_PARTY\" id_name=\"ID\"/>\n" + 
				"    <table table_code=\"2010\" table_name=\"RM_PARTY_RELATION\" id_name=\"ID\"/>\n" + 
				"</RmIdFactory>";
	}
	
	@Test
    public void parseWithNS() throws Exception {
    	DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    	Document doc = RmXmlHelper.getDocumentFromString(getIdDocStr());
    	XPathExpression expr = XPathFactory.newInstance().newXPath().compile("/RmIdFactory/table");
		NodeList lTreeNode = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
		System.out.println(lTreeNode.getLength());
    }
    
	@Test
    public void saveXmlToPath() throws Exception {
    	File tmpXml = new File(System.getProperty("java.io.tmpdir") + File.separator + "test.saveXmlToPath.xml");
    	RmXmlHelper.saveXmlToPath(getDoc(), tmpXml.toString());
    	assertTrue(System.currentTimeMillis() - tmpXml.lastModified() < 5000);
    	tmpXml.delete();
    	assertTrue(!tmpXml.exists());
    }
	
	@Test
    public void deepCopyElement() throws Exception {
    	Document doc = RmXmlHelper.getRss20Document("a", "b", "c");
    	Document doc2 = RmXmlHelper.getRss20Document("a", "b", "c");
    	((Element)doc2.getDocumentElement().getFirstChild().getFirstChild()).setTextContent("aaa");
    	RmXmlHelper.deepCopyElement((Element)doc.getDocumentElement().getFirstChild(), (Element)doc2.getDocumentElement().getFirstChild());
    	System.out.println(RmXmlHelper.getStringFromDocument(doc2));
	}
    
}
