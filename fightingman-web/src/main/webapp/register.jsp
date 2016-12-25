<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String contextPath = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户注册</title>
</head>
<body>
	<center>
		<h1>注册</h1>
		<label>账号：</label>
		<input type="text" id="userId" name="userId">
		<label>密码：</label>
		<input type="password" id="password" name="password">
		<input type="button" id="registerBtn" value="注册">
		<br>
		<div id="result"></div>
	</center>
	<script src="<%=contextPath%>/library/js/jquery/jquery-1.3.2.js" type="text/javascript"></script>
	<script type="text/javascript">
	$(function(){
		$("#registerBtn").click(function() {
			register();
		});
	});
	function register() {
		var userId = $("#userId").val();
		var password = $("#password").val();
		var url = '<%=contextPath%>/register.json';
		$.post(url,{userId:userId,password:password},function(json){
			var result =json.result;
			if(result=="1") {
				$("#result").html("注册成功");
				window.location.href="<%=contextPath%>/login.jsp";
			}
			else {
				$("#result").html("注册失败");
			}
		},"json");
	}
	</script>
</body>
</html>