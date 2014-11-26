package com.topic.parserAdapter.core.listener;

import javax.servlet.ServletContext;

import org.nutz.dao.Dao;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

import com.topic.parserAdapter.core.office.converter.Word2003ToHtmlConverter;
import com.topic.parserAdapter.core.util.MyWebContext;
import com.topic.parserAdapter.model.Document;
import com.topic.parserAdapter.model.Sheet;
import com.topic.parserAdapter.model.Topic;
import com.topic.parserAdapter.model.User;
/**
 * 提供Office服务的启停状态/初始化庫表/同步配置文件等功能
 * @author jiangzx0526@gmail.com
 *
 */
public class ServerSetupListener implements Setup {
	
	@Override
	public void init(NutConfig nc) {
		Dao dao = nc.getIoc().get(null,"dao");
		dao.create(Topic.class, false);
		dao.create(Document.class, false);
		dao.create(User.class, false);
		dao.create(Sheet.class, false);
		PropertiesProxy prop = nc.getIoc().get(null, "config");
		ServletContext servletContext =  nc.getServletContext();
		Word2003ToHtmlConverter.relativeFilePath = prop.get("doc_Path");
		Word2003ToHtmlConverter.htmPath = prop.get("htm_Path");
		MyWebContext.init(servletContext, prop);
	}

	@Override
	public void destroy(NutConfig nc) {
		ServletContext servletContext =  nc.getServletContext();
		MyWebContext.destroy(servletContext);
	}
	
}
