package com.topic.parserAdapter.core.office.converter;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.imageio.ImageIO;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.PicturesTable;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;

import com.topic.parserAdapter.core.util.MyFileUtils;
import com.topic.parserAdapter.core.util.Wmf2Png;

/**
 * 
 * Word2003转换器
 * @author jiangzx0526@gmail.com
 *
 */
public class Word2003ToHtmlConverter {
	
	//回车符
    private static final short ENTER_ASCII = 13;  
    //空格符
    private static final short SPACE_ASCII = 32;  
    //制表符
    private static final short TABULATION_ASCII = 9;  
    //htm文本
    private static String htmlText = "";  
    //工程目录
    private static String projectRealPath = "";  
    //临时文件路径
    public static String relativeFilePath ;
    public static String htmPath;
    //word文档名称 
    private static String wordName = "";  
    
    /** 
     * 读写文档中的图片 
     *  
     * @param pTable 
     * @param cr 
     * @throws Exception 
     */  
    private static void readPicture(PicturesTable pTable, CharacterRun cr) throws Exception {  
        // 提取图片  
        Picture pic = pTable.extractPicture(cr, false);  
        BufferedImage image = null;// 图片对象  
        // 获取图片样式  
        int picHeight = pic.getHeight() * pic.getVerticalScalingFactor() / 100;  
        int picWidth = pic.getHorizontalScalingFactor() * pic.getWidth() / 100;  
        if (picWidth > 500) {  
            picHeight = 500 * picHeight / picWidth;  
            picWidth = 500;  
        }  
        String style = " height='" + picHeight + "' width='" + picWidth + "'";  
  
        // 返回POI建议的图片文件名  
        String afileName = pic.suggestFullFileName();  
        //项目路径  
        String directory = htmPath + "images/" + wordName + "/";  
        MyFileUtils.makeDir(projectRealPath, directory);// 创建文件夹  
  
        int picSize = cr.getFontSize();  
        int myHeight = 0;  
  
        if (afileName.indexOf(".wmf") > 0) {  
            OutputStream out = new FileOutputStream(new File(projectRealPath + directory + afileName));  
            out.write(pic.getContent());  
            out.close();  
            afileName = Wmf2Png.convert(projectRealPath + directory + afileName);  
  
            File file = new File(projectRealPath + directory + afileName);  
  
            try {  
                image = ImageIO.read(file);  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
  
            int pheight = image.getHeight();  
            int pwidth = image.getWidth();  
            if (pwidth > 500) {  
                htmlText += "<img width ='"+pwidth +"' height='"+myHeight+"' src=\"" +projectRealPath+ directory + afileName + "\"/>";  
                //后期添加对style的处理
              /*  htmlText += "<img width ='"+pwidth +"' height='"+myHeight+"'  style='width:3px;height:4px'" + " src=\"" +projectRealPath+ directory  
                        + afileName + "\"/>";  */
            } else {  
                myHeight = (int) (pheight / (pwidth / (picSize * 1.0)) * 1.5);  
                htmlText += "<img width ='"+picSize * 1.5 +"' height='"+myHeight+"'  src=\"" +projectRealPath + directory + afileName + "\"/>";  
                //后期添加对style的处理
                /*htmlText += "<img    width ='"+picSize * 1.5 +"' height='"+myHeight+"'  style='vertical-align:middle;'" + " src=\"" +projectRealPath + directory + afileName + "\"/>"; */ 
            }  
  
        } else {  
            OutputStream out = new FileOutputStream(new File(projectRealPath + directory + afileName));  
            out.write(pic.getContent());  
            out.close();  
            // 处理jpg或其他（即除png外）  
            if (afileName.indexOf(".png") == -1) {  
                try {  
                    File file = new File(projectRealPath + directory + afileName);  
                    image = ImageIO.read(file);  
                    picHeight = image.getHeight();  
                    picWidth = image.getWidth();  
                    if (picWidth > 500) {  
                        picHeight = 500 * picHeight / picWidth;  
                        picWidth = 500;  
                    }  
                    style = " height='" + picHeight + "' width='" + picWidth + "'";  
                } catch (Exception e) {  
                    // e.printStackTrace();  
                }  
            }  
            htmlText += "<img " + style + " src=\"" +projectRealPath+ directory + afileName + "\"/>";  
        }  
        if (pic.getWidth() > 450) {  
            htmlText += "<br/>";  
        }  
    }  
    
    /**
     * 比较两个字符的样式
     * @param cr1
     * @param cr2
     * @return
     */
    private static boolean compareCharStyle(CharacterRun cr1, CharacterRun cr2) {  
        boolean flag = false;  
        if (cr1.isBold() == cr2.isBold() && cr1.isItalic() == cr2.isItalic()  
                && cr1.getFontName().equals(cr2.getFontName()) && cr1.getFontSize() == cr2.getFontSize()) {  
            flag = true;  
        }  
        return flag;  
    }  
    
    /** 
     * 读取每个文字样式 
     *  
     * @param fileName 
     * @throws Exception 
     */  
  
    private static void getWordAndStyle(String fileName) throws Exception {  
        FileInputStream in = new FileInputStream(new File(fileName));  
        HWPFDocument doc = new HWPFDocument(in);  
  
        //Range rangetbl = doc.getRange();// 得到文档的读取范围  
  
        // 取得文档中字符的总数  
        int length = doc.characterLength();  
        // 创建图片容器  
        PicturesTable pTable = doc.getPicturesTable();  
        // 创建段落容器  
  
        htmlText = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /><title>"  
                + doc.getSummaryInformation().getTitle()  
                + "</title></head><body><div style='margin:60px;text-align:center;'><div style='width:620px;text-align:left;line-height:24px;'>\n";  
        // 创建临时字符串,好加以判断一串字符是否存在相同格式  
        String tempString = "";  
        for (int i = 0; i < length - 1; i++) {  
            // 整篇文章的字符通过一个个字符的来判断,range为得到文档的范围  
            Range range = new Range(i, i + 1, doc);  
            CharacterRun cr = range.getCharacterRun(0);  
            //解析图片
            if (pTable.hasPicture(cr)) {  
                htmlText += tempString;  
                // 读写图片  
                try {  
                    readPicture(pTable, cr);  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
                tempString = "";  
            //解析字符
            } else {  
                Range range2 = new Range(i + 1, i + 2, doc);  
                // 第二个字符  
                CharacterRun cr2 = range2.getCharacterRun(0);  
                char c = cr.text().charAt(0);  
                // 判断是否为回车符  
                if (c == ENTER_ASCII) {  
                    tempString += "<br/>";  
                }  
                // 判断是否为空格符  
                else if (c == SPACE_ASCII)  
                    tempString += " ";  
                // 判断是否为水平制表符  
                else if (c == TABULATION_ASCII)  
                    tempString += "    ";  
                // 比较前后2个字符是否具有相同的格式  
                boolean flag = compareCharStyle(cr, cr2);  
                if (flag)  
                    tempString += cr.text();  
                else {  
                    String fontStyle = "<span style=\"font-family:" + cr.getFontName() + ";font-size:"  
                            + cr.getFontSize() / 2 + "pt;";  
  
                    if (cr.isBold())  
                        fontStyle += "font-weight:bold;";  
                    if (cr.isItalic())  
                        fontStyle += "font-style:italic;";  
                    if (cr.isStrikeThrough())  
                        fontStyle += "text-decoration:line-through;";  
  
                    int fontcolor = cr.getIco24();  
                    int[] rgb = new int[3];  
                    if (fontcolor != -1) {  
                        rgb[0] = (fontcolor >> 0) & 0xff; // red;  
                        rgb[1] = (fontcolor >> 8) & 0xff; // green  
                        rgb[2] = (fontcolor >> 16) & 0xff; // blue  
                    }  
                    fontStyle += "color: rgb(" + rgb[0] + "," + rgb[1] + "," + rgb[2] + ");";  
                    htmlText += fontStyle + "\">\n" + tempString + cr.text() + "</span>";  
                    tempString = "";  
                }  
            }  
        }  
  
        htmlText += tempString + "</div></div></body></html>";  
        // System.out.println(htmlText);  
    }  
  
    /** 
     * 写文件（成功返回true，失败则返回false） 
     *  
     * @param s  要写入的内容 
     * @param filePath  文件 
     */  
    private static boolean writeFile(String s, String filePath) {  
        FileOutputStream fos = null;  
        BufferedWriter bw = null;  
        s = s.replaceAll("EMBED", "").replaceAll("Equation.DSMT4", "");  
        try {  
        	MyFileUtils.makeDir(projectRealPath, htmPath);// 创建文件夹  
            File file = new File(filePath);  
            if (file.exists()) {  //删除历史编译的htm
                file.delete();
            }  
            fos = new FileOutputStream(file);  
            bw = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"));  
            bw.write(s);  
        } catch (FileNotFoundException fnfe) {  
            fnfe.printStackTrace();  
        } catch (IOException ioe) {  
            ioe.printStackTrace();  
        } finally {  
            try {  
                if (bw != null)  
                    bw.close();  
                if (fos != null)  
                    fos.close();  
            } catch (IOException ie) {  
                ie.printStackTrace();  
            }  
        }  
        return true;  
    }  
    
    /** 
     * 将word文档转化,返回转化后的文件路径 
     *  
     * @param projectPath  项目路径 
     * @param docFile  文件 
     * @return 返回生成的htm路径（如果出错，则返回null） 
     */  
    public static String parseWord2003ToHtml(String projectPath, String docFile) {  
        String resultPath = null;  
        //工程目录  
        projectRealPath = projectPath;
        //输入doc路径
        String docFilePath = projectPath + relativeFilePath + docFile;
        
        //输出htm路径
        String htmFilePath = "";  
        try {  
        	//doc文档
            File file = new File(docFilePath);  
            if (file.exists()) {  
                if (file.getName().indexOf(".doc") == -1 || file.getName().indexOf(".docx") > 0) {  
                    throw new FileFormatException("请确认文件格式为doc!");  
                } else {  
                    wordName = file.getName();  
                    wordName = wordName.substring(0, wordName.indexOf("."));  
  
                    htmFilePath = projectRealPath +  htmPath + wordName + ".htm";  
                    synchronized (docFilePath) {// 处理线程同步问题  
                        getWordAndStyle(docFilePath);  //doc文档的位置
                        writeFile(htmlText, htmFilePath);  
                    }  
                    resultPath = htmPath + wordName + ".htm";  
                }  
            } else {  
                throw new FileNotFoundException("没找到相关文件！");  
            }  
        } catch (NullPointerException e) {  
            e.printStackTrace();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return resultPath;  
    }  
    
}
