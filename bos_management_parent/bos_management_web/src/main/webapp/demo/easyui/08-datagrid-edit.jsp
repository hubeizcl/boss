<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>easyui-datagrid-行编辑功能</title>
<!-- easyui资源文件 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/js/easyui/themes/icon.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.ocupload-1.1.2.js"></script>
</head>
<body>
	<table id="grid"></table>
	<script type="text/javascript">
	$(function(){
		$('#grid').datagrid({
			url:'${pageContext.request.contextPath}/data/courier.json',
			columns:[[
			          {title:'编号',field:'id',checkbox:true},
			          {title:'姓名',field:'name',editor:{
			        	  type:'validatebox',
			        	  options:{
			        		  required:true
			        	  }
			          }},
			          {title:'电话',field:'telephone',editor:{
			        	  type:'validatebox',
			        	  options:{
			        		  required:true
			        	  }
			          }}
			          ]],
			pagination:true,
			pageList:[10,30,50],
			toolbar:[
			         {text:'增加',iconCls:'icon-add',handler:function(){
			        	 //在第一行添加一空行
			        	 $('#grid').datagrid('insertRow',{
			        			index: 0,	// 索引从0开始
			        			row: {}
			        	  });
			        	 //开启第一行的编辑状态
			        	 $('#grid').datagrid('beginEdit', 0);
			         }},
					 {text:'删除',iconCls:'icon-add',handler:function(){
			        	 //1.获取选中的数据
						 var rows = $('#grid').datagrid('getSelections');
			        	 //2.循环删除选中行
			        	 for(var i=0; i<rows.length; i++){
			        		 //2.1获取行号
			        		 var index = $('#grid').datagrid('getRowIndex', rows[i]);
			        		 //2.2根据行号，删除行
			        		 $('#grid').datagrid('deleteRow', index);
			        	 }
			         }},
					 {text:'修改',iconCls:'icon-add',handler:function(){
						 //1.获取选中的数据
						 var rows = $('#grid').datagrid('getSelections');
			        	 //2.循环修改选中行
			        	 for(var i=0; i<rows.length; i++){
			        		 //2.1获取行号
			        		 var index = $('#grid').datagrid('getRowIndex', rows[i]);
			        		 //2.2根据行号，删除行
			        		 $('#grid').datagrid('beginEdit', index);
			        	 }
			         }},
					 {text:'保存',iconCls:'icon-add',handler:function(){
						//1.获取选中的数据
						 var rows = $('#grid').datagrid('getSelections');
			        	 //2.循环保存选中行
			        	 for(var i=0; i<rows.length; i++){
			        		 //2.1获取行号
			        		 var index = $('#grid').datagrid('getRowIndex', rows[i]);
			        		 //2.2根据行号，关闭行编辑状态
			        		 $('#grid').datagrid('endEdit', index);
			        	 }
			         }}
			         ],
			 onAfterEdit:function(rowIndex, rowData, changes){
				 alert(rowIndex+"--"+rowData.name+"--"+changes.name);
			 }
		});
	});
	</script>
</body>
</html>