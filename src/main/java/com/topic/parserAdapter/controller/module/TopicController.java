package com.topic.parserAdapter.controller.module;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.impl.AdaptorErrorContext;

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
	 * 首页面
	 */
	@At("/index")
	@Ok("jsp:/index")
	@Fail("http:404")
	public void toIndex(){
	}
	
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
		List<Topic> list = null;
		if(topicList.size()>0){
			list = new ArrayList<Topic>();
			for(Topic topic : topicList){
				int subject = Integer.parseInt(topic.getSubject());
				int catalog = Integer.parseInt(topic.getCatalog().trim());
				if(subject==1) topic.setSubjectName("语文");
				else topic.setSubjectName("其他");
				switch (catalog) {
				case 1:
					topic.setCatalogName("填空题");
					break;
				case 2:
					topic.setCatalogName("选择题");
					break;
				case 3:
					topic.setCatalogName("判断题");
					break;
				case 4:
					topic.setCatalogName("改错题");
					break;
				case 5:
					topic.setCatalogName("选词组词题");
					break;
				case 6:
					topic.setCatalogName("选此组句题");
					break;
				case 7:
					topic.setCatalogName("作文题");
					break;
				case 8:
					topic.setCatalogName("临摹题");
					break;
				case 9:
					topic.setCatalogName("临帖题");
					break;
				case 10:
					topic.setCatalogName("闪现默写题");
					break;
				case 11:
					topic.setCatalogName("听写题");
					break;
				case 12:
					topic.setCatalogName("词语接龙");
					break;
				case 13:
					topic.setCatalogName("成语接龙");
					break;
				case 14:
					topic.setCatalogName("解答题");
					break;
				default:
					topic.setCatalogName("未知题型");
					break;
				}
				list.add(topic);
			}
		}
		
		req.setAttribute("topicList", list);
	}
	
	@At("/detail/?")
	@Ok("jsp:jsp.topic.detail")
	@Fail("http:500")
	public void toDetailPage(@Param("id") int id, HttpServletRequest req, AdaptorErrorContext errCtx){
		if(errCtx != null){
			System.out.println("跳转页面出错："+errCtx.getErrors()[0]);
		}
		System.out.println("id-->"+id);
		Topic topic = basicDao.find(id, Topic.class);//根据id查询
		Timestamp ts = new Timestamp(topic.getCreateTime().getTime());
		String str = ts.toString();
		System.out.println(str.substring(0, str.indexOf(".")));
		topic.setCreateTimeStr(str.substring(0, str.indexOf(".")));
		req.setAttribute("topic", topic);
	}
	
	@At("/detailOpen/?")
	@Ok("jsp:jsp.topic.detailOpen")
	@Fail("http:500")
	public void toDetailOpenPage(@Param("id") int id, HttpServletRequest req, AdaptorErrorContext errCtx){
		if(errCtx != null){
			System.out.println("跳转页面出错："+errCtx.getErrors()[0]);
		}
		System.out.println("id-->"+id);
		Topic topic = basicDao.find(id, Topic.class);//根据id查询
		Timestamp ts = new Timestamp(topic.getCreateTime().getTime());
		String str = ts.toString();
		System.out.println(str.substring(0, str.indexOf(".")));
		topic.setCreateTimeStr(str.substring(0, str.indexOf(".")));
		req.setAttribute("topic", topic);
	}
	
	@At("/query")
	@Ok("jsp:jsp.topic.query")
	@Fail("http:404")
	public void toQueryPage(){
	}
}
