<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>angularjs-事件绑定</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/angular.min.js"></script>
</head>
<body>
	<div ng-app="myApp" ng-controller="myCtrl">
		<!-- ng-model：将模型中的属性绑定到输入框 -->
		姓名：<input type="text" name="name" ng-model="name"/>
		<input type="button" value="清空" ng-click="clearContent();"/>
		<br/>
		您输入的姓名是：{{name}}
	</div>
	<script type="text/javascript">
		//1.使用angular的module创建模块，参数1：模块名，要和ng-app的值相同；参数2:数组，设置该模块依赖的其他模块
		var app = angular.module('myApp',[]);
		//2.在模块中创建控制器：参数1：控制器名称，要和ng-controller的值一致，参数2：该控制器绑定的函数
		app.controller('myCtrl',function($scope){
			$scope.name='传智播客最好！';
			//定义函数
			$scope.clearContent=function(){
				$scope.name='';
			}
		});
	</script>
</body>
</html>