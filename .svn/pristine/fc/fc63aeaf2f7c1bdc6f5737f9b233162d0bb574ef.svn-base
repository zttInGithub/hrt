package com.hrt.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class FileZipUtil {
	
	private static final Log log = LogFactory.getLog(FileZipUtil.class);
	/**
     * 功能:压缩多个文件成一个zip文件
     * @param srcfile：源文件列表
     * @param zipfile：压缩后的文件
     */
    public static void zipFiles(File[] srcfile,File zipfile){
        byte[] buf=new byte[1024];
        try {
            ZipOutputStream out=new ZipOutputStream(new FileOutputStream(zipfile));
            for(int i=0;i<srcfile.length;i++){
                FileInputStream in=new FileInputStream(srcfile[i]);
                out.putNextEntry(new ZipEntry(srcfile[i].getName()));
                int len;
                while((len=in.read(buf))>0){
                    out.write(buf,0,len);
                }
                out.closeEntry();
                in.close();
            }
            out.close();
            log.info("文件压缩完成.");
        } catch (Exception e) {
        	log.info("文件压缩异常："+e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 功能:解压缩
     * @param zipfile：需要解压缩的文件
     * @param descDir：解压后的目标目录
     * @throws IOException 
     */
    public static void unZipFiles(File zipfile,String descDir) {
    	ZipFile zf = null;
        try {
            zf=new ZipFile(zipfile);
            for(Enumeration entries=zf.entries();entries.hasMoreElements();){
                ZipEntry entry=(ZipEntry) entries.nextElement();
                String zipEntryName=entry.getName();
                InputStream in=zf.getInputStream(entry);
                OutputStream out=new FileOutputStream(descDir+zipEntryName);
                byte[] buf1=new byte[1024];
                int len;
                while((len=in.read(buf1))>0){
                    out.write(buf1,0,len);
                }
                in.close();
                out.close();
                log.info("文件解压缩完成.");
            }
        } catch (Exception e) {
            log.info("文件解压缩异常："+e.getMessage());
            e.printStackTrace();
        } finally{
            if (zf != null) {
                try {
                    zf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 下载zip文件
     * @param serverPath
     * @param str
     * @throws Exception 
     */
    public static void downZipFile(String serverPath,String str) throws Exception {
		String filename="";
		try {
			filename = new String(str.getBytes("gb2312"), "ISO-8859-1");
			File file = new File(serverPath);
			byte a[] = new byte[1024 * 1024];
			FileInputStream fin = new FileInputStream(file);
			OutputStream outs = ServletActionContext.getResponse().getOutputStream();
			ServletActionContext.getResponse().setHeader("Content-Disposition","attachment; filename=" + filename);
			ServletActionContext.getResponse().setContentType("application/x-msdownload;charset=utf-8");
			ServletActionContext.getResponse().flushBuffer();
			int read = 0;
			while ((read = fin.read(a)) != -1) {
				outs.write(a, 0, read);
			}
			outs.close();
			fin.close();
		} catch (Exception e) {
			log.info("下载压缩包异常："+e.getMessage());
			throw e;
		}
		
  }

    /**
     * 图片上传(图片Base64码上传)
     * @param path 上传的路径
     * @param upload 图片Base64码
     * @param fName 保存的图片名称
     * @param imageDay 日期字符串
     */
    public static void uploadFile2byte(String path,String upload, String fName, String imageDay) {
        if(upload!=null) {
            // 去除图片Base64转码后的前缀信息
            upload = upload.replaceAll("data:image/png;base64,", "");
        }
        try {
            String realPath = path + File.separator + imageDay;
            File dir = new File(realPath);
            if(!dir.exists()) {
                dir.mkdirs();
            }
            String fPath = realPath + File.separator + fName;
            byte[] imgByte = decode(upload);
            FileOutputStream outputStream = new FileOutputStream(new File(fPath));
            outputStream.write(imgByte);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            log.error("图片上传(图片Base64码上传)异常:",e);
        }
    }

    /**
     * Bse64解码
     * @param imageData
     * @return
     * @throws Exception
     */
    private static byte[] decode(String imageData){
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] data = new byte[0];
        try {
            data = decoder.decodeBuffer(imageData);
        } catch (IOException e) {
            log.error("Bse64解码异常:",e);
        }
        for (int i = 0; i < data.length; ++i) {
            if (data[i] < 0) {
                // 调整异常数据
                data[i] += 256;
            }
        }
        return data;
    }

    /**
     * 图片文件转base64
     * @param filePath
     * @return
     */
    public static String encodeImageBase64(String filePath){
        File file=new File(filePath);
        FileInputStream fis = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        BASE64Encoder base64Encoder=new BASE64Encoder();
        byte[] b = new byte[1024];
        try {
            fis = new FileInputStream(file);
            int n;
            while ((n = fis.read(b)) != -1)
            {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
        } catch (FileNotFoundException e) {
            log.error("图片文件转base64文件未找到异常",e);
        } catch (IOException e) {
            log.error("图片文件转base64文件异常",e);
        }

        return base64Encoder.encode(bos.toByteArray());
    }
}
