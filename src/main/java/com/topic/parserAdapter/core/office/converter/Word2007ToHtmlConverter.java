package com.topic.parserAdapter.core.office.converter;

import javax.servlet.ServletContext;


/**
 * 
 * Word2007-2010转换器
 * @author jiangzx0526@gmail.com
 *
 */
public class Word2007ToHtmlConverter {
	
	 public static String parseWord2007ToHtml(ServletContext sc, String projectPath, String docFile) {  
		 Word2007To2003Converter.convert(sc,projectPath, docFile);
		 docFile = docFile.replaceAll(".docx", ".doc");
		 String resultPath = Word2003ToHtmlConverter.parseWord2003ToHtml(projectPath, docFile);
		 return resultPath;
	 }
}
