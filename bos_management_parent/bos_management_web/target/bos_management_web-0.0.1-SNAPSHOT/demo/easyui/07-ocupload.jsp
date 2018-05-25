<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>ocupload-一键上传</title>
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
	<!-- 传统文件上传 -->
	<!-- <form action="abc.action" method="post" enctype="multipart/form-data">
		<input type="file" name="uploadfile"/><input type="submit" value="上传"/>
	</form> -->
	<a id="uploadBtn" class="easyui-linkbutton">上传</a>
	<script type="text/javascript">
	$(function(){
		$('#uploadBtn').upload({  
            action: 'abc.action',//请求地址属性
            name: 'uploadfile',//后台接收数据的属性名
            onComplete: function (data) {
            	alert(data);
            }  
        });  
	});
	</script>
</body>
</html>