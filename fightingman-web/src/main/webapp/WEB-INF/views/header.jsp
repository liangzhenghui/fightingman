<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='sec' uri='http://www.springframework.org/security/tags' %> 
<c:set var="ctx" value="<%=request.getContextPath() %>" scope="request" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${ctx}/static/css/easyui/themes/metro/easyui.css" rel="stylesheet" />
<link href="${ctx}/static/css/easyui/themes/color.css" rel="stylesheet" />
<link href="${ctx}/static/css/easyui/themes/icon.css" rel="stylesheet" />
<link href="${ctx}/static/js/zTree/zTreeStyle.css" rel="stylesheet" />
<script src="${ctx}/static/js/jquery/jquery-1.4.4.min.js" type="text/javascript"></script>
<script src="${ctx}/static/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${ctx}/static/js/easyui/easyui-lang-zh_CN.js" type="text/javascript"></script>
<script src="${ctx}/static/js/common.js" type="text/javascript"></script>
<sec:csrfMetaTags/>