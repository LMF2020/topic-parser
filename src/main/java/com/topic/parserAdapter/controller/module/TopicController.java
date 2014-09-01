package com.topic.parserAdapter.controller.module;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;

/**
 * 内部测试页面用到controller
 * @author Rayintee
 *
 */
@At("/topic")
@IocBean
public class TopicController {
	
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
	public void listTopics(){
		
	}
}
