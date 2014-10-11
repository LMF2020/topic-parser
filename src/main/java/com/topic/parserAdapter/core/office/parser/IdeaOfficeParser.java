package com.topic.parserAdapter.core.office.parser;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletContext;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.topic.parserAdapter.core.office.converter.Word2003ToHtmlConverter;
import com.topic.parserAdapter.core.office.converter.OfficeToHtmlConverter;
import com.topic.parserAdapter.model.Document;
import com.topic.parserAdapter.model.Topic;
/**
 * 支持Office格式的智能转换
 * @author jiangzx0526@gmail.com
 *
 */
@InjectName
@IocBean
public class IdeaOfficeParser {
	
	@Inject
	private CoolHtmlParser coolHtmlParser;
	/**
	 * 转换器
	 * @param projectPath	工程目录
	 * @param docFile		待转换文件(如abc.docx),此时记住文件已经保存在doc目录下了
	 * @return
	 * @throws IOException
	 */
	public static String parseOfficeToHtml(ServletContext sc, String projectPath, String fileName){
		
		String htmlPath = null;
		String extension = null;
		int extensionPos = fileName.lastIndexOf(".");
		int length = fileName.length();
		if(extensionPos != -1){
			extension = fileName.substring(extensionPos, length).toLowerCase();
		}
		if(extension.equals(".doc")){ 			//处理Word2003
			htmlPath = Word2003ToHtmlConverter.parseWord2003ToHtml(projectPath, fileName);
		}else{ 		//处理其他office格式
			htmlPath = OfficeToHtmlConverter.parseToHtml(sc,projectPath, fileName,extension);
		}
		return htmlPath;
	}
	
	/**
	 * 从文档提取题目列表对象
	 * @param sc
	 * @param projectPath	工程目录
	 * @param docFile		文件
	 * @param docInfo		文档信息
	 * @return
	 */
	public List<Topic> getTopicList(ServletContext sc, String projectPath, String fileName ,Document docInfo){
		
		String htmlPath = parseOfficeToHtml(sc,projectPath,fileName);
		String contextPath = sc.getContextPath();
		if(htmlPath !=null){
			return	coolHtmlParser.parse(contextPath,projectPath + htmlPath , docInfo);
		}
		return Collections.emptyList();
	}

}
