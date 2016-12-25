<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String contextPath = request.getContextPath();%>
<%@ page language="java"  import="util.SpringUtil"%>
<%@ page language="java"  import="ums.model.Role"%>
<%@ page language="java"  import="ums.service.RoleService"%>
<%@ page language="java"  import="java.util.List"%>
<%RoleService roleService = RoleService.getInstance();
List list = roleService.getAllRoles();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/library/css/style.css"/>
<link href="<%=contextPath%>/library/js/zTree/zTreeStyle.css" rel="stylesheet" />
<link href="<%=contextPath%>/library/css/easyui/themes/metro/easyui.css" rel="stylesheet" />
<link href="<%=contextPath%>/library/css/easyui/themes/color.css" rel="stylesheet" />
<link href="<%=contextPath%>/library/css/easyui/themes/icon.css" rel="stylesheet" />
<link href="<%=contextPath%>/library/js/zTree/zTreeStyle.css" rel="stylesheet" />
<script src="<%=contextPath%>/library/js/jquery/jquery-1.4.4.min.js" type="text/javascript"></script>
<script src="<%=contextPath%>/library/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="<%=contextPath%>/library/js/easyui/easyui_extend_validate.js" type="text/javascript"></script>
<script src="<%=contextPath%>/library/js/common.js" type="text/javascript"></script>
<script src="<%=contextPath%>/library/js/easyui/easyui-lang-zh_CN.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=contextPath%>/library/js/zTree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=contextPath%>/library/js/zTree/jquery.ztree.excheck-3.5.js"></script>
<title>机构树</title>
</head>
<body>
<center>
	<form action="">
		<table>
			<tr>
				<td>
					<div>
						<ul id="departmentTree" class="ztree">
							
						</ul>
					</div>
				</td>
			</tr>
			<!-- <tr>
				<td>
					<input type="button" id="createRoleBtn" value="确定">
				</td>
			</tr> -->
			<tr>
				<td id="result"></td>
			</tr>
		</table>
	</form>
</center>
	<script type="text/javascript">
	var setting = {
			check: {
				enable: true,
				chkStyle: "checkbox",
				chkboxType: {"Y":"ps","N":"s"},
				open:true
			},
			data: {
				key: {
					name:"name"
				},
				simpleData: {
					idKey:"id",
					pIdKey:"pid",
					rootPId:null,
					enable: true
				}
			}
		};

		var zNodes = [];
		$(function(){
			var url = '<%=contextPath%>/departmentTree.do';
			$.post(url,{},function(data){
				//将json对象转换成js识别的对象
				zNodes = data;
				var treeObj = $.fn.zTree.init($("#departmentTree"), setting, zNodes);
				expandFirstNode(treeObj);
			},"json");
		});
		
		function expandFirstNode(treeObj){
			var nodes = treeObj.getNodes();
			if (nodes.length > 0) {
				treeObj.expandNode(nodes[0], true,false,false,false);
			}
		}
	</script>
</body>
</html>