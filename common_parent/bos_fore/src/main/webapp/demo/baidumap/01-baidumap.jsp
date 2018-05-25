<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<style type="text/css">
	body, html {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
	#map1_container,#map2_container {width:100%;height:50%;float:left;overflow: hidden;margin:0;}
	#allmap1{margin:0 0 3px;height:100%;}
	#allmap2{margin:3px 0 0;height:100%;}
	</style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=4IU3oIAMpZhfWZsMu7xzqBBAf6vMHcoa"></script>
	<title>同时加载两个地图</title>
</head>
<body>
	<div id="map1_container"><div id="allmap1"></div></div>
	<div id="map2_container"><div id="allmap2"></div></div>
</body>
</html>
<script type="text/javascript">
	//百度地图API功能
	//加载第一张地图
	var map1 = new BMap.Map("allmap1");            // 创建Map实例
    var point1 = new BMap.Point(116.404, 39.915);  
    map1.centerAndZoom(point1,15);               
    map1.enableScrollWheelZoom();                  //启用滚轮放大缩小
	//加载第二张地图
	var map2 = new BMap.Map("allmap2");            // 创建Map实例
    var point2 = new BMap.Point(116.404, 39.915);  
    map2.centerAndZoom(point2,15);                 
    map2.enableScrollWheelZoom();                  //启用滚轮放大缩小
</script>
