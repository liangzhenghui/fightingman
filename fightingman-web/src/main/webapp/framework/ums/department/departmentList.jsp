<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String contextPath = request.getContextPath();%>
<%@ page language="java"  import="util.SpringUtil"%>
<%@ page language="java"  import="ums.service.DepartmentService"%>
<%@ page language="java"  import="java.util.List"%>
<%@ page language="java"  import="ums.model.Department"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
<style type="text/css">
	 .selectInValid{
        border-color: #ffa8a8;
		background-color: #fff3f3;
		color: #404040;
      }
</style>
<title>机构列表</title>
  	<link rel="stylesheet" type="text/css" href="<%=contextPath %>/library/css/style.css"/>
</head>
<body>
<%DepartmentService departmentService = DepartmentService.getInstance();
	List departmentList = departmentService.getAllDepartments();
%>
<div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newFunction()">新建功能</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editFunction()">编辑功能</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyFunction()">删除功能</a>
</div>
<table id="departmentList" class="easyui-datagrid" 
			url="<%=contextPath%>/departmentList.json"
			title="机构管理" 
			rownumbers="true" pagination="true" toolbar="#toolbar"  singleSelect="true">
		<thead>
			<tr>
				<th field="name" width="200">机构名称</th>
			</tr>
		</thead>
	</table>
<table width="100%">
	<tr align="right">
		<td>
			<a href="<%=contextPath%>/framework/ums/department/departmentTree.jsp">查看机构树</a>
			<a href="#" onclick="newDepartment()">新建机构</a>
		<td>
	</tr>
</table>
<center>
<table class="dataintable">
<tr>
	<th>机构名称</th>
	<th>操作</th>
</tr>
	<%for(int i = 0 ; i < departmentList.size(); i++) {
		Department department =(Department)departmentList.get(i);
	%>
		<tr class="department">
			<td>
				<%=department.getName()%>
			</td>
			<td>
				<a href="<%=contextPath%>/framework/ums/department/departmentEdit.jsp?id=<%=department.getId()%>">编辑</a>
				<a href="javascript:void(0)" onclick="deleteDepartment('<%=department.getId()%>',this)">删除</a>
			</td>
		</tr>
	<%}%>
</table>
<div id="result">
	
</div>
<div id="dlg" class="easyui-dialog" style="width:350px;height:150px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons" title="机构管理">
        <form id="fm" method="post" novalidate>
             <div class="fitem">
                <label>上级机构 :</label>
                <input id="parentId" name="parentId" class="easyui-validatebox">
            </div>
             <div class="fitem">
                <label>机构名称 :</label>
            	<input id="department_name" name="department_name" class="easyui-validatebox"  value="" required="true">
            </div>
            <input type="hidden" id="parentMenuZw" name="parentMenuZw">
        </form>
 </div>
 <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveDepartmentSubmit()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
 </div>
 <div id="dlg1" class="easyui-dialog" style="width:350px;height:150px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons1" title="机构管理">
        <form id="fm" method="post" novalidate>
             <div class="fitem">
                <label>上级机构 :</label>
                <input id="parentId" name="parentId" class="easyui-validatebox">
            </div>
             <div class="fitem">
                <label>机构名称 :</label>
            	<input id="department_name" name="department_name" class="easyui-validatebox"  value="" required="true">
            </div>
            <input type="hidden" id="parentMenuZw" name="parentMenuZw">
        </form>
 </div>
 <div id="dlg-buttons1">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveDepartmentSubmit()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
 </div>
</center>
<script type="text/javascript">
function newDepartment(){
	 $('#dlg').dialog('open').dialog('setTitle','新建机构');
	 $('#fm1').form('clear');
	 $('#parentId').combobox({
	        url:'<%=contextPath %>/getParentDepartmentSelectWhileCreate.do',
	        valueField:'code',
	        textField:'detail',
	        onSelect:function(record){
	        	$('#parentId').next().children("input[type='text']").removeClass('selectInValid');
	        	$("#parentMenuZw").val(record.detail);
	        }
	    });
}

function editDepartment(){
	 $('#dlg1').dialog('open').dialog('setTitle','编辑机构');
	 $('#fm1').form('clear');
	 $('#parentId').combobox({
	        url:'<%=contextPath %>/getParentDepartmentSelectWhileCreate.do',
	        valueField:'code',
	        textField:'detail',
	        onSelect:function(record){
	        	$('#parentId').next().children("input[type='text']").removeClass('selectInValid');
	        	$("#parentMenuZw").val(record.detail);
	        }
	    });
}

function saveDepartmentSubmit(){
	url = '<%=contextPath%>/departmentCreate.json';
	var data = form2JsonStr("fm");
	$('#parentId').next().children("input[type='text']").addClass('selectInValid');
	$('#parentId').next().tooltip({
	    position: 'right',
	    content: '<span style="color:#000000">必选</span>',
	    onShow: function(){
	        $(this).tooltip('tip').css({
	            backgroundColor: '#F0E68C',
	            borderColor: '#F0E68C'
	        });
	    }
	});
	var flag = $("#fm").form('validate');
	if(flag&&($('#parentId').combobox('getValue')!="")){
		$.post(url,{data:data},function(data){
			if(data.isExits){
				$.messager.show({
	                title: '提示',
	                msg: "已经存在该机构"
	            });
			}else{
				if(data.result){
					$.messager.show({
		                title: '提示',
		                msg: "创建成功"
		            });
					$('#dlg').dialog('close');// close the dialog
			       /*  $('#userList').datagrid('reload');  */   // reload the user datac
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
function deleteDepartment(id,self) {
	if (!confirm("确认要删除？")) {
		return;
	}
	var url = '<%=contextPath%>/departmentDeleteServlet';
	var data = {
		id:id
	}
	$.post(url,data,function(json){
		var result = json.result;
		if(result) {
			$(self).parents("tr").remove();
			$("#result").html("删除成功");
		}
		else {
			$("#result").html("删除失败");
		}
	},"json");
}
</script>
</body>
</html>