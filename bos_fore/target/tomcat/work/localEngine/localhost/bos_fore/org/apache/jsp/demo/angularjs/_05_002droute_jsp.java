package org.apache.jsp.demo.angularjs;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class _05_002droute_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<title>angularjs-路由</title>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/js/angular.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/js/angular-route.min.js\"></script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\t<div ng-app=\"myApp\">\r\n");
      out.write("\t\t<ul>\r\n");
      out.write("\t\t\t<li><a href=\"#/\">首页</a></li>\r\n");
      out.write("\t\t\t<li><a href=\"#/customer\">用户管理</a></li>\r\n");
      out.write("\t\t\t<li><a href=\"#/quanxian\">权限管理</a></li>\r\n");
      out.write("\t\t\t<li><a href=\"#/system\">系统管理</a></li>\r\n");
      out.write("\t\t</ul>\r\n");
      out.write("\t\t<div ng-view></div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<script type=\"text/javascript\">\r\n");
      out.write("\t\t//1.使用angular的module创建模块，参数1：模块名，要和ng-app的值相同；参数2:数组，设置该模块依赖的其他模块\r\n");
      out.write("\t\tvar app = angular.module('myApp',['ngRoute']);\r\n");
      out.write("\t\t//2.在模块中创建控制器：参数1：控制器名称，要和ng-controller的值一致，参数2：该控制器绑定的函数\r\n");
      out.write("\t\tapp.config(function($routeProvider){\r\n");
      out.write("\t\t\t$routeProvider.when('/',{templateUrl:'05-route2.jsp'})\r\n");
      out.write("\t\t\t\t\t\t  .when('/customer',{templateUrl:'05-route3.jsp'})\r\n");
      out.write("\t\t\t\t\t\t  .when('/quanxian',{templateUrl:'05-route4.jsp'})\r\n");
      out.write("\t\t\t\t\t\t  .when('/system',{templateUrl:'05-route5.jsp'})\r\n");
      out.write("\t\t\t\t\t\t  .otherwise({redirectTo:'/'});\r\n");
      out.write("\t\t});\r\n");
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
