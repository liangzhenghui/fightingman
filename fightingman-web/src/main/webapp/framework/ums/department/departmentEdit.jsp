<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page language="java"  import="util.SpringUtil"%>
<%@ page language="java"  import="ums.service.DepartmentService"%>
<%@ page language="java"  import="java.util.List"%>
<%@ page language="java"  import="ums.model.Department"%>
<%@ page language="java"  import="org.apache.commons.lang.StringUtils"%>
<%String contextPath = request.getContextPath();%>
<%
String id = request.getParameter("id");
DepartmentService departmentService = DepartmentService.getInstance();
Department department = departmentService.getDepartmentById(id);
List departmentList = departmentService.getAllDepartments();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/library/css/style.css"/>
<title>机构编辑</title>
</head>
<body>
	机构管理>>编辑机构
	<center>
		<form id="formId" action="">
			<label>上级机构</label>
		<!-- 获取上级机构的名字 -->
		<select id="pid" name = "pid">
		<% 
			String pid = department.getPid();
			String parentName = null;	
			if(StringUtils.isNotBlank(pid)) {
			    Department parentDepartment =departmentService.getDepartmentById(pid);
				if(parentDepartment != null) {
					parentName = parentDepartment.getName();%>
				<option value="<%=pid%>" selected="selected"><%=parentName%></option>
				<option value="">无上级机构</option>
				 <%for(int i = 0 ; i < departmentList.size(); i++) {
					 Department d =(Department)departmentList.get(i);
					if(!d.getId().equals(department.getId())&&!d.getId().equals(department.getPid())) {%>
					 	 <option value ="<%=d.getId()%>"><%=d.getName()%></option>
					<%}
				   }
				}
			}
			else {%>
				<option value="" selected="selected">无上级机构</option>
				<% for(int i = 0 ; i < departmentList.size(); i++) {
					Department d =(Department)departmentList.get(i);%>
					  <option value ="<%=d.getId()%>"><%=d.getName()%></option>
				<%}
			}
		%>
		</select>
		
		<!--<label>是否发布</label><input type="radio">-->
		<label>机构名称</label><input type="text" id="name" name = "name" value="<%=department.getName()%>">
		<input type="hidden" id="id" name="id" value="<%=department.getId()%>">
		<input type="button" id="editDepartmentBtn" value="编辑机构">
		</form>
		<div id="result"></div>
	</center>
	<script language=javascript src="<%=contextPath%>/library/js/jquery/jquery-1.3.2.js"></script>
	<script type="text/javascript">
		$(function() {
			$("#editDepartmentBtn").click(function() {
				editDepartment();
			});
		});
	
		function editDepartment() {
			var name = $("#name").val();
			var pid = $("#pid option:selected").val();
			var id = $("#id").val();
			var url = '<%=contextPath%>/departmentEditServlet';
			var data = {
				id:id,
				pid:pid,
				name:name
			}
			$.post(url,data,function(json){
				var result = json.result;
				if(result) {
					$("#result").html("编辑机构成功");
				}
				else {
					$("#result").html("编辑机构失败");
				}
			},"json");
		}
	</script>
</body>
</html>