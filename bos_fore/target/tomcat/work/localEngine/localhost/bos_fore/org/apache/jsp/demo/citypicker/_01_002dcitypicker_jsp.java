package org.apache.jsp.demo.citypicker;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class _01_002dcitypicker_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<title>citypicker-三级联动</title>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/js/jquery.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/js/citypicker/js/city-picker.data.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/js/citypicker/js/city-picker.js\"></script>\r\n");
      out.write("<link type=\"text/css\" rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/js/citypicker/css/city-picker.css\">\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\t<!-- 创建方式 -->\r\n");
      out.write("\t<h1> 1.使用html创建</h1>\r\n");
      out.write("\t<div style=\"position: relative\">\r\n");
      out.write("\t\t<input readonly type=\"text\" data-toggle=\"city-picker\"/>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<h1> 2.使用js创建</h1>\r\n");
      out.write("\t<div style=\"position: relative\">\r\n");
      out.write("\t\t<input id=\"citypicker1\" readonly type=\"text\"/><br/><br/><br/>\r\n");
      out.write("\t\t<input onclick=\"reset();\" type=\"button\" value=\"重置\"/>\r\n");
      out.write("\t\t<input onclick=\"doDefault()\" type=\"button\" value=\"默认值\"/>\r\n");
      out.write("\t\t<script type=\"text/javascript\">\r\n");
      out.write("\t\t$(function(){\r\n");
      out.write("\t\t\t$('#citypicker1').citypicker();\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t//使用操作\r\n");
      out.write("\t\t//重置的方法\r\n");
      out.write("\t\tfunction reset(){\r\n");
      out.write("\t\t\t//reset就是citypicker的内部重置方法\r\n");
      out.write("\t\t\t$('#citypicker1').citypicker('reset');\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t//设置默认值方法\r\n");
      out.write("\t\tfunction doDefault(){\r\n");
      out.write("\t\t\t//1.先调用reset\r\n");
      out.write("\t\t\t$('#citypicker1').citypicker('reset');\r\n");
      out.write("\t\t\t//2.调用destroy\r\n");
      out.write("\t\t\t//$('#citypicker1').citypicker('destroy');\r\n");
      out.write("\t\t\t//3.调用初始化函数\r\n");
      out.write("\t\t\t$('#citypicker1').citypicker({\r\n");
      out.write("\t\t\t\tprovince:'北京市',\r\n");
      out.write("\t\t\t\tcity:'北京市',\r\n");
      out.write("\t\t\t\tdistrict:'东城区'\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t</script>\r\n");
      out.write("\t</div>\r\n");
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
