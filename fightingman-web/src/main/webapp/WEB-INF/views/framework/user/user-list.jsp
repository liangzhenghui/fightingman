<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix='sec' uri='http://www.springframework.org/security/tags' %> 
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

#fm1 {
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
<body >
	<div id="toolbar">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-add" plain="true" onclick="newUser()">新建用户</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-edit" plain="true" onclick="editUser()">编辑用户</a> 
			<sec:authorize access="hasRole('系统管理员')">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">删除用户</a>
			</sec:authorize> 
			<a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-add" plain="true" onclick="addRole()">分配角色</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-edit" plain="true" onclick="manageUserRole()">编辑用户角色关联</a>
	</div>
	<table id="userList" class="easyui-datagrid"
		method="get" url="${ctx}/user-list-data" title="用户管理" i rownumbers="true"
		pagination="true" toolbar="#toolbar" singleSelect="true">
		<thead>
			<tr>
				<th field="userid" width="200">用户账号</th>
				<th field="username" width="200">用户名称</th>
				<th field="mobile" width="200">手机号</th>
				<th field="roles" width="400">角色</th>
			</tr>
		</thead>
	</table>
	<div id="dlg" class="easyui-dialog"
		style="width: 500px; height: 200px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons" title="用户管理">
		<form id="fm" method="post" novalidate>
			<div class="fitem">
				<label>用户账号 :</label> <input id="userid" name="userid" value=""
					class="easyui-validatebox" required="true" validType="username"
					style="width: 200px">
			</div>
			<div class="fitem">
				<label>用户昵称 :</label> <input id="username" name="username"
					class="easyui-validatebox" required="true" value=""
					style="width: 200px">
			</div>
			<div class="fitem">
				<label>手机号:</label> <input id="mobile" name="mobile"
					class="easyui-validatebox" required="true" validType="mobile"
					value="" style="width: 200px">
			</div>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-ok" onclick="saveUserSubmit()">保存</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>
	<div id="dlg1" class="easyui-dialog"
		style="width: 500px; height: 200px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons1" title="用户管理">
		<form id="fm1" method="post" novalidate>
			<div class="fitem">
				<label>用户账号 :</label> <input id="userid" name="userid" value=""
					class="easyui-validatebox" required="true" validType="username"
					style="width: 200px">
			</div>
			<div class="fitem">
				<label>用户昵称 :</label> <input id="username" name="username"
					class="easyui-validatebox" required="true" value=""
					style="width: 200px">
			</div>
			<div class="fitem">
				<label>手机号:</label> <input id="mobile" name="mobile"
					class="easyui-validatebox" required="true" validType="mobile"
					value="" style="width: 200px">
			</div>
			<input type="hidden" id="id" name="id">
		</form>
	</div>
	<div id="dlg-buttons1">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-ok" onclick="editUserSubmit()">保存</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg1').dialog('close')">取消</a>
	</div>
	<div id="dlg2" class="easyui-dialog"
		style="width: 600px; height: 150px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons2" title="用户角色管理">
		<form id="fm2" method="post" novalidate>
			<div class="fitem">
				<label>用户账号 :</label> <input id="userName" name="userName" value=""
					readonly="readonly" class="easyui-validatebox" style="width: 200px">
			</div>
			<div class="fitem">
				<label>角色 :</label> <input id="roleId" name="roleId"
					class="easyui-validatebox" value="" style="width: 200px">
			</div>
			<input type="hidden" id="roleName" name="roleName"> <input
				type="hidden" id="userId" name="userId">
		</form>
	</div>
	<div id="dlg-buttons2">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-ok" onclick="addRoleSubmit()">保存</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg2').dialog('close')">取消</a>
	</div>
	<script type="text/javascript">
var url;
var pid;
function newUser(){
    $('#dlg').dialog('open').dialog('setTitle','新建用户');
    $('#fm').form('clear');
}

