package com.hrt.frame.utility;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;


public class ConfigureCache {
	
	private static final String FILE_PATH = ConfigureCache.class.getClassLoader().getResource("configure.xml").getPath();
	
	private static final HashMap configureMap = new HashMap();
	
	/**
	 * 加载数据
	 */
	public static void loadData(String id,String value,String key){
		try {
			SAXReader reader=new SAXReader();
			File file=new File(FILE_PATH);
			
			Document document=reader.read(file);	//读取xml文件
			Element root=document.getRootElement();	//得到根节点
			Iterator iterator=root.elementIterator("second");
			while (iterator.hasNext()) {
				Element element=(Element) iterator.next();
				if(element.attributeValue("id").equals(id)){
					configureMap.put(key, element.selectSingleNode(value).getText());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * 获得系统标题
	 */
	public static String getTitle(){
		if(configureMap.get("title")==null){
			loadData("01", "title", "title");
		}
		return configureMap.get("title").toString();
	}
	
	/**
	 * 获得系统标题(英文)
	 */
	public static String getTitleEnglish(){
		if(configureMap.get("titleEnglish")==null){
			loadData("01", "titleEnglish", "titleEnglish");
		}
		return configureMap.get("titleEnglish").toString();
	}
}
