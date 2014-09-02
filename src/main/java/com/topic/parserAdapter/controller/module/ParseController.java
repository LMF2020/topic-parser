package com.topic.parserAdapter.controller.module;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
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
	@Fail("http:500")
	@Ok("json")
	@AdaptBy(type = UploadAdaptor.class, args = { "ioc:myUpload" })
	public boolean convert(@Param("..") FileProperty docInfo, @Param("office")  TempFile tf, ServletContext sc, AdaptorErrorContext errCtx){
			if(errCtx != null){
				System.out.println("上传错误："+errCtx.getErrors()[0]);
			}
			if(docInfo != null){
				System.out.println(docInfo.getUuid()+"=="+docInfo.getSubject());
			}
			File tmpFile = tf.getFile();                 // 这个是保存的临时文件
		    FieldMeta meta = tf.getMeta();               // 这个原本的文件信息
		    String fileName = meta.getFileLocalName();    // 这个时原本的文件名称
		    String projectPath = sc.getRealPath("")+File.separatorChar;
		    try {
		    	//临时文件写入系统配置目录
		    	String filePath = projectPath+Word2003ToHtmlConverter.relativeFilePath+fileName;
				Files.move(tmpFile, new File(filePath));
			} catch (IOException e) {
				System.err.println("临时文件写入配置目录失败！");
			}
		    //处理|转换文档
		    List<Topic> topics = ideaWordParser.getTopicList(sc, projectPath, fileName, docInfo);
		    //打印测试
		    printDocList(topics);
		    //保存文档
		    Molecule<Boolean> mc = new Molecule<Boolean>() {
		        public void run() {
		        	 //setObj(basicDao.save);;
		        }
		    };
		    Trans.exec(mc);
		    //这里判断是否成功
		    return mc.getObj();
		    
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
	
	
	public static void main(String[] args) {
		try {
			Files.move(new File("D:/1/01.doc"), new File("D:/2/02.doc"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
