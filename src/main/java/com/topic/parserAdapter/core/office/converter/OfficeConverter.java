package com.topic.parserAdapter.core.office.converter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.OfficeException;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.topic.parserAdapter.core.util.MyFileUtils;
import com.topic.parserAdapter.core.util.MyWebContext;

/**
 * 
 * Office文档的转换,支持ppt,pptx,pdf,docx转换为doc格式
 * @author jiangzx0526@gmail.com
 *
 */
public class OfficeConverter {
	
	/**
	 * 
	 * 负责转换的实现
	 * @param servletContext	ServletContext
	 * @param projectPath		工程目录	
	 * @param docFile	   		待转换的docx文件
	 * 
	 */
	public static void convert2Doc(ServletContext servletContext,String projectPath, String docFile){
		OfficeDocumentConverter documentConverter = MyWebContext.get(servletContext).getDocumentConverter();
		try {

			String relativeFilePath = Word2003ToHtmlConverter.relativeFilePath;
			String inputFileDir = projectPath + relativeFilePath; //doc目录
			MyFileUtils.makeDir(projectPath, relativeFilePath);   //如不存在,创建doc目录
			String inputFilePath = inputFileDir + docFile;
			File inputFile = new File(inputFilePath);
			if(!inputFile.exists()){
				throw new FileNotFoundException("待转换的office文档不存在！");
			}
			int extensionPos = inputFilePath.lastIndexOf(".");
			int length = inputFilePath.length();
			String fileExtension = inputFilePath.substring(extensionPos, length).toLowerCase();
			String outputFilePath  = "";
			if(fileExtension.equals(".pdf")){
				outputFilePath = inputFilePath.replaceAll(fileExtension, ".docx");  
				//转换PDF文件
				covertPdf2Docx(inputFilePath, outputFilePath);  //inputFilePath ： pdf outputFilePath： docx
				//改变输入文件
				inputFile = new File(outputFilePath);
				//改变输出文件
				outputFilePath = inputFilePath.replaceAll(fileExtension, ".doc"); 
			}else if(fileExtension.equals(".docx") 
					|| fileExtension.equals(".ppt") || fileExtension.equals(".pptx")){ //格式转换
				outputFilePath = inputFilePath.replaceAll(fileExtension, ".doc");  
			}else{
				throw new FileNotFoundException("不支持当前格式的转换！");
			}
			//继续通过jdoConveter转换为doc'文件
			File outputFile = new File(outputFilePath);
			if(outputFile.exists()){
				outputFile.delete();
			}
			outputFile.createNewFile();
			
			long startTime = System.currentTimeMillis();
			documentConverter.convert(inputFile, outputFile);
			long conversionTime = System.currentTimeMillis() - startTime;
			System.err.println("转换Word2007到Word2003共耗时: "+ conversionTime/1000 + "秒");
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
	/**
	 * 转换pdf文件到Word2007
	 * @param inputFilePath
	 * @param outputFilePath
	 */
	 public static void covertPdf2Docx(String inputFilePath,String outputFilePath){
		 	long startTime = System.currentTimeMillis();
		 	FileOutputStream out = null;
		 	PdfReader reader = null;
			XWPFDocument doc = new XWPFDocument();
			String pdf = inputFilePath;
		  	try {
		  		reader = new PdfReader(pdf);
				PdfReaderContentParser parser = new PdfReaderContentParser(reader);
				for (int i = 1; i <= reader.getNumberOfPages(); i++) {
				    TextExtractionStrategy strategy = parser.processContent(i,new SimpleTextExtractionStrategy());
				    String text = strategy.getResultantText();
				    XWPFParagraph p = doc.createParagraph();
				    XWPFRun run = p.createRun();
				    run.setText(text);
				    run.addBreak(BreakType.PAGE);
				}
				out = new FileOutputStream(outputFilePath);
				doc.write(out);
				out.close();
				reader.close();
				long conversionTime = System.currentTimeMillis() - startTime;
				System.err.println("转换pdf文件到Word2007共耗时: "+ conversionTime/1000 + "秒");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					if(out!=null) out.close();
					if(reader!=null) reader.close();
				} catch (IOException e) {e.printStackTrace();}
			}
	 }
	 

}