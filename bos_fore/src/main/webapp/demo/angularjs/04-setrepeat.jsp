<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>angularjs-集合遍历</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/angular.min.js"></script>
</head>
<body>
	<table ng-app="myApp" ng-controller="myCtrl">
		<thead>
			<tr>
				<th>编号</th>
				<th>姓名</th>
				<th>电话</th>
			</tr>
		</thead>
		<tbody>
			<tr ng-repeat="customer in customers">
				<td>{{customer.id}}</td>
				<td>{{customer.name}}</td>
				<td>{{customer.telephone}}</td>
			</tr>
		</tbody>
	</table>

	<script type="text/javascript">
		//1.使用angular的module创建模块，参数1：模块名，要和ng-app的值相同；参数2:数组，设置该模块依赖的其他模块
		var app = angular.module('myApp',[]);
		//2.在模块中创建控制器：参数1：控制器名称，要和ng-controller的值一致，参数2：该控制器绑定的函数
		app.controller('myCtrl',function($scope){
			$scope.customers=[
			                  {id:001,name:'隔壁老王',telephone:'18888888888'},
			                  {id:002,name:'隔壁老宋',telephone:'18888888889'},
			                  {id:003,name:'隔壁老马',telephone:'18888888880'}
			                  ]
		});
	</script>
</body>
</html>