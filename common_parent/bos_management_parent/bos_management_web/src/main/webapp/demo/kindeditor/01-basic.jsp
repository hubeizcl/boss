<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>kindeditor-基本应用</title>
<script charset="utf-8"
	src="${pageContext.request.contextPath}/js/kindeditor/kindeditor-all.js"></script>
<script charset="utf-8"
	src="${pageContext.request.contextPath}/js/kindeditor/lang/zh-CN.js"></script>
</head>
<body>
	<textarea id="editor_id" name="content">
	</textarea>
	<script>
		KindEditor.ready(function(K) {
			window.editor = K.create('#editor_id');
		});
	</script>
</body>
</html>