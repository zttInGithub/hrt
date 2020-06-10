package com.hrt.biz.query;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ListPageParser {
	//private File configFile;
	private Document doc ;
	public ListPageParser(File configFile){
		DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
        /* dFactory.setValidating(true);
         dFactory.setNamespaceAware(true);*/
		DocumentBuilder builder;
		try {
			builder = dFactory.newDocumentBuilder();
		 
			doc = builder.parse(configFile);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	public ListPage parse(){
		
       // builder = null;
		ListPage listpage=new ListPage();
        try {
			/*for(int j=0;j<list.getLength();j++){
				System.out.println(list.item(j).getNodeName()+"===="+list.item(j).getTextContent()+"==="+list.item(j).getAttributes().getNamedItem("ID").getNodeValue());
			}*/
		    
        	
		    listpage.setTitle(getContentTextByTag(doc.getFirstChild(), "TITLE"));
		    listpage.setFilterPageUrl(getContentTextByTag(doc.getFirstChild(), "FILTERPAGE"));
		    listpage.setTablename(getContentTextByTag(doc.getFirstChild(), "TABLENAME")); 
		    listpage.setFilter(getContentTextByTag(doc.getFirstChild(),"FILTER"));
		    listpage.setPageSize(getContentIntByTag(doc.getFirstChild(),"PAGESIZE"));
		    listpage.setOrderBy(getContentTextByTag(doc.getFirstChild(),"ORDERBY"));
		    parseOperations(listpage);
		    parseColumns(listpage);
//		    parseOrder(listpage);
		}catch (Exception e) {
			e.printStackTrace();
		}
        return listpage;
	}
//	private void parseOrder(ListPage listpage){
//		try {
//			 Node termNode=XPathAPI.selectSingleNode(doc.getFirstChild(), "ORDERBY");
//			 NodeList list=XPathAPI.selectNodeList(termNode, "ORDER");
//			 for(int j=0;j<list.getLength();j++){
//				//System.out.println(list.item(j).getTextContent()+"==="+list.item(j).getAttributes().getNamedItem("ID").getNodeValue());
//				Node orderNode=list.item(j);
//				ListPageColumn column=parseColumn(orderNode);
//				listpage.addColumn(column);
//			 }
//		} catch (Exception e) {
//			System.err.println(":::::::::::::::::::::::解析ORDERS出错");
//		}
//	}
	private void parseColumns(ListPage listpage){
		try {
			 Node termNode=XPathAPI.selectSingleNode(doc.getFirstChild(), "COLUMNS");
			 NodeList list=XPathAPI.selectNodeList(termNode, "COLUMN");
			 for(int j=0;j<list.getLength();j++){
//				System.out.println(list.item(j).getTextContent()+"==="+list.item(j).getAttributes().getNamedItem("ID").getNodeValue());
				Node columnNode=list.item(j);
				
				ListPageColumn column=parseColumn(columnNode);
				listpage.addColumn(column);
			 }
		} catch (Exception e) {
			System.err.println(":::::::::::::::::::::::解析COLUMNS出错");
		}
	}
	private ListPageColumn parseColumn(Node node){
		ListPageColumn column=new ListPageColumn();
		column.setName(getAttrText(node,"ID"));
		column.setTitle(node.getTextContent());
		int width=0;
		try{
			width=Integer.parseInt(getAttrText(node,"WIDTH"));
		}catch(Exception e){
			
		}
		column.setWidth(width);
		column.setKey("true".equals(getAttrText(node,"ISKEY").toLowerCase().trim()));
		column.setNum("true".equals(getAttrText(node,"ISNUM").toLowerCase().trim()));
		column.setHidden("true".equals(getAttrText(node,"HIDDEN").toLowerCase().trim()));
		//column.setDefaultShow("true".equals(getAttrText(node,"DEFAULTSHOW").toLowerCase().trim()));
		column.setSearchable("true".equals(getAttrText(node,"SEARCHABLE").toLowerCase().trim()));
		column.setAlign(getAttrText(node,"ALIGN").trim());
		column.setFormatter(getAttrText(node,"FORMATTER").trim());
		//20100203 为动态列而增加
		//column.setDynamic("true".equals(getAttrText(node,"ISDYNAMIC").toLowerCase().trim()));
		//column.setIdField(getAttrText(node,"IDFEILD").trim());
		//column.setTitleField(getAttrText(node,"TITLEFEILD").trim());
		//column.setValueField(getAttrText(node,"VALUEFEILD").trim());
		//column.setGroupByField(getAttrText(node,"GROUPBYFEILD").trim());	
		//column.setGroupByFieldTitle(getAttrText(node,"GROUPBYFEILDTITLE").trim());
		//column.setValuemap(getAttrText(node,"VALUEMAP").trim());
		return column;
	}
	private void parseOperations(ListPage listpage){
		try {
			Node operationsNode;
			operationsNode = XPathAPI.selectSingleNode(doc.getFirstChild(), "OPERATIONS");
		    NodeList operationList=XPathAPI.selectNodeList(operationsNode, "OPERATION");
			for(int j=0;j<operationList.getLength();j++){
	//			System.out.println(operationList.item(j).getTextContent()+"==="+operationList.item(j).getAttributes().getNamedItem("ID").getNodeValue());
				Node operationNode=operationList.item(j);
				ListPageOperation column=parseOperation(operationNode);
				listpage.addOperation(column);
			}
		} catch (Exception e) {
			System.err.println(":::::::::::::::::::::::解析OPERATIONS出错");
		}
	}
	private ListPageOperation parseOperation(Node node){
		ListPageOperation operation=new ListPageOperation();
		operation.setTitle(getContentTextByTag(node, "TITLE")); 
		operation.setAction(getContentTextByTag(node, "ACTION")); 
		operation.setContent(getContentTextByTag(node, "CONTENT")); 
		operation.setWidth(getContentTextByTag(node, "WIDTH")); 
		return operation;
	}
	
	private String getAttrText(Node curNode,String attrName){
		String text="";
		try {
			text=curNode.getAttributes().getNamedItem(attrName).getNodeValue();
			System.out.println("text"+text);
		} catch (Exception e) {
			System.err.println("获取标签aa"+curNode.getNodeName()+"属性出错::::::::::::::::::"+attrName);
		}
		return text;
	}
	private int  getContentIntByTag(Node curNode,String tagName){
		int r=0;
		try {
			r=Integer.parseInt(getContentTextByTag(curNode,tagName));
		} catch (Exception e) {
			System.err.println("获取标签内容integer出错::::::::::::::::::"+tagName);
		}
		return r;
	}
	private String getContentTextByTag(Node curNode,String tagName){
		String text="";
		try {
			text=XPathAPI.selectSingleNode(curNode, tagName).getTextContent();
//			System.out.println(text);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("获取标签内容出错::::::::::::::::::"+tagName);
		}
		return text;
	}
	
	public static void main(String args[]) throws Exception{
		try{
		String filename="D:\\920010.xml";
		File f=new File(filename);
		DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dFactory.newDocumentBuilder();
		Document doc = builder.parse(f);

		Node termNode=XPathAPI.selectSingleNode(doc.getFirstChild(), "COLUMNS");
		NodeList list=XPathAPI.selectNodeList(termNode, "COLUMN");
		/*for(int j=0;j<list.getLength();j++){
			System.out.println(list.item(j).getNodeName()+"===="+list.item(j).getTextContent()+"==="+list.item(j).getAttributes().getNamedItem("ID").getNodeValue());
		}*/
		
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			System.exit(0);
		}
//		NodeList nodeList=doc.getChildNodes().item(0).getChildNodes();
//	    for(int j=0; j<nodeList.getLength(); j++){
//	    	Node termNode = nodeList.item(j);
//	    	System.out.println(termNode.getNodeName()+"======="+termNode.getNodeValue());
////	    	if(termNode.getNodeName().equals("TITLE")){
////	    		
////	    	}else if(termNode.getNodeName().equals("TABLENAME")){
////	    		
////	    	}else if(termNode.getNodeName().equals("DETAILURL")){
////	    		
////	    	}
////            NamedNodeMap termAtts = termNode.getAttributes();
////            String dd = termAtts.getNamedItem("dd").getNodeValue();
//	    }
	}
	

}
