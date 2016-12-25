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
<title>菜单赋予角色</title>
</head>
<body>
<center>
	<form action="">
		<table>
			<tr>
				<td>
					<label>角色名称：</label>
					<select id="roleName" name = "roleName">
						<%for(int i = 0;i <list.size(); i++) {
							Role role = (Role)list.get(i);
						%>
						<option value="<%=role.getRoleId()%>"><%=role.getRoleName() %></option>
						<%} %>
					</select>
				</td>
				<td>
					<div>
						<ul id="menuTree" class="ztree">
							
						</ul>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<input type="button" id="createRoleBtn" value="确定">
				</td>
			</tr>
			<tr>
				<td id="result"></td>
			</tr>
		</table>
	</form>
</center>
<script src="<%=contextPath%>/library/js/jquery/jquery-1.4.4.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=contextPath%>/library/js/zTree/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/library/js/zTree/jquery.ztree.excheck-3.5.js"></script>
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
			var url = '<%=contextPath%>/menuTree.do';
			$.post(url,function(data){
				//将json对象转换成js识别的对象
				zNodes = data;
				var treeObj = $.fn.zTree.init($("#menuTree"), setting, zNodes);
				expandFirstNode(treeObj);
				grantMenuToRole(treeObj);
			},"json");
		});
		
		function expandFirstNode(treeObj){
			var nodes = treeObj.getNodes();
			if (nodes.length > 0) {
				treeObj.expandNode(nodes[0], true,false,false,false);
			}
		}
		
		function grantMenuToRole(treeObj) {
			$("#createRoleBtn").click(function() {
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
				var parameters = str+"roleId="+$("#roleName option:selected").val();
				var url =  '<%=contextPath%>/menuGrantRole.json'+parameters;
				$.post(url, function(json){
					var result = json.result;
					if(result) {
						$("#result").html("赋予权限成功！");
					}
				},"json");
			});
		}
	</script>
</body>
</html>