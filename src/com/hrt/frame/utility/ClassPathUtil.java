package com.hrt.frame.utility;

import java.io.File;
import java.net.URLDecoder;

/**
 * 类功能：获取classpath路径
 * 创建人：wwy
 * 创建日期：2012-12-22
 */
public class ClassPathUtil extends File {
	
	private static final long serialVersionUID = 1L;
	
	private static ClassPathUtil instance = new ClassPathUtil();
	
	private static String getPath(Class cls) {
		try {
			String path = URLDecoder.decode(cls.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8");
			if (false) {
			} else if (path.endsWith(".jar")) {
				path += separator + "..";
			} else if (path.endsWith(".class")) {
				int nParent = ClassPathUtil.class.getPackage().getName().split("\\.").length + 1;
				for (int i = 0; i < nParent; i ++) {
					path += separator + "..";
				}
			}
			return new File(path).getCanonicalPath();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private ClassPathUtil() {
		this(ClassPathUtil.class);
	}
	
	public ClassPathUtil(Class cls) {
		super(getPath(cls));
	}
	
	public ClassPathUtil(String pathname) {
		super(pathname);
	}
	
	public static ClassPathUtil getInstance() {
		return instance;
	}
	
	public ClassPathUtil append(String strPath) {
		try {
			return new ClassPathUtil(new File(getPath() + separator + strPath).getCanonicalPath());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
