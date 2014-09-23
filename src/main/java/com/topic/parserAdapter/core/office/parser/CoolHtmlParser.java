package com.topic.parserAdapter.core.office.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Streams;
import org.nutz.lang.Strings;

import com.topic.parserAdapter.model.Document;
import com.topic.parserAdapter.model.Topic;

/**
 * Html文档解析
 * 			http://localhost:8015/topic-parser/doc/transferFile/images/%E8%AF%BE%E6%96%87/119fa.jpg
 * 解析规范的3种情况说明:
 *  	1.找题目类型 
 *  	2.找关键字<bd> 
 *  	3.判断是否标题换行,需要根据题目类型判断  
 *  	4.抽取题干(处理主要过程)
 *  	5.找关键字<end>(处理实体组装db)
 *  	6.保存列表
 *  	7.题号如(1、2、)与Tag<bd>允许在同行也可能不在同行,所以读取中与结束读都要做记录题号.
 *  
 * @author jiangzx0526@gmail.com
 *
 */
@InjectName
@IocBean
public class CoolHtmlParser {
	
	private static final String orderLine = "<课文>_0_<填空题>_1_<判断题>_3_<选择题>_2_<选词组词题>_5_<连词成句题>_6_<作文题>_7_<临摹题>_8_<临帖题>_9_<闪现默写题>_10_<听写题>_11_<词语接龙>_12_<成语接龙>_13_<解答题>_14_<改错题>_4_";
	private static final String chineseChars = "，。？、.";
	private static final Pattern typePattern;
	private static final Pattern numberPattern;
	private static final Pattern replacePattern;
	private String contextPath;
	
	//定义一些表达式参数
	static {
		String regEx1 = "<课文>|<填空题>|<选择题>|<判断题>|<选词组词题>|<连词成句题>|<作文题>|<临摹题>|<临帖题>|<闪现默写题>|<听写题>|<词语接龙>|<成语接龙>|<解答题>|<改错题>";
		String regEx2 = "<(\\d*?)>";
		String regEx3 = "[（(]([\\s\\S]*?)[）)]";
		typePattern = Pattern.compile(regEx1);
		numberPattern = Pattern.compile(regEx2);
		replacePattern = Pattern.compile(regEx3);
	}
	
