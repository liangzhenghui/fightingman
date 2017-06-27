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
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/library/uploadify/uploadify.css">
<script src="<%=contextPath%>/library/js/jquery/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="<%=contextPath%>/library/uploadify/jquery.uploadify.min.js" type="text/javascript"></script>
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
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="createAppVersion()">新建版本</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteAppVersion()">删除类别</a>
    </div>
	<table id="app-version" class="easyui-datagrid" 
			url="<%=contextPath%>/api/app-version-list.json"
			title="app版本管理" 
			rownumbers="true" pagination="true" toolbar="#toolbar"  singleSelect="true">
		<thead>
			<tr>
				<th field="versionCode" width="200">版本code</th>
				<th field="versionName" width="200" >版本name</th>
			</tr>
		</thead>
	</table>
	<div id="dlg" class="easyui-dialog" style="width:300px;height:370px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons" title="版本管理">
        <form id="fm" method="post" novalidate>
            <div class="fitem">
                <label>版本code:</label>
                <input id="versionCode" name="versionCode" class="easyui-validatebox" required="true" style="width:100%">
            </div>
             <div class="fitem">
                <label>版本name:</label>
                <input id="versionName" name="versionName" class="easyui-validatebox" required="true" style="width:100%">
            </div>
            <div class="fitem">
                <label>图片:</label>
            	<input type="file" name="image" id="file_upload" />
        	</div>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="createAppVersionSubmit()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
    </div>
<script type="text/javascript">
var url;
var file_count=0;
$(function() {
	//新建时使用
    $('#file_upload').uploadify({
    	'auto':false,
    	'buttonText' : '请选择',
        'swf'      : '<%=contextPath%>/library/uploadify/uploadify.swf',
        'uploader' : '<%=contextPath%>/api/app-version-create.do',
        'fileObjName'   : 'file',
        'onCancel' : function(file) {
            file_count--;
        },
        'onSelect' : function(file) {
            file_count++;
        },
        'onUploadStart' : function(file) {
            $("#file_upload").uploadify("settings", "formData",{ 'versionCode': $("#versionCode").val(),'versionName': $("#versionName").val()});
        },
        'onQueueComplete' : function(queueData) {
        	$.messager.show({
                title: '提示',
                msg: queueData.uploadsSuccessful+"个图片上传成功,"+queueData.uploadsErrored+"个图片上传失败"
            });
        	$('#dlg').dialog('close');// close the dialog
        	$('#app-version').datagrid('reload');    // reload the user datac
        }
    });
});
function createAppVersion(){
    $('#dlg').dialog('open').dialog('setTitle','新建App版本');
    $('#fm').form('clear');
}

function createAppVersionSubmit(){
	var flag = $("#fm").form('validate');
	url = '<%=contextPath%>/api/app-version-create.json';
	var data = form2JsonStr("fm");
	var flag = $("#fm").form('validate');
	if(flag){
		$('#file_upload').uploadify('upload','*');
	}
}


function deleteAppVersion(){
    var row = $('#app-version').datagrid('getSelected');
    if (row){
        $.messager.confirm('删除功能','确定要删除么？',function(r){
            if (r){
                $.post('<%=contextPath%>/api/lb-delete.json',{id:row.id},function(json){
                    if (json.result){
                    	$.messager.show({
        	                title: '提示',
        	                msg: "删除成功"
        	            });
                        $('#app-version').datagrid('reload');    // reload the user data
                    } else {
                        $.messager.show({    // show error message
                            title: 'Error',
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
</body>
</html>
