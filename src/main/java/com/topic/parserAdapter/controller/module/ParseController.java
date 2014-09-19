package com.topic.parserAdapter.controller.module;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.sql.Criteria;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.Files;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.impl.AdaptorErrorContext;
import org.nutz.mvc.upload.FieldMeta;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;
import org.nutz.trans.Molecule;
import org.nutz.trans.Trans;

import com.topic.parserAdapter.core.office.converter.Word2003ToHtmlConverter;
import com.topic.parserAdapter.core.office.parser.IdeaWordParser;
import com.topic.parserAdapter.dao.TopicTypeDao;
import com.topic.parserAdapter.model.Document;
import com.topic.parserAdapter.model.Topic;
import com.topic.parserAdapter.model.TopicType;
/**
 * 提供给第三方的接口服务
 * @author jiangzx0526@gmail.com
 * @see http://localhost:8015/topic-parser/officeCenter/service/upload 上传文档并解析入库接口
 */
@At("/officeCenter")
@IocBean
public class ParseController {

	@Inject
	private TopicTypeDao topicTypeDao;
	
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
	public String convert(@Param("fileProperty") Document docInfo, @Param("office")  TempFile tf, 
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
		    docInfo = topicTypeDao.save(docInfo);
		    //处理|转换文档
		    final List<Topic> topics = ideaWordParser.getTopicList(sc, projectPath, fileName, docInfo);
		    //printDocList(topics); //打印输出
		    Molecule<Boolean> mol = new Molecule<Boolean>(){
				@Override
				public void run() {
					boolean flag = topicTypeDao.saveBatch(topics);
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
		    	topicTypeDao.delById(docInfo.getDocId().intValue(), Document.class);
		    }
		    return "{\"CODE\":\"" + code + "\",\"MSG\":\"" + msg +"\"}";
	}
	
	/**
	 * 根据用户id查询文档信息
	 * @param doc
	 * @param sc
	 * @param errCtx
	 * @return
	 */
	@At("/service/getDocList")
	@Ok("json")
	@Fail("http:500")
	public String getDocList(@Param("..") Document doc, AdaptorErrorContext errCtx){
		if(errCtx != null){
			System.out.println("查询文档信息出错："+errCtx.getErrors()[0]);
		}
		int code = 1; //状态码：1失败、0成功
	    String msg = "获取文档信息失败";
	    Map<String, Object> mm = new HashMap<String, Object>();
	    List<Document> docList = null;//文档列表
		if(doc != null && doc.getUserId() != null){
			System.out.println("查询用户【" + doc.getUserId() + "】所拥有的文档");
			Criteria cri = Cnd.cri();//复杂组合查询
			cri.where().andEquals("user_id", doc.getUserId());
			if(doc.getHours() != null){
				cri.where().andEquals("hours", doc.getHours());
			}
			if(doc.getSubject()!=null){
				cri.where().andEquals("subject", doc.getSubject());
			}
			if(doc.getClassName() != null){
				cri.where().andEquals("className", doc.getClassName());
			}
			docList = topicTypeDao.search(Document.class, cri);
		}
		if(docList !=null && docList.size()>0){
			code = 0; msg = "获取文档信息成功";
			List<Map<String, Object>> ml = new ArrayList<Map<String, Object>>();
			for(Document d : docList){
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("docId", d.getDocId());
				m.put("fileName", d.getFileName());
				m.put("userId", d.getUserId());
				m.put("school", d.getSchool());
				m.put("className", d.getClassName());
				ml.add(m);//添加list对象
			}
			mm.put("list", ml);
		}
		mm.put("code", code);
		mm.put("msg", msg);
		String ret = Json.toJson(mm);//转换成json
		System.out.println("返回报文-->\n"+ret);
		return ret;
	}
	
	@At("/service/getTopicList")
	@Ok("json:{quoteName:true, ignoreNull:true}")
	@Fail("http:500")
	public String getTopicList(@Param("..") Topic topic, AdaptorErrorContext errCtx){
		if(errCtx != null){
			System.out.println("查询文档内容出错："+errCtx.getErrors()[0]);
		}
		int code = 1; //状态码：1失败、0成功
	    String msg = "获取文档内容失败";
	    List<TopicType> ttList = null;
		Map<String, Object> m = new HashMap<String, Object>();
		List<Topic> tList = null;
		if(topic != null && topic.getDocId() != null){
			m.put("docId", topic.getDocId());
			System.out.println("用户查询ID=【" + topic.getDocId() + "】的文档内容信息");
			ttList = getTopicTypeList(topic.getDocId(), topic.getCatalog());
			System.out.println("查询题型结束，共计【" + ttList.size() + "】种题型");
			System.out.println("开始查询具体的题目列表--->");
			Criteria cri = Cnd.cri();//复杂组合查询
			cri.where().andEquals("doc_id", topic.getDocId());
			if(topic.getCatalog() != null){
				cri.where().andEquals("catalog", topic.getCatalog());
			}
			tList = topicTypeDao.search(Topic.class, cri);
			System.out.println("查询具体题目结束，共计【" + tList.size() + "】道题");
		}
		if(tList != null & tList.size()>0){
			code = 0; msg = "获取文档内容成功";
			List<Map<String, Object>> mml = new ArrayList<Map<String, Object>>();
			for(TopicType tt: ttList){
				Map<String, Object> mmt = new HashMap<String, Object>();
				List<Map<String, Object>> ml = new ArrayList<Map<String, Object>>();
				for(Topic t: tList){
					if(tt.getTopicTypeNum() == Integer.parseInt(t.getCatalog())){
						Map<String, Object> mt = new HashMap<String, Object>();
						mt.put("topicId", t.getId());
						mt.put("lowNum", t.getLowNum());
						mt.put("content", t.getContent());
						mt.put("answer", t.getAnswer());
						mt.put("score", t.getScore());
						mt.put("imgUrl", t.getImgUrl());
						ml.add(mt);
					}
				}
				mmt.put("topics", ml);
				mmt.put("catalog", tt.getTopicTypeNum());
				mmt.put("cataName", tt.getTopicType());
				mmt.put("title", tt.getTitle());
				mmt.put("topicsCount", tt.getTypeCount());
				mmt.put("fullScore", tt.getFullScore());
				mml.add(mmt);
			}
			m.put("list", mml);
		}
		m.put("code", code);
		m.put("msg", msg);
		String ret = Json.toJson(m);
		System.out.println("返回报文-->\n"+ret);
		return ret;
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
	 * 获取题型list，根据文档id和题型id
	 * @param docId
	 * @param catalog
	 * @return
	 */
	public List<TopicType> getTopicTypeList(Long docId, String catalog){
		String sql = "SELECT catalog as topicType,title,COUNT(catalog) as typeCount, fullScore,doc_id as docId from t_topic $condition GROUP BY catalog ORDER BY id ASC";
		String condition = "where doc_id=" + docId;
		if(catalog != null && catalog != "") condition += " and catalog='" + catalog + "'";
		System.out.println("sql-->"+sql+"\n condition-->"+condition);
		List<TopicType> tl = topicTypeDao.queryByNativeSql(TopicType.class, sql, condition);
		return tl;
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
	
	public static void main(String[] args) {
		try {
			Files.move(new File("D:/1/01.doc"), new File("D:/2/02.doc"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
