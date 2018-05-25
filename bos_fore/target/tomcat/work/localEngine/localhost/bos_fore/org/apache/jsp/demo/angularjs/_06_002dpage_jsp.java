package org.apache.jsp.demo.angularjs;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class _06_002dpage_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n");
      out.write("<title>angularjs+bootstrap实现分页查询</title>\r\n");
      out.write("<script type=\"text/javascript\"\r\n");
      out.write("\tsrc=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/js/angular.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\"\r\n");
      out.write("\tsrc=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/js/angular-route.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\"\r\n");
      out.write("\tsrc=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/js/jquery.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\"\r\n");
      out.write("\tsrc=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/js/bootstrap.js\"></script>\r\n");
      out.write("<link rel=\"stylesheet\"\r\n");
      out.write("\thref=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/css/bootstrap/bootstrap.css\" />\r\n");
      out.write("</head>\r\n");
      out.write("<body ng-app=\"paginationApp\" ng-controller=\"paginationCtrl\">\r\n");
      out.write("\t<table class=\"table table-bordered table-hover\">\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<th>序号</th>\r\n");
      out.write("\t\t\t<th>商品编号</th>\r\n");
      out.write("\t\t\t<th>名称</th>\r\n");
      out.write("\t\t\t<th>价格</th>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr ng-repeat=\"product in products\">\r\n");
      out.write("\t\t\t<td>{{$index+1}}</td>\r\n");
      out.write("\t\t\t<td>{{product.id}}</td>\r\n");
      out.write("\t\t\t<td>{{product.name}}</td>\r\n");
      out.write("\t\t\t<td>{{product.price}}</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t</table>\r\n");
      out.write("\t<div>\r\n");
      out.write("\t\t<ul class=\"pagination pull-right\">\r\n");
      out.write("\t\t\t<li><a href ng-click=\"prev()\">上一页</a></li>\r\n");
      out.write("\t\t\t<li ng-repeat=\"page in pageList\"\r\n");
      out.write("\t\t\t\tng-class=\"{active: isActivePage(page)}\"><a\r\n");
      out.write("\t\t\t\tng-click=\"selectPage(page)\">{{page}}</a></li>\r\n");
      out.write("\t\t\t<li><a href ng-click=\"next()\">下一页</a></li>\r\n");
      out.write("\t\t</ul>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<script type=\"text/javascript\">\r\n");
      out.write("\t\tvar myapp = angular.module(\"paginationApp\", []);\r\n");
      out.write("\t\tmyapp.controller(\"paginationCtrl\", [\r\n");
      out.write("\t\t\t\t\"$scope\",\r\n");
      out.write("\t\t\t\t\"$http\",\r\n");
      out.write("\t\t\t\tfunction($scope, $http) {\r\n");
      out.write("\t\t\t\t\t$scope.currentPage = 1; // 当前页\r\n");
      out.write("\t\t\t\t\t$scope.pageSize = 2; // 当前页显示的记录数\r\n");
      out.write("\t\t\t\t\t$scope.totalCount = 0; // 总记录数\r\n");
      out.write("\t\t\t\t\t$scope.totalPages = 0; // 总页数\r\n");
      out.write("\t\t\t\t\t// 上一页\r\n");
      out.write("\t\t\t\t\t$scope.prev = function() {\r\n");
      out.write("\t\t\t\t\t\tif ($scope.currentPage > 1) {\r\n");
      out.write("\t\t\t\t\t\t\t$scope.currentPage = $scope.currentPage - 1;\r\n");
      out.write("\t\t\t\t\t\t\t$scope.selectPage($scope.currentPage);\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t// 下一页\r\n");
      out.write("\t\t\t\t\t$scope.next = function() {\r\n");
      out.write("\t\t\t\t\t\tif ($scope.currentPage < $scope.totalPages) {\r\n");
      out.write("\t\t\t\t\t\t\t$scope.currentPage = $scope.currentPage + 1;\r\n");
      out.write("\t\t\t\t\t\t\t$scope.selectPage($scope.currentPage);\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t// 选择页码，当前页，传递当前页\r\n");
      out.write("\t\t\t\t\t$scope.selectPage = function(page) {\r\n");
      out.write("\t\t\t\t\t\t$http({\r\n");
      out.write("\t\t\t\t\t\t\tmethod : 'GET',\r\n");
      out.write("\t\t\t\t\t\t\turl : './06_' + page + '.json',//动态加载json的文件\r\n");
      out.write("\t\t\t\t\t\t\tparams : {\r\n");
      out.write("\t\t\t\t\t\t\t\tpage : page, // 传递当前页\r\n");
      out.write("\t\t\t\t\t\t\t\tpageSize : $scope.pageSize\r\n");
      out.write("\t\t\t\t\t\t\t// 传递当前页最多显示的记录数\r\n");
      out.write("\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t}).success(\r\n");
      out.write("\t\t\t\t\t\t\t\tfunction(data, status, headers, config) {\r\n");
      out.write("\t\t\t\t\t\t\t\t\t// 显示表格数据 \r\n");
      out.write("\t\t\t\t\t\t\t\t\t$scope.products = data.products;\r\n");
      out.write("\t\t\t\t\t\t\t\t\t// 计算总页数\r\n");
      out.write("\t\t\t\t\t\t\t\t\t$scope.totalCount = data.totalCount;\r\n");
      out.write("\t\t\t\t\t\t\t\t\t// 计算总页数：Math.ceil返回大于参数x的最小整数,即对浮点数向上取整.\r\n");
      out.write("\t\t\t\t\t\t\t\t\t$scope.totalPages = Math\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t.ceil($scope.totalCount\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t/ $scope.pageSize);\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t// 当前显示页，设为当前页\r\n");
      out.write("\t\t\t\t\t\t\t\t\t$scope.currentPage = page;\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t// 固定显示10页 (前5后4)\r\n");
      out.write("\t\t\t\t\t\t\t\t\tvar begin;\r\n");
      out.write("\t\t\t\t\t\t\t\t\tvar end;\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\tbegin = page - 5;\r\n");
      out.write("\t\t\t\t\t\t\t\t\tif (begin < 0) {\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\tbegin = 1;\r\n");
      out.write("\t\t\t\t\t\t\t\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\tend = begin + 9;\r\n");
      out.write("\t\t\t\t\t\t\t\t\tif (end > $scope.totalPages) {\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\tend = $scope.totalPages;\r\n");
      out.write("\t\t\t\t\t\t\t\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\tbegin = end - 9;\r\n");
      out.write("\t\t\t\t\t\t\t\t\tif (begin < 1) {\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\tbegin = 1;\r\n");
      out.write("\t\t\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t\t\t\t// 定义数组\r\n");
      out.write("\t\t\t\t\t\t\t\t\t$scope.pageList = new Array();\r\n");
      out.write("\t\t\t\t\t\t\t\t\tfor (var i = begin; i <= end; i++) {\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t$scope.pageList.push(i);\r\n");
      out.write("\t\t\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t\t\t}).error(\r\n");
      out.write("\t\t\t\t\t\t\t\tfunction(data, status, headers, config) {\r\n");
      out.write("\t\t\t\t\t\t\t\t\talert(\"分页出错，请联系管理员\");\r\n");
      out.write("\t\t\t\t\t\t\t\t});\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t// 是否激活当前页\r\n");
      out.write("\t\t\t\t\t$scope.isActivePage = function(page) {\r\n");
      out.write("\t\t\t\t\t\treturn page == $scope.currentPage; // 返回boolean类型，如果传递的页码和当前页相等返回true\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t// 激活当前页\r\n");
      out.write("\t\t\t\t\t$scope.selectPage($scope.currentPage);\r\n");
      out.write("\t\t\t\t} ])\r\n");
      out.write("\t</script>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
