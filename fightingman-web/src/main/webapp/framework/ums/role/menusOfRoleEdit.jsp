<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String contextPath = request.getContextPath();%>
<%@ page language="java"  import="util.SpringUtil"%>
<%@ page language="java"  import="ums.model.Role"%>
<%@ page language="java"  import="ums.service.RoleService"%>
<%@ page language="java"  import="util.SpringUtil"%>
<%@ page language="java"  import="ums.model.User"%>
<%@ page language="java"  import="ums.service.UserService"%>
<%@ page language="java"  import="java.util.List"%>
<%
String roleId = request.getParameter("roleId");
RoleService roleService = RoleService.getInstance();
Role role = roleService.getRoleById(roleId);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=contextPath%>/library/js/zTree/zTreeStyle.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/library/css/style.css"/>
<script src="<%=contextPath%>/library/js/jquery/jquery-1.4.4.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=contextPath%>/library/js/zTree/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/library/js/zTree/jquery.ztree.excheck-3.5.js"></script>
<title>角色赋予用户</title>
</head>
<body>
<center>
	<form action="">
		<table>
			<tr>
				<td>
					<label>角色名称：</label>
					<input type="text" value="<%=role.getRoleName()%>">
				</td>
				<td>
					<div>
						<ul id="menuTree" class="ztree">
							
						</ul>
					</div>
				</td>
				<td>
					<input type="button" id="editMenusOfRoleBtn" value="确定">
				</td>
			</tr>
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
					pIdKey:"pId",
					rootPId:null,
					enable: true
				}
			}
		};

		var zNodes = [];
		$(function(){
			var roleId = '<%=role.getRoleId()%>';
			var url = '<%=contextPath%>/editMenusOfRole.json?roleId='+roleId;
			$.post(url,function(data){
				//将json对象转换成js识别的对象
				zNodes = data;
				var treeObj = $.fn.zTree.init($("#menuTree"), setting, zNodes);
				expandFirstNode(treeObj);
				grantMenuToRole(treeObj);
			},'json');
		});
		
		function expandFirstNode(treeObj){
			var nodes = treeObj.getNodes();
			if (nodes.length > 0) {
				treeObj.expandNode(nodes[0], true,false,false,false);
			}
		}
		
		function grantMenuToRole(treeObj) {
			var roleId = '<%=role.getRoleId()%>';
			$("#editMenusOfRoleBtn").click(function() {
				var nodes = treeObj.getCheckedNodes(true);
				if(nodes.length == 0||(nodes.length==1&&nodes[0].id=="root_menu")) {
					$("#result").html("请先选定权限");
					return;
				}
				var str = "?";
				for(var i = 0; i < nodes.length; i++) {
					//忽略根目录id
					if(nodes[i].id != "root_menu") {
						str = str+"menuId="+nodes[i].id+"&";
					}
				}
				var parameters = str+"roleId="+roleId;
				var url =  '<%=contextPath%>/menuGrantRole.json'+parameters;
				$.post(url,{}, function(json){
					var result = json.result;
					if(result) {
						$("#result").html("编辑角色权限成功！");
					}
				},"json");
			});
		}
	</script>
</body>
</html>