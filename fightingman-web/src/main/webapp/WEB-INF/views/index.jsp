<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix='sec' uri='http://www.springframework.org/security/tags' %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns:th="http://www.thymeleaf.org">
<head>
<jsp:include page="header.jsp"></jsp:include>
<title>管理平台</title>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false"
		style="background: #B3DFDA;">
		<div id="header-inner">
			<table cellpadding="0" cellspacing="0" style="width: 100%;">
				<tbody>
					<tr>
						<td rowspan="2" style="width: 20px;"></td>
						<td style="height: 52px;">
							<div style="color: #fff; font-size: 22px; font-weight: bold;">
								<a href="#"
									style="color: #000; font-size: 22px; font-weight: bold; text-decoration: none">管理平台</a>
							</div>
							<div style="color: #fff">
								<a href="#" style="color: #000; text-decoration: none"></a>
							</div>
						</td>
						<td
							style="padding-right: 5px; text-align: right;">
							<sec:authentication property="principal.username"></sec:authentication>
							<a href="#" style="color: #000; text-decoration: none"
									onclick="logout()">退出</a>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	 <form action="${ctx}/logout" method="post" id="logoutForm" style="display:none">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    </form>
	<div data-options="region:'west',split:true,title:'菜单'"
		style="width: 200px; padding: 10px;">
		<ul id="menuTree" class="ztree">
		</ul>
	</div>
	<!-- <div data-options="region:'east',split:true,collapsed:true,title:'East'" style="width:100px;padding:10px;">east region</div>
<div data-options="region:'south',border:false" style="height:50px;background:#A9FACD;padding:10px;">south region</div> -->
	<div id="content" data-options="region:'center',title:'操作界面'">
		<%-- <iframe id="iframe" name="bodyIFrame" src="${ctx}/workspace.jsp" width="100%" height="100%" frameborder="0" scrolling="auto"></iframe> --%>
		<div id="tt" class="easyui-tabs" style="width: 100%; height: auto">

			<div title="工作台"></div>

		</div>
	</div>
	<script type="text/javascript"
		src="${ctx}/static/js/zTree/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript"
		src="${ctx}/static/js/zTree/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript">
	var setting = {
			data: {
				key: {
					name:"name"
				},
				simpleData: {
					idKey:"id",
					pIdKey:"pId",
					rootPId:null,
					enable: true
				}
			},
			callback: {
				onClick:zTreeOnClick
			}
		};
		function zTreeOnClick(event, treeId, treeNode) {
			if(treeNode.functionUrl=="#"){
				return;
			}
			else{
				<%-- $("#iframe").attr("src",'${ctx}'+treeNode.functionUrl); --%>
				addTab(treeNode.name,'${ctx}'+treeNode.functionUrl);
				//open(treeNode);
			}
		};

		var zNodes = [];
		//zNodes = ${result};
		//'${xxx}'方式可以获取spring返回的数据
		var zNodes = jQuery.parseJSON('${result}');
		var treeObj = $.fn.zTree.init($("#menuTree"), setting, zNodes);
		expandFirstNode(treeObj);
		
		function expandFirstNode(treeObj){
			var nodes = treeObj.getNodes();
			if (nodes.length > 0) {
				treeObj.expandNode(nodes[0], true,false,false,false);
			}
		}
	   function open(treeNode) {
		   var href ='${ctx}'+treeNode.functionUrl;
	      $("#iframe").attr("src",href);
	    }
	   
	   function addTab(title, url){
		   var style = "width:100%;"+"height:"+($("#content").height()-50)+"px";
	        var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="'+style+'"></iframe>';
	        $('#tt').tabs('add',{
	            title:title,
	            content:content,
	            closable:true
	        });

		}
	   
	   function logout(){
	    	var result = confirm("确定退出吗?"); 
	    	if(result){
	    		document.getElementById("logoutForm").submit();
	    	}
	    }

	</script>
</body>
</html>