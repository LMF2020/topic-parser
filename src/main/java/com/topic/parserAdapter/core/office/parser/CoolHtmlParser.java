package com.topic.parserAdapter.core.office.parser;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.topic.parserAdapter.controller.component.BasicDBJob;

/**
 * 完美的html解析类
 * @author jiangzx0526@gmail.com
 *
 */
@InjectName
@IocBean
public class CoolHtmlParser {
	
	//在处理html转换逻辑时需要有数据库交互,所以注入BasicJob完成特性的交互逻辑
	@Inject
	private BasicDBJob basicDBJob;
	
	/**
	 * 解析html文件
	 * @param htmlFullPath html文件的完整路径
	 * @return
	 */
	public boolean begin(String htmlFullPath){
		boolean hasDone = false;
		//TODO:完成解析
		
		
		
		return hasDone;
	}
	
}
