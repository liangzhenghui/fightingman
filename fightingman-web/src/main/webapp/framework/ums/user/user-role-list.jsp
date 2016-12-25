<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String contextPath = request.getContextPath();%>
<%String userId = request.getParameter("userId"); %>
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
	<style type="text/css">
        #fm{
            margin:0;
            padding:10px 30px;
        }
        #fm1{
            margin:0;
            padding:10px 30px;
        }
        .ftitle{
            font-size:14px;
            font-weight:bold;
            padding:5px 0;
            margin-bottom:10px;
            border-bottom:1px solid #ccc;
        }
        .fitem{
            margin-bottom:5px;
        }
        .fitem label{
            display:inline-block;
            width:80px;
        }
    </style>
</head>
<body>
	 <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUserRole()">删除角色</a>
    </div>
	<table id="user-role-list" class="easyui-datagrid" 
			url="<%=contextPath %>/user-role-list.json?userId=<%=userId%>"
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
                $.post('<%=contextPath%>/user-role-delete.json',{id:row.id},function(json){
                    if (json.result){
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
                },'json');
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