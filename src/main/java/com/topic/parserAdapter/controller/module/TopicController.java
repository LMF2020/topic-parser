package com.topic.parserAdapter.controller.module;

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
		List<Topic> topicList = basicDao.search(Topic.class, "id");
		System.out.println("==获取题库列表成功,size==="+topicList.size());
		req.setAttribute("topicList", topicList);
	}
}
