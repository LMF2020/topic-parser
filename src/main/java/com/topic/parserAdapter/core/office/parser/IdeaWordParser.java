package com.topic.parserAdapter.core.office.parser;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletContext;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.topic.parserAdapter.core.office.converter.Word2003ToHtmlConverter;
import com.topic.parserAdapter.core.office.converter.Word2007ToHtmlConverter;
import com.topic.parserAdapter.model.FileProperty;
import com.topic.parserAdapter.model.Topic;
/**
 * 支持Word所有格式的智能转换
 * @author jiangzx0526@gmail.com
 *
 */
@InjectName
@IocBean
public class IdeaWordParser {
	
	@Inject
	private CoolHtmlParser coolHtmlParser;
	/**
	 * 转换器
	 * @param projectPath	工程目录
	 * @param docFile		待转换文件(如abc.docx),此时记住文件已经保存在doc目录下了
	 * @return
	 * @throws IOException
	 */
	public static String parseWordToHtml(ServletContext sc, String projectPath, String fileName){
		
		String htmlPath;
		if(fileName.indexOf(".docx")>0){ //处理Word2007+档案格式
			htmlPath = Word2007ToHtmlConverter.parseWord2007ToHtml(sc,projectPath, fileName);
		}else{ //处理Word2003-档案格式
			htmlPath = Word2003ToHtmlConverter.parseWord2003ToHtml(projectPath, fileName);
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
	public List<Topic> getTopicList(ServletContext sc, String projectPath, String fileName ,FileProperty docInfo){
		
		String htmlPath = parseWordToHtml(sc,projectPath,fileName);
		if(htmlPath !=null){
			return	coolHtmlParser.parse(projectPath + htmlPath , docInfo);
		}
		return Collections.emptyList();
	}

}
