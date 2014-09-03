package com.topic.parserAdapter.controller.module;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.eclipse.jetty.util.ajax.JSON;
import org.nutz.dao.Cnd;
import org.nutz.dao.sql.Criteria;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.impl.AdaptorErrorContext;
import org.nutz.mvc.upload.FieldMeta;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;
import org.nutz.trans.Molecule;
import org.nutz.trans.Trans;

import com.topic.parserAdapter.core.office.converter.Word2003ToHtmlConverter;
import com.topic.parserAdapter.core.office.parser.IdeaWordParser;
import com.topic.parserAdapter.dao.BasicDao;
import com.topic.parserAdapter.model.FileProperty;
import com.topic.parserAdapter.model.Topic;
/**
 * 提供给第三方的接口服务
 * @author jiangzx0526@gmail.com
 * @see http://localhost:8015/topic-parser/officeCenter/service/upload 上传文档并解析入库接口
 */
@At("/officeCenter")
@IocBean
public class ParseController {
	
	@Inject	
	private BasicDao basicDao;
	@Inject	
	private IdeaWordParser ideaWordParser;
	
	/**
	 * 本地开发的上传服务，文件保存到/doc目录下等待处理
	 * @param tf
	 */
	@At("/service/upload")
	@Ok("json:{quoteName:true, ignoreNull:true}")
	@Fail("http:500")
	@AdaptBy(type = UploadAdaptor.class, args = { "ioc:myUpload" })
	public String convert(@Param("fileProperty") FileProperty docInfo, @Param("office")  TempFile tf, 
			ServletContext sc, AdaptorErrorContext errCtx){

			if(errCtx != null){
				System.out.println("上传出错："+errCtx.getErrors()[0]);
			}
			/*if(docInfo != null){
				System.out.println(docInfo.getUuid()+"=="+docInfo.getSubject());
			}*/
			File tmpFile = tf.getFile();                 // 这个是保存的临时文件
		    FieldMeta meta = tf.getMeta();               // 这个原本的文件信息
		    String fileName = meta.getFileLocalName();   // 原始文件名称
		    String projectPath = sc.getRealPath("")+File.separatorChar;
		    try {
		    	//临时文件写入系统配置目录
		    	String filePath = projectPath+Word2003ToHtmlConverter.relativeFilePath+fileName;
		    	File tmp = new File(filePath);
		    	if(tmp.exists()) tmp.delete();
				Files.move(tmpFile, tmp);
			} catch (IOException e) {
				System.err.println("临时文件写入配置目录失败！");
			}
		    
		    //保存文档数据
		    docInfo.setFileName(fileName);
		    docInfo.setCreateTime(new Date());
		    docInfo = basicDao.save(docInfo);
		    //处理|转换文档
		    final List<Topic> topics = ideaWordParser.getTopicList(sc, projectPath, fileName, docInfo);
		    //printDocList(topics); //打印输出
		    Molecule<Boolean> mol = new Molecule<Boolean>(){
				@Override
				public void run() {
					boolean flag = basicDao.saveBatch(topics);
					setObj(flag);
				}
		    };
		    Trans.exec(mol);
		    int code = 1; //状态码：1失败、0成功
		    String msg = "上传题库失败";
		    if(mol.getObj()){
		    	code = 0;
		    	msg = "上传题库成功";
		    }
		    if(code == 1 && docInfo.getDocId()!=null){
		    	basicDao.delById(docInfo.getDocId().intValue(), FileProperty.class);
		    }
		   /* 
		    *   =》 another portable usage
		    * 	Map m = new HashMap();
		    	m.put("CODE", code);
		    	m.put("MSG", msg);
		    	return Json.toJson(m);
		    */
		    return "{\"CODE\":\"" + code + "\",\"MSG\":\"" + msg +"\"}";
	}
	
	/**
	 * 获取课文里的某一个大题目下的所有小题目(比如:选择题)
	 * @param docId		文档Id
	 * @param highNum   大题编号
	 * @return
	 */
	@At("/service/getLowByHighAndDoc/?/?")
	@GET
	@Ok("json")
	public List<Topic> getTopicByNumber(String docId , String highNum){
		//TODO:...
		return null;
	}
	
	/**
	 * 查询关键字是...的所有文档
	 */
	//TODO:....
	private void printDocList(List<Topic> topics){
		for(Topic t: topics){
			System.out.println(t.toString());
		}
	}
	
	@At("/service/queryTopics")
	@Ok("json:{quoteName:true, ignoreNull:true}")
	@Fail("http:500")
	@AdaptBy(type = JsonAdaptor.class)
	public String getTopicList(@Param("topic") Topic topic){
		Map<String, Object> m = new HashMap<String, Object>();
		List<Topic> tList = new ArrayList<Topic>();
		List<Map<String, Object>> lm = null;
		if(topic == null){
			tList = basicDao.search(Topic.class, "id", "asc");
		} else {
			Criteria cri = Cnd.cri();//复杂组合查询
			if(topic.getUserId() != null){
				cri.where().andEquals("user_id", topic.getUserId());
			}
			if(topic.getHours() != null){
				cri.where().andEquals("hours", topic.getHours());
			}
			if(topic.getSubject()!=null){
				cri.where().andEquals("subject", topic.getSubject());
			}
			if(topic.getCatalog() != null){
				cri.where().andEquals("catalog", topic.getCatalog());
			}
			if(topic.getClassName() != null){
				cri.where().andEquals("className", topic.getClassName());
			}
			tList = basicDao.search(Topic.class, cri);
		}
		m.put("CODE", 0);
		m.put("MSG", "查询题目成功");
		if(!tList.isEmpty()){
			lm = new ArrayList<Map<String, Object>>();
			for(Topic t:tList){
				Map<String, Object> mt = new HashMap<String, Object>();
				mt.put("id", t.getId());
				mt.put("lowNum", t.getLowNum());
				mt.put("catalog", t.getCatalog());
				mt.put("content", t.getContent());
				mt.put("answer", t.getAnswer());
				mt.put("score", t.getScore());
				mt.put("imgUrl", t.getImgUrl());
				mt.put("userId", t.getUserId());
				mt.put("hours", t.getHours());
				mt.put("className", t.getClassName());
				mt.put("createTime", t.getCreateTime());
				mt.put("subject", t.getSubject());
				mt.put("docId", t.getDocId());
				lm.add(mt);
			}
			m.put("LIST", lm);
		}
		return JSON.toString(m);
	}
	
	
	public static void main(String[] args) {
		try {
			Files.move(new File("D:/1/01.doc"), new File("D:/2/02.doc"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
