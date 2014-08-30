package com.topic.parserAdapter.controller.test;

import java.io.File;

import javax.servlet.ServletContext;

import org.nutz.mvc.adaptor.VoidAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;

import com.topic.parserAdapter.core.office.converter.Word2003ToHtmlConverter;
import com.topic.parserAdapter.core.office.converter.Word2007To2003Converter;
import com.topic.parserAdapter.core.office.converter.Word2007ToHtmlConverter;
import com.topic.parserAdapter.core.office.parser.IdeaWordParser;
/**
 * MVC Test
 * @author jiangzx0526@gmail.com
 * 
 * 本地服务测试
 * @see - http://localhost:8015/topic-parser/test/office/word2003ToHtml
 * @see - http://localhost:8015/topic-parser/test/office/word2007To2003
 * @see - http://localhost:8015/topic-parser/test/office/word2007ToHtml
 * @see - http://localhost:8015/topic-parser/test/office/parserWordToHtml
 */
@At("/test")
@AdaptBy(type=VoidAdaptor.class)
public class TestOfficeController {
	
	//03-html的无插件转换
	@At("/office/word2003ToHtml")
	public void word2003ToHtml(ServletContext sc){
		String projectPath = sc.getRealPath("")+File.separatorChar;
		String docFileName = "白板语文题库.doc";
		Word2003ToHtmlConverter.parseWord2003ToHtml(projectPath, docFileName);
	}
	
	//因为加了一个转换,所以多传了一个参数来获取Office服务的单例
	@At("/office/word2007To2003")  
	public void word2007To2003(ServletContext sc){
		String projectPath = sc.getRealPath("")+File.separatorChar;
		String docFileName = "白板语文题库.doc"; 
		Word2007To2003Converter.convert(sc, projectPath, docFileName);
	}
	
	//07-html的带插件转换
	@At("/office/word2007ToHtml")
	public void word2007ToHtml(ServletContext sc){
		String projectPath = sc.getRealPath("")+File.separatorChar;
		String docFileName = "白板语文题库.docx";
		Word2007ToHtmlConverter.parseWord2007ToHtml(sc, projectPath, docFileName);
	}
	
	//自动判断word类型转换成html格式
	@At("/office/parserWordToHtml")
	public void parserWordToHtml(ServletContext sc){
		String projectPath = sc.getRealPath("")+File.separatorChar;
		String relativeFilePath = Word2003ToHtmlConverter.relativeFilePath;
		String docFileName = "白板语文题库.docx";
		
		String fullDocPath = projectPath + relativeFilePath + docFileName;
		File docFile = new File(fullDocPath);
		IdeaWordParser.parseWordToHtml(sc, projectPath, docFile);
	}
	
}
