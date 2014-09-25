package com.topic.parserAdapter.core.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取真实Ip地址
 * 
 * @author Jiang
 * 
 */
public class IPUtil {

	public static String getClientIp(HttpServletRequest request) {
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

	// 获取服务器端地址
	public static String getServerAddr(HttpServletRequest req) {
		StringBuffer url = new StringBuffer();
		String protocol = req.getScheme();//获取通讯协议
		String serverIp = req.getLocalAddr();// 获取服务器端地址
		url.append(protocol).append("://").append(serverIp);
		int serverPort = req.getLocalPort();// 获取服务器端端口
		if ((serverPort != 80) && (serverPort != 443)) {
			url.append(":").append(serverPort);
		}
		return url.toString();
	}

	// 获取远程地址
	public static String getClientAddr(HttpServletRequest req) {
		StringBuffer url = new StringBuffer();
		String protocol = req.getScheme();// 获取通讯协议
		String clientIp = req.getRemoteAddr();// 获取服务器端地址
		url.append(protocol).append("://").append(clientIp);
		int clientPort = req.getRemotePort();// 获取服务器端端口
		if ((clientPort != 80) && (clientPort != 443)) {
			url.append(":").append(clientPort);
		}
		return url.toString();
	}
}
