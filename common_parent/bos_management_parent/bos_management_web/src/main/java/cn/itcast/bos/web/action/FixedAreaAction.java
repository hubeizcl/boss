package cn.itcast.bos.web.action;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
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

import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.service.IFixedAreaService;
import cn.itcast.crm.domain.Customer;

@ParentPackage("struts-default")
@Namespace("/")
@Actions
@Controller
@Scope("prototype")
public class FixedAreaAction extends BaseAction<FixedArea> {
	
	@Resource
	private IFixedAreaService fixedAreaService;
	
	@Action(value="fixedAreaAction_save",
			results={
					@Result(name="success",location="/pages/base/fixed_area.jsp")
			})
	public String add(){
		fixedAreaService.add(model);
		return "success";
	}
	
	/**
	 * 分页查询方法
	 * @return
	 * @throws IOException 
	 */
	@Action(value="fixedAreaAction_pageQuery")
	public String pageQuery() throws IOException{
		//1.封装分页查询参数
		//参数1：当前页-1
		//参数2：每页条数
		Pageable pageable = new PageRequest(page - 1, rows);
		Specification<FixedArea> spec = new Specification<FixedArea>(){

			/**
			 * root：保存当前查询的实体类和表的映射关系
			 * query：可以将多个查询条件组装成一个查询数据
			 * cb：可以组装一个查询条件，比如id like ?
			 */
			@Override
			public Predicate toPredicate(Root<FixedArea> root, 
					CriteriaQuery<?> query, CriteriaBuilder cb) {
                return null;
			}
		};
		//2.调用service执行分页查询：总条数和每页要显示的数据集合
		Page<FixedArea> page = fixedAreaService.pageQuery(spec, pageable);
		//3.将查询的结果转换成json对象
		/*{                                                      
			"total":100,	
			"rows":[ 
				{"id":"001","courierNum":"09876","name":"李大","telephone":"13912345678","checkPwd":"123456","pda":"123456","standard":{"name":"10-20公斤"},"type":"小件员","vehicleType":"卡车","vehicleNum":"98765","company":"杭州分部","deltag":"0"},
				{"id":"002","courierNum":"09876","name":"李大","telephone":"13912345678","checkPwd":"123456","pda":"123456","standard":{"name":"10-20公斤"},"type":"小件员","vehicleType":"卡车","vehicleNum":"98765","company":"杭州分部","deltag":"0"}
			]
		}*/
		String[] excludes = {"subareas", "couriers"};
		this.writePage2JsonObject(page, excludes);
		return NONE;
	}
	
	/**
	 * 查询所有的未关联定区的客户信息，转换成json数组，返回到界面
	 * @return
	 * @throws IOException 
	 */
	@Action(value="fixedareaAction_findNoGuanLianCustomers")
	public String findNoGuanLianCustomers() throws IOException{
		//1.查询所有的未关联定区的客户信息
		List<Customer> list = (List<Customer>) WebClient.create("http://localhost:8080/")
				 .path("crm_management/services/crmservice/customer/findNoGuanLianCustomers")
				 .type(MediaType.APPLICATION_JSON)
				 .accept(MediaType.APPLICATION_JSON)
				 .getCollection(Customer.class);
		String[] excludes = {};
		//2.转换成json数组，返回到界面
		this.writeJava2JsonArray(list, excludes);
		return NONE;
	}
	
	/**
	 * 查询所有的关联选中定区的客户信息，转换成json数组，返回到界面
	 * @return
	 * @throws IOException 
	 */
	@Action(value="fixedareaAction_findGuanLianCustomers")
	public String findGuanLianCustomers() throws IOException{
		//1.查询所有的未关联定区的客户信息
		List<Customer> list = (List<Customer>) WebClient.create("http://localhost:8080/")
				 .path("crm_management/services/crmservice/customer/findGuanLianCustomers/"+model.getId())
				 .type(MediaType.APPLICATION_JSON)
				 .accept(MediaType.APPLICATION_JSON)
				 .getCollection(Customer.class);
		String[] excludes = {};
		//2.转换成json数组，返回到界面
		this.writeJava2JsonArray(list, excludes);
		return NONE;
	}

	private Integer[] customerIds;
	
	public void setCustomerIds(Integer[] customerIds) {
		this.customerIds = customerIds;
	}
	
	/**
	 * 将客户绑定到选中的定区上，返回成功结果集
	 * @return
	 */
	@Action(value="fixedAreaAction_assignCustomers2FixedArea",
			results={
					@Result(name="success",location="/pages/base/fixed_area.jsp")
			})
	public String assignCustomers2FixedArea(){
		//1.将客户id拼接成以,隔开的字符串
		String customerIdsStr = StringUtils.join(customerIds, ",");
		WebClient.create("http://localhost:8080/")
				 .path("crm_management/services/crmservice/customer/assignCustomers2FixedArea/"+customerIdsStr+"/"+model.getId())
				 .type(MediaType.APPLICATION_JSON)
				 .accept(MediaType.APPLICATION_JSON)
				 .put(null);
		return "success";
	}

	private Integer courierId;
	
	private Integer takeTimeId;
	
	public void setCourierId(Integer courierId) {
		this.courierId = courierId;
	}
	
	public void setTakeTimeId(Integer takeTimeId) {
		this.takeTimeId = takeTimeId;
	}
	
	@Action(value="fixedAreaAction_associationCourierToFixedArea",
			results={
					@Result(name="success", location="/pages/base/fixed_area.jsp")
			})
	public String associationCourierToFixedArea(){
		fixedAreaService.associationCourierToFixedArea(model.getId(), courierId, takeTimeId);
		return "success";
	}
}
