package com.topic.parserAdapter.core.util;

import javax.servlet.http.HttpServletRequest;
/**
 * 获取真实Ip地址
 * @author Jiang
 *
 */
public class IPUtil {
	
	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	public static int getPort(HttpServletRequest request){
		return request.getLocalPort();
	}
	
	public static String getRealAddr(HttpServletRequest request){
		return getIp(request)+":"+getPort(request);
	}
}
