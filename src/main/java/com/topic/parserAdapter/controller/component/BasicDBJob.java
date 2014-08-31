package com.topic.parserAdapter.controller.component;

import java.util.List;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.topic.parserAdapter.dao.BasicDao;
import com.topic.parserAdapter.model.Topic;

/**
 * 做一些html的转换过程中的数据库交互逻辑
 * @author jiangzx0526@gmail.com
 *
 */
@InjectName
@IocBean
public class BasicDBJob {
	
	@Inject
	private BasicDao basicDao;
	
	public Topic insertOne(Topic E){
		return basicDao.save(E);
	}
	
	public List<Topic> listAll(){
		return basicDao.search(Topic.class, "id");
	}
	
}
