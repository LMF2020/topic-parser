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
@At("/jsp")
@IocBean
public class InnerController {
	
	@At("/file/upload")
	@Ok("jsp:jsp.topic.upload")
	@Fail("http:404")
	public void toUploadPage(){
	}
	
	
}
