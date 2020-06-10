package com.hrt.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PropertiesUtil {
	
	private static Log logger = LogFactory.getLog(PropertiesUtil.class);
    /**
     * 读取properties属性值
     *
     * @param filePath
     * @param key
     * @return
     */
    public static String readValue(String filePath, String key) {
        Properties props = new Properties();
        InputStream in = null;
        String value = null;
        String path = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
        try {
            in = new BufferedInputStream(new FileInputStream(path + File.separator + filePath));
            props.load(in);
            value = props.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }
    
    /**
     * 获取配置文件
     * @param filePath
     * @return
     */
    public static Properties getProperties(String filePath) {
        Properties prop = new Properties();
        InputStream in = null;
        try {
            File file = new File(filePath);
//          直接读取文件
            if (file.canRead()) {
                in = new BufferedInputStream(new FileInputStream(file));
//          从当前路径中获取文件流
            } else {
                in = PropertiesUtil.class.getClassLoader().getResourceAsStream(filePath);
            }
            if (in != null) {
                prop.load(in);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error("载入配置文件出错", e);
            }
        }
        return prop;
    }
}
