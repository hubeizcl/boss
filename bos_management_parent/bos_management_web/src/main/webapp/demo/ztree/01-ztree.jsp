<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>ztree-树插件</title>
<!-- easyui资源文件 -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/jquery.easyui.min.js"></script>
<!-- ztree的资源文件 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/js/ztree/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ztree/jquery.ztree.all-3.5.js"></script>
</head>
<body class="easyui-layout">
	<div title="XX管理系统" data-options="region:'north'" style="height:100px">北部区域</div>
	<div title="菜单管理" data-options="region:'west'" style="width:200px">
		<!-- 折叠面板 
			fit:自动填充满父容器
		-->
		<div class="easyui-accordion" fit="true">
			<div title="面板1" data-options="iconCls:'icon-help'">
				<a onclick="doAdd();" class="easyui-linkbutton">动态添加选项卡</a>
				<script type="text/javascript">
					function doAdd(){
						$('#tt').tabs('add',{    
						    title:'动态选项卡',    
						    content:'这是一个动态选项卡！',    
						    closable:true,
						    iconCls:'icon-help',
						    tools:[{    
						        iconCls:'icon-mini-refresh',    
						        handler:function(){    
						            alert('refresh');    
						        }    
						    }]    
						}); 
					}
				</script>
			</div>
			<div title="面板2" data-options="iconCls:'icon-help'">
			<!-- 一：基于标准json构建ztree树 （了解）-->
			<!-- 1.提供一个容器：ul标签 -->
			<ul id="ztree1" class="ztree"></ul>
			<script type="text/javascript">
				$(function(){
					//2.定义全局的setting变量，使用标准json，setting可以是{}
					var settting = {};
					//3.定义节点数组
					var nodes = [
					             {name:'权限管理'},
					             {name:'用户管理',children:[
					                                    {name:'用户添加'},
					                                    {name:'用户修改'},
					                                    {name:'用户删除'}
					                                    ]},
					             {name:'系统管理'}
					             ];
					//4.初始化树
					$.fn.zTree.init($("#ztree1"), settting, nodes);
				});
			</script>
			</div>
			<div title="面板3" data-options="iconCls:'icon-help'">
			<!-- 二：基于简单json构建ztree树 （掌握）-->
			<!-- 1.提供一个容器：ul标签 -->
			<ul id="ztree2" class="ztree"></ul>
			<script type="text/javascript">
				$(function(){
					//2.定义全局的setting变量，使用标准json，setting可以是{}
					var settting = {
							data:{
								simpleData:{
									enable:true//开启了简单json数据功能
								}
							}
					};
					//3.定义节点数组
					var nodes = [
					             {id:'1', pId:'0', name:'权限管理'},
					             {id:'2', pId:'0', name:'用户管理'},
					             {id:'21', pId:'2', name:'用户添加'},
					             {id:'22', pId:'2', name:'用户修改'},
					             {id:'23', pId:'2', name:'用户删除'},
					             {id:'3', pId:'0', name:'系统管理'}
					             ];
					//4.初始化树
					$.fn.zTree.init($("#ztree2"), settting, nodes);
				});
			</script>
			</div>
			<div title="面板4" data-options="iconCls:'icon-help'">
			<!-- 三：基于ajax加载树节点数据 （掌握）-->
			<!-- 1.提供一个容器：ul标签 -->
			<ul id="ztree3" class="ztree"></ul>
			<script type="text/javascript">
				$(function(){
					//2.定义全局的setting变量，使用标准json，setting可以是{}
					var settting = {
							data:{
								simpleData:{
									enable:true//开启了简单json数据功能
								}
							},
							callback:{
								onClick:function(event, treeId, treeNode){
									//1.获取到page属性
									var page = treeNode.page;
									//2.判断是否是子节点
									if(page != undefined){
										//是子节点，可以添加选项卡
										//3.判断要添加的选项卡是否存在，
										var b = $('#tt').tabs('exists',treeNode.name);
										if(b){
											//3.1存在，选中
											$('#tt').tabs('select',treeNode.name);
										} else {
											//3.2不存在，添加新选项卡
											$('#tt').tabs('add',{    
											    title:treeNode.name,    
											    content:'<iframe src="${pageContext.request.contextPath}/'+page+'" style="height:100%;width:100%" frameborder="0"></iframe>',    
											    closable:true,
											    iconCls:'icon-help',
											    tools:[{    
											        iconCls:'icon-mini-refresh',    
											        handler:function(){    
											            alert('refresh');    
											        }    
											    }]    
											}); 
										}
									}
								}
							}
					};
					//3.基于ajax加载节点数组
					var url = "${pageContext.request.contextPath}/data/menu.json";
					$.post(url,{},function(data){
						//4.初始化树
						$.fn.zTree.init($("#ztree3"), settting, data);
					},'json');
				});
			</script>
			</div>
		</div>
	</div>
	<div data-options="region:'center'">
		<!-- 选项卡面板 
			fit:自动填充满父容器
		-->
		<div id="tt" class="easyui-tabs" fit="true">
			<div title="面板1" data-options="iconCls:'icon-help',closable:true">1</div>
			<div title="面板2" data-options="iconCls:'icon-help'">2</div>
			<div title="面板3" data-options="iconCls:'icon-help'">3</div>
			<div title="面板4" data-options="iconCls:'icon-help'">4</div>
		</div>
	</div>
	<div data-options="region:'east'" style="width:100px">东部区域</div>
	<div data-options="region:'south'" style="height:100px">南部区域</div>
</body>
</html>