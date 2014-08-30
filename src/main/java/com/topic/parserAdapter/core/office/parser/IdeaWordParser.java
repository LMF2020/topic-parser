package com.topic.parserAdapter.core.office.parser;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

import com.topic.parserAdapter.core.office.converter.Word2003ToHtmlConverter;
import com.topic.parserAdapter.core.office.converter.Word2007ToHtmlConverter;
/**
 * 支持Word所有格式的智能转换
 * @author jiangzx0526@gmail.com
 *
 */
public class IdeaWordParser {
	
	/**
	 * 转换器
	 * @param projectPath	工程目录
	 * @param docFile		待转换文件(如abc.docx),此时记住文件已经保存在doc目录下了
	 * @return
	 * @throws IOException
	 */
	public static boolean parseWordToHtml(ServletContext sc, String projectPath, File docFile){
		
		String fileName = docFile.getName();
		if(fileName.indexOf(".docx")>0){ //处理Word2007+档案格式
			Word2007ToHtmlConverter.parseWord2007ToHtml(sc,projectPath, fileName);
		}else{ //处理Word2003-档案格式
			Word2003ToHtmlConverter.parseWord2003ToHtml(projectPath, fileName);
		}
		return true;
	}

}
