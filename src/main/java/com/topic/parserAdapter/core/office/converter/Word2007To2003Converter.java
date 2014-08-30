package com.topic.parserAdapter.core.office.converter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.OfficeException;

import com.topic.parserAdapter.core.util.MyFileUtils;
import com.topic.parserAdapter.core.util.MyWebContext;

/**
 * 
 * Word2007+转换成Word2003版本
 * @author jiangzx0526@gmail.com
 *
 */
public class Word2007To2003Converter {
	
	/**
	 * 
	 * docx转换doc,名称相同只是把扩展名docx替换成了doc
	 * @param servletContext	ServletContext
	 * @param projectPath		工程目录	
	 * @param docFile	   		待转换的docx文件
	 * 
	 */
	public static void convert(ServletContext servletContext,String projectPath, String docFile){
		OfficeDocumentConverter documentConverter = MyWebContext.get(servletContext).getDocumentConverter();
		try {

			String relativeFilePath = Word2003ToHtmlConverter.relativeFilePath;
			String inputFileDir = projectPath + relativeFilePath; //doc目录
			MyFileUtils.makeDir(projectPath, relativeFilePath);   //如不存在,创建doc目录
			File inputFile = new File(inputFileDir + docFile);
			if(!inputFile.exists()){
				throw new FileNotFoundException("待转换的docx文件不存在！");
			}
			String inputFilePath = inputFileDir + docFile;
			String outputFilePath = inputFilePath.replaceAll(".docx", ".doc");  
			File outputFile = new File(outputFilePath);
			if(outputFile.exists()){
				outputFile.delete();
			}
			outputFile.createNewFile();
			
			long startTime = System.currentTimeMillis();
			documentConverter.convert(inputFile, outputFile);
			long conversionTime = System.currentTimeMillis() - startTime;
			System.err.println("转换word2007-2003耗时: "+ conversionTime/1000 + "秒");
			Thread.sleep(100);
		} catch (OfficeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
		}
	}

}