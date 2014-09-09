package com.topic.parserAdapter.controller.module;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.topic.parserAdapter.dao.BasicDao;
import com.topic.parserAdapter.model.User;
import com.topic.parserAdapter.utils.MyServletContext;

/**
 * 简单权限控制系统
 * 
 * @author Rayintee
 * 
 */
@At("/sys")
@IocBean
public class SystemController {

	@Inject
	private BasicDao basicDao;
	
	@At("/login")
	@Ok("jsp:jsp.system.login")
	@Fail("http:404")
	public void login() {
	}
	
	@At("/loginCheck")
	@Fail("http:500")
	public void loginCheck(@Param("..") User user, HttpServletRequest req, HttpServletResponse rep) 
			throws ServletException, IOException{
		boolean flag = false, isExist=false;//默认的为false
		//String baseUrl = MyServletContext.getBaseURL(req);//获取根路径
		List<User> u = basicDao.search(User.class, Cnd.where("user_id", "=", user.getUserId()));
		if(u.size() > 0){
			isExist = true;
			User uu = u.get(0);
			if(user.getUserPwd().equals(uu.getUserPwd())){
				flag = true;
			};
			System.out.println("-->用户登录成功");
		}
		System.out.println(user.getUserId());
		System.out.println(user.getUserPwd());
		if(isExist){
			if(flag){
				req.setAttribute("userId", user.getId());
				req.getRequestDispatcher("/topic/home.htm").forward(req, rep);
			} else {
				req.setAttribute("msg", "密码不正确");
				req.getRequestDispatcher("/sys/login.htm").forward(req, rep);
			}
		} else {
			req.setAttribute("msg", "用户不存在");
			req.getRequestDispatcher("/sys/login.htm").forward(req, rep);
		}
	}
	
}
