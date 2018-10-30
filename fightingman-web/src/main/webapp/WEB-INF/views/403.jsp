<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String contextPath = request.getContextPath();%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="ctx" value="<%=contextPath%>" scope="request" />
<html>
<head>
<jsp:include page="header.jsp"></jsp:include>
<title>403</title>
</head>
<body>
	<center>
     	亲,你没权限哦~
     </center>
</body>
</html>