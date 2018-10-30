<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String contextPath = request.getContextPath();%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="ctx" value="<%=contextPath%>" scope="request" />
<html>
<head>
<jsp:include page="header.jsp"></jsp:include>
<title>用户登录</title>
</head>
<body>
	<center>
        <h2>超能系统</h2>
   <!--  <p>All things in their being are good for something.</p> -->
    <div style="margin:20px 0;"></div>
 <!--    <div class="easyui-panel" title="登陆" style="width:400px;padding:30px 60px">
        <div style="margin-bottom:20px">
            <div>账号:</div>
            <input class="easyui-textbox" type="text" value='liangzhenghui' id="userid" name="userid" data-options="required:true" style="width:100%;height:32px">
        </div>
        <div style="margin-bottom:20px">
            <div>密码:</div>
            <input class="easyui-textbox" type="password" value='123456' id="password" name="password" data-options="required:true" style="width:100%;height:32px">
        </div>
        <div>
            <a href="#" id="loginBtn" class="easyui-linkbutton"  style="float:left;width:100%;height:32px">登陆</a>
        </div>
         <div>
        </div>
    </div> -->
    <div id="login-box">

		<h3>Login with Username and Password</h3>

		<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="msg">${msg}</div>
		</c:if>
		<c:url value="/login" var="loginUrl"/>
		<form name='loginForm'
			action="${loginUrl}" method='POST'>
			<table>
				<tr>
					<td>User:</td>
					<td><input type='text' name='username'></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type='password' name='password' /></td>
				</tr>
				<tr>
					<td colspan='2'><input name="submit" type="submit"
						value="submit" /></td>
				</tr>
			</table>

			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />

		</form>
	</div>
	</center>
	<script type="text/javascript">
	$(function(){
		$("#loginBtn").click(function() {
			login();
		});
	});
	function login() {
		var userId = $("#userid").val();
		var password = $("#password").val();
		var url = '<%=contextPath%>/login.do';
		alert(url);
		if(!userId){
			$.messager.show({    // show error message
	            title: '提示',
	            msg: "请输入账号"
	        });
			return;
		}
		if(!password){
			$.messager.show({    // show error message
	            title: '提示',
	            msg: "请输入密码"
	        });
			return;
		}
		$.post(url,{userId:userId,password:password},function(json){
			var result =json.result;
			if(result) {
				$.messager.show({    // show error message
		            title: '提示',
		            msg: "登陆成功"
		        });
				window.location.href="<%=contextPath%>/index.jsp";
			}
			else {
				$.messager.show({    // show error message
		            title: '提示',
		            msg: "用户名或者密码错误"
		        });
			}
		},"json");
	}
	</script>
</body>
</html>