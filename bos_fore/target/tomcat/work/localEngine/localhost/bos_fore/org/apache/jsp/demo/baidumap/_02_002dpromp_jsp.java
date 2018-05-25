package org.apache.jsp.demo.baidumap;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class _02_002dpromp_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\t\tbody, html{width: 100%;height: 100%;margin:0;font-family:\"微软雅黑\";font-size:14px;}\r\n");
      out.write("\t\t#l-map{height:300px;width:100%;}\r\n");
      out.write("\t\t#r-result{width:100%;}\r\n");
      out.write("\t</style>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"http://api.map.baidu.com/api?v=2.0&ak=4IU3oIAMpZhfWZsMu7xzqBBAf6vMHcoa\"></script>\r\n");
      out.write("\t<title>关键字输入提示词条</title>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\t<div id=\"l-map\"></div>\r\n");
      out.write("\t<div id=\"r-result\">请输入:<input type=\"text\" id=\"suggestId\" size=\"20\" value=\"百度\" style=\"width:150px;\" /></div>\r\n");
      out.write("\t<div id=\"searchResultPanel\" style=\"border:1px solid #C0C0C0;width:150px;height:auto; display:none;\"></div>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\t// 百度地图API功能\r\n");
      out.write("\tfunction G(id) {\r\n");
      out.write("\t\treturn document.getElementById(id);\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tvar map = new BMap.Map(\"l-map\");\r\n");
      out.write("\tmap.centerAndZoom(\"北京\",12);                   // 初始化地图,设置城市和地图级别。\r\n");
      out.write("\r\n");
      out.write("\tvar ac = new BMap.Autocomplete(    //建立一个自动完成的对象\r\n");
      out.write("\t\t{\"input\" : \"suggestId\"\r\n");
      out.write("\t\t,\"location\" : map\r\n");
      out.write("\t});\r\n");
      out.write("\r\n");
      out.write("\tac.addEventListener(\"onhighlight\", function(e) {  //鼠标放在下拉列表上的事件\r\n");
      out.write("\tvar str = \"\";\r\n");
      out.write("\t\tvar _value = e.fromitem.value;\r\n");
      out.write("\t\tvar value = \"\";\r\n");
      out.write("\t\tif (e.fromitem.index > -1) {\r\n");
      out.write("\t\t\tvalue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;\r\n");
      out.write("\t\t}    \r\n");
      out.write("\t\tstr = \"FromItem<br />index = \" + e.fromitem.index + \"<br />value = \" + value;\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tvalue = \"\";\r\n");
      out.write("\t\tif (e.toitem.index > -1) {\r\n");
      out.write("\t\t\t_value = e.toitem.value;\r\n");
      out.write("\t\t\tvalue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;\r\n");
      out.write("\t\t}    \r\n");
      out.write("\t\tstr += \"<br />ToItem<br />index = \" + e.toitem.index + \"<br />value = \" + value;\r\n");
      out.write("\t\tG(\"searchResultPanel\").innerHTML = str;\r\n");
      out.write("\t});\r\n");
      out.write("\r\n");
      out.write("\tvar myValue;\r\n");
      out.write("\tac.addEventListener(\"onconfirm\", function(e) {    //鼠标点击下拉列表后的事件\r\n");
      out.write("\tvar _value = e.item.value;\r\n");
      out.write("\t\tmyValue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;\r\n");
      out.write("\t\tG(\"searchResultPanel\").innerHTML =\"onconfirm<br />index = \" + e.item.index + \"<br />myValue = \" + myValue;\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tsetPlace();\r\n");
      out.write("\t});\r\n");
      out.write("\r\n");
      out.write("\tfunction setPlace(){\r\n");
      out.write("\t\tmap.clearOverlays();    //清除地图上所有覆盖物\r\n");
      out.write("\t\tfunction myFun(){\r\n");
      out.write("\t\t\tvar pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果\r\n");
      out.write("\t\t\tmap.centerAndZoom(pp, 18);\r\n");
      out.write("\t\t\tmap.addOverlay(new BMap.Marker(pp));    //添加标注\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tvar local = new BMap.LocalSearch(map, { //智能搜索\r\n");
      out.write("\t\t  onSearchComplete: myFun\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\tlocal.search(myValue);\r\n");
      out.write("\t}\r\n");
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
