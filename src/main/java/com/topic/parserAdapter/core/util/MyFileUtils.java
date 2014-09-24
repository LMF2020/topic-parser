package com.topic.parserAdapter.core.util;

import java.io.File;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;

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

	// 设置一个随机数作为文件名称
	public static String getRadomFileName() {
		Calendar nowtime = new GregorianCalendar();
		StringBuffer sb = new StringBuffer(17);
		sb.append(String.format("%04d", nowtime.get(Calendar.YEAR)))
				.append(String.format("%02d", nowtime.get(Calendar.MONTH)))
				.append(String.format("%02d", nowtime.get(Calendar.DATE)))
				.append(String.format("%02d", nowtime.get(Calendar.HOUR)))
				.append(String.format("%02d", nowtime.get(Calendar.MINUTE)))
				.append(String.format("%02d", nowtime.get(Calendar.SECOND)))
				.append(String.format("%03d", nowtime.get(Calendar.MILLISECOND)));
		return sb.toString();
	}
	
/*	public static void main(String[] args) {
		System.out.println(MyFileUtils.getRadomFileName());
	}*/
}
