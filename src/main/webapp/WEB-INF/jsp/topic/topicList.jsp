<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/jsp/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>演示系统-题目列表</title>
<%@include file="/common/jsp/common.jsp"%>
<style>
.topic-list{
	width: 80%;
	height: auto;
	min-height: 400px;
	border:1px solid #ccc;
	margin:0 auto;
	margin: 0 auto;
}
h3{
	height: 40px;
	font-weight:100;
	line-height: 40px;
	margin-top: 20px;
}
.btn-group{
	height: 100px;
	margin-top:60px;
	line-height: 100px;
}
.style{line-height: 40px;}
.btn-group button{
	color: #fff;
	height: 35px;
	width:75%;
	margin: 5px auto;
}
.detail{
	background: #468CD2;
	border-color: #438CCF;
	border-right-style: none;
	border-bottom-style: none;
	cursor: pointer;
}
.show{
	background-color: #3fa156;
	border: 1px solid #528641;
	cursor: pointer;
}

</style>
</head>
<body>
	<div class="wrapper">
		<%@include file="/common/jsp/header.jsp" %>
		<div class="content">
			<div class="navbar">
				<ul class="nav">
					<li><a href="${ctx}/topic/${user.userId}/docList.htm">我的文档</a></li>
					<li><a href="${ctx}/topic/${docId}/topicTypeList.htm">文档题型列表</a></li>
					<li class="active"><a href="#">文档题目列表</a></li>
				</ul>
			</div>
			<div class="page-content df">
				<div class="left-side rb-style w7 lb pr20">
					<table class="tab-header">
						<tr>
							<th style="width: 10%;border-left:none;">编号ID</th>
							<th style="width: 10%;">题型</th>
							<th style="width: 30%;">题目内容</th>
							<th style="width: 20%;">答案</th>
							<th style="width: 10%;">得分</th>
							<th style="width: 20%;border-right:none;">提交时间</th>
						</tr>
					</table>
					<table class="tab-content">
						<c:choose>
							<c:when test="${topicList eq null}">
								<tr>
									<td colspan="7" style="width:100%;text-align:center;">暂时没有题库信息，请上传</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach var="topic" items="${topicList}">
									<tr class="tab-item">
										<td style="width: 10%;">${topic.id}</td>
										<td style="width: 10%;">${topic.catalogName}</td>
										<td style="width: 30%;">${topic.content}</td>
										<td style="width: 20%;">${topic.answer}</td>
										<td style="width: 10%;">${topic.score}分</td>
										<td style="width: 20%;">${topic.createTimeStr}</td>
										<td style="display:none;">${topic.docId}\/${topic.catalog}</td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</table>
				</div>
				<div class="right-side w3">
					<div class="topic-list tac">
						<h3>大题属性面板</h3>
						<div class="btn-group">
							<div class="row-container style">
								<button type="button" class="detail">查看详细数据</button>
							</div>
							<div class="row-container style">
								<button type="button" class="show">Html页面展示</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@include file="/common/jsp/footer.jsp" %>
	</div>
	<script type="text/javascript">
		$(function(){
			$("tr.tab-item").on("click", function(e){
				e.stopImmediatePropagation();//组织冒泡事件//tmp = $td.split("="),
				var $td = $(this).find("td:first-child").text(),
					$ld = $(this).find("td:last-child").text(),
					type = parseInt($td.split("=")[1]),
					id = parseInt($td),
					url = "${ctx}/topic/" + $ld +"/";
				if(type==0){
					alert('课文直接显示');
				} else 
				window.location.href = url + id + "/detail.htm";//跳转
			});
			$(".detail").on('click', function(){
				
			});
		});
	</script>
</body>
</html>