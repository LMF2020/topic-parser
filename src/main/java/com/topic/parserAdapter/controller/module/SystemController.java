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

	@At("/interface")
	@Ok("jsp:/index")
	@Fail("http:404")
	public void getInterface(HttpServletRequest req){
		System.out.println("查看接口规范...");
	}
	
	@At("/register")
	@Ok("jsp:jsp.system.register")
	@Fail("http:404")
	public void register(){
		System.out.println("page forward to register html");
	}
	
	@At("/login")
	@Ok("jsp:jsp.system.login")
	@Fail("http:404")
	public void login(HttpServletRequest req) {
		System.out.println("msg-->"+(String)req.getAttribute("msg"));
		String succ_msg = (String) req.getSession().getAttribute("succ_msg");
		System.out.println("succ_msg-->"+succ_msg);
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
		req.getSession().removeAttribute("succ_msg");
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
	
	@At("/doRegister")
	@Ok("forward:/sys/${obj}")
	@Fail("http:404")
	public String doRegister(@Param("..") User user, HttpServletRequest req){
		System.out.println(user.getUserId()+"||"+user.getSchool()+"||"+user.getUserPwd());
		int code = -1; String msg = "注册失败";
		String url = "register.htm";
		List<User> ul = basicDao.search(User.class, Cnd.where("user_id", "=", user.getUserId()));
		if(ul.size()>0){
			msg = "用户名已经存在";
			req.setAttribute("msg", msg);
			return url;
		}
		User u = basicDao.save(user);
		if(u!=null) {
			code = 0;
			msg = "注册成功,请登录";
			url = "login";
			req.getSession().setAttribute("succ_msg", msg);
		} else req.setAttribute("msg", msg);
		return url;
	}

}
