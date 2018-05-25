<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>运输配送管理</title>
		<!-- 导入jquery核心类库 -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
		<!-- 导入easyui类库 -->
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/themes/icon.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/ext/portal.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/default.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/ext/jquery.portal.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/ext/jquery.cookie.js"></script>
		<script src="${pageContext.request.contextPath}/js/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
		<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=bp09g732UD7Kh0UbG5GoaUyMGbMizI0A"></script>
		<script type="text/javascript">
			$(function() {
				// 设置3个全局变量
				var map = new BMap.Map("allmap");
				var allstart;//发货地全局变量
				var allend;//收货地全局变量
				
				// 先将body隐藏，再显示，不会出现页面刷新效果
				$("body").css({
					visibility: "visible"
				});

				var transitToolbar = [{
					id: 'button-inoutstore',
					text: '出入库',
					iconCls: 'icon-add',
					handler: function() {
						//1.获取客户选中项
						var row = $('#transitGrid').datagrid('getSelected');
						//2.判断是否选中
						if(null == row){
							//3.未选中，提示客户选择一条
							$.messager.alert('提示信息','请您选择一条数据！','warning');
						} else {
							// 判断运输状态是否为“出入库”
							if(row.status == '出入库中转'){
								// 在表单隐藏域保存当前操作中转信息
								$("#inOutStoreId").val(row.id);
								// 回显运输配送信息
								$("#inOutStoreTransitInfoView").text("");//清空数据
								$("#inOutStoreTransitInfoView").append("运单号："+ row.wayBill.wayBillNum + "</br>");
								$("#inOutStoreTransitInfoView").append("货物类型："+ row.wayBill.goodsType + "</br>");
								$("#inOutStoreTransitInfoView").append("发货地："+ row.wayBill.sendAddress + "</br>");
								$("#inOutStoreTransitInfoView").append("收货地："+ row.wayBill.recAddress + "</br>");
								// transferInfo属性在实体中不存在，需要手动进行设置。
								$("#inOutStoreTransitInfoView").append("物流信息："+ row.transferInfo + "</br>");
							}
							else{
								// 状态不为出入库中转，不必进行出入库操作
								$.messager.alert('警告','进行出入库操作，必须操作状态为出入库中转的运单','warning');
								return;
							}
							//4.选中，打开窗口
							$("#inoutstoreWindow").window('open');
							
						}
					}
				}, {
					id: 'button-delivery',
					text: '开始配送',
					iconCls: 'icon-print',
					handler: function() {
						//1.获取选中项
						var row = $('#transitGrid').datagrid('getSelected');
						//2.判断是否选中
						if(null == row){
							//3.未选中，提示客户选择一条
							$.messager.alert('提示信息','请您选择一条数据！','warning');
						} else {
							// 判断运输状态是否为“到达网点”
							if(row.status == '到达网点'){
								// 在表单隐藏域保存当前操作中转信息
								$("#deliveryId").val(row.id);
								// 回显运输配送信息
								$("#deliveryTransitInfoView").text("");//清空数据
								$("#deliveryTransitInfoView").append("运单号："+ row.wayBill.wayBillNum + "</br>");
								$("#deliveryTransitInfoView").append("货物类型："+ row.wayBill.goodsType + "</br>");
								$("#deliveryTransitInfoView").append("发货地："+ row.wayBill.sendAddress + "</br>");
								$("#deliveryTransitInfoView").append("收货地："+ row.wayBill.recAddress + "</br>");
								// transferInfo属性在实体中不存在，需要手动进行设置。
								$("#deliveryTransitInfoView").append("物流信息："+ row.transferInfo + "</br>");
							}
							else{
								// 状态不为出入库中转，不必进行出入库操作
								$.messager.alert('警告','进行配送操作，运单状态必须是到达网点！','warning');
								return;
							}
							//4.选中，打开窗口
							$("#deliveryWindow").window('open');
							
						}
					}
				}, {
					id: 'button-sign',
					text: '签收录入',
					iconCls: 'icon-save',
					handler: function() {
						//1.获取选中项
						var row = $('#transitGrid').datagrid('getSelected');
						//2.判断是否选中
						if(null == row){
							//3.未选中，提示客户选择一条
							$.messager.alert('提示信息','请您选择一条数据！','warning');
						} else {
							// 判断运输状态是否为“开始配送”
							if(row.status == '开始配送'){
								// 在表单隐藏域保存当前操作中转信息
								$("#signId").val(row.id);
								// 回显运输配送信息
								$("#signTransitInfoView").text("");//清空数据
								$("#signTransitInfoView").append("运单号："+ row.wayBill.wayBillNum + "</br>");
								$("#signTransitInfoView").append("货物类型："+ row.wayBill.goodsType + "</br>");
								$("#signTransitInfoView").append("发货地："+ row.wayBill.sendAddress + "</br>");
								$("#signTransitInfoView").append("收货地："+ row.wayBill.recAddress + "</br>");
								// transferInfo属性在实体中不存在，需要手动进行设置。
								$("#signTransitInfoView").append("物流信息："+ row.transferInfo + "</br>");
							} else{
								// 状态不为出入库中转，不必进行出入库操作
								$.messager.alert('警告','进行配送操作，运单状态必须是到达网点！','warning');
								return;
							}
							$('#signWindow').window('open');
						}
					}
				}, {
					id: 'button-transit',
					text: '运输路径',
					iconCls: 'icon-search',
					handler: function() {
						//1.获取选中项
						var row = $('#transitGrid').datagrid('getSelected');
						if(null == row){
							//2.未选中，提示客户选择一条
							$.messager.alert('提示信息','请您选择一条数据!','warning');
						} else {
							//3.在打开窗口中显示路径导航
							// 百度地图API功能
							var start = row.wayBill.sendAddress;//发货地
							var end = row.wayBill.recAddress;//收货地
							allstart = start;
							allend = end;
							// 将发货地和收货地保存到全局变量
							map.centerAndZoom("北京", 11);
							//三种驾车策略：最少时间，最短距离，避开高速
							var routePolicy = [BMAP_DRIVING_POLICY_LEAST_TIME,BMAP_DRIVING_POLICY_LEAST_DISTANCE,BMAP_DRIVING_POLICY_AVOID_HIGHWAYS];
							map.clearOverlays(); 
							search(start,end,routePolicy[0]); 
							function search(start,end,route){ 
								var driving = new BMap.DrivingRoute(map, {renderOptions:{map: map, autoViewport: true},policy: route});
								driving.search(start,end);
							}
							//4.选中，打开显示路径的窗口
							$('#transitPathWindow').window('open');
						}
					}
				}, {
					id: 'button-path',
					text: '实时配送路径',
					iconCls: 'icon-search',
					handler: function() {
						$("#deliveryInTimePathWindow").window('open');
					}
				}];

				var transitColumns = [
					[{
						field: 'id',
						title: '编号',
						width: 30
					}, {
						field: 'wayBillNum',
						title: '运单号',
						width: 100,
						formatter: function(data, row, index) {
							if(row.wayBill.wayBillNum != undefined) {
								return row.wayBill.wayBillNum;
							}
						}
					}, {
						field: 'sendName',
						title: '寄件人姓名',
						width: 100,
						formatter: function(data, row, index) {
							return row.wayBill.sendName;
						}

					}, {
						field: 'sendAddress',
						title: '寄件地址',
						width: 100,
						formatter: function(data, row, index) {
							return row.wayBill.sendAddress;
						}
					}, {
						field: 'recName',
						title: '收件人姓名',
						width: 100,
						formatter: function(data, row, index) {
							return row.wayBill.recName;
						}
					}, {
						field: 'recAddress',
						title: '收件地址',
						width: 100,
						formatter: function(data, row, index) {
							return row.wayBill.recAddress;
						}
					}, {
						field: 'goodsType',
						title: '托寄物',
						width: 100,
						formatter: function(data, row, index) {
							return row.wayBill.goodsType;
						}
					}, {
						field: 'status',
						title: '运输状态',
						width: 100
					}, {
						field: 'outletAddress',
						title: '网点地址',
						width: 100
					}, {
						field: 'transferInfo',
						title: '物流信息',
						width: 100
					}]
				];

				// 运输配送管理 表格定义 
				$('#transitGrid').datagrid({
					iconCls: 'icon-forward',
				    url: '${pageContext.request.contextPath}/transitinfoAction_pageQuery.action',
					fit: true,
					border: false,
					rownumbers: true,
					striped: true,
					pageList: [20, 50, 100],
					pagination: true,
					idField: 'id',
					singleSelect: true,
					toolbar: transitToolbar,
					columns: transitColumns
				});
				// 出入库保存按钮点击事件
				$("#inoutStoreSave").click(function(){
					if($("#inoutStoreForm").form('validate')){
						$("#inoutStoreForm").submit();
					}
				});	
				
				// 开始配送保存按钮点击事件
				$("#deliverySave").click(function(){
					if($("#deliveryForm").form('validate')){
						$("#deliveryForm").submit();
					}
				});	
				
				// 签收录入按钮点击事件
				$("#signSave").click(function(){
					if($("#signForm").form('validate')){
						$("#signForm").submit();
					}
				});	
				
				// 点击查询按钮，实现途经点的路线规划
				$('#searchBtn').click(function(){
					//1.获取途经点
					var waypoints = $('#waypoints').val();
					//2.切割成数组
					var waypointsArr = waypoints.split(',');
					//3.判断是否输入
					if(waypointsArr!=''){
						//4.有，根据途经点查询路径
						//三种驾车策略：最少时间，最短距离，避开高速
						var routePolicy = [BMAP_DRIVING_POLICY_LEAST_TIME,BMAP_DRIVING_POLICY_LEAST_DISTANCE,BMAP_DRIVING_POLICY_AVOID_HIGHWAYS];
						map.clearOverlays(); 
						search(allstart,allend,routePolicy[0],waypointsArr); 
						function search(start,end,route,waypoints){ 
							var driving = new BMap.DrivingRoute(map, {renderOptions:{map: map, autoViewport: true},policy: route});
							driving.search(start,end,{waypoints:waypoints});
						}
					} else {
						//5.没有，直接起始点查询路径
						var routePolicy = [BMAP_DRIVING_POLICY_LEAST_TIME,BMAP_DRIVING_POLICY_LEAST_DISTANCE,BMAP_DRIVING_POLICY_AVOID_HIGHWAYS];
						map.clearOverlays(); 
						search(allstart,allend,routePolicy[0]); 
						function search(start,end,route){ 
							var driving = new BMap.DrivingRoute(map, {renderOptions:{map: map, autoViewport: true},policy: route});
							driving.search(start,end);
						}
					}
			});
			});
		</script>
		<style type="text/css">
			body, html {width: 100%;height: 100%; margin:0;font-family:"微软雅黑";}
			#allmap{height:92%;width:100%;}
		</style>
	</head>

	<body class="easyui-layout" style="visibility:hidden;">

		<div data-options="region:'center'">
			<table id="transitGrid"></table>
		</div>

		<div class="easyui-window" title="出入库" id="inoutstoreWindow" modal="true" closed="true" collapsible="false" minimizable="false" maximizable="false" style="top:100px;left:200px;width: 600px; height: 300px">
			<div region="north" style="height:30px;overflow:hidden;" split="false" border="false">
				<div class="datagrid-toolbar">
					<a id="inoutStoreSave" icon="icon-save" href="#" class="easyui-linkbutton" plain="true">保存</a>
				</div>
			</div>
			<div region="center" style="overflow:auto;padding:5px;" border="false">
				<form id="inoutStoreForm" method="post" action="${pageContext.request.contextPath}/inoutstoreAction_save.action">
					<table class="table-edit" width="80%" align="center">
						<tr class="title">
							<td colspan="2">入库操作</td>
						</tr>
						<tr>
							<td>运单信息</td>
							<td>
								<input type="hidden" name="transitInfoId" id="inOutStoreId" />
								<span id="inOutStoreTransitInfoView"></span>
						</tr>
						<tr>
							<td>操作</td>
							<td>
								<select name="operation" class="easyui-combobox">
									<option value="入库">入库</option>
									<option value="出库">出库</option>
									<option value="到达网点">到达网点</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>仓库或网点地址</td>
							<td>
								<input type="text" name="address" size="40" />
							</td>
						</tr>
						<tr>
							<td>描述</td>
							<td>
								<textarea rows="3" cols="40" name="description"></textarea>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>

		<div class="easyui-window" title="开始配送" id="deliveryWindow" modal="true" closed="true" collapsible="false" minimizable="false" maximizable="false" style="top:100px;left:200px;width: 600px; height: 300px">
			<div region="north" style="height:30px;overflow:hidden;" split="false" border="false">
				<div class="datagrid-toolbar">
					<a id="deliverySave" icon="icon-save" href="#" class="easyui-linkbutton" plain="true">保存</a>
				</div>
			</div>
			<div region="center" style="overflow:auto;padding:5px;" border="false">
				<form id="deliveryForm" method="post" action="${pageContext.request.contextPath}/deliveryAction_save.action">
					<table class="table-edit" width="80%" align="center">
						<tr class="title">
							<td colspan="2">开始配送</td>
						</tr>
						<tr>
							<td>运单信息</td>
							<td>
								<input type="hidden" name="transitInfoId" id="deliveryId" />
								<span id="deliveryTransitInfoView"></span>
						</tr>
						<tr>
							<td>快递员工号</td>
							<td>
								<input type="text" required="true" name="courierNum" />
							</td>
						</tr>
						<tr>
							<td>快递员姓名</td>
							<td>
								<input type="text" required="true" name="courierName" />
							</td>
						</tr>
						<tr>
							<td>描述</td>
							<td>
								<textarea rows="3" cols="40" name="description"></textarea>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>

		<div class="easyui-window" title="签收录入" id="signWindow" modal="true" closed="true" collapsible="false" minimizable="false" maximizable="false" style="top:100px;left:200px;width: 600px; height: 300px">
			<div region="north" style="height:30px;overflow:hidden;" split="false" border="false">
				<div class="datagrid-toolbar">
					<a id="signSave" icon="icon-save" href="#" class="easyui-linkbutton" plain="true">保存</a>
				</div>
			</div>
			<div region="center" style="overflow:auto;padding:5px;" border="false">
				<form id="signForm" method="post" action="${pageContext.request.contextPath}/signAction_save.action">
					<table class="table-edit" width="80%" align="center">
						<tr class="title">
							<td colspan="2">签收录入</td>
						</tr>
						<tr>
							<td>运单信息</td>
							<td>
								<input type="hidden" name="transitInfoId" id="signId" />
								<span id="signTransitInfoView"></span>
						</tr>
						<tr>
							<td>签收人</td>
							<td>
								<input type="text" required="true" name="signName" />
							</td>
						</tr>
						<tr>
							<td>签收日期</td>
							<td>
								<input type="text" class="easyui-datebox" required="true" name="signTime" />
							</td>
						</tr>
						<tr>
							<td>签收状态</td>
							<td colspan="3">
								<select class="easyui-combobox" style="width:150px" name="signType">
									<option value="正常">正常</option>
									<option value="返单">返单</option>
									<option value="转发签收">转发签收</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>异常备注</td>
							<td>
								<textarea name="errorRemark" rows="4" cols="40"></textarea>
							</td>
						</tr>
						<tr>
							<td>描述</td>
							<td>
								<textarea rows="3" cols="40" name="description"></textarea>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>

		<div class="easyui-window" title="运输路径查看" id="transitPathWindow" modal="true" closed="true" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:100px;width: 800px; height: 400px">
			<div id="allmap"></div>
			<div>
				途经点录入，如果有多个以,隔开：<input type="text" id="waypoints"/><input type="button" value="查询" id="searchBtn"/>
			</div>
		</div>

		<div class="easyui-window" title="实时配送路径" id="deliveryInTimePathWindow" modal="true" closed="true" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:100px;width: 800px; height:400px">
		</div>
	</body>

</html>