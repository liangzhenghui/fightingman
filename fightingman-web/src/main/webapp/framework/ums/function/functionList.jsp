<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String contextPath = request.getContextPath();%>
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
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newFunction()">新建功能</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editFunction()">编辑功能</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyFunction()">删除功能</a>
    </div>
	<table id="functionList" class="easyui-datagrid" 
			url="<%=contextPath%>/functionList.json"
			title="功能管理" 
			rownumbers="true" pagination="true" toolbar="#toolbar"  singleSelect="true">
		<thead>
			<tr>
				<th field="functionName" width="200">功能名称</th>
				<th field="url" width="350">URL</th>
			</tr>
		</thead>
	</table>
	<div id="dlg" class="easyui-dialog" style="width:600px;height:200px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons" title="功能管理">
        <form id="fm" method="post" novalidate>
            <div class="fitem">
                <label>功能名称 :</label>
                <input name="functionName" class="easyui-validatebox" required="true" style="width:300px">
            </div>
            <div class="fitem">
                <label>功能url:</label>
                <input name="url" class="easyui-validatebox" required="true" style="width:300px">
            </div>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="newFunctionSubmit()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
    </div>
    <div id="dlg1" class="easyui-dialog" style="width:500px;height:200px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons1" title="功能管理">
        <form id="fm1" method="post" novalidate>
             <div class="fitem">
                <label>功能名称 :</label>
                <input name="functionName" class="easyui-validatebox" required="true" style="width:300px">
            </div>
            <div class="fitem">
                <label>功能url:</label>
                <input name="url" class="easyui-validatebox" required="true" style="width:300px">
            </div>
            <input type="hidden" id="id" name="id">
        </form>
    </div>
    <div id="dlg-buttons1">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="editFunctionSubmit()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg1').dialog('close')">取消</a>
    </div>
<script type="text/javascript">
var url;
function newFunction(){
    $('#dlg').dialog('open').dialog('setTitle','新建功能');
    $('#fm').form('clear');
}
function editFunction(){
    var row = $('#functionList').datagrid('getSelected');
    if (row){
        $('#dlg1').dialog('open').dialog('setTitle','编辑功能');
        $('#fm1').form('load',row);
        $("#id").val(row.id);
    }
}
function newFunctionSubmit(){
	url = '<%=contextPath%>/functionCreate.json';
	var data = form2JsonStr("fm");
	var flag = $("#fm").form('validate');
	if(flag){
		$.post(url,{data:data},function(data){
			if(data.isExits){
				$.messager.show({
	                title: '提示',
	                msg: "已经存在该功能"
	            });
			}else{
				if(data.result){
					$.messager.show({
		                title: '提示',
		                msg: "创建成功"
		            });
					$('#dlg').dialog('close');// close the dialog
			        $('#functionList').datagrid('reload');    // reload the user datac
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

function editFunctionSubmit(){
	url = '<%=contextPath%>/functionEdit.json';
	var data = form2JsonStr("fm1");
	var flag = $("#fm1").form('validate');
	if(flag){
		$.post(url,{data:data},function(data){
			if(data.result){
				$.messager.show({
	                title: '提示',
	                msg: "修改成功"
	            });
				$('#dlg1').dialog('close');// close the dialog
		        $('#functionList').datagrid('reload');    // reload the user datac
			}else{
				$.messager.show({
	                title: '提示',
	                msg: "修改失败"
	            });
			}
		},"json");
	}
}

function destroyFunction(){
    var row = $('#functionList').datagrid('getSelected');
    if (row){
        $.messager.confirm('删除功能','确定要删除么？',function(r){
            if (r){
                $.post('<%=contextPath%>/functionDelete.json',{id:row.id},function(json){
                    if (json.result){
                    	$.messager.show({
        	                title: '提示',
        	                msg: "删除成功"
        	            });
                        $('#functionList').datagrid('reload');    // reload the user data
                    } else {
                        $.messager.show({    // show error message
                            title: 'Error',
                            msg: "删除失败"
                        });
                    }
                },'json');
            }
        });
    }
}
</script>
</body>
</html>
