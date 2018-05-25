<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>angularjs-路由</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/angular.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/angular-route.min.js"></script>
</head>
<body>
	<div ng-app="myApp">
		<ul>
			<li><a href="#/">首页</a></li>
			<li><a href="#/customer">用户管理</a></li>
			<li><a href="#/quanxian">权限管理</a></li>
			<li><a href="#/system">系统管理</a></li>
		</ul>
		<div ng-view></div>
	</div>
	<script type="text/javascript">
		//1.使用angular的module创建模块，参数1：模块名，要和ng-app的值相同；参数2:数组，设置该模块依赖的其他模块
		var app = angular.module('myApp',['ngRoute']);
		//2.在模块中设置路由
		app.config(function($routeProvider){
			//参数1：
			$routeProvider.when('/',{templateUrl:'05-route2.jsp'})
						  .when('/customer',{templateUrl:'05-route3.jsp'})
						  .when('/quanxian',{templateUrl:'05-route4.jsp'})
						  .when('/system',{templateUrl:'05-route5.jsp'})
						  .otherwise({redirectTo:'/'});
		});
	</script>
</body>
</html>