package com.topic.parserAdapter.core.util;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
/**
 * 读取配置
 * @author jiangzx0526@gmail.com
 *
 */
public class MyPropUtils {
	
	public static Configuration config ;
	
	public static Configuration getConfig(){
		if(config == null){
			try {
				config = new PropertiesConfiguration("config.properties");
			} catch (ConfigurationException e) {
				e.printStackTrace();
			}
		}
		return config;
	}
}
