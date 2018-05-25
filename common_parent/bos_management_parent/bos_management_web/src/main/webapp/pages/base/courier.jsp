<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<title>管理取派员</title>
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
		<script type="text/javascript">
			function doAdd(){
				$('#addWindow').window("open");
			}
			
			function doEdit(){
				alert("修改...");
			}
			
			function doDelete(){
				//1.获取客户选中的数据
				var rows = $('#grid').datagrid('getSelections');
				//2.判断是否选中，
				if(rows.length <= 0){
					//3.未选中，提示客户，选择数据
					$.messager.alert('提示信息','请您选择要删除的数据！','warning');
				} else {
					//4.选中，让客户确认是否删除数据，
					$.messager.confirm('确认信息','您确定要删除这些数据么？', function(r){
						if(r){
							//5客户确认删除，执行删除操作update Courier set deltag = 1 where id=?
							var idsArr = new Array();
							for(var i=0; i<rows.length; i++){
								idsArr.push(rows[i].id);
							}
							//将idsArr中的id拼接成字符串：1,2,3
							var ids = idsArr.join(',');
							//6.通过请求将数据发送到后台
							//请求发送方式：ajax、表单提交、location、iframe
							window.location.href="${pageContext.request.contextPath}/courierAction_delete.action?ids="+ids;
						}
					});
				}
			}
			
			function doRestore(){
				alert("将取派员还原...");
			}
			//工具栏
			var toolbar = [ 
			<shiro:hasPermission name="courier">
			{
				id : 'button-add',	
				text : '增加',
				iconCls : 'icon-add',
				handler : doAdd
			}, 
			</shiro:hasPermission>
			{
				id : 'button-edit',
				text : '修改',
				iconCls : 'icon-edit',
				handler : doEdit
			}, {
				id : 'button-delete',
				text : '作废',
				iconCls : 'icon-cancel',
				handler : doDelete
			},{
				id : 'button-restore',
				text : '还原',
				iconCls : 'icon-save',
				handler : doRestore
			},{
				id : 'button-search',
				text : '查询',
				iconCls : 'icon-search',
				handler : doSearch
			}];
			// 定义列
			var columns = [ [ {
				field : 'id',
				checkbox : true,
			},{
				field : 'courierNum',
				title : '工号',
				width : 80,
				align : 'center'
			},{
				field : 'name',
				title : '姓名',
				width : 80,
				align : 'center'
			}, {
				field : 'telephone',
				title : '手机号',
				width : 120,
				align : 'center'
			}, {
				field : 'checkPwd',
				title : '查台密码',
				width : 120,
				align : 'center'
			}, {
				field : 'pda',
				title : 'PDA号',
				width : 120,
				align : 'center'
			}, {
				field : 'standard.name',
				title : '取派标准',
				width : 120,
				align : 'center',
				formatter : function(data,row, index){
					if(row.standard != null){
						return row.standard.name;
					}
					return "";
				}
			}, {
				field : 'type',
				title : '取派员类型',
				width : 120,
				align : 'center'
			}, {
				field : 'company',
				title : '所属单位',
				width : 200,
				align : 'center'
			}, {
				field : 'deltag',
				title : '是否作废',
				width : 80,
				align : 'center',
				formatter : function(data,row, index){
					if(data=="0"){
						return "正常使用"
					}else{
						return "已作废";
					}
				}
			}, {
				field : 'vehicleType',
				title : '车型',
				width : 100,
				align : 'center'
			}, {
				field : 'vehicleNum',
				title : '车牌号',
				width : 120,
				align : 'center'
			} ] ];
			
			$(function(){
				// 先将body隐藏，再显示，不会出现页面刷新效果
				$("body").css({visibility:"visible"});
				
				// 取派员信息表格
				$('#grid').datagrid( {
					iconCls : 'icon-forward',
					fit : true,
					border : false,
					rownumbers : true,
					striped : true,
					pageList: [30,50,100],
					pagination : true,
					toolbar : toolbar,
					url : "${pageContext.request.contextPath}/courierAction_pageQuery.action",
					idField : 'id',
					columns : columns,
					onDblClickRow : doDblClickRow
				});
				
				// 添加取派员窗口
				$('#addWindow').window({
			        title: '添加取派员',
			        width: 800,
			        modal: true,
			        shadow: true,
			        closed: true,//将窗口设置为默认关闭
			        height: 400,
			        resizable:false//设置窗口是否可以修改大小
			    });
				
			});
		
			function doDblClickRow(){
				alert("双击表格数据...");
			}
			
			function doSearch(){
				//1.打开查询窗口
				$('#searchWindow').window('open');
			}
		</script>
	</head>

	<body class="easyui-layout" style="visibility:hidden;">
		<div region="center" border="false">
			<table id="grid"></table>
		</div>
		<div class="easyui-window" title="对收派员进行添加或者修改" id="addWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
			<div region="north" style="height:31px;overflow:hidden;" split="false" border="false">
				<div class="datagrid-toolbar">
					<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true">保存</a>
					<script type="text/javascript">
					$(function(){
						$('#save').click(function(){
							//1.校验表单
							var b = $('#addCourierForm').form('validate');
							//2.通过，提交表单
							if(b){
								$('#addCourierForm').submit();
							}
						});
					});
					</script>
				</div>
			</div>

			<div region="center" style="overflow:auto;padding:5px;" border="false">
				<form id="addCourierForm" method="post"
					action="${pageContext.request.contextPath}/courierAction_add.action">
					<table class="table-edit" width="80%" align="center">
						<tr class="title">
							<td colspan="4">收派员信息</td>
						</tr>
						<tr>
							<td>快递员工号</td>
							<td>
								<input type="text" name="courierNum" class="easyui-validatebox" required="true" />
							</td>
							<td>姓名</td>
							<td>
								<input type="text" name="name" class="easyui-validatebox" required="true" />
							</td>
						</tr>
						<tr>
							<td>手机</td>
							<td>
								<input type="text" name="telephone" class="easyui-validatebox" required="true"
									data-options="validType:'telephone'"/>
								<script type="text/javascript">
								$(function(){
									$.extend($.fn.validatebox.defaults.rules, {    
									    telephone: {    
									        validator: function(value){
									        	//1.定义手机号正则表达式
									        	var reg = /^1[3,5,7,8][0-9]{9}$/;
									        	//2.使用正则表达式校验手机号
									            return reg.test(value);    
									        },    
									        message: '手机号不合法！'   
									    }    
									});  
								});
								</script>
							</td>
							<td>所属单位</td>
							<td>
								<input type="text" name="company" class="easyui-validatebox" required="true" />
							</td>
						</tr>
						<tr>
							<td>查台密码</td>
							<td>
								<input type="text" name="checkPwd" class="easyui-validatebox" required="true" />
							</td>
							<td>PDA号码</td>
							<td>
								<input type="text" name="pda" class="easyui-validatebox" required="true" />
							</td>
						</tr>
						<tr>
							<td>快递员类型</td>
							<td>
								<input type="text" name="type" class="easyui-validatebox" required="true" />
							</td>
							<td>取派标准</td>
							<td>
								<input type="text" name="standard.id" 
										class="easyui-combobox" 
										data-options="required:true,valueField:'id',textField:'name',
											url:'${pageContext.request.contextPath}/standardAction_findAll.action'"/>
							</td>
						</tr>
						<tr>
							<td>车型</td>
							<td>
								<input type="text" name="vehicleType" class="easyui-validatebox" required="true" />
							</td>
							<td>车牌号</td>
							<td>
								<input type="text" name="vehicleNum" class="easyui-validatebox" required="true" />
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		
		<!-- 查询快递员-->
		<div class="easyui-window" title="查询快递员窗口" closed="true" id="searchWindow" collapsible="false" minimizable="false" maximizable="false" style="width: 400px; top:40px;left:200px">
			<div style="overflow:auto;padding:5px;" border="false">
				<form id="searchForm">
					<table class="table-edit" width="80%" align="center">
						<tr class="title">
							<td colspan="2">查询条件</td>
						</tr>
						<tr>
							<td>工号</td>
							<td>
								<input type="text" name="courierNum" />
							</td>
						</tr>
						<tr>
							<td>收派标准</td>
							<td>
								<input type="text" name="standard.name" />
							</td>
						</tr>
						<tr>
							<td>所属单位</td>
							<td>
								<input type="text" name="company" />
							</td>
						</tr>
						<tr>
							<td>类型</td>
							<td>
								<input type="text" name="type" />
							</td>
						</tr>
						<tr>
							<td colspan="2"><a id="searchBtn" href="#" class="easyui-linkbutton"
							 data-options="iconCls:'icon-search'">查询</a> 
							 <script type="text/javascript">
							 $(function(){
								 //1.给表单绑定序列化方法
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
								 
								 $('#searchBtn').click(function(){
									 //1.调用序列化方法，获取表单的json对象
									 var data = $('#searchForm').serializeJson();
									 //2.调用datagrid的load方法，发送url的请求地址，传查询参数
									 $('#grid').datagrid('load', data);
									 //3.关闭查询窗口
									 $('#searchWindow').window('close');
								 });
							 });
							 </script>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</body>

</html>