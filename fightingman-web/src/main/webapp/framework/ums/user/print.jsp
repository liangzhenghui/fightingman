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
<link href="<%=contextPath%>/library/js/zTree/zTreeStyle.css" rel="stylesheet" />
<script src="<%=contextPath%>/library/js/jquery/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="<%=contextPath%>/library/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="<%=contextPath%>/library/js/easyui/easyui_extend_validate.js" type="text/javascript"></script>
<script src="<%=contextPath%>/library/js/common.js" type="text/javascript"></script>
<script src="<%=contextPath%>/library/js/easyui/easyui-lang-zh_CN.js" type="text/javascript"></script>
<script language="javascript" src="<%=contextPath%>/library/js/lodop/LodopFuncs.js"></script>
<object  id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0> 
       <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
</object>
</head>
<a href="#" onclick="myAddHtml()">直接打印</a>
<body>
<script type="text/javascript">
var LODOP; //声明为全局变量  
$(function(){
	CheckIsInstall();
	setTimeout(f,2000);
});
function CheckIsInstall() {	 
	try{ 
	     var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM')); 
	     //if ((LODOP!=null)&&(typeof(LODOP.VERSION)!="undefined")) alert("本机已成功安装过Lodop控件!\n  版本号:"+LODOP.VERSION); 
	 }catch(err){ 
		//alert("Error:本机未安装或需要升级!"); 
	} 
}; 

var str =
'<table border="1" width="360" height="220" style="border-collapse:collapse;border:solid 1px" bordercolor="#000000">'
+'<tr>'
  +'<td width="100%" height="240">'
    +'<p align="center">' 
    +'<font face="隶书" size="5" style="letter-spacing: 10px">郭德强</font>'
    +'<p align="center"><font face="宋体" size="3">科学家</font></p>'
    +'<p align="left"><font face="宋体" size="3">　地址：中国北京社会科学院附近东大街西胡同</font></p>'
    +'<p align="left"><font face="宋体" size="3">　电话：010-88811888</font></p>'
    +'<p><br>'
    +'</p>'
  +'</td>'
+'</tr>'
+'</table>';
function myAddHtml() {       
	LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));         
	LODOP.PRINT_INIT("");		            
	LODOP.ADD_PRINT_HTM(10,55,"100%","100%",str);	
	//LODOP.PREVIEW();
	var result = LODOP.PRINT();
	console.log(result);
};
function f(){
	for(var i = 0;i<10;i++){
		myAddHtml();
	}
	setTimeout(f,2000);
}
setTimeout(f,2000);
</script>
</html>