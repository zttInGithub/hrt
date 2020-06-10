package com.hrt.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

/**************************
 * @description: 图片添加水印
 * @user: xuegangliu
 * @date: 2019/1/23
 ***************************/
public class PictureWaterMarkUtil {

    /**
     * @description
     * @param fileInputStream 图片流
     * @param tarImgPath 保存的图片路径
     * @param waterMarkContent 水印内容
     * @param fileExt 图片格式
     * @return void
     */
    public static void addWatermark(FileInputStream fileInputStream, String tarImgPath,
                                    String waterMarkContent, String fileExt){
        Color markContentColor = Color.LIGHT_GRAY;//水印颜色
        Integer degree = -20;//设置水印文字的旋转角度
        float alpha = 1.0f;//设置水印透明度 默认为1.0  值越小颜色越浅
        OutputStream outImgStream = null;
        try {
//            File srcImgFile = new File(sourceImgPath);//得到文件
            Image srcImg = ImageIO.read(fileInputStream);//文件转化为图片
            int srcImgWidth = srcImg.getWidth(null);//获取图片的宽
            int srcImgHeight = srcImg.getHeight(null);//获取图片的高
            // 加水印
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();//得到画笔
            // 设置对线段的锯齿状边缘处理
//            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
//                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
//            g.clearRect(0, 0, 0, 0);
            g.setColor(markContentColor); //设置水印颜色
//            int size = srcImgWidth>srcImgHeight?srcImgHeight/35:srcImgWidth/35;
            int size = (int) Math.sqrt(srcImgWidth*srcImgHeight)/35;
            Font font = new Font("宋体", Font.BOLD, size);//水印字体，大小
            g.setFont(font);              //设置字体
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));//设置水印文字透明度
            if (null != degree) {
                g.rotate(Math.toRadians(degree),(double)bufImg.getWidth()/2,(double)bufImg.getHeight()/2);//设置水印旋转
            }

//            int x = srcImgWidth/2>srcImgHeight?-srcImgHeight:(-srcImgWidth / 2);
            int x = srcImgWidth/4;
            int y;
            int markWidth = size * getTextLength (waterMarkContent);// 字体长度
//            int XMOVE = srcImgWidth/2>srcImgHeight?srcImgWidth/4:srcImgWidth/3;
            int XMOVE = srcImgWidth/2;
//            int YMOVE = srcImgHeight/3;
            int YMOVE = srcImgHeight/2;
            while (x < srcImgWidth * 1) {
//                y = -srcImgHeight / 2;
                y = srcImgHeight/4;
                while (y < srcImgHeight * 1) {
                    g.drawString(waterMarkContent, x, y);
                    y += size + YMOVE;
                }
                x += markWidth + XMOVE;
            }
            g.dispose();// 释放资源
            // 输出图片
            outImgStream = new FileOutputStream(tarImgPath);
            ImageIO.write(bufImg, fileExt, outImgStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                if(outImgStream != null){
                    outImgStream.flush();
                    outImgStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取文本长度。汉字为1:1，英文和数字为2:1
     */
    private static int getTextLength (String text) {
        int length = text.length ();
        for (int i = 0; i < text.length (); i++) {
            String s = String.valueOf (text.charAt (i));
            if (s != null) {
                if (s.getBytes ().length > 1) {
                    length++;
                }
            }
        }
        length = length % 2 == 0 ? length / 2 : length / 2 + 1;
        return length;
    }
}