function editUser(){
    var row = $('#userList').datagrid('getSelected');
    if (row){
        $('#dlg1').dialog('open').dialog('setTitle','编辑用户');
        $('#fm1').form('load',row);
        $("#id").val(row.id);
    }
}
function saveUserSubmit(){
	url = '${ctx}/userCreate';
	var data = form2JsonStr("fm");
	var flag = $("#fm").form('validate');
	if(flag){
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
	                title: '提示',
	                msg: "创建成功"
	            });
				$('#dlg').dialog('close');// close the dialog
				 $('#userList').datagrid('reload');// reload the user data
			} else {
				if (data.result == "isExits") {
					$.messager.show({
						title : '提示',
						msg : "已经存在相同的用户名称"
					});
				} else {
					$.messager.show({
						title : '提示',
						msg : "创建用户失败"
					});
				}

			}
		});
	}
}
	
function editUserSubmit(){
	url = '${ctx}/userEdit';
	var data = form2JsonStr("fm1");
	var flag = $("#fm1").form('validate');
	if(flag){
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
	                title: '提示',
	                msg: "修改成功"
	            });
				$('#dlg1').dialog('close');// close the dialog
		        $('#userList').datagrid('reload');    // reload the user datac
			} else {
				$.messager.show({
	                title: '提示',
	                msg: "修改失败"
	            });
			}
		});
	}
}
function destroyUser(){
    var row = $('#userList').datagrid('getSelected');
    if (row){
        $.messager.confirm('删除用户','确定要删除么？',function(r){
            if (r){
        		commonAjax({
        			url : '${ctx}/userDelete',
        			type : "POST",
        			data : {
        				"id" : row.id
        			},
        			dataType : "json"
        		}, function(data) {
        			if (!data.error) {
        				$.messager.show({    // show error message
                            title: '提示',
                            msg: "删除成功"
                        });
                        $('#userList').datagrid('reload');    // reload the user data
        			} else {
        				$.messager.show({    // show error message
                            title: '提示',
                            msg: "删除失败"
                        });
        			}
        		});
            }
        });
    }
}

function addRole(){
	var row = $('#userList').datagrid('getSelected');
	 if (row){
		 $('#roleId').combobox({
		        url:'${ctx}/getAllRoles',
		        method : 'get',
		        valueField:'code',
		        textField:'detail',
		        onSelect:function(record){
		        	$('#roleId').next().children("input[type='text']").removeClass('selectInValid');
		        	$("#roleName").val(record.detail);
		        }
		    });
		 $('#dlg2').dialog('open').dialog('setTitle','分配角色');
	     $('#fm2').form('load',row);
	     $("#userId").val(row.id);
	     $("#userName").val(row.userid);
	 }else{
		 $.messager.show({    // show error message
             title: '提示',
             msg: "请先选定一行"
         });
	 }
}

function addRoleSubmit(){
	$('#roleId').next().children("input[type='text']").addClass('selectInValid');
	$('#roleId').next().tooltip({
	    position: 'right',
	    content: '<span style="color:#000000">必选</span>',
	    onShow: function(){
	        $(this).tooltip('tip').css({
	            backgroundColor: '#F0E68C',
	            borderColor: '#F0E68C'
	        });
	    }
	});
	var url = '${ctx}/roleGrantUser';
	var data = form2JsonStr("fm2");
	var flag = $("#fm2").form('validate');
	if(flag&&$('#roleId').combobox('getValue')!=""){
		commonAjax({
			url : url,
			type : "POST",
			data : {
				"data" : data
			},
			dataType : "json"
		}, function(data) {
			if (!data.error) {
				if(data.result.hasTheRole){
					$.messager.show({
		                title: '提示',
		                msg: "该用户已经有这个角色"
		            });
				}else{
					$('#dlg2').dialog('close');
					 $('#userList').datagrid('reload');
					$.messager.show({
		                title: '提示',
		                msg: "给用户赋予角色成功"
		            });
				}
			} else {
				$.messager.show({
	                title: '提示',
	                msg: "创建失败"
	            });
			}
		});
	}
}

function manageUserRole(){
	var row = $('#userList').datagrid('getSelected');
	if(row){
		window.location.href="${ctx}/user-role-list?id="+ row.id;
			} else {
				$.messager.show({ // show error message
					title : '提示',
					msg : "请先选定一行"
				});
			}
		}
	</script>
</html>