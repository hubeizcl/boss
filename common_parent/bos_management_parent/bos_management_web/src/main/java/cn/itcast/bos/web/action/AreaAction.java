package cn.itcast.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.ServletOutputStream;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.IAreaService;
import cn.itcast.bos.utils.FileUtils;
import cn.itcast.bos.utils.PinYin4jUtils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@ParentPackage("struts-default")
@Namespace("/")
@Actions
@Controller
@Scope("prototype")
public class AreaAction extends BaseAction<Area> {

    @Resource
    private IAreaService areaService;

    private File areaFile;

    public void setAreaFile(File areaFile) {
        this.areaFile = areaFile;
    }

    /**
     * 导入方法
     *
     * @return
     * @throws IOException
     */
    @Action(value = "areaAction_importXls")
    public String importXls() throws IOException {
        String flag = "1";//标志位：1-操作成功；0-操作失败
        try {
            //1.通过workbook获取excel文件
            HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(areaFile));
            //2.通过workbook对象获取sheet页
            HSSFSheet sheet = wb.getSheetAt(0);
            //3.通过sheet页获取每一行row
            List<Area> list = new ArrayList<Area>();
            for (Row row : sheet) {
                //4.通过row获取每个单元格
                //4.1跳过第一行标题行
                int rowNum = row.getRowNum();//获取行号
                if (0 == rowNum) {
                    continue;//跳过本次循环，进入下一次循环
                }
                String id = row.getCell(0).getStringCellValue();
                String province = row.getCell(1).getStringCellValue();
                String city = row.getCell(2).getStringCellValue();
                String district = row.getCell(3).getStringCellValue();
                String postcode = row.getCell(4).getStringCellValue();
                //5.将数据封装到Area对象中
                Area area = new Area();
                area.setId(id);
                area.setProvince(province);
                area.setCity(city);
                area.setDistrict(district);
                area.setPostcode(postcode);

                //1.1字符串分页，去掉省市区
                province = province.substring(0, province.length() - 1);
                city = city.substring(0, city.length() - 1);
                district = district.substring(0, district.length() - 1);
                //1.2拼成字符串:河北石家庄开发
                String temp = province + city + district;
                //1.3获取汉字拼音首字母:[H,B,S,J,Z,K,F]
                String[] headByString = PinYin4jUtils.getHeadByString(temp);
                //1.4.拼成简码
                String shortcode = StringUtils.join(headByString, "");
                area.setShortcode(shortcode);

                //2.城市码
                String citycode = PinYin4jUtils.hanziToPinyin(city, "");
                area.setCitycode(citycode);

                list.add(area);
            }
            //6.将区域数据批量保存
            areaService.batchSave(list);
        } catch (IOException e) {
            flag = "0";
            e.printStackTrace();
        }
        //7.将标志位返回到界面
        ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
        ServletActionContext.getResponse().getWriter().print(flag);
        return NONE;
    }

    /**
     * 分页查询方法
     *
     * @return
     * @throws IOException
     */
    @Action(value = "areaAction_pageQuery")
    public String pageQuery() throws IOException {
        //1.封装分页查询参数
        //参数1：当前页-1
        //参数2：每页条数
        Pageable pageable = new PageRequest(page - 1, rows);
        Specification<Area> spec = new Specification<Area>() {

            /**
             * root：保存当前查询的实体类和表的映射关系
             * query：可以将多个查询条件组装成一个查询数据
             * cb：可以组装一个查询条件，比如id like ?
             */
            @Override
            public Predicate toPredicate(Root<Area> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                //1.从model中获取查询条件
                String province = model.getProvince();
                //2.判断是否输入
                if (StringUtils.isNotBlank(province)) {
                    //录入了查询条件
                    list.add(cb.like(root.get("province").as(String.class), "%" + province + "%"));
                }
                //1.从model中获取查询条件
                String city = model.getCity();
                //2.判断是否输入
                if (StringUtils.isNotBlank(city)) {
                    //录入了查询条件
                    list.add(cb.like(root.get("city").as(String.class), "%" + city + "%"));
                }
                //1.从model中获取查询条件
                String district = model.getDistrict();
                //2.判断是否输入
                if (StringUtils.isNotBlank(district)) {
                    //录入了查询条件
                    list.add(cb.like(root.get("district").as(String.class), "%" + district + "%"));
                }
                //8.使用query返回完整的查询条件
                Predicate[] pre = new Predicate[list.size()];
                return query.where(list.toArray(pre)).getRestriction();
            }
        };
        //2.调用service执行分页查询：总条数和每页要显示的数据集合
        Page<Area> page = areaService.pageQuery(spec, pageable);
        //3.将查询的结果转换成json对象
		/*{                                                      
			"total":100,	
			"rows":[ 
				{"id":"001","courierNum":"09876","name":"李大","telephone":"13912345678","checkPwd":"123456","pda":"123456","standard":{"name":"10-20公斤"},"type":"小件员","vehicleType":"卡车","vehicleNum":"98765","company":"杭州分部","deltag":"0"},
				{"id":"002","courierNum":"09876","name":"李大","telephone":"13912345678","checkPwd":"123456","pda":"123456","standard":{"name":"10-20公斤"},"type":"小件员","vehicleType":"卡车","vehicleNum":"98765","company":"杭州分部","deltag":"0"}
			]
		}*/
        String[] excludes = {"subareas"};
        this.writePage2JsonObject(page, excludes);
        return NONE;
    }

    /**
     * 查询所有的区域数据，转换成数组，返回到界面
     *
     * @return
     * @throws IOException
     */
    @Action(value = "areaAction_findAll")
    public String findAll() throws IOException {
        List<Area> list = areaService.findAll();
        String[] excludes = {"subareas"};
        this.writeJava2JsonArray(list, excludes);
        return NONE;
    }

    @Resource
    private DataSource dataSource;

    /**
     * 区域数据导出pdf方法
     *
     * @return
     * @throws IOException
     * @throws JRException
     */
    @Action(value = "areaAction_exportPDF")
    public String exportPDF() throws Exception {
        //1.查询所有的区域数据
        List<Area> list = areaService.findAll();
        //2.判断是否为null
        if (null != list && list.size() > 0) {
            //3.不为空，设置响应参数：文件名和一个流两个头
            String filename = "区域数据报表.pdf";
            String agent = ServletActionContext.getRequest().getHeader("User-Agent");
            String mimeType = ServletActionContext.getServletContext().getMimeType(filename);
            filename = FileUtils.encodeDownloadFilename(filename, agent);

            ServletOutputStream os = ServletActionContext.getResponse().getOutputStream();

            ServletActionContext.getResponse().setContentType(mimeType);

            ServletActionContext.getResponse().setHeader("content-disposition", "attachment;filename=" + filename);
            //4.根据模板生成pdf，返回到界面
            // 读取 jrxml 文件
            String jrxml = ServletActionContext.getServletContext().getRealPath("/WEB-INF/jasper/area_entity.jrxml");
            // 准备需要数据
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("company", "传智播客");
            // 准备需要数据
            JasperReport report = JasperCompileManager.compileReport(jrxml);
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, new JRBeanCollectionDataSource(list));

            // 设置相应参数，以附件形式保存PDF
            // 使用JRPdfExproter导出器导出pdf
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, os);
            exporter.exportReport();// 导出
            os.close();// 关闭流
        }
        return NONE;
    }
}
