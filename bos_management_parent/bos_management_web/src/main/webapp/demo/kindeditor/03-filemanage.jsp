<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>kindeditor-文件管理</title>
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
			window.editor = K.create('#editor_id', {
                uploadJson : '${pageContext.request.contextPath}/pages/promotion/upload_json.jsp',//文件上传的请求地址
                fileManagerJson : '${pageContext.request.contextPath}/pages/promotion/file_manager_json.jsp',//文件管理的请求地址
                allowFileManager : true//文件管理的开发，默认是false
        	});
		});
	</script>
</body>
</html>