package com.topic.parserAdapter.core.util;

import java.io.File;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;
import org.nutz.ioc.impl.PropertiesProxy;
/**
 * 定义ServletContext上下文的Office服务工具类
 * @author jiangzx0526@gmail.com
 *
 */
public class MyWebContext {

	private static final String KEY = MyWebContext.class.getName();
	private final OfficeManager officeManager;
	private final OfficeDocumentConverter documentConverter;

	public MyWebContext(ServletContext servletContext,PropertiesProxy prop) {
		
		DefaultOfficeManagerConfiguration configuration = new DefaultOfficeManagerConfiguration();
		String officePortParam = prop.get("OFFICE_PORT");
		if (!StringUtils.isEmpty(officePortParam)) {
		    configuration.setPortNumber(Integer.parseInt(officePortParam));
		}
		String officeHomeParam = getOfficeHome(prop);
		if (!StringUtils.isEmpty(officeHomeParam)) {
		    configuration.setOfficeHome(new File(officeHomeParam));
		}
		String officeProfileParam = prop.get("OFFICE_PROFILE");
		if (!StringUtils.isEmpty(officeProfileParam)) {
		    configuration.setTemplateProfileDir(new File(officeProfileParam));
		}

		officeManager = configuration.buildOfficeManager();
		documentConverter = new OfficeDocumentConverter(officeManager);
	}

	public static void init(ServletContext servletContext,PropertiesProxy prop) {
		MyWebContext instance = new MyWebContext(servletContext,prop);
		servletContext.setAttribute(KEY, instance);
		instance.officeManager.start();
	}

	public static void destroy(ServletContext servletContext) {
		MyWebContext instance = get(servletContext);
		instance.officeManager.stop();
	}

	public static MyWebContext get(ServletContext servletContext) {
		return (MyWebContext) servletContext.getAttribute(KEY);
	}

	public OfficeManager getOfficeManager() {
        return officeManager;
    }

	public OfficeDocumentConverter getDocumentConverter() {
        return documentConverter;
    }
	
	private  String getOfficeHome(PropertiesProxy prop) {  
	    String osName = System.getProperty("os.name");  
	    if (Pattern.matches("Linux.*", osName)) {  
	        return prop.get("OFFICE_HOME_LINUX");  
	    } else if (Pattern.matches("Windows.*", osName)) {  
	        return prop.get("OFFICE_HOME_WIN");  
	    }
	    return null;  
	}  

}
