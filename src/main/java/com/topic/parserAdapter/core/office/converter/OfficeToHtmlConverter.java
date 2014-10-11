package com.topic.parserAdapter.core.office.converter;

import javax.servlet.ServletContext;


/**
 * 
 * Office格式转换器,统一转成doc格式,再由doc格式转成统一的html文档
 * @author jiangzx0526@gmail.com
 *
 */
public class OfficeToHtmlConverter {
	
	 public static String parseToHtml(ServletContext sc, String projectPath, String docFile,String extension) {  
		 //将offic文档全部转换为doc格式
		 OfficeConverter.convert2Doc(sc,projectPath, docFile);
		 docFile = docFile.replaceAll(extension, ".doc");
		 String resultPath = Word2003ToHtmlConverter.parseWord2003ToHtml(projectPath, docFile);
		 return resultPath;
	 }
}
