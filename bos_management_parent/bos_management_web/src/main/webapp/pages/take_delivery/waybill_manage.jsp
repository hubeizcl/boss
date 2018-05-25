<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="keywords" content="jquery,ui,easy,easyui,web">
		<meta name="description" content="easyui help you build your web page easily!">
		<title>运单管理</title>
		<link rel="stylesheet" type="text/css" href="../../js/easyui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="../../js/easyui/themes/icon.css">
		<link rel="stylesheet" type="text/css" href="../../js/easyui/ext/portal.css">
		<link rel="stylesheet" type="text/css" href="../../css/default.css">

		<script type="text/javascript" src="../../js/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="../../js/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="../../js/easyui/ext/jquery.portal.js"></script>
		<script type="text/javascript" src="../../js/easyui/ext/jquery.cookie.js"></script>
		<script src="../../js/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
		<script type="text/javascript">
			$(function(){
				$.fn.serializeJson=function(){  
		            var serializeObj={};  
		            var array=this.serializeArray();  
		            var str=this.serialize();  
		            $(array).each(function(){  
		                if(serializeObj[this.name]){  
		                    if($.isArray(serializeObj[this.name])){  
		                        serializeObj[this.name].push(this.value);  
		                    }else{  
		                        serializeObj[this.name]=[serializeObj[this.name],this.value];  
		                    }  
		                }else{  
		                    serializeObj[this.name]=this.value;   
		                }  
		            });  
		            return serializeObj;  
		        }; 
			});
			function doSearch() {
				//1.获取表单的json对象数据
				var data = $('#searchForm').serializeJson();
				//2.调用datagrid的load方法，实现待条件的分页查询
				$('#tt').datagrid('load', data);
			}
		</script>
	</head>

	<body>
		<div id="tb">
			<a id="save" icon="icon-edit" href="#" class="easyui-linkbutton" plain="true">修改</a>
			<a id="cut" icon="icon-cut" href="#" class="easyui-linkbutton" plain="true">作废</a>
			<a id="help" icon="icon-help" href="#" class="easyui-linkbutton" plain="true">任务提示</a>
			<a id="help" icon="icon-print" href="#" class="easyui-linkbutton" plain="true">打印查询结果</a>
			<a id="help" icon="icon-cancel" href="#" class="easyui-linkbutton" plain="true">取消</a>
			<a id="help" icon="icon-save" href="#" class="easyui-linkbutton" plain="true">保存</a>
			<a id="help" icon="icon-print" href="#" class="easyui-linkbutton" plain="true">打印标签</a>
			<a id="help" icon="icon-print" href="#" class="easyui-linkbutton" plain="true">打印工作单</a>
			<a id="transitBtn" icon="icon-print" href="#" class="easyui-linkbutton" plain="true">开始配送</a>
			<script type="text/javascript">
			$(function(){
				$('#transitBtn').click(function(){
					//1.获取客户选中数据
					var rows = $('#tt').datagrid('getSelections');
					//2.判断是否选中
					if(rows.length <= 0){
						//3.未选中，提示客户选择需要配送的运单
						$.messager.alert('提示信息','请您选择要配送的运单！','warning');
					} else {
						//4.选中，获取选中的运单id，通过请求发送到后台，更新状态，通知客户成功或失败
						var array = new Array();
						for(var i=0;i<rows.length;i++){
							array.push(rows[i].id);
						}
						var waybillIds = array.join(',');
						//5.通过请求发送到后台，更新状态，通知客户成功或失败
						var url = "${pageContext.request.contextPath}/transitinfoAction_startTransit.action";
						$.post(url,{waybillIds:waybillIds},function(data){
							if("1" == data){
								//成功，提示成功
								$.messager.alert('提示信息','开启配送成功！','info');
							} else {
								//失败，提示失败
								$.messager.alert('提示信息','开启配送失败！','warning');
							}
						});
					}
				});
			});
			</script>
			<br />
			<form id="searchForm">
				运单号：<input name="wayBillNum" style="line-height:26px;border:1px solid #ccc">
				发货地：<input name="sendAddress" style="line-height:26px;border:1px solid #ccc" >
				收货地：<input name="recAddress" style="line-height:26px;border:1px solid #ccc" >
				
				<select class="easyui-combobox" style="width:150px" name="sendProNum">
					<option value="">请选择快递产品类型</option>
					<option value="速运当日">速运当日</option>
					<option value="速运次日">速运次日</option>
					<option value="速运隔日">速运隔日</option>
				</select>
				
				<select class="easyui-combobox" style="width:150px" name="signStatus">
					<option value="0">请选择运单状态</option>
					<option value="1">待发货</option>
					<option value="2">派送中</option>
					<option value="3">已签收</option>
					<option value="4">异常</option>
				</select>
				
				<a href="javascript:void" class="easyui-linkbutton" 
					plain="true" onclick="doSearch()">查询</a> 
			</form>
		</div>
		<table id="tt" class="easyui-datagrid" fit="true" toolbar="#tb" rownumbers="true" pagination="true"
			url="${pageContext.request.contextPath}/waybillAction_pageQuery.action">
			<thead>
				<tr>
					<th field="id" checkbox="true"></th>
					<th field="wayBillNum" width="80">运单编号</th>
					<th field="sendName" width="80">寄件人</th>
					<th field="sendMobile" width="80">寄件人电话</th>
					<th field="sendCompany" width="80">寄件人公司</th>
					<th field="sendAddress" width="120">寄件人详细地址</th>
					<th field="recName" width="80">收件人</th>
					<th field="recMobile" width="80">收件人电话</th>
					<th field="recCompany" width="80">收件人公司</th>
					<th field="recAddress" width="120">收件人详细地址</th>
					<th field="sendProNum" width="80">产品类型</th>
					<th field="payTypeNum" width="80">支付类型</th>
					<th field="weight" width="80"> 重量</th>
					<th field="num" width="80"> 原件数</th>
					<th field="feeitemnum" width="80">实际件数</th>
					<th field="actlweit" width="80">实际重量</th>
					<th field="vol" width="80">体积</th>
					<th field="floadreqr" width="80">配载要求</th>
					<th field="wayBillType" width="80">运单类型</th>
				</tr>
			</thead>
		</table>
	</body>

</html>