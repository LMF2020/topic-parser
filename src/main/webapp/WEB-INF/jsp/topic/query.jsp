<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/jsp/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>题目查询页面</title>
<%@include file="/common/jsp/common.jsp"%>
<style>
.query-wrap{
	width: 100%;
	display: flex;
}
input[type="text"]{
	margin: 0;
	height: 30px;
	width: 276px;
	padding: 0 5px;
	line-height: 30px;
}
.oneRow{
	width: 100%;
	height: 40px;
	padding: 5px 0;
}
.oneRow span{
	float:left;
	padding: 10px 0;
	width:60px;
	text-align: right;
	padding-right: 15px;
}
.oneRow select {
	height: 34px;
	width: 290px;
	padding: 0 5px;
}
.oneRow button{
	margin: 10px;
	height: 35px;
	width: 120px;
}
.submitBtn{
	color: #fff;
	background-color: #428bca;
	border: 1px solid #357ebd;
	cursor: pointer;
	margin-left: 70px !important;
}
.restBtn{
	background-color: #EBEBEB;
	border: 1px solid #E1E1E1;
	cursor: pointer;
}
.show{
	display: block !important;
}
.loading{
	position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    z-index: 1000;
    background: rgba(255, 255, 255, 0.7);
    display: none;
}
.loading-logo{
	z-index:1001;
	position: relative;
	height: 150px;
	width: 150px;
	margin: 200px auto;
	background: url("${ctx}/common/images/loading.gif") no-repeat;
	background-size: 100% 100%; 
}
form{
	float: left;
	width: 40%;
}
.list{
	float:right;
	width:60%;
	border-left:1px solid #ccc;
	height: auto;
	min-height: 20px;
	padding:0 10px;
}
.wrap-header{
	background: #F7F7F7;
	border-radius: 2px;
	border: 1px solid #D2D2D2;
	color: #888;
	height: 41px;
	width: 100%;
}
tr{display: flex;}
th{
	height: 41px;
	line-height: 41px;
	border-left: 1px solid #fff;
	border-right: 1px solid #e5e5e5;
	float: left;
	white-space: nowrap;
	text-overflow: ellipsis;
	overflow: hidden;
	font-weight: 100;
}
.wrap-content{
	width: 100%;
	display: flex;
}
td{
	height: 38px;
	float: left;
	white-space: nowrap;
	text-overflow: ellipsis;
	overflow: hidden;
	text-align: center;
	line-height: 38px;
}
.wrap-item{
	border-bottom: 1px solid #D2D2D2;
	zoom:1;
}
.wrap-item:hover{
	background: #E6E6E6;
	cursor: pointer;
}
tbody,tr,thead {
	width: 100%;
}
</style>
</head>
<body>
	<h3 class="title">题目查询页面</h3>

	<div class="navbar">
		<ul class="nav">
			<li><a href="${ctx}/topic/index.htm">首页</a></li>
			<li><a href="${ctx}/topic/list.htm">题库列表</a></li>
			<li><a href="${ctx}/topic/upload.htm">上传题库</a></li>
			<li class="active"><a href="#">题目查询</a></li>
		</ul>
	</div>
	
	<div class="content">
		<div class="query-wrap">
			<form method="post" id="queryForm">
				<div class="oneRow">
					<span>上传者ID</span>
					<input type="text" name="userId" id="userId" value="teacher_01"/>
				</div>
				<div class="oneRow">
					<span>课 时</span>
					<input type="text" name="hours" id="hours" value="1" />
				</div>
				<div class="oneRow">
					<span>科 目</span>
					<select name="subject" id="subject">
						<option>--</option>
						<option value="1">语文</option>
					</select>
				</div>
				<div class="oneRow">
					<span>题 型</span>
					<select name="catalog" id="catalog">
						<option>--</option>
						<option value="1">填空题</option>
						<option value="2">选择题</option>
						<option value="3">判断题</option>
						<option value="4">改错题</option>
						<option value="5">选词组词题</option>
						<option value="6">选此组句题</option>
						<option value="7">作文题</option>
						<option value="8">临摹题</option>
						<option value="9">临帖题</option>
						<option value="10">闪现默写题</option>
						<option value="11">听写题</option>
						<option value="12">词语接龙</option>
						<option value="13">成语接龙</option>
						<option value="14">解答题</option>
					</select>
				</div>
				<div class="oneRow">
					<span>年 级</span>
					<select name="className" id="className">
						<option>--</option>
						<option value="1">一年级</option>
						<option value="2">二年级</option>
						<option value="3">三年级</option>
						<option value="3">三年级</option>
						<option value="4">四年级</option>
						<option value="5">五年级</option>
						<option value="6">六年级</option>
					</select>
				</div>
				<div class="oneRow">
					<button type="button" class="submitBtn">查询</button>
					<button type="reset" class="restBtn">重置</button>
				</div>
			</form>
			<div class="list">
				<table class="wrap-header">
					<tr>
						<th style="width: 10%;border-left:none;">ID</th>
						<th style="width: 15%;">科目</th>
						<th style="width: 20%;">题型</th>
						<th style="width: 35%;">题目内容</th>
						<th style="width: 20%;border-left:none;">答案</th>
					</tr>
				</table>
				<table class="wrap-content" id="wrapContent">
					<tr>
						<td colspan="5" style="text-align:center;">请在左侧面板查询</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	
	<div class="loading" id="loading"><div class="loading-logo"></div></div>
	
	<script type="text/javascript">
		$(function(){
			//表单提交前验证
			$(".submitBtn").on("click", function(e){
				e.stopImmediatePropagation();//组织冒泡事件
				var lenght = 0,
					params = $("#queryForm").serializeArray(),
					obj = new Object();
				$.each(params, function(i,v){
					if(!(v.name in obj) && (v.value != "" && v.value != "--")) {
						obj[v.name]= v.value;
						lenght += 1;
					}
				});
				/* if(length == 0) {
					alert("题目查询至少要有一个条件");
					return false;
				} */
				var topic = {"topic": obj};
				//alert(JSON.stringify(obj));
				//ajax上传
				$.ajax({
				    url: "http://localhost:8015/topic-parser/officeCenter/service/queryTopics",
				    type: 'post',
				    data: JSON.stringify(topic),
				    cache: false,
    				contentType:false,
				    timeout: 10*1000,//超时/ms
				    dataType: 'json',
				    processData: false,
				    beforeSend: function () {
				    	$("#loading").addClass("show");
                	}
                }).done(function(data){
                	console.log(data);
                	var obj = $.parseJSON(data),
                		htmlTemplate = "", 
                		list = obj.LIST||[];
                	$.each(list, function(i, v){
                		var tr='<tr class="wrap-item"><td style="width: 10%;">'+v["id"]
                			+'</td><td style="width: 15%;">'+v["subject"]+'</td><td style="width: 20%;">'
                			+v["catalog"]+'</td><td style="width: 35%;">'+v["content"]+'</td><td style="width: 20%;">'
                			+v["answer"]+'</td></tr>';
                			htmlTemplate += tr;
                	});
                	$("#wrapContent").html('').html(htmlTemplate);
                	return false;
                }).fail(function (data) {
                    alert("ajax请求失败");
                    return false;
                }).always(function( data, textStatus,jqXHR ){
                	setTimeout(function(){//做一下延迟，为了演示
                		$("#loading").removeClass("show");
                	},300);
                });
                
                $("#wrapContent").on("click", "tr.wrap-item", function(e){
					e.stopImmediatePropagation();//组织冒泡事件
					var $td = $(this).find("td:first-child"),
						url = "${ctx}/topic/detailOpen/";
					var id = parseInt($td.text());
					console.log(url+id+".htm");
					window.open(url + id + ".htm");//跳转
				});
                
                return false;
			});
		});
	</script>
</body>
</html>