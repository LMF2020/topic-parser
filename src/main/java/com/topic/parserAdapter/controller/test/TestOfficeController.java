package com.topic.parserAdapter.controller.test;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.adaptor.VoidAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

import com.topic.parserAdapter.controller.component.BasicDBJob;
import com.topic.parserAdapter.core.office.converter.Word2003ToHtmlConverter;
import com.topic.parserAdapter.core.office.converter.Word2007To2003Converter;
import com.topic.parserAdapter.core.office.converter.Word2007ToHtmlConverter;
import com.topic.parserAdapter.core.office.parser.IdeaWordParser;
import com.topic.parserAdapter.model.Topic;
/**
 * MVC Test ,暂时用浏览器模拟吧.未来可以用httpClient模拟或者Nutz的Http包模拟测试
 * @author jiangzx0526@gmail.com
 * 
 * 本地服务测试用例	*[Avaliable]
 * @see - http://localhost:8015/topic-parser/test/office/word2003ToHtml
 * @see - http://localhost:8015/topic-parser/test/office/word2007To2003
 * @see - http://localhost:8015/topic-parser/test/office/word2007ToHtml
 * @see - http://localhost:8015/topic-parser/test/office/parserWordToHtml
 * @see - http://localhost:8015/topic-parser/test/db/insertOne
 * @see - http://localhost:8015/topic-parser/test/db/listAll
 */
@At("/test")
@AdaptBy(type=VoidAdaptor.class)
@IocBean
public class TestOfficeController {
	
	@Inject
	private BasicDBJob basicDBJob;
	
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
	
	//测试保存一个POJO对象
	@At("/db/insertOne")
	@Ok("json")
	public Topic instertPOJO(){
		Topic E = new Topic("1", "题目内容是此处省略一万字....", "题目答案是C","12.5" , "2");
		Topic r =  basicDBJob.insertOne(E);
		return r;
	}
	
	//测试获取全部POJO列表并id降序排列
	@At("/db/listAll")
	@Ok("json")
	public List<Topic> getPOJOList(){
		return basicDBJob.listAll();
	}
}
