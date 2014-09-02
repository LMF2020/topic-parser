package com.topic.parserAdapter.controller.module;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;

import com.topic.parserAdapter.dao.BasicDao;
import com.topic.parserAdapter.model.Topic;

/**
 * 内部测试页面用到controller
 * @author Rayintee
 *
 */
@At("/topic")
@IocBean
public class TopicController {
	
	@Inject
	private BasicDao basicDao;
	
	/**
	 * 跳转到上传页面
	 */
	@At("/upload")
	@Ok("jsp:jsp.topic.upload")
	@Fail("http:404")
	public void toUploadPage(){
	}
	
	/**
	 * 跳转到列表页面
	 */
	@At("/list")
	@Ok("jsp:jsp.topic.list")
	@Fail("http:404")
	public void listTopics(HttpServletRequest req){
		List<Topic> topicList = basicDao.search(Topic.class, "id", "asc");
		System.out.println("==获取题库列表成功,size==="+topicList.size());
		List<Topic> list = new ArrayList<Topic>();
		for(Topic topic : topicList){
			String subject = topic.getCourse();
			String catalog = topic.getCatalog();
			System.out.println(subject+"++");
			if(subject.equals("1")) topic.setSubjectName("语文");
			else topic.setSubjectName("其他");
			if(catalog.equals("1"))topic.setCatalogName("填空题");
			else if(catalog.equals("2"))topic.setCatalogName("选择题");
			else if(catalog.equals("3"))topic.setCatalogName("判断题");
			else if(catalog.equals("4"))topic.setCatalogName("改错题");
			else if(catalog.equals("5"))topic.setCatalogName("选词组词题");
			else if(catalog.equals("6"))topic.setCatalogName("选此组句题");
			else if(catalog.equals("7"))topic.setCatalogName("作文题");
			else if(catalog.equals("8"))topic.setCatalogName("临摹题");
			else if(catalog.equals("9"))topic.setCatalogName("临帖题");
			else if(catalog.equals("10"))topic.setCatalogName("闪现默写题");
			else if(catalog.equals("11"))topic.setCatalogName("听写题");
			else if(catalog.equals("12"))topic.setCatalogName("词语接龙");
			else if(catalog.equals("13"))topic.setCatalogName("成语接龙");
			else if(catalog.equals("14"))topic.setCatalogName("解答题");
			list.add(topic);
		}
		req.setAttribute("topicList", list);
		System.out.println("=========");
	}
}
