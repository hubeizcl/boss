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
	<script type="text/javascript">
		//1.messager的简单提示框：alert
		//参数1：消息框标题
		//参数2：消息框内容
		//参数3：级别，info、warning、error
		/* $.messager.alert('提示信息','上课好好听讲！','error'); */
		//2.messager的确认框：confirm
		/* $.messager.confirm('确认信息','你确定上课不好好听么？',function(r){
			//当用户点击确定时，r=true；点击取消，r=false
			alert(r);
		}); */
		//3.messager的欢迎框：show
		window.setTimeout(function(){
			$.messager.show({
				title : '欢迎信息',
				msg : '欢迎某某同学登录系统！',
				timeout : 3000,
				showType : 'fade'
			});
		},3000);
	</script>
</body>
</html>