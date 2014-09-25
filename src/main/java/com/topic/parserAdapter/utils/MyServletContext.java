package com.topic.parserAdapter.utils;

import javax.servlet.http.HttpServletRequest;

public class MyServletContext {
	private static String scheme;// 协议
	private static String serverName;// 服务器地址
	private static int serverPort;// 服务器端口
	private static String contextPath;// 上下文环境
	private static String servletPath;// servlet path
	private static String pathInfo;// 路径信息
	private static String queryString;// 参数

	public MyServletContext(HttpServletRequest req) {
		scheme = req.getScheme(); // http
		serverName = req.getServerName(); // hostname.com
		serverPort = req.getServerPort(); // 80
		contextPath = req.getContextPath(); // /mywebapp
		servletPath = req.getServletPath(); // /servlet/MyServlet
		pathInfo = req.getPathInfo(); // /a/b;c=123
		queryString = req.getQueryString(); // d=789
	}

	// 获取全路径
	public static String getFullURL() {
		StringBuffer url = new StringBuffer();// Reconstruct original requesting
												// URL
		url.append(scheme).append("://").append(serverName);
		if ((serverPort != 80) && (serverPort != 443)) {
			url.append(":").append(serverPort);
		}
		url.append(contextPath).append(servletPath);

		if (pathInfo != null) {
			url.append(pathInfo);
		}
		if (queryString != null) {
			url.append("?").append(queryString);
		}
		return url.toString();
	}

	// 获取根路径
	public static String getBaseURL(HttpServletRequest req) {
		StringBuffer url = new StringBuffer();// Reconstruct original requesting
												// URL
		url.append(scheme).append("://").append(serverName);
		if ((serverPort != 80) && (serverPort != 443)) {
			url.append(":").append(serverPort);
		}
		url.append(contextPath);
		return url.toString();
	}

	// 获取服务器ip地址
	public static String getServerIpAdrr(HttpServletRequest req) {
		return req.getProtocol() + "://" + req.getRemoteAddr() + ":" + req.getRemotePort();
	}
}
