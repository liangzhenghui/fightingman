<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String contextPath = request.getContextPath();%>
<%@ page language="java"  import="util.SpringUtil"%>
<%@ page language="java"  import="ums.service.DepartmentService"%>
<%@ page language="java"  import="java.util.List"%>
<%@ page language="java"  import="ums.model.Department"%>
<%DepartmentService departmentservice = DepartmentService.getInstance();
	List departmentList = departmentservice.getAllDepartments();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	<link rel="stylesheet" type="text/css" href="<%=contextPath %>/library/css/style.css"/>
<title>新建机构</title>
</head>
<body>
<center>
	<form action="">
		<label>上级机构</label>
		<select id="pid" name="pid">
		<%
		int size = 	departmentList.size();
		%>
		 <option value ="">无上级机构</option>
		<%for(int i = 0 ; i < size; i++) {
			Department department =(Department)departmentList.get(i);
		%>
		  	<option value ="<%=department.getId()%>"><%=department.getName()%></option>
			<%}
		%>
		</select>
		<!--<label>是否发布</label><input type="radio">-->
		<label>机构名称</label>
		<input type="text" id="name" name="name">
		<input type = "button" id="createDepartmentBtn" value="创建机构">
		<div id="result"></div>
	</form>
</center>
<script language=javascript src="<%=contextPath%>/library/js/jquery/jquery-1.3.2.js"></script>
<script type="text/javascript">
$(function() {
	$("#createDepartmentBtn").click(function() {
		createDepartment();
	});
});
function createDepartment() {
	var name = $("#name").val().trim();
	if(name.length ==0) {
		$("#result").html("机构名不能为空");
		return;
	}
	var pid = $("#pid option:selected").val();
	var url = '<%=contextPath%>/departmentCreateServlet';
	var data = {
		pid:pid,
		name:name
	}
	$.post(url,data,function(json){
		var result = json.result;
		if(result) {
			$("#result").html("创建机构成功");
		}
		else {
			$("#result").html("创建机构失败");
		}
	},"json");
}
</script>
</body>
</html>