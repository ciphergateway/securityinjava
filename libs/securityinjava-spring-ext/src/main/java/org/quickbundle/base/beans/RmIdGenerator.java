package org.quickbundle.base.beans;

import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.quickbundle.base.RmRuntimeException;
import org.quickbundle.base.beans.idwrapper.MaxInDbWrapper;
import org.quickbundle.base.beans.idwrapper.ShardingInCacheWrapper;
import org.quickbundle.config.RmBaseConfig;
import org.quickbundle.itf.IRmIdGenerator;
import org.quickbundle.itf.IRmIdWrapper;
import org.quickbundle.tools.context.RmPathHelper;
import org.quickbundle.tools.helper.RmLogHelper;
import org.quickbundle.tools.helper.RmXmlHelper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class RmIdGenerator implements IRmIdGenerator {

	private Map<String, TableIdRuleVo> rule = null;
	
	private Map<String, IRmIdWrapper> mapWrapper = null;
	
	
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void init() {
        try {
            long startTime = System.currentTimeMillis();
            loadRule();
            initMapWrapper();
            doInitId();
            
            RmLogHelper.getLogger(this.getClass()).info("init " + mapWrapper.size() + " tables, cost " + (System.currentTimeMillis()-startTime) + " milliseconds!");
        } catch (Exception e) {
            e.printStackTrace();
            RmLogHelper.getLogger(RmIdFactory.class).error("id.xml init failed: " + e.toString());
        }
    }
    
	private void loadRule() {
		rule = new HashMap<String, TableIdRuleVo>();
        //id.xml命名空间
        Map<String, String> defaultNameSpaceMap = new HashMap<String, String>();  
        defaultNameSpaceMap.put("q", "http://www.ciphergateway.com/schema");
        //读入id.xml
        Document docIdxml = null;;
		try {
			docIdxml = RmXmlHelper.parse(RmPathHelper.getWebInfDir() + "/config/rm/id.xml");
			XPath xpath = XPathFactory.newInstance().newXPath();
			NodeList lTable = (NodeList) xpath.compile("/RmIdFactory/table").evaluate(docIdxml, XPathConstants.NODESET);
			for(int i=0; i<lTable.getLength(); i++) {
				Element table =(Element)lTable.item(i);
				
				String tableName = ((String)xpath.compile("@table_name").evaluate(table, XPathConstants.STRING)).toUpperCase();
				TableIdRuleVo ruleVo = new TableIdRuleVo();
				ruleVo.setTableCode((String)xpath.compile("@table_code").evaluate(table, XPathConstants.STRING));
				ruleVo.setTableName(tableName);
				ruleVo.setIdName((String)xpath.compile("@id_name").evaluate(table, XPathConstants.STRING));
				ruleVo.setMultiDb("true".equals((String)xpath.compile("@multi_db").evaluate(table, XPathConstants.STRING))
						|| "1".equals((String)xpath.compile("@multi_db").evaluate(table, XPathConstants.STRING)));
				ruleVo.setWrapperClass((String)xpath.compile("@wrapper_class").evaluate(table, XPathConstants.STRING));
				ruleVo.setWrapperClassFormat((String)xpath.compile("@wrapper_class_format").evaluate(table, XPathConstants.STRING));
				rule.put(tableName, ruleVo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	private void initMapWrapper() {
		mapWrapper = new HashMap<String, IRmIdWrapper>();
        for(Map.Entry<String, TableIdRuleVo> en : rule.entrySet()) {
        	String tableName = en.getKey();
        	TableIdRuleVo ruleVo = en.getValue();
        	//如果指定了wrapper_class
        	String wrapperClass = ruleVo.getWrapperClass();
        	if(wrapperClass == null || wrapperClass.length() == 0) {
        		mapWrapper.put(tableName, RmBaseConfig.getSingleton().isSystemDebugMode() ? new MaxInDbWrapper(ruleVo) : new ShardingInCacheWrapper(ruleVo));
        	} else {
        		try {
					Class clazz = this.getClass().getClassLoader().loadClass(wrapperClass);
					IRmIdWrapper customWrapper = (IRmIdWrapper)clazz.getConstructor(TableIdRuleVo.class).newInstance(ruleVo);
					mapWrapper.put(tableName, customWrapper);
				} catch (Throwable e) {
					e.printStackTrace();
					RmLogHelper.getLogger(RmIdFactory.class).error("id.xml init " + tableName + ": " + e.toString());
				}
        	}
        	
        }
	}
    
    private void doInitId() {
        for (Map.Entry<String, IRmIdWrapper> en: mapWrapper.entrySet()) {
            IRmIdWrapper wrapper = en.getValue();
            wrapper.init();
        }
    }

    public String[] requestIdInner(String tableName, int length) {
    	IRmIdWrapper wrapper = mapWrapper.get(tableName);
    	if(wrapper == null) {
    	    throw new RmRuntimeException("RmIdFactory have not table \"" + tableName + "\", please checkout id.xml");
    	}
    	return wrapper.nextValue(length);
    }
}
