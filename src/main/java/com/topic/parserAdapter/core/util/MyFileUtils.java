package com.topic.parserAdapter.core.util;

import java.io.File;

/**
 * 文件操作
 * @author jiangzx0526@gmail.com
 *
 */
public class MyFileUtils {

	/** 
     * 根据路径名生成多级路径 
     *  
     * @param url  参数要以"\classes\cn\qtone\"或者"/classes/cn/qtone/" 
     */  
    public static String makeDir(String root, String url) {  
        String[] sub;  
        url = url.replaceAll("\\/", "\\\\");  
        if (url.indexOf("\\") > -1) {  
            sub = url.split("\\\\");  
        } else {  
            return "-1";  
        }  
  
        File dir = null;  
        try {  
            dir = new File(root);  
            for (int i = 0; i < sub.length; i++) {  
                if (!dir.exists() && !sub[i].equals("")) {  
                    dir.mkdir();  
                }  
                File dir2 = new File(dir + File.separator + sub[i]);  
                if (!dir2.exists()) {  
                    dir2.mkdir();  
                }  
                dir = dir2;  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
            return "-1";  
        }  
        return dir.toString();  
    }  
    
    
}
