package com.topic.parserAdapter.controller.module;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;

import com.topic.parserAdapter.dao.BasicDao;

/**
 * 基础类Controller
 * 
 * @author Rayintee
 * 
 */
@IocBean
public class BaseController {
	@Inject
	protected BasicDao basicDao;

	public void setBasicDao(BasicDao basicDao) {
		this.basicDao = basicDao;
	}
	/**
	 * 首页面
	 */
	@At({ "/index", "/" })
	@Ok("jsp:jsp.system.login")
	@Fail("http:404")
	public void toIndex() {
	}

}
