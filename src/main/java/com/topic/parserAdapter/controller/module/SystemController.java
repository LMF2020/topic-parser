package com.topic.parserAdapter.controller.module;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.topic.parserAdapter.model.Document;
import com.topic.parserAdapter.model.User;



/**
 * 简单权限控制系统
 * 
 * @author Rayintee
 * 
 */
@At("/sys")
@IocBean
public class SystemController extends BaseController {

	@At("/login")
	@Ok("jsp:jsp.system.login")
	@Fail("http:404")
	public void login(HttpServletRequest req) {
		System.out.println("msg-->"+(String)req.getAttribute("msg"));
	}
	
	@At("/loginout")
	@Ok("jsp:jsp.system.login")
	@Fail("http:404")
	public void loginout(HttpServletRequest req){
		HttpSession session = req.getSession();
		System.out.println("用户"+req.getSession().getAttribute("userId")+"退出");
		session.removeAttribute("userId");
	}

	@At("/?/home")
	@Ok("jsp:jsp.system.home")
	@Fail("http:404")
	public void toHome(@Param("userId") String userId, HttpServletRequest req) {
		System.out.println("userId-->" + userId);
		List<User> u = basicDao.search(User.class, Cnd.where("user_id", "=", userId));
		if(u.size()>0) {
			int count = basicDao.searchCount(Document.class, Cnd.where("user_id", "=", userId));
			System.out.println(userId + " 共上传了【" +count +"】篇文档");
			req.setAttribute("user", u.get(0));
			req.setAttribute("docCount", count);
		}
	}

	@At("/loginCheck")
	@Ok("forward:/sys/${obj}")
	@Fail("http:404")
	public String loginCheck(@Param("..") User user, HttpServletRequest req) {
		boolean flag = false, isExist = false;// 默认的为false
		String url = "login.htm";//默认跳装路径
		List<User> u = basicDao.search(User.class, Cnd.where("user_id", "=", user.getUserId()));
		if (u.size() > 0) {
			isExist = true;
			User uu = u.get(0);
			if (user.getUserPwd().equals(uu.getUserPwd())) {
				flag = true;
				System.out.println("-->用户登录成功");
			}
		}
		if (isExist) {
			if (flag) {
				url = user.getUserId()+ "/home.htm";
			} else req.setAttribute("msg", "密码不正确");
		} else req.setAttribute("msg", "用户不存在");
		return url;
	}

}
