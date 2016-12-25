<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page language="java"  import="ums.service.UserManager"%>
<%@ page language="java"  import="ums.model.User"%>
<%String contextPath = request.getContextPath();%>
<%-- <%OrderService orderService = OrderService.getInstance(); 
UserManager userManager = UserManager.getInstance();
User user = userManager.getLoginUser(request);
int count = 0;
if(null != user){
	count  = orderService.getCountByDeliveryPerson(user.getId());
}
%> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="keywords" content="jquery,ui,easy,easyui,web">
	<meta name="description" content="easyui help you build your web page easily!">
	<title>待办列表</title>
	<link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/themes/icon.css">
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="http://www.jeasyui.com/easyui/jquery.easyui.min.js"></script>
	<style type="text/css">
        #fm{
            margin:0;
            padding:10px 30px;
        }
        .ftitle{
            font-size:14px;
            font-weight:bold;
            padding:5px 0;
            margin-bottom:10px;
            border-bottom:1px solid #ccc;
        }
        .fitem{
            margin-bottom:5px;
        }
        .fitem label{
            display:inline-block;
            width:80px;
        }
    </style>
</head>
<body>
<%-- <div id="order" class="easyui-draggable easyui-resizable" data-options="handle:'#mytitle'" style="width:200px;height:150px;border:1px solid #ccc">
        <div id="mytitle" style="background:#ddd;padding:5px;">待处理订单</div>
        <div style="padding:40px;text-align:center;"><a href="<%=contextPath%>/wlgl/todolist/orderList.jsp?userId=<%=user.getId()%>" style="font-size:40px;text-decoration:none ;"><%=count%></a></div>
</div> --%>
</html>