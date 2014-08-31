package com.topic.parserAdapter.controller.module;

import java.io.File;
import java.util.List;

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

import com.topic.parserAdapter.core.office.parser.CoolHtmlParser;
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
	
	/*@Inject  //需要处理html的转换逻辑,所以注入CoolHtmlParser解析类
	private CoolHtmlParser htmlParser;*/
	
	@Inject	 //一些业务的接口,比如根据题号获取题目等
	private BasicDao basicDao;
	
	/**
	 * 本地开发的上传服务，文件保存到/doc目录下等待处理
	 * @param tf
	 */
	@At("/service/convert")
	@Fail("http:500")
	@AdaptBy(type = UploadAdaptor.class, args = { "ioc:myUpload" })
	public void convert(@Param("..") FileProperty fp, @Param("office")  TempFile tf, AdaptorErrorContext errCtx){
			if(errCtx != null){
				System.out.println("上传错误："+errCtx.getErrors()[0]);
			}
			if(fp != null){
				System.out.println(fp.getUuid()+"=="+fp.getSubject());
			}
			File f = tf.getFile();                       // 这个是保存的临时文件
		    FieldMeta meta = tf.getMeta();               // 这个原本的文件信息
		    String oldName = meta.getFileLocalName();    // 这个时原本的文件名称
		    //TODO:处理文档
		    //TODO:调用转换逻辑
		    //TODO:可以先模拟本地转换好的/doc/
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
	
}
