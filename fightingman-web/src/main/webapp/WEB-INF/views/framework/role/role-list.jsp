<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单列表</title>
<jsp:include page="../../header.jsp"></jsp:include>
</head>
<body>
	<div id="toolbar">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-add" plain="true" onclick="newRole()">新建角色</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-edit" plain="true" onclick="editRole()">编辑权限</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-remove" plain="true" onclick="deleteRole()">删除角色</a>
	</div>
	<table id="roleList" class="easyui-datagrid" url="${ctx}/role-list-data"
		method="get" title="角色管理" rownumbers="true" pagination="true"
		toolbar="#toolbar" singleSelect="true">
		<thead>
			<tr>
				<th field="roleName" width="200">角色名称</th>
				<!-- <th field="url" width="350">URL</th> -->
			</tr>
		</thead>
	</table>
	<%-- <table width="100%">
		<tr align="right">
			<td><a href="#" onclick="newRole()">新建角色</a> <a
				href="<%=contextPath%>/framework/ums/menu/menuGrantRole.jsp">分配权限</a>
			<td>
		</tr>
	</table> --%>
	<div id="dlg" class="easyui-dialog"
		style="width: 400px; height: 420px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons" title="编辑权限">
		<ul id="menuTree" class="ztree">

		</ul>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-ok" onclick="grantMenuToRole()">保存</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>
	<div id="dlg1" class="easyui-dialog"
		style="width: 300px; height: 150px; padding: 10px 20px" closed="true"
		buttons="#dlg1-buttons" title="新建角色">
		<form id="fm1" method="post" novalidate>
			<div class="fitem">
				<label>角色名称 :</label> <input name="roleName"
					class="easyui-validatebox" required="true" style="width: 200px">
			</div>
		</form>
	</div>
	<div id="dlg1-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-ok" onclick="newRoleSubmit()">保存</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg1').dialog('close')">取消</a>
	</div>
	<%-- <center>
		<table class="dataintable" style="width:100%">
			<tr>
				<th>角色名称</th>
				<th>权限</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${roleList}" var="item">
				<tr class="function">
					<td width="33%">${item.roleName}</td>
					<td><a
						href="<%=contextPath%>/framework/ums/role/menusOfRoleEdit.jsp?roleId=${item.roleId}">编辑</a>
						<a href="javascript:void(0)"
						onclick="deleteRole('${item.roleId}',this)">删除</a></td>
				</tr>
			</c:forEach>
		</table>
	</center> --%>
	<script type="text/javascript"
		src="${ctx}/static/js/zTree/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript"
		src="${ctx}/static/js/zTree/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript">
	var treeObj;
	var roleId;
	var setting = {
		check: {
			enable: true,
			chkStyle: "checkbox",
			chkboxType: {"Y":"ps","N":"s"},
			open:true
		},
		data: {
			key: {
				name:"menuName"
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
		function expandFirstNode(treeObj){
			var nodes = treeObj.getNodes();
			if (nodes.length > 0) {
				treeObj.expandNode(nodes[0], true,false,false,false);
			}
		}
		
		function grantMenuToRole() {
			var nodes = treeObj.getCheckedNodes(true);
			if(nodes.length == 0||(nodes.length==1&&nodes[0].id=="root_menu")) {
				$.messager.show({
	                title: '提示',
	                msg: "请先选定权限"
	            });
				return;
			}
			var str = "?";
			for(var i = 0; i < nodes.length; i++) {
				//忽略根目录id
				if(nodes[i].id != "root_menu") {
					str = str+"menuId="+nodes[i].id+"&";
				}
			}
			 var row = $('#roleList').datagrid('getSelected');
			var parameters = str;
			var url =  '${ctx}/menuGrantRole'+parameters;
		   	commonAjax({
				url : url,
				type : "POST",
				data : {roleId:row.roleId},
				dataType : "json"
			}, function(data) {
				if(!data.error){
            		$.messager.show({
		                title: '提示',
		                msg: "权限修改成功"
		            });
            		$('#dlg').dialog('close');// close the dialog
				}else{
					$.messager.show({
		                title: '提示',
		                msg: "权限修改失败"
		            });
				}
			});
		}
	function editRole(){
		 var row = $('#roleList').datagrid('getSelected');
		    if (row){
		        $('#dlg').dialog('open');
		        var roleId = row.roleId;
				var url = '${ctx}/editMenusOfRole';
				commonAjax({
					url : url,
					type : "POST",
					data : {roleId:roleId},
					dataType : "json"
				}, function(data) {
            		//将json对象转换成js识别的对象
					zNodes = data.result;
            		console.info(zNodes);
					treeObj = $.fn.zTree.init($("#menuTree"), setting, zNodes);
					expandFirstNode(treeObj);
				});
		    }else{
		    	$.messager.show({
	                title: '提示',
	                msg: "请至少选择一项"
	            });
		    }
	}
	function deleteRole(roleId,self) {
	    var row = $('#roleList').datagrid('getSelected');
	    if (row){
	    	if (!confirm("确认要删除？")) {
				return;
			}
			var url = '${ctx}/role-delete';
			var data = {
				roleId:row.roleId
			};
			commonAjax({
				url : url,
				type : "POST",
				data : data,
				dataType : "json"
			}, function(data) {
				if(!data.error){
            		$('#roleList').datagrid('reload');    // reload the user datac
    				$.messager.show({
    	                title: '提示',
    	                msg: "删除成功"
    	            });
				}else{
					$.messager.show({
		                title: '提示',
		                msg: "删除失败"
		            });
				}
			});
	    }
	    else{
	    	$.messager.show({
                title: '提示',
                msg: "请至少选择一项"
            });
	    }
		
	}
	function newRole(){
	    $('#dlg1').dialog('open').dialog('setTitle','新建角色');
	    $('#fm1').form('clear');
	}
	function newRoleSubmit(){
		var url = '${ctx}/role-create';
		var data = form2JsonStr("fm1");
		var flag = $("#fm1").form('validate');
		if(flag){
			commonAjax({
				url : url,
				type : "POST",
				data : {"data":data},
				dataType : "json"
			}, function(data) {
				if(data.error){
					$.messager.show({
		                title: '提示',
		                msg: "已经存在该角色"
		            });
				}else{
					$.messager.show({
		                title: '提示',
		                msg: "创建成功"
		            });
					$('#dlg1').dialog('close');// close the dialog
					$('#roleList').datagrid('reload');    // reload the user datac
				}
			});
		}
	}
</script>
</body>
</html>