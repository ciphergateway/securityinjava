package org.quickbundle.project.common;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.quickbundle.util.RmSequenceMap;

/**
 * 一个有序的key值忽略大小写的vo，实现了Map接口，key值必须是String，value可以是Object
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class RmCommonVo implements Cloneable, Serializable, Map {
    /**
     * mapAttribute 表示: 存放属性数据的Map
     */
    private Map<String, Object> mapAttribute;
    private Map<String, String> mapKey;
    
    /**
     * 构造函数: 初始化属性，私有构造函数
     * 
     */
    public RmCommonVo() {
        this.mapAttribute = new RmSequenceMap<>();
        this.mapKey = new HashMap<>();
    }
    
    /**
     * 构造函数: 初始化属性，私有构造函数
     * 
     */
    public RmCommonVo(Map<String, Object> map) {
    	this();
    	for(Map.Entry<String, Object> en : map.entrySet()) {
    		put(en.getKey(), en.getValue());
    	}
    }

    /**
     * 功能: 为RmCommonVo增加属性，访问时有序(按属性加入的先后顺序)
     *
     * @param key 属性名称
     * @param value 属性值
     */
    public Object put(Object key_, Object value) {
        String key = key_ != null ? key_.toString() : null;
    	String keyUpper = key_ != null ? key_.toString().toUpperCase() : null;
    	if(!mapKey.containsKey(keyUpper)) {
    		mapKey.put(keyUpper, key);
    	}
    	if(value instanceof BigDecimal) {
    		value = value.toString();
    	}
    	return mapAttribute.put(keyUpper, value);
    }
    
    /**
     * 功能: 获得RmCommonVo的属性String，null自动过滤为""
     *
     * @param key 属性名称
     * @return
     */
    public String getString(String key) {
        return getString(key, -1);
    }
    
    /**
     * 功能: 获得RmCommonVo的属性String，null自动过滤为""
     *
     * @param key 属性名称
     * @param length 截取前几位
     * @return
     */
    public String getString(String key, int length) {
    	String keyUpper = key != null ? key.toUpperCase() : null;
    	if(mapAttribute.get(keyUpper) == null) {
    		return "";
    	} else if(length < 0){
    		return mapAttribute.get(keyUpper).toString();
    	} else {
    		String value = mapAttribute.get(keyUpper).toString();
    		if(value.length() <= length) {
    			return value;
    		} else {
    			return value.substring(0, length);
    		}
    	}
    }
    
    /**
     * 功能：获得RmCommonVo的属性值
     * 
     * @param key 属性名称
     * @return
     */
    public Object get(String key) {
    	String keyUpper = key != null ? key.toUpperCase() : null;
        return mapAttribute.get(keyUpper);
    }
    

	public Object get(Object key) {
		return mapAttribute.get(key);
	}
    
    /**
     * 功能: 删除属性
     *
     * @param attributeName 属性名称
     * @return
     */
    public Object remove(String key) {
    	String keyUpper = key != null ? key.toUpperCase() : null;
    	mapKey.remove(keyUpper);
    	Object result = mapAttribute.remove(keyUpper);
        return result;
    }
    
	/**
	 * override method 'equals'
	 * 
	 * @param _other 与本对象比较的其它对象
	 * @return boolean 两个对象的各个属性是否都相等
	 */
	public boolean equals(Object _other) {
		if (_other == null) {
			return false;
		}
		
		if (_other == this) {
			return true;
		}
		
		if (!(_other instanceof RmCommonVo)) {
			return false;
		}
		
		final RmCommonVo _cast = (RmCommonVo) _other;
		
		for(String key : mapAttribute.keySet()) {
			String keyUpper = key != null ? key.toUpperCase() : null;
		    Object thisValue = get(keyUpper);
		    if(thisValue == null && _cast.get(keyUpper) != null) {
		    	return false;
		    } else if(thisValue != null && !thisValue.equals(_cast.get(keyUpper))){
		    	return false;
		    }
		}
		return true;
	}

	/**
	 * override method 'hashCode'
	 * 
	 * @return int Hash码
	 */
	public int hashCode() {
		int _hashCode = 0;
		for(String key : mapAttribute.keySet()) {
		    String thisValue = this.getString(key);
			if (thisValue != null) {
				_hashCode = 29 * _hashCode + thisValue.hashCode();
			}
		}
		return _hashCode;
	}
	
	/**
	 * override method 'toString'
	 * 
	 * @return String 字符串表示
	 */
	public String toString() {
	    int index = 0; 
		StringBuilder rt = new StringBuilder();
		rt.append( super.toString() );
		for(String keyUpper : mapAttribute.keySet()) {
			String key = mapKey.get(keyUpper);
		    String thisValue = getString(key);
		    rt.append("\n");
		    rt.append(++index);
		    rt.append(": ");
		    rt.append(key);
		    rt.append("=");
		    rt.append(thisValue);
		}
		rt.append("\n");
		return rt.toString();
	}
	
	/**
	 * override method 'clone'
	 *
	 * @see java.lang.Object#clone()
	 * @return Object 克隆后对象
	 * @throws CloneNotSupportedException
	 */
	public Object clone() throws CloneNotSupportedException {
        super.clone();
        RmCommonVo vo = new RmCommonVo();
		for(String keyUpper : mapAttribute.keySet()) {
			String key = mapKey.get(keyUpper);
		    String thisValue = this.getString(key);
		    vo.put(key, thisValue);
		}
        return vo;
    }

	public void clear() {
		mapKey.clear();
		mapAttribute.clear();
	}

	public boolean containsKey(String key) {
		String keyUpper = key != null ? key.toUpperCase() : null;
		return mapAttribute.containsKey(keyUpper);
	}
	
	@Override
	public boolean containsKey(Object key) {
		return mapAttribute.containsKey(key);
	}


	public boolean containsValue(Object value) {
		return mapAttribute.containsValue(value);
	}

	public Set entrySet() {
		Set<Map.Entry<String, Object>> setUpper = mapAttribute.entrySet();
		Set<Map.Entry<String, Object>> result = new HashSet<>();
		for(Entry<String, Object> entry : setUpper) {
			result.add(new AbstractMap.SimpleEntry(mapKey.get(entry.getKey()), entry.getValue()));
		}
		return result;
	}

	public boolean isEmpty() {
		return mapAttribute.isEmpty();
	}

	public Set keySet() {
		Set<String> setUpper = mapAttribute.keySet();
		Set<String> result = new HashSet<>();
		for(String keyUpper : setUpper) {
			result.add(mapKey.get(keyUpper));
		}
		return result;
	}


	public void putAll(Map map) {
		for(String key : (Set<String>)map.keySet()) {
			put(key, map.get(key));
		}
	}

	public Object remove(Object key) {
    	mapKey.remove(key);
    	Object result = mapAttribute.remove(key);
        return result;
	}

	public int size() {
		return mapAttribute.size();
	}

	public Collection values() {
		return mapAttribute.values();
	}

}