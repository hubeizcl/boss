package cn.itcast.bos.web.action;

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

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.ICourierService;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@ParentPackage("struts-default")
@Namespace("/")
@Actions
@Controller
@Scope("prototype")
public class CourierAction extends BaseAction<Courier> {

    @Resource
    private ICourierService courierService;

    /**
     * 保存方法
     *
     * @return
     */
    @Action(value = "courierAction_add",
            results = {
                    @Result(name = "success", location = "/pages/base/courier.jsp"),
                    @Result(name = "failure", location = "/unauthorizedUrl.jsp")
            })
    public String add() {
        try {
            courierService.add(model);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "failure";
        }
    }

    private int page;//当前页

    private int rows;//每页条数

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setPage(int page) {
        this.page = page;
    }

    /**
     * 分页查询方法
     *
     * @return
     * @throws IOException
     */
    @Action(value = "courierAction_pageQuery")
    public String pageQuery() throws IOException {
        //1.封装分页查询参数
        //参数1：当前页-1
        //参数2：每页条数
        Pageable pageable = new PageRequest(page - 1, rows);
        Specification<Courier> spec = new Specification<Courier>() {

            /**
             * root：保存当前查询的实体类和表的映射关系
             * query：可以将多个查询条件组装成一个查询数据
             * cb：可以组装一个查询条件，比如id like ?
             */
            @Override
            public Predicate toPredicate(Root<Courier> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                //1.从model中获取查询条件
                String courierNum = model.getCourierNum();
                //2.判断是否输入
                if (StringUtils.isNotBlank(courierNum)) {
                    //录入了查询条件
                    list.add(cb.equal(root.get("courierNum").as(String.class), courierNum));
                }
                //3.从model中获取查询条件
                String company = model.getCompany();
                //4.判断是否输入
                if (StringUtils.isNotBlank(company)) {
                    //录入了查询条件
                    list.add(cb.equal(root.get("company").as(String.class), company));
                }
                //5.从model中获取查询条件
                String type = model.getType();
                //6.判断是否输入
                if (StringUtils.isNotBlank(type)) {
                    //录入了查询条件
                    list.add(cb.equal(root.get("type").as(String.class), type));
                }
                //7.获取查询条件
                Standard standard = model.getStandard();
                if (null != standard) {
                    String name = standard.getName();
                    if (StringUtils.isNotBlank(name)) {
                        //录入了查询条件
                        Join<Courier, Standard> userJoin = root.join(root.getModel().getSingularAttribute("standard", Standard.class));
                        list.add(cb.like(userJoin.get("name").as(String.class), "%" + name + "%"));
                    }
                }
                //8.使用query返回完整的查询条件
                Predicate[] pre = new Predicate[list.size()];
                return query.where(list.toArray(pre)).getRestriction();
            }
        };
        //2.调用service执行分页查询：总条数和每页要显示的数据集合
        Page<Courier> page = courierService.pageQuery(spec, pageable);
        //3.将查询的结果转换成json对象
		/*{                                                      
			"total":100,	
			"rows":[ 
				{"id":"001","courierNum":"09876","name":"李大","telephone":"13912345678","checkPwd":"123456","pda":"123456","standard":{"name":"10-20公斤"},"type":"小件员","vehicleType":"卡车","vehicleNum":"98765","company":"杭州分部","deltag":"0"},
				{"id":"002","courierNum":"09876","name":"李大","telephone":"13912345678","checkPwd":"123456","pda":"123456","standard":{"name":"10-20公斤"},"type":"小件员","vehicleType":"卡车","vehicleNum":"98765","company":"杭州分部","deltag":"0"}
			]
		}*/
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", page.getTotalElements());//总条数
        map.put("rows", page.getContent());//每页要显示的数据集合
        //json-lib：将java格式的数据转换成json数据
        //JSONObject：将java格式的数据转换成json对象
        //JSONArray：将java格式的数据转换成json数组
        //JsonConfig：将转换结果中不需要的属性排除
        JsonConfig jsonConfig = new JsonConfig();
        String[] excludes = {"fixedAreas"};
        jsonConfig.setExcludes(excludes);
        JSONObject jsonObject = JSONObject.fromObject(map, jsonConfig);
        String json = jsonObject.toString();
        //4.将json对象通过response返回到界面
        ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");//设置返回的数据格式
        ServletActionContext.getResponse().getWriter().print(json);//返回json数据
        return NONE;
    }

    private String ids;

    public void setIds(String ids) {
        this.ids = ids;
    }

    @Action(value = "courierAction_delete",
            results = {
                    @Result(name = "success", location = "/pages/base/courier.jsp"),
                    @Result(name = "failure", location = "/unauthorizedUrl.jsp")
            })
    public String delete() {
        try {
            courierService.delete(ids);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "failure";
        }
    }

    /**
     * 查询所有正常使用（deltag = ‘0’）的快递员，转换成json数组，返回到界面
     *
     * @return
     * @throws IOException
     */
    @Action(value = "courierAction_listajax")
    public String listajax() throws IOException {
        List<Courier> list = courierService.findNoDel();
        String[] excludes = {"standard", "takeTime", "fixedAreas"};
        this.writeJava2JsonArray(list, excludes);
        return NONE;
    }
}
