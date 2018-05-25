package cn.itcast.bos.web.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FileUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.base.Promotion;
import cn.itcast.bos.utils.PageBean;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@ParentPackage("struts-default")
@Namespace("/")
@Actions
@Controller
@Scope("prototype")
public class PromotionAction extends BaseAction<Promotion> {

	private Integer page;//当前页
	
	private Integer pageSize;//每页条数

	public void setPage(Integer page) {
		this.page = page;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	/**
	 * 分页查询方法
	 * @return
	 * @throws IOException 
	 */
	@Action(value="promotionAction_pageQuery")
	public String pageQuery() throws IOException{
		//1.调用bos_management分页接口，查询分页数据
		PageBean<Promotion> pageBean = WebClient.create("http://localhost:8080/")
				 .path("bos_management_web/services/promotionservice/promotion/pageQuery/"+page+"/"+pageSize)
				 .type(MediaType.APPLICATION_JSON)
				 .accept(MediaType.APPLICATION_JSON)
				 .get(PageBean.class);
		String[] excludes = {};
		//2.将分页查询数据转换成json对象，返回到界面
		this.writeJava2JsonObject(pageBean, excludes);
		return NONE;
	}
	
	/**
	 * 宣传任务详情查询方法
	 * @return
	 * @throws IOException 
	 * @throws TemplateException 
	 */
	@Action(value="promotionAction_showDetail")
	public String showDetail() throws IOException, TemplateException{
		//1.获取绝对路径
		String absPath = ServletActionContext.getServletContext().getRealPath("/")+"promotion/";
		//2.拼接完整的宣传任务文件路径
		String htmlPath = absPath + model.getId() + ".html";
		//3.加载文件，判断是否存在
		File htmlFile = new File(htmlPath);
		if(!htmlFile.exists()){
			//4.未存在，根据宣传任务id调用远程接口查询任务信息，
			Promotion promotion = null;
			try {
				
				promotion = WebClient.create("http://localhost:8080/")
						.path("bos_management_web/services/promotionservice/promotion/findPromotionById/"+model.getId())
						.type(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.get(Promotion.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//4.1通过freemarker根据模板生成静态界面，保存到缓存目录，
			Configuration configuration = new Configuration(Configuration.VERSION_2_3_22);
			configuration.setDirectoryForTemplateLoading(new File("src/main/webapp/WEB-INF/template"));
			// 获取模板对象
			Template template = configuration.getTemplate("promotion_detail.ftl");
			
			//使用Map集合创建动态数据对象
			Map<String, Object> parameterMap = new HashMap<String,Object>();
			parameterMap.put("promotion", promotion);
			
			//合并输出，在控制台显示
			template.process(parameterMap,  new OutputStreamWriter(new FileOutputStream(htmlFile), "utf-8"));
		}
		//5.已存在，直接将该文件返回到界面
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		FileUtils.copyFile(htmlFile, ServletActionContext.getResponse().getOutputStream());
		return NONE;
	}
}
