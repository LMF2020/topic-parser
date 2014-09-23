package com.topic.parserAdapter.adapter.common;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.topic.parserAdapter.dao.BasicDao;

/**
 * 基本接口
 * @author Rayintee
 * 
 */
@InjectName
@IocBean
public class BasicAdapter {

	@Inject
	protected BasicDao basicDao;
}
