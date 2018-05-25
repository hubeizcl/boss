package org.apache.jsp.demo.baidumap;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class _01_002dbaidumap_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n");
      out.write("\t<meta name=\"viewport\" content=\"initial-scale=1.0, user-scalable=no\" />\r\n");
      out.write("\t<style type=\"text/css\">\r\n");
      out.write("\tbody, html {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:\"微软雅黑\";}\r\n");
      out.write("\t#map1_container,#map2_container {width:100%;height:50%;float:left;overflow: hidden;margin:0;}\r\n");
      out.write("\t#allmap1{margin:0 0 3px;height:100%;}\r\n");
      out.write("\t#allmap2{margin:3px 0 0;height:100%;}\r\n");
      out.write("\t</style>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"http://api.map.baidu.com/api?v=2.0&ak=4IU3oIAMpZhfWZsMu7xzqBBAf6vMHcoa\"></script>\r\n");
      out.write("\t<title>同时加载两个地图</title>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\t<div id=\"map1_container\"><div id=\"allmap1\"></div></div>\r\n");
      out.write("\t<div id=\"map2_container\"><div id=\"allmap2\"></div></div>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\t//百度地图API功能\r\n");
      out.write("\t//加载第一张地图\r\n");
      out.write("\tvar map1 = new BMap.Map(\"allmap1\");            // 创建Map实例\r\n");
      out.write("    var point1 = new BMap.Point(116.404, 39.915);  \r\n");
      out.write("    map1.centerAndZoom(point1,15);               \r\n");
      out.write("    map1.enableScrollWheelZoom();                  //启用滚轮放大缩小\r\n");
      out.write("\t//加载第二张地图\r\n");
      out.write("\tvar map2 = new BMap.Map(\"allmap2\");            // 创建Map实例\r\n");
      out.write("    var point2 = new BMap.Point(116.404, 39.915);  \r\n");
      out.write("    map2.centerAndZoom(point2,15);                 \r\n");
      out.write("    map2.enableScrollWheelZoom();                  //启用滚轮放大缩小\r\n");
      out.write("</script>\r\n");
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
