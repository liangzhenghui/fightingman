<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String contextPath = request.getContextPath();%>
<%@ page language="java"  import="util.SpringUtil"%>
<%@ page language="java"  import="fightingman.ums.service.RoleService"%>
<%@ page language="java"  import="java.util.List"%>
<%@ page language="java"  import="fightingman.ums.model.Role"%>
<%@ page language="java"  import="fightingman.ums.model.User"%>
<%@ page language="java"  import="fightingman.ums.service.UserService"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单列表</title>
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
</head>
<body>
<%RoleService roleService = RoleService.getInstance();
	List roleList = roleService.getAllRoles();
	UserService userService = UserService.getInstance();
	List userList = userService.getUsers();
%>
<br>
<table width="100%">
	<tr align="right">
		<td>
			<a href="#" onclick="newRole()">新建角色</a>
			<a href="<%=contextPath%>/framework/ums/menu/menuGrantRole.jsp">分配权限</a>
		<td>
	</tr>
</table>
	<div id="dlg" class="easyui-dialog" style="width:300px;height:120px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons" title="功能管理">
        <form id="fm" method="post" novalidate>
            <div class="fitem">
                <label>角色名称 :</label>
                <input name="roleName" class="easyui-validatebox" required="true" style="width:180px">
            </div>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="newRoleSubmit()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
    </div>
<center>
<table class="dataintable">
<tr>
	<th>角色名称</th>
	<th>权限</th>
	<th>操作</th>
</tr>
	<%for(int i = 0 ; i < roleList.size(); i++) {
		Role role =(Role)roleList.get(i);
	%>
		<tr class="function">
			<td>
				<%=role.getRoleName()%>
			</td>
			<td class="tree">
				<div>
					<ul id="<%=role.getRoleId()%>" class="ztree">
						
					</ul>
				</div>
				<input type="hidden" value="<%=role.getRoleId()%>">
			</td>
			<td>
				<a href="<%=contextPath%>/framework/ums/role/menusOfRoleEdit.jsp?roleId=<%=role.getRoleId()%>">编辑</a>
				<a href="javascript:void(0)" onclick="deleteRole('<%=role.getRoleId()%>',this)">删除</a>
			</td>
		</tr>
	<%}%>
</table>
</center>
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
		}
	};

	var zNodes = [];
	$(function(){
		$(".tree").each(function(){
			var roleId = $(this).find("input").val();
			var url = '<%=contextPath%>/menuTreeForOneRole.do?roleId='+roleId;
			$.post(url,function(data){
				//将json对象转换成js识别的对象
				zNodes = data;
				var treeObj = $.fn.zTree.init($('#'+roleId), setting, zNodes);
				expandFirstNode(treeObj);
			},"json");
		});
		
	});
	
	function expandFirstNode(treeObj){
		var nodes = treeObj.getNodes();
		if (nodes.length > 0) {
			treeObj.expandNode(nodes[0], true,false,false,false);
		}
	}
	
	function deleteRole(roleId,self) {
		if (!confirm("确认要删除？")) {
			return;
		}
		var url = '<%=contextPath%>/role-delete.json';
		var data = {
			roleId:roleId
		};
		$.post(url,data,function(json){
			var result = json.result;
			if(result) {
				$(self).parents("tr").remove();
				$.messager.show({
	                title: '提示',
	                msg: "删除成功"
	            });
			}
			else {
				$.messager.show({
	                title: '提示',
	                msg: "删除失败"
	            });
			}
		},"json");
	}
	function newRole(){
	    $('#dlg').dialog('open').dialog('setTitle','新建角色');
	    $('#fm').form('clear');
	}
	function newRoleSubmit(){
		var url = '<%=contextPath%>/roleCreate.json';
		var data = form2JsonStr("fm");
		var flag = $("#fm").form('validate');
		if(flag){
			$.post(url,{data:data},function(data){
				if(data.isExits){
					$.messager.show({
		                title: '提示',
		                msg: "已经存在该角色"
		            });
				}else{
					if(data.result){
						$.messager.show({
			                title: '提示',
			                msg: "创建成功"
			            });
						$('#dlg').dialog('close');// close the dialog
					}else{
						$.messager.show({
			                title: '提示',
			                msg: "创建失败"
			            });
					}
				}
			},"json");
		}
	}
</script>
</body>
</html>