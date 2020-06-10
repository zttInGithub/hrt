package com.hrt.biz.query;

import java.io.File;
import java.util.HashMap;

import com.hrt.util.ParamPropUtils;


/**
 * 查询页面 配置信息
 * @author kevin
 *
 */
public class ListPageConfig {
	private static boolean isDebug=true;
	private static HashMap<String,ListPage> PAGES=new HashMap<String,ListPage>();
	/**
	 * 获取ListPage对象
	 * @param listPageCode 区分大小写
	 * @return
	 */
	public static ListPage  getListPageByCode(String listPageCode){
		ListPage listPage=null;
		if(isDebug){
			//实时的去读取配置文件
			listPage=parseListPageConfig(listPageCode);
		}else{
			//生产模式，在系统启动时将所有的listpage配置读入PAGES对象中
			listPage=PAGES.get(listPageCode);
		}
		return listPage;
	}
	private static ListPage  parseListPageConfig(String listPageCode){
		
		//String appConfigPath = "D:/Program Files/Tomcat6.0/webapps/HrtApp";
		
		String appConfigPath = ParamPropUtils.props.getProperty("QUERYPATH");
    	System.out.println("APP_CONFIG_PATH:"+appConfigPath);
       // String xmlFile = appConfigPath+File.separator+"query"+File.separator+listPageCode+".xml";
    	String xmlFile = appConfigPath+File.separator+File.separator+listPageCode+".xml";
        File ff = new File(xmlFile);
        
        System.out.println("APP_CONFIG_PATH:"+xmlFile);
        ListPageParser obj_Parser=new  ListPageParser(ff);          
		return obj_Parser.parse();
	}
	
	
}