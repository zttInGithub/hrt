package com.hrt.biz.util;
import com.google.zxing.common.BitMatrix;
import com.hrt.biz.bill.entity.model.MerchantInfoModel;
import com.hrt.util.ParamPropUtils;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.OutputStream;
import java.io.IOException;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

 public final class QRToImageWriter {
 
   private static final int BLACK = 0xFF000000;
   private static final int WHITE = 0xFFFFFFFF;
	
	public QRToImageWriter() {}
 
   
   public static BufferedImage toBufferedImage(BitMatrix matrix) {
     int width = matrix.getWidth();
     int height = matrix.getHeight();
     BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
     for (int x = 0; x < width; x++) {
       for (int y = 0; y < height; y++) {
         image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
       }
     }
     return image;
   }
 
   
   public static void writeToFile(BitMatrix matrix, String format, File file)
       throws IOException {
     BufferedImage image = toBufferedImage(matrix);
     if (!ImageIO.write(image, format, file)) {
       throw new IOException("Could not write an image of format " + format + " to " + file);
     }
   }
   
   
   /**
    * 给二维码图片添加Logo
    * 
    * @param qrPic
    * @param logoPic
    */
   public static void addLogo_QRCode(File qrPic, File logoPic)
   {
	   
	  
       try
       {
           if (!qrPic.isFile() || !logoPic.isFile())
           {
               System.out.print("file not find !");
               System.exit(0);
           }

           /**
            * 读取二维码图片，并构建绘图对象
            */
           BufferedImage image = ImageIO.read(qrPic);
           Graphics2D g = image.createGraphics();

           /**
            * 读取Logo图片
            */
           BufferedImage logo = ImageIO.read(logoPic);
            
           int widthLogo = logo.getWidth(), heightLogo = logo.getHeight();
            
           // 计算图片放置位置
           int x = (image.getWidth() - widthLogo) / 2;
           int y = (image.getHeight() - logo.getHeight()) / 2;

           //开始绘制图片
           g.drawImage(logo, x, y, widthLogo, heightLogo, null);
           g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
//           g.setStroke(new BasicStroke(logoConfig.getBorder()));
          // Color c =new Color("red");
           g.setColor(Color.BLUE);
           g.drawRect(x, y, widthLogo, heightLogo);
            
           g.dispose();
            
           ImageIO.write(image, "jpeg", new File("D:/newPic.jpg"));
       }
       catch (Exception e)
       {
           e.printStackTrace();
       }
   }
 
   
   public static void writeToStream(BitMatrix matrix, String format, OutputStream stream)
       throws IOException {
     BufferedImage image = toBufferedImage(matrix);
     if (!ImageIO.write(image, format, stream)) {
       throw new IOException("Could not write an image of format " + format);
     }
   }
   
   public MerchantInfoModel doQR(MerchantInfoModel merchantInfoModel) {
		   try {
			     //10位随机数
			   	 String chars = "abcdefghijklmnopqrstuvwxyz";
				 System.out.println(chars.charAt((int)(Math.random() * 26)));
				 String sign="";
				 for(int i=0;i<10;i++){
					sign=sign+String.valueOf((char)(Math.random()*26+65));
				 }
			     //String content = "http://test.hybunion.com/WeChatServlet/toOpenAuth.do?mid=123456789&mername=北京和融通支付科技有限公司&sign=xmwxtyui01yxcxpx";
			   	 //链接路径
				 String content =null;
				 if(merchantInfoModel.getIsM35()==1){
					 content = ParamPropUtils.props.get("xpayPath")+"/qrpayment?" +
					 "mid="+merchantInfoModel.getMid()+"&sign="+sign;
				 }else{
					 content = ParamPropUtils.props.get("xpayPath")+"/hrtjspay?" +
					 "mid="+merchantInfoModel.getMid()+"&sign="+sign;
				 }
			    /* //存放路径
			     //String path = "D:"+File.separator;
			     String path = File.separator+"u01"+File.separator+"hrtwork"+File.separator+"QRCODE"+File.separator;
			     MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
			     Map hints = new HashMap();
			     hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			     BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 300, 300);
			     File file1 = new File(path,merchantInfoModel.getMid()+".jpg");
			     QRToImageWriter.writeToFile(bitMatrix, "jpg", file1);*/

			     /*
			      	带背景图
			     MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
			     BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 235, 235);
			     BufferedImage imgQR = toBufferedImage(bitMatrix);
			     //String rootPath = this.getClass().getClassLoader().getResource("../../").getPath();/u01/hrtwork/QRCODE
			     //背景图
			     //InputStream imgBGI=new FileInputStream(File.separator+"u01"+File.separator+"hrtwork"+File.separator+"QRCODE"+File.separator+"imgBG.png");
			     InputStream imgBGI=new FileInputStream("D:"+File.separator+"imgBG.png");
			     BufferedImage imgBG=ImageIO.read(imgBGI);
			     //背景合成
			     Graphics g=imgBG.getGraphics();
                 g.drawImage(imgQR,(imgBG.getWidth()-imgQR.getWidth())/2,(imgBG.getHeight()-imgQR.getHeight())/2+20,imgQR.getWidth(),imgQR.getHeight(),null);
                 OutputStream outImage=new FileOutputStream(new File(path,merchantInfoModel.getMid()+".jpg"));
                 JPEGImageEncoder enc=JPEGCodec.createJPEGEncoder(outImage);
                 enc.encode(imgBG);
                 imgBGI.close();
                 outImage.close();
                 */

			     //存储
			     merchantInfoModel.setQrUrl(content);
			    // merchantInfoModel.setQrUpload(path+merchantInfoModel.getMid()+".jpg");
			     merchantInfoModel.setQrUpload("0");
			 } catch (Exception e) {
			     e.printStackTrace();
			 }
			 
	   return merchantInfoModel;
	  // File qrPic =new File("C://餐巾纸.jpg");
	  // File logoPic =new File("C://weixin1.png");
	   //MatrixToImageWriter.addLogo_QRCode(qrPic,logoPic);
   }
 }