	/**
	 * 返回文档集合对象交由dao处理
	 * @param contextPath 	服务器上下文路径
	 * @param htmlPath		htm相对路径
	 * @param docInfo		文档信息
	 * @return
	 */
	public List<Topic> parse(String contextPath,String htmlPath,Document docInfo){
		this.contextPath = contextPath;
		List<Topic> topics = new ArrayList<Topic>(50); //初始化一篇文档50道题
		File f = getFile(htmlPath);
		if(f == null){
			try {
				throw new FileNotFoundException("转换的.htm文件路径不存在");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		//开始解析
		StringBuffer sb= new StringBuffer("");
		Reader reader =  Streams.fileInr(f);
		BufferedReader br = new BufferedReader(reader);
		int count = 0;						//计数
		try {
			
			//文档信息
			Long   DdocId  = docInfo.getDocId();		//文档编号
			String DSchool = docInfo.getSchool();		//学校
			String Dclass = docInfo.getClassName();		//年级
			String Dsubject = docInfo.getSubject();		//科目
			String Dhours = docInfo.getHours();			//课时
			String DuserId = docInfo.getUserId();			//员工流水号
			Date   DcreateTime = docInfo.getCreateTime();	//上传时间
			
			//题目信息
			String str = "";				//文本行
		    String $score = "0";  			//小题分数
		    String $fullscore = "0";  		//小题总分
		    String $answer = "";			//小题答案:按顺序以逗号分隔
		    String $catalog = "";			//题型代码
		    String $placeholder = "";		//占位符
		    String $lowNum = "0";			//题号
		    String $content= "";			//内容
		    String $title = "";				//题目标题
		    String $imgsrc = "";			//图片地址:按顺序以逗号分隔	
		   
		    boolean onread = false;		//读取状态
		    
			while((str = br.readLine()) != null) {
			    
			    //读取文档完毕
			    if(str.indexOf("<end>") > -1){ //No<end>Tag时需要做容错处理?
			    	onread = false;
			    	break;
			    }
			    
			    //~~~开始读~~~
			    String cat = ofCatText(str);
			    if(cat!=null){
			    	//题目标题
			    	$title = str.substring(0,str.indexOf(cat)); //Todo:
			    	str = str.substring(str.indexOf(cat),str.length());
			    	Matcher mat = numberPattern.matcher(str);
			    	//分数
			    	if(mat.find()){$score = mat.group(1);}
			    	if(mat.find()){$fullscore = mat.group(1);}
			    	//题型枚举值
			    	$catalog = ofCatNum(cat);
			    	//题型表达式
			    	$placeholder = ofExpr($catalog);
			    	
			    	onread = true;
			    	continue;
			    }
			    
			    //~~~结束读~~~
			    if(str.indexOf("<bd>")>-1){ //容错处理:是否会有多个<bd>标签??
			    	//去题号
			    	String numChar = str.substring(0,1);
			    	if(StringUtils.isNumeric(numChar)){
			    		$lowNum = numChar;
			    		str = str.substring(1);
			    	}
			    	//去特殊符号
				    String signChar = str.substring(0,1);
				    if(chineseChars.contains(signChar)){
				    	str = str.substring(1);
				    }
			    	count++;
			    	onread = true;
			    	str = str.replace("<bd>", "").replace("", "");
			    	sb.append(str);
	//System.out.println("=====after complied=======\n"+str);
			    	$content = sb.toString();
			    	//去首尾空白
			    	$content = $content.trim();
			    	//答案
			    	$answer = answer($content,$placeholder);
			    	//编译
			    	$content = compile($content,$placeholder);
			    	//图片
			    	//$imgSrc = image();
			    	//链表
					Topic topic = new Topic($catalog,$content,$answer,$score,$lowNum,$fullscore,$imgsrc);
					topic.setTitle($title);
					topic.setDocId(DdocId);
					topic.setUserId(DuserId);
					topic.setSchool(DSchool);
					topic.setClassName(Dclass);
					topic.setSubject(Dsubject);
					topic.setHours(Dhours);
					topic.setCreateTime(DcreateTime);
					topics.add(topic);
			    	//重置buffer
			    	sb.setLength(0);
			    	continue;
			    }
			    
			    //~~~读取中~~~
			    if(onread){
			    	//去题号
			    	String numChar = str.substring(0,1);
			    	if(StringUtils.isNumeric(numChar)){
			    		$lowNum = numChar; //记录题号,为什么题号木有去掉??
			    		str = str.substring(1);
			    	}
			    	//去特符
				    String signChar = str.substring(0,1);
				    if(chineseChars.contains(signChar)){
				    	str = str.substring(1);
				    }
				    //拼接
				    sb.append(str); 
			    }
			}
			
			br.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			System.err.println("本片文档共有("+count+")道题目");
		}
		return topics;
		
	}
	
	//获取文件
	private File getFile(String htmlPath){
		File f = new File(htmlPath);
		if(f.exists() && f.getName().indexOf(".htm")>-1){
			return f;
		}
		return null;
	}
	
	//题型文本<如,填空题,选择题...>
	private String ofCatText(String str){
		Matcher mat = typePattern.matcher(str);
		boolean find = mat.find();
		if(find){
			return mat.group();
		}
		return null;
	}
	
	//题型代码<如,填空题 =1,选择题 =2...>,题型不能多于99种,如果多于99种需要修改此处代码
	private String ofCatNum(String str){
		int pos = orderLine.indexOf(str);
		int start = pos + str.length() + 1;
		if(orderLine.charAt(start+1) == '_'){
			return orderLine.substring(start , start + 1);
		}
		return orderLine.substring(start , start + 2);
	}
	
	/**
	 * 根据题型代码获取本题占位符
	 * ${space}空格 - 		2选择题
	 * ${matts}田字格 -  	8临摹题/9临帖题/10闪现默写题/11听写题/12词语接龙/13成语接龙
	 * ${square}方正格子 - 	1填空题/3判断题/4改错题/5选词组词题/6连词成句题/
	 * @param str	$catalog
	 * @return		字典值
	 */
	private String ofExpr(String str){
		String pattern = "";
		int ch = Integer.parseInt(str);
		switch (ch) {
			case 2:
				pattern = "${space}";
				break;
			case 8:case 9:case 10:case 11:case 12:case 13:
				pattern = "${matts}";
				break;
			case 1:case 3:case 4:case 5:case 6:
				pattern = "${square}";
				break;
			default:
				pattern = "";
				break;
		}
		return pattern;
	}
	
	//字串变量替换(包含图片地址转换)
	private String compile(String str,String $placeholder){
		//如果有图片，替换图片地址
		//TODO:如果一行存在多个img路径，这句代码将无效，考虑使用jsoup去做
		if(str.indexOf("<img") > -1){
			int startImgPos = str.indexOf("src=");
			int endImgPos =str.indexOf("doc/");
			str = str.replace(str.substring(startImgPos+5,endImgPos-1),"${server}"+this.contextPath)
					.replace("\"", "'")  //清除src路径双引号
					.replace("\b", "");  //清楚\b标签
		}
		//如果无占位符无需替换文本
		if(Strings.isBlank($placeholder)){
			return str;
		}
		$placeholder = $placeholder.replace("$", "\\$");
		Matcher mat = replacePattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (mat.find()) {
			String replacement = "";
			int count = mat.group(1).length();
			for(int i=0;i<count;i++){
				replacement += $placeholder;
			}
			mat.appendReplacement(sb, replacement);
		}
		mat.appendTail(sb);
		return sb.toString();
	}
	
	//答案
	private String answer(String str,String $placeholder){
		String answer = "";
		int count = 0;
		if(!Strings.isBlank($placeholder)){
			Matcher mat = replacePattern.matcher(str);
			while (mat.find()) {
				if(count>0){
					answer += ","+mat.group(1);
				}else{
					answer = mat.group(1);
				}
				count ++;
			}
		}
		return answer;
	}
	
	public static void main(String[] args) {
//		List<Topic> topics = new  CoolHtmlParser().parse(
//				"D:/tools/jee-eclipse-keeper/play/topic-parser/src/main/webapp/doc/transferFile/白板语文题库.htm",new Document());
//		for(Topic t:topics){ //输出所有题目
//			System.out.println(t.toString());
//		}
		String str = "<img  style='height:375px;width:500px' src=\"D:\\tools\\jee-eclipse-keeper\\play\\topic-parser\\/doc/transferFile/images/课文/119fa.jpg\"/>　　路上只我一个人，背着手踱着。这一片天地好像是我的；我也像超出了平常的自己，到了另一世界里。我爱热闹，也爱冷静；爱群居，也爱独处。像今晚上，一个人在这苍茫的月下，什么都可以想，什么都可以不想，便觉是个自由的人。白天里一定要做的事，一定要说的话，现在都可不理。这是独处的妙处，我且受用这无边的荷香月色好了。　　曲曲折折的荷塘上面，弥望的是田田的叶子。叶子出水很高，像亭亭的舞女的裙。层层的叶子中间，零星地点缀着些白花，有袅娜地开着的，有羞涩地打着朵儿的；正如一粒粒的明珠，又如碧天里的星星，又如刚出浴的美人。微风过处，送来缕缕清香，仿佛远处高楼上渺茫的歌声似的。这时候叶子与花也有一丝的颤动，像闪电般，霎时传过荷塘的那边去了。叶子本是肩并肩密密地挨着，这便宛然有了一道凝碧的波痕。叶子底下是脉脉的流水，遮住了，不能见一些颜色；而叶子却更见风致了。<br/>";
		if(str.indexOf("<img") > -1){
			int startImgPos = str.indexOf("src=");
			int endImgPos =str.indexOf("doc/");
			str = str.replace(str.substring(startImgPos+5,endImgPos-1),"/topic_parser");
		}
		System.out.println(str);
	}
}
