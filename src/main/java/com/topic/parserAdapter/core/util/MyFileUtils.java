package com.topic.parserAdapter.core.util;

import java.io.File;
import java.math.BigDecimal;

import javax.servlet.ServletContext;

/**
 * 文件操作
 * 
 * @author jiangzx0526@gmail.com
 * 
 */
public class MyFileUtils {

	private ServletContext sc;

	public String getUploadPath(String path) {
		return sc.getRealPath(path);
	}

	/**
	 * 根据路径名生成多级路径
	 * 
	 * @param url
	 *            参数要以"\classes\cn\qtone\"或者"/classes/cn/qtone/"
	 */
	public static String makeDir(String root, String url) {
		String[] sub;
		url = url.replaceAll("\\/", "\\\\");
		if (url.indexOf("\\") > -1) {
			sub = url.split("\\\\");
		} else {
			return "-1";
		}

		File dir = null;
		try {
			dir = new File(root);
			for (int i = 0; i < sub.length; i++) {
				if (!dir.exists() && !sub[i].equals("")) {
					dir.mkdir();
				}
				File dir2 = new File(dir + File.separator + sub[i]);
				if (!dir2.exists()) {
					dir2.mkdir();
				}
				dir = dir2;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}
		return dir.toString();
	}

	public static String getFileSize(long bytes) {
		BigDecimal filesize = new BigDecimal(bytes);
		BigDecimal megabyte = new BigDecimal(1024 * 1024);
		float returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP).floatValue();
		if (returnValue > 1)
			return (returnValue + " MB ");
		BigDecimal kilobyte = new BigDecimal(1024);
		returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP)
				.floatValue();
		return (returnValue + "  KB ");
	}
}
