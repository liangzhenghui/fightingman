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
<body>
	 <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUserRole()">删除角色</a>
    </div>
	<table id="user-role-list" class="easyui-datagrid" 
			method="get"
			url="${ctx}/user-role-list-data?userId=${userId}"
			title="用户角色管理" 
			rownumbers="true" pagination="true" toolbar="#toolbar"  singleSelect="true">
		<thead>
			<tr>
				<th field="userName" width="200">用户名称</th>
				<th field="roleName" width="200">角色</th>
			</tr>
		</thead>
	</table>
<script type="text/javascript">
function destroyUserRole(){
    var row = $('#user-role-list').datagrid('getSelected');
    if (row){
        $.messager.confirm('删除该关联用户的角色','确定要删除么？',function(r){
            if (r){
            	commonAjax({
        			url : '${ctx}/user-role-delete',
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
        				 $('#user-role-list').datagrid('reload');    // reload the user data
        			} else {
        				 $.messager.show({    // show error message
                             title: '提示',
                             msg: "删除失败"
                         });
        			}
        		});
            }
        });
    }else{
    	 $.messager.show({    // show error message
             title: '提示',
             msg: "请先选定一行"
         });
    }
}
</script>
</html>