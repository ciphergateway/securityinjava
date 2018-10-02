package org.quickbundle.tools.helper;
/*
 * 系统名称:Quickbundle.org --> ranminXmlGenerateCode
 * 
 * 文件名称: ranminXmlGenerateCode.test --> TestScroll.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2005-12-4 2:46:28 创建1.0.0版 (baixiaoyong)
 *  
 */

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.quickbundle.config.RmBaseConfig;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class RmXmlHelper {

    /**
     * 功能: 从Document对象中获取String
     * 
     * @param document
     * @return
     * @throws TransformerException 
     */
    public static String getStringFromDocument(Document document) throws TransformerException {
    	return getStringFromNode(document);
    }
    
    /**
     * 功能: 从Node对象中获取String
     * 
     * @param node
     * @return
     * @throws TransformerException
     */
    public static String getStringFromNode(Node node) throws TransformerException {
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer();
        transformer.setOutputProperty("encoding", RmBaseConfig.getSingleton().getDefaultEncode());
        transformer.setOutputProperty("indent", "yes");
        Writer out = new StringWriter();
        transformer.transform(new DOMSource(node), new StreamResult(out));
        return out.toString();
    }

    /**
     * 转化xml字符串为Document对象
     * 
     * @param xmlStr
     * @return
     * @throws ParserConfigurationException 
     * @throws IOException 
     * @throws SAXException 
     * @throws UnsupportedEncodingException 
     */
    public static Document getDocumentFromString(String xmlStr) throws ParserConfigurationException, UnsupportedEncodingException, SAXException, IOException {
        	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        	DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
        	Document doc = documentBuilder.parse(new ByteArrayInputStream(xmlStr.getBytes(RmBaseConfig.getSingleton().getDefaultEncode())));
        	return doc;
    }
    
    /**
     * 获得一个Rss2.0格式的Document对象 --rss
     * @param channelTitle
     * @param channelLink
     * @param channelDescription
     * @return
     * @throws ParserConfigurationException 
     */
    public static Document getRss20Document(String channelTitle, String channelLink, String channelDescription) throws ParserConfigurationException {
    	Document doc = null;
		doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
    	Element rssEle = doc.createElement("rss");
    	rssEle.setAttribute("version", "2.0");
    	doc.appendChild(rssEle);
    	
    	Element channelEle = doc.createElement("channel");
    	rssEle.appendChild(channelEle);
    	
    	Element titleEle = doc.createElement("title");
    	titleEle.setTextContent(channelTitle);
    	channelEle.appendChild(titleEle);
    	
    	Element linkEle = doc.createElement("link");
    	linkEle.setTextContent(channelLink);
    	channelEle.appendChild(linkEle);
    	
    	Element descriptionEle = doc.createElement("description");
    	descriptionEle.setTextContent(channelDescription);
    	channelEle.appendChild(descriptionEle);
    	
    	return doc;
    }
    
    /**
     * 获得一个Rss2.0格式的Document对象--item
     * @param itemTitle
     * @param itemLink
     * @param itemDescription
     * @param pubDate
     * @return
     * @throws ParserConfigurationException 
     */
    public static Document getRss20Item(String itemTitle, String itemLink, String itemDescription, String pubDate) throws ParserConfigurationException {
    	Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
    	Element itemEle = doc.createElement("item");
    	doc.appendChild(itemEle);

		Element titleEle = doc.createElement("title");
		titleEle.setTextContent(itemTitle);
		itemEle.appendChild(titleEle);

		Element linkEle = doc.createElement("link");
		linkEle.setTextContent(itemLink);
		itemEle.appendChild(linkEle);

		Element descriptionEle = doc.createElement("description");
		descriptionEle.setTextContent(itemDescription);
		itemEle.appendChild(descriptionEle);

		Element pubDateEle = doc.createElement("pubDate");
		pubDateEle.setTextContent(pubDate);
		itemEle.appendChild(pubDateEle);
    	
    	return doc;
    }

    /**
     * 功能: 从xmlPath的资源转化成Document对象
     * 
     * @param ruleXml
     * @return
     * @throws ParserConfigurationException 
     * @throws IOException 
     * @throws SAXException 
     * @throws DocumentException
     */
    public static Document parse(String xmlPath) throws ParserConfigurationException, SAXException, IOException {
        if (xmlPath == null || xmlPath.length() == 0) {
            throw new NullPointerException("xml路径是空!");
        }
    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    	DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
    	Document doc = documentBuilder.parse(formatToUrl(xmlPath));
    	return doc;
    }
    
    /**
     * 功能: 把xml保存到指定的路径文件名
     * 
     * @param document
     * @param targetFile
     * @throws IOException
     * @throws TransformerException 
     */
    public static void saveXmlToPath(Document document, String targetFile) throws IOException, TransformerException {
        targetFile = formatToFile(targetFile);
        RmFileHelper.initParentDir(targetFile);
        
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer();
        transformer.setOutputProperty("encoding", RmBaseConfig.getSingleton().getDefaultEncode());
        transformer.setOutputProperty("indent", "yes");
        Writer out = new FileWriter(targetFile);
        try {
        	transformer.transform(new DOMSource(document), new StreamResult(out));
		}  finally {
			out.close();
		}
    }

    /**
     * 功能: 将路径格式化为url --> file:///c:/rmdemo.log
     * 
     * @param filePath
     * @return
     */
    public static String formatToUrl(String filePath) {
    	return RmFileHelper.formatToUrl(filePath);
    }
    
    /**
     * 功能: 将路径格式化为url --> c:/rmdemo.log
     * 
     * @param filePath
     * @return
     */
    public static String formatToUrlNoPrefix(String filePath) {
    	return RmFileHelper.formatToUrlNoPrefix(filePath);
    }
    
    /**
     * 功能: 将路径格式化为File形式 --> c:\rmdemo.log
     * 
     * @param filePath
     * @param osSeparatorStr 指定当前操作系统分隔符
     * @return
     */
    public static String formatToFile(String filePath, String osSeparatorStr) {
    	return RmFileHelper.formatToFile(filePath, osSeparatorStr);
    }
    
    /**
     * 功能: 将路径格式化为File形式 --> c:\rmdemo.log
     *
     * @param filePString
     * @return
     */
    public static String formatToFile(String filePString) {
        return formatToFile(filePString, File.separator);
    }
	
	/**
	 * 复制from下的所有节点(包括Attribute, Element, Text)到to
	 * 
	 * @param from
	 * @param to
	 * @throws XPathExpressionException 
	 */
	public static void deepCopyElement(Element from, Element to) throws XPathExpressionException {
		if(from == null || to == null) {
			return;
		}
		
		XPathExpression expr = XPathFactory.newInstance().newXPath().compile("@*|node()");
		NodeList nodes = (NodeList)expr.evaluate(from, XPathConstants.NODESET);
		for(int i=0; i<nodes.getLength(); i++) {
			Node fromNode = nodes.item(i);
			if(fromNode instanceof Attr) {
				Attr attr = (Attr)fromNode;
				to.setAttribute(attr.getName(), attr.getValue());
			} else if(fromNode instanceof Element) {
				Node newNode = to.getOwnerDocument().importNode((Element)fromNode, true);
				to.appendChild(newNode);
			} else if(fromNode instanceof Text) {
				to.setTextContent(fromNode.getNodeValue());
			}
		}
	}
 
}