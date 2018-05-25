<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>citypicker-三级联动</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/citypicker/js/city-picker.data.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/citypicker/js/city-picker.js"></script>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/js/citypicker/css/city-picker.css">
</head>
<body>
	<!-- 创建方式 -->
	<h1> 1.使用html创建</h1>
	<div style="position: relative">
		<input readonly type="text" data-toggle="city-picker"/>
	</div>
	<h1> 2.使用js创建</h1>
	<div style="position: relative">
		<input id="citypicker1" readonly type="text"/><br/><br/><br/>
		<input onclick="reset();" type="button" value="重置"/>
		<input onclick="doDefault()" type="button" value="默认值"/>
		<script type="text/javascript">
		$(function(){
			$('#citypicker1').citypicker();
		});
		//使用操作
		//重置的方法
		function reset(){
			//reset就是citypicker的内部重置方法
			$('#citypicker1').citypicker('reset');
		}
		//设置默认值方法
		function doDefault(){
			//1.先调用reset
			$('#citypicker1').citypicker('reset');
			//2.调用destroy
			$('#citypicker1').citypicker('destroy');
			//3.调用初始化函数
			$('#citypicker1').citypicker({
				province:'北京市',
				city:'北京市',
				district:'东城区'
			});
		}
		</script>
	</div>
</body>
</html>