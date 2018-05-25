package cn.itcast.bos.web.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.ServletOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.service.ISubAreaService;
import cn.itcast.bos.utils.FileUtils;

@ParentPackage("struts-default")
@Namespace("/")
@Actions
@Controller
@Scope("prototype")
public class SubAreaAction extends BaseAction<SubArea> {
	
	@Resource
	private ISubAreaService subAreaService;
	
	/**
	 * 保存方法
	 * @return
	 */
	@Action(value="subareaAction_save",
			results={
					@Result(name="success", location="/pages/base/sub_area.jsp")
			})
	public String add(){
		subAreaService.add(model);
		return "success";
	}
	
	/**
	 * 分页查询方法
	 * @return
	 * @throws IOException 
	 */
	@Action(value="subareaAction_pageQuery")
	public String pageQuery() throws IOException{
		//1.封装分页查询参数
		//参数1：当前页-1
		//参数2：每页条数
		Pageable pageable = new PageRequest(page - 1, rows);
		Specification<SubArea> spec = new Specification<SubArea>(){

			/**
			 * root：保存当前查询的实体类和表的映射关系
			 * query：可以将多个查询条件组装成一个查询数据
			 * cb：可以组装一个查询条件，比如id like ?
			 */
			@Override
			public Predicate toPredicate(Root<SubArea> root, 
					CriteriaQuery<?> query, CriteriaBuilder cb) {
                return null;
			}
		};
		//2.调用service执行分页查询：总条数和每页要显示的数据集合
		Page<SubArea> page = subAreaService.pageQuery(spec, pageable);
		//3.将查询的结果转换成json对象
		/*{                                                      
			"total":100,	
			"rows":[ 
				{"id":"001","courierNum":"09876","name":"李大","telephone":"13912345678","checkPwd":"123456","pda":"123456","standard":{"name":"10-20公斤"},"type":"小件员","vehicleType":"卡车","vehicleNum":"98765","company":"杭州分部","deltag":"0"},
				{"id":"002","courierNum":"09876","name":"李大","telephone":"13912345678","checkPwd":"123456","pda":"123456","standard":{"name":"10-20公斤"},"type":"小件员","vehicleType":"卡车","vehicleNum":"98765","company":"杭州分部","deltag":"0"}
			]
		}*/
		String[] excludes = {"subareas", "fixedArea"};
		this.writePage2JsonObject(page, excludes);
		return NONE;
	}
	
	/**
	 * 导出所有分区的excel文件方法
	 * @return
	 * @throws IOException 
	 */
	@Action(value="subareaAction_exportXls")
	public String exportXls() throws IOException{
		//1.查询所有的分区数据
		List<SubArea> list = subAreaService.findAll();
		//2.创建excel
		//2.1创建空excel
		HSSFWorkbook wb = new HSSFWorkbook();
		//2.2在excel中创建sheet页
		HSSFSheet sheet = wb.createSheet();
		//2.3在sheet页中创建第一行标题row
		HSSFRow row = sheet.createRow(0);
		//2.4在row中创建标题单元格cell：分区编号、关键字、辅助关键字、位置信息、区域编号
		row.createCell(0).setCellValue("分区编号");
		row.createCell(1).setCellValue("关键字");
		row.createCell(2).setCellValue("辅助关键字");
		row.createCell(3).setCellValue("位置信息");
		row.createCell(4).setCellValue("区域编号");
		//3.判断分区是否为空
		if(null != list && list.size() > 0){
			//4.不为空，将数据写入excel
			int index = 1;
			for(SubArea subarea : list){
				//4.1创建新行row
				row = sheet.createRow(index ++);
				//4.2在新行row中创建新cell，赋值
				row.createCell(0).setCellValue(subarea.getId());
				row.createCell(1).setCellValue(subarea.getKeyWords());
				row.createCell(2).setCellValue(subarea.getAssistKeyWords());
				row.createCell(3).setCellValue(subarea.getAddress());
				Area area = subarea.getArea();
				if(null != area){
					//分区关联了区域
					row.createCell(4).setCellValue(area.getId());
				} else {
					//分区未关联区域
					row.createCell(4).setCellValue("未指定区域");
				}
			}
		}
		//5.将excel返回到界面
		//5.1设置文件名
		String filename = "分区数据.xls";
		//通过User-Agent请求头获取浏览器类型
		String agent = ServletActionContext.getRequest().getHeader("User-Agent");
		//使用工具类编码文件名
		String mimeType = ServletActionContext.getServletContext().getMimeType(filename);
		filename = FileUtils.encodeDownloadFilename(filename, agent);
		
		//5.2设置响应参数：一个流两个头
		//5.2.1一个流：response的输出流
		ServletOutputStream os = ServletActionContext.getResponse().getOutputStream();
		//5.2.2两个头之一：content-type，告诉前台返回数据的格式，
		ServletActionContext.getResponse().setContentType(mimeType);
		//5.2.3两个头之二：content-disposition，告诉前台数据的打开方式，以文件下载的方式打开参数：attachment;filename=文件名
		ServletActionContext.getResponse().setHeader("content-disposition", "attachment;filename="+filename);
		//5.3通过response将excel文件返回到前台
		wb.write(os);
		return NONE;
	}
	
	/**
	 * 查询所有分区分布（省份、分区个数）数据，转换成json数组，返回到界面
	 * @return
	 * @throws IOException 
	 */
	@Action(value="subareaAction_findGroupedSubareas")
	public String findGroupedSubareas() throws IOException{
		//1.查询所有分区分布（省份、分区个数）数据
		List<Object> list = subAreaService.findGroupedSubareas();
		String[] excludes = {};
		//2.转换成json数组，返回到界面
		this.writeJava2JsonArray(list, excludes);
		return NONE;
	}
}
