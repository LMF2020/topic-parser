package com.topic.parserAdapter.controller.module;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.impl.AdaptorErrorContext;
import org.nutz.mvc.upload.FieldMeta;
import org.nutz.mvc.upload.TempFile;
/**
 * 暴露给外部的服务
 * @author jiangzx0526@gmail.com
 *
 */
import org.nutz.mvc.upload.UploadAdaptor;
import org.nutz.trans.Atom;
import org.nutz.trans.Molecule;
import org.nutz.trans.Trans;

import com.topic.parserAdapter.core.office.parser.IdeaWordParser;
import com.topic.parserAdapter.dao.BasicDao;
import com.topic.parserAdapter.model.FileProperty;
import com.topic.parserAdapter.model.Topic;
/**
 * 提供给第三方的接口服务
 * @author jiangzx0526@gmail.com
 *
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
	@Ok("json")
	@Fail("http:500")
	@AdaptBy(type = UploadAdaptor.class, args = { "ioc:myUpload" })
	public String convert(@Param("..") FileProperty docInfo, @Param("office")  TempFile tf, 
			ServletContext sc, AdaptorErrorContext errCtx){
			if(errCtx != null){
				System.out.println("上传错误："+errCtx.getErrors()[0]);
			}
			if(docInfo != null){
				System.out.println(docInfo.getUuid()+"=="+docInfo.getSubject());
			}
			File docFile = tf.getFile();                 // 这个是保存的临时文件
		    FieldMeta meta = tf.getMeta();               // 这个原本的文件信息
		    String oldName = meta.getFileLocalName();    // 这个时原本的文件名称
		    String projectPath = sc.getRealPath("")+File.separatorChar;
		    //处理|转换文档
		    final List<Topic> topics = ideaWordParser.getTopicList(sc, projectPath, docFile, docInfo);
		    Molecule<Boolean> mol = new Molecule<Boolean>(){
				@Override
				public void run() {
					boolean flag = basicDao.saveBatch(topics);
					setObj(flag);
				}
		    };
		    Trans.exec(mol);
		    int code = 1; //失败码：1--失败、0表示成功
		    String msg = "上传题库失败";
		    if(mol.getObj()){
		    	code = 0;
		    	msg = "上传题库成功";
		    }
		    //打印测试
		    printDocList(topics);
		    //保存文档
		    
		    return "{CODE:" + code + ",MSG:" + msg +"}";
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
}
