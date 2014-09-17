<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="header">
	<a href="" class="logo">登录演示系统</a>
	<div class="rightBlock">
		<label>欢迎您，</label><a href="#"><c:if test="${user != null}">${user.userId}</c:if></a>
		<a href="${ctx}/sys/loginout" class="logout">
			<img alt="退出" src="${ctx}/common/images/logout.png">
		</a>
	</div>
</div>