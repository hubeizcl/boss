<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>easyui-messager-消息框</title>
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
</head>
<body>
	<h3>第一种：将一个简单的html的table表格转换成datagrid</h3>
	<table class="easyui-datagrid">
		<thead>
			<tr>
				<th data-options="field:'id'">编号</th>
				<th data-options="field:'name'">姓名</th>
				<th data-options="field:'telephone'">电话</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>1</td>
				<td>马容</td>
				<td>13838389438</td>
			</tr>
			<tr>
				<td>2</td>
				<td>宋吉吉</td>
				<td>13838389438</td>
			</tr>
		</tbody>
	</table>
	<h3>第二种：基于ajax请求动态加载datagrid的数据</h3>
	<!-- 所有easyui的控件加载数据都是使用的url -->
	<table class="easyui-datagrid" data-options="url:'${pageContext.request.contextPath}/data/courier.json'">
		<thead>
			<tr>
				<!-- field:设置表头从返回的rows的数组中的对象中哪个属性取值 -->
				<th data-options="field:'id'">编号</th>
				<th data-options="field:'name'">姓名</th>
				<th data-options="field:'telephone'">电话</th>
			</tr>
		</thead>
	</table>
	<h3>第三种：基于api动态构建一个datagrid</h3>
	<table id="grid"></table>
	<script type="text/javascript">
	$(function(){
		$('#grid').datagrid({
			url:'${pageContext.request.contextPath}/data/courier.json',
			columns:[[
			          {title:'编号',field:'id',checkbox:true},
			          //title:指定列名称，
			          //field:设置表头从返回的rows的数组中的对象中哪个属性取值,
			          //checkbox：将该列显示成复选框 
			          {title:'姓名',field:'name'},
			          {title:'电话',field:'telephone'}
			          ]],//配置列的属性，二维数组
			toolbar:[
			         {text:'增加',iconCls:'icon-add',handler:function(){
			        	 alert(1);
			         }},//text：设置工具条中按钮名称,handler:给按钮绑定点击事件,该函数无参
			         {text:'修改'},
			         {text:'作废'},
			         {text:'还原'}
			         ],
			singleSelect:true,//开启单选功能
			pagination:true,//开启分页查询条
			pageList:[10,50,100]//设置分页查询每页条数选项
		});
	});
	</script>
</body>
</html>