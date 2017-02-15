<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String contextPath = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=contextPath%>/library/css/easyui/themes/metro/easyui.css" rel="stylesheet" />
<link href="<%=contextPath%>/library/css/easyui/themes/color.css" rel="stylesheet" />
<link href="<%=contextPath%>/library/css/easyui/themes/icon.css" rel="stylesheet" />
<script src="<%=contextPath%>/library/js/jquery/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="<%=contextPath%>/library/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="<%=contextPath%>/library/js/easyui/easyui-lang-zh_CN.js" type="text/javascript"></script>
<title>用户登录</title>
</head>
<body>
	<center>
        <h2>电商平台系统</h2>
   <!--  <p>All things in their being are good for something.</p> -->
    <div style="margin:20px 0;"></div>
    <div class="easyui-panel" title="登陆" style="width:400px;padding:30px 60px">
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
		var url = '<%=contextPath%>/login.json';
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