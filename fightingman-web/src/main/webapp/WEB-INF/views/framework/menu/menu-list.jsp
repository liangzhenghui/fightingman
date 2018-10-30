<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="../../header.jsp"></jsp:include>
<style type="text/css">
#fm {
	margin: 0;
	padding: 10px 30px;
}

.ftitle {
	font-size: 14px;
	font-weight: bold;
	padding: 5px 0;
	margin-bottom: 10px;
	border-bottom: 1px solid #ccc;
}

.fitem {
	margin-bottom: 5px;
}

.fitem input {
	width: 300px
}

.fitem label {
	display: inline-block;
	width: 80px;
}

.selectInValid {
	border-color: #ffa8a8;
	background-color: #fff3f3;
	color: #404040;
}
</style>
</head>
<body>
	<div id="toolbar">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-add" plain="true" onclick="newMenu()">新建菜单</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-edit" plain="true" onclick="editMenu()">编辑菜单</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-remove" plain="true" onclick="destroyMenu()">删除菜单</a>
	</div>
	<table id="menuList" class="easyui-datagrid"
		url="${ctx}/menu-list-data" title="菜单管理" method="get"
		rownumbers="true" pagination="true" toolbar="#toolbar"
		singleSelect="true">
		<thead>
			<tr>
				<th field="menuName" width="200">菜单名称</th>
				<th field="parentMenuZw" width="350">上级菜单</th>
				<th field="menuCode" width="350">菜单代码</th>
				<th field="menuTypeZw" width="350">菜单类型</th>
			</tr>
		</thead>
	</table>
	<div id="dlg" class="easyui-dialog"
		style="width: 600px; height: 300px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons" title="菜单管理">
		<form id="fm" method="post" novalidate>
			<div class="fitem">
				<label>菜单名称 :</label> <input name="menuName"
					class="easyui-validatebox" required="true">
			</div>
			<div class="fitem">
				<label>上级菜单 :</label> <input id="parentId"
					class="easyui-validatebox" name="parentId" value="">
			</div>
			<div class="fitem">
				<label>引用功能</label> <input id="functionId"
					class="easyui-validatebox" name="functionId">
			</div>
			<div class="fitem">
				<label>菜单代码</label> <input id="menuCode" class="easyui-validatebox"
					required="true" name="menuCode">
			</div>
			<div class="fitem">
				<label>菜单类型</label> <input id="menuType" name="menuType" value="">
			</div>
			<input type="hidden" id="menuTypeZw" name="menuTypeZw"> <input
				type="hidden" id="parentMenuZw" name="parentMenuZw"> <input
				type="hidden" id="functionZw" name="functionZw"> <input
				type="hidden" id="id" name="id">
		</form>
	</div>
	<div id="dlg-buttons">
		<a id="create" href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-ok" onclick="newMenuSubmit()">保存</a> <a id="edit"
			href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"
			onclick="editMenuSubmit()">保存</a> <a href="javascript:void(0)"
			class="easyui-linkbutton" iconCls="icon-cancel"
			onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>
	<script type="text/javascript">
		var url;
		var pid;
		function newMenu() {
			$("#edit").hide();
			$("#create").show();
			$('#dlg').dialog('open').dialog('setTitle', '新建菜单');
			$('#fm').form('clear');
			pid = null;
			$('#parentId').combobox(
					{
						url : '${ctx}/getParentMenuSelectWhileCreate',
						method : 'get',
						valueField : 'code',
						textField : 'detail',
						onSelect : function(record) {
							$('#parentId').next()
									.children("input[type='text']")
									.removeClass('selectInValid');
							$("#parentMenuZw").val(record.detail);
						}
					});
			$('#functionId').combobox({
				url : '${ctx}/getFunctionSelectWhileCreate',
				method : 'get',
				valueField : 'code',
				textField : 'detail',
				onSelect : function(record) {
					/* $('#functionId').next().children("input[type='text']").removeClass('selectInValid'); */
					$("#functionZw").val(record.detail);
				}
			});
			$('#menuType').combobox(
					{
						url : '${ctx}/getMenuTypeWhileCreate',
						method : 'get',
						valueField : 'code',
						textField : 'detail',
						onSelect : function(record) {
							$('#menuType').next()
									.children("input[type='text']")
									.removeClass('selectInValid');
							$("#menuTypeZw").val(record.detail);
						}
					});
		}
		function editMenu() {
			$('#fm').form('clear');
			$("#edit").show();
			$("#create").hide();
			var row = $('#menuList').datagrid('getSelected');
			if (row) {
				$('#dlg').dialog('open').dialog('setTitle', '编辑菜单');
				$('#fm').form('load', row);
				pid = row.parentId;
				$('#parentId').combobox(
						{
							url : '${ctx}/getParentMenuSelectWhileEdit?pid='
									+ pid,
							method : 'get',
							valueField : 'code',
							textField : 'detail',
							onSelect : function(record) {
								$('#parentId').next().children(
										"input[type='text']").removeClass(
										'selectInValid');
								$("#parentMenuZw").val(record.detail);
							}
						});
				$('#functionId').combobox({
					url : '${ctx}/getFunctionSelectWhileEdit?menuId=' + row.id,
					method : 'get',
					valueField : 'code',
					textField : 'detail',
					onSelect : function(record) {
						/* $('#functionId').next().children("input[type='text']").removeClass('selectInValid'); */
						$("#functionZw").val(record.detail);
					}
				});
				$('#menuType').combobox(
						{
							url : '${ctx}/getMenuTypeWhileEdit?menuId='
									+ row.id,
							method : 'get',
							valueField : 'code',
							textField : 'detail',
							onSelect : function(record) {
								$('#menuType').next().children(
										"input[type='text']").removeClass(
										'selectInValid');
								$("#menuTypeZw").val(record.detail);
							}
						});
				$("#id").val(row.id);
			}
		}
		function newMenuSubmit() {
			url = '${ctx}/menuCreate';
			var data = form2JsonStr("fm");
			$('#parentId').next().children("input[type='text']").addClass(
					'selectInValid');
			$('#parentId').next().tooltip({
				position : 'right',
				content : '<span style="color:#000000">必选</span>',
				onShow : function() {
					$(this).tooltip('tip').css({
						backgroundColor : '#F0E68C',
						borderColor : '#F0E68C'
					});
				}
			});
			$('#menuType').next().children("input[type='text']").addClass(
					'selectInValid');
			$('#menuType').next().tooltip({
				position : 'right',
				content : '<span style="color:#000000">必选</span>',
				onShow : function() {
					$(this).tooltip('tip').css({
						backgroundColor : '#F0E68C',
						borderColor : '#F0E68C'
					});
				}
			});
			var flag = $("#fm").form('validate');
			if (flag && ($('#parentId').combobox('getValue') != "")
					&& ($('#menuType').combobox('getValue') != "")) {
				commonAjax({
					url : url,
					type : "POST",
					data : {
						"data" : data
					},
					dataType : "json"
				}, function(data) {
					if (!data.error) {
						$.messager.show({
							title : '提示',
							msg : "创建菜单成功"
						});
						$('#dlg').dialog('close');// close the dialog
						$('#menuList').datagrid('reload'); // reload the user data
					} else {
						if (data.result == "isExits") {
							$.messager.show({
								title : '提示',
								msg : "已经存在相同的菜单"
							});
						} else {
							$.messager.show({
								title : '提示',
								msg : "创建菜单失败"
							});
						}

					}
				});
			}
		}

		function editMenuSubmit() {
			url = '${ctx}/menuEdit';
			var data = form2JsonStr("fm");
			var flag = $("#fm").form('validate');
			if (flag) {
				commonAjax({
					url : url,
					type : "POST",
					data : {
						"data" : data
					},
					dataType : "json"
				}, function(data) {
					if (!data.error) {
						$.messager.show({
							title : '提示',
							msg : "修改成功"
						});
						$('#dlg').dialog('close');// close the dialog
						$('#menuList').datagrid('reload'); // reload the user data
					} else {
						$.messager.show({
							title : '提示',
							msg : "修改失败"
						});

					}
				});
			}
		}

		function destroyMenu() {
			var row = $('#menuList').datagrid('getSelected');
			if (row) {
				if (row.id == "root_menu") {
					return;
				}
				$.messager.confirm('删除菜单', '确定要删除么？', function(r) {
					if (r) {
						commonAjax({
							url : '${ctx}/menuDelete',
							type : "POST",
							data : {
								id : row.id
							},
							dataType : "json"
						}, function(data) {
							if (!data.error) {
								$.messager.show({ // show error message
									title : '提示',
									msg : "删除成功"
								});
								$('#menuList').datagrid('reload'); // reload the user data
							} else {
								$.messager.show({ // show error message
									title : '提示',
									msg : "删除失败"
								});

							}
						});
						/*   $.post('${ctx}/menuDelete',{id:row.id},function(json){
						      if (json.result){
						      	$.messager.show({    // show error message
						              title: '提示',
						              msg: "删除成功"
						          });
						          $('#menuList').datagrid('reload');    // reload the user data
						      } else {
						          $.messager.show({    // show error message
						              title: '提示',
						              msg: "删除失败"
						          });
						      }
						  },'json'); */
					}
				});
			}
		}
	</script>
</html>