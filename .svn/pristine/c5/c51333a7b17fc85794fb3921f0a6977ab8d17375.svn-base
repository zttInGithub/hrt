package com.hrt.frame.action.sysadmin;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.utility.RandomNumUtil;
import com.opensymphony.xwork2.ActionContext;

/**
 * 验证码
 */
public class RandomAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	
	private ByteArrayInputStream inputStream;

	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	} 
	
	public void randImage() throws Exception{
		RandomNumUtil rdnu=RandomNumUtil.Instance(); 
		//this.setInputStream(rdnu.getImage());
		ActionContext.getContext().getSession().put("random", rdnu.getStr());
		//return "success";
		BufferedInputStream bis = null;
		OutputStream os = null;
		  HttpServletResponse response= super.getResponse();
	        bis = new BufferedInputStream(rdnu.getImage());  
	                byte[] buffer = new byte[512];  
	                response.setCharacterEncoding("UTF-8");  
	                        //不同类型的文件对应不同的MIME类型  
	                response.setContentType("image/*");  
	                        //文件以流的方式发送到客户端浏览器
	                response.setContentLength(bis.available());  
	                  
	                os = response.getOutputStream();  
	                int n;  
	                while ((n = bis.read(buffer)) != -1) {  
	                  os.write(buffer, 0, n);  
	                }  
	                bis.close();  
	                os.flush();  
	                os.close();
	}
	
	public void ImageShow() throws IOException{   
		//String img = "D:\\u01\\hrtwork\\upload\\111000\\111000.486001094000604.20140509.1.jpg";
		HttpServletResponse response= super.getResponse();
		OutputStream os = null;  
	    String img =super.getRequest().getParameter("upload");
	   // img="D:/"+img;
        BufferedInputStream bis = null; 
        try{
        FileInputStream fileInputStream = new FileInputStream(new File(img));  
        
              bis = new BufferedInputStream(fileInputStream);  
                byte[] buffer = new byte[512];  
                response.setCharacterEncoding("UTF-8");  
                        //不同类型的文件对应不同的MIME类型  
                response.setContentType("image/*");  
                        //文件以流的方式发送到客户端浏览器
                response.setContentLength(bis.available());  
                  
                os = response.getOutputStream();  
                int n;  
                while ((n = bis.read(buffer)) != -1) {  
                  os.write(buffer, 0, n);  
                }
                fileInputStream.close();
                bis.close();
                os.flush();  
                os.close();
        }catch(Exception e){
        	
        }
	}
}
