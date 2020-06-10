package com.hrt.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;




/**
 * 利用开源组件POI3.0.2动态导出EXCEL文档
 * 
 * @author leno
 * @version v1.0
 * @param <T>
 * 应用泛型，代表任意一个符合javabean风格的类
 * 注意这里为了简单起见，boolean型的属性xxx的get器方式为getXxx(),而不是isXxx()
 * byte[]表jpg格式的图片数据
 */
public class ExcelServiceImpl<T>{
	private static final Log log = LogFactory.getLog(ExcelServiceImpl.class);

	@SuppressWarnings("unchecked")
	public void exportExcel(List list,int columnSize,HttpServletResponse response,String sheetName,String fileName,String[] cellFormatType) throws IOException {
		HSSFWorkbook wb = new HSSFWorkbook();  
        HSSFSheet sheet = wb.createSheet();     //获取一个sheetnew String( sheetName.getBytes("GBK"),"ISO8859-1")
        wb.setSheetName(0, sheetName);
        HSSFHeader header = sheet.getHeader(); 
        header.setCenter("Center Header"); 
        
        HSSFRow row=null;
        for(int rowId=0;rowId<list.size();rowId++){
        	String[] st=(String[])list.get(rowId);
        	row = sheet.createRow((short)rowId);    //创建行
        	
      	  	for(int columnId=0; columnId < columnSize; columnId++){
      	  		HSSFCell cell = row.createCell((short)columnId);    	//创建格	  		
      	  		//cell.setEncoding("UTF-16");			//设置单元格的字符编码格式
      	  		if(cellFormatType==null || cellFormatType.length==0){	//如果cellFormatType数组不为空
      	  			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      	  			cell.setCellValue(st[columnId]); 
      	  		}else{
      	  			if(!cellFormatType[columnId].equals("t") && rowId!=0){
      	  				cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
      	  				//如果数值不为空
	  					if(st[columnId]!=null){
	  					 cell.setCellValue(Double.parseDouble(st[columnId])); 
	  					}else{
	  					 cell.setCellValue(st[columnId]); 
	  					}
      	  			}else{
      	  				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      	  				cell.setCellValue(st[columnId]); 
      	  			}
      	  		}
      	  	}
        }
        
        /**
         * 弹框，让用户选择保存路径
         */
        response.reset();  
		response.setHeader( "Content-Disposition", "attachment;filename="  + new String( fileName.getBytes("GBK"), "ISO8859-1"));
        wb.write(response.getOutputStream());
        response.getOutputStream().close(); 
        
	}

	/**
	 * 删除上传的文件
	 * @param folderPath 文件目录
	 * @param path 文件路径
	 */
	public static void deleteUploadFile(String folderPath,String path) {
		File file = new File(folderPath);
		String[] tempList = file.list();
		File temp = null;
		if (tempList != null) {
			for (int i = 0; i < tempList.length; i++) {
				if (path.endsWith(File.separator)) {
					temp = new File(folderPath + tempList[i]);
				} else {
					temp = new File(folderPath + File.separator + tempList[i]);
				}
				if (temp.exists() && temp.isFile()) {
					boolean flag = temp.delete(); // 删除上传到服务器的文件
					log.info("删除文件："+folderPath+path+" 成功:"+flag);
					int j = i + 1;
				} else {
				}
			}
		}
	}

	/**
	 * 下载文件
	 * @param resp
	 * @param name         文件真实名字
	 * @param downloadName 文件下载时名字
	 */
	public static void download(HttpServletResponse resp, String name, String downloadName) throws ClassNotFoundException {
		String fileName = null;
		try {
			fileName = new String(downloadName.getBytes("GBK"), "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String path = Class.forName("word.model").getResource("").getPath();
		File file = new File(path+File.separator+name);
		resp.reset();
		resp.setContentType("application/octet-stream");
		resp.setCharacterEncoding("utf-8");
		resp.setContentLength((int) file.length());
		resp.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		byte[] buff = new byte[1024];
		BufferedInputStream bis = null;
		OutputStream os = null;
		try {
			os = resp.getOutputStream();
			bis = new BufferedInputStream(new FileInputStream(file));
			int i = 0;
			while ((i = bis.read(buff)) != -1) {
				os.write(buff, 0, i);
				os.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws ClassNotFoundException {
		String path = Class.forName("word.model").getResource("").getPath();
		System.out.println(path);
	}

}
