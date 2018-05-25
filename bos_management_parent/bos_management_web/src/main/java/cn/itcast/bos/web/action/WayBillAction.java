package cn.itcast.bos.web.action;

import java.io.IOException;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.base.WayBill;
import cn.itcast.bos.service.IWayBillService;

@ParentPackage("struts-default")
@Namespace("/")
@Actions
@Controller
@Scope("prototype")
public class WayBillAction extends BaseAction<WayBill> {
	
	@Resource
	private IWayBillService wayBillService;
	
	@Action(value="waybillAction_add")
	public String add() throws IOException{
		String flag = "1";
		try {
			wayBillService.add(model);
		} catch (Exception e) {
			flag = "0";
			e.printStackTrace();
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(flag);
		return NONE;
	}
	
	/**
	 * 分页查询方法
	 * @return
	 * @throws IOException 
	 */
	@Action(value="waybillAction_pageQuery")
	public String pageQuery() throws IOException{
		//1.封装分页查询参数
		//参数1：当前页-1
		//参数2：每页条数
		Pageable pageable = new PageRequest(page - 1, rows,Direction.ASC, "id");
		//2.调用service执行分页查询：总条数和每页要显示的数据集合
		//2.1判断是否输入了查询条件
		String wayBillNum = model.getWayBillNum();
		String sendAddress = model.getSendAddress();
		String recAddress = model.getRecAddress();
		String sendProNum = model.getSendProNum();
		Integer signStatus = model.getSignStatus();
		
		Page<WayBill> page = null;
		if(StringUtils.isBlank(wayBillNum)
				&& StringUtils.isBlank(sendAddress)
				&& StringUtils.isBlank(recAddress)
				&& StringUtils.isBlank(sendProNum)
				&& null == signStatus){
			//2.2未输入查询条件，使用springdatajpa查询分页数据
			Specification<WayBill> spec = new Specification<WayBill>(){
				
				/**
				 * root：保存当前查询的实体类和表的映射关系
				 * query：可以将多个查询条件组装成一个查询数据
				 * cb：可以组装一个查询条件，比如id like ?
				 */
				@Override
				public Predicate toPredicate(Root<WayBill> root, 
						CriteriaQuery<?> query, CriteriaBuilder cb) {
					return null;
				}
			};
			
			page = wayBillService.pageQuery(spec, pageable);
		} else {
			//2.3输入查询条件，使用springdataes查询分页数据
			BoolQueryBuilder boolBuiler = new BoolQueryBuilder();
			if(StringUtils.isNotBlank(wayBillNum)){
				//1.创建词条查询对象
				TermQueryBuilder termBuilder = new TermQueryBuilder("wayBillNum", wayBillNum);
				//2.使用词条对象组装查询条件
				boolBuiler.must(termBuilder);
			}
			if(StringUtils.isNotBlank(sendAddress)){
				//1.创建词条查询对象
				WildcardQueryBuilder wildcardBuilder = new WildcardQueryBuilder("sendAddress", "*"+sendAddress+"*");
				//创建搜索内容查询对象
				QueryStringQueryBuilder queryStringBuilder = new QueryStringQueryBuilder(sendAddress);
				//创建bool查询对象：用来封装模糊查询和内容搜索查询：should
				BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
				boolQueryBuilder.should(wildcardBuilder).should(queryStringBuilder);
				//2.使用词条对象组装查询条件
				boolBuiler.must(boolQueryBuilder);
			}
			if(StringUtils.isNotBlank(recAddress)){
				//1.创建词条查询对象
				WildcardQueryBuilder wildcardBuilder = new WildcardQueryBuilder("recAddress", "*"+recAddress+"*");
				//2.使用词条对象组装查询条件
				boolBuiler.must(wildcardBuilder);
			}
			if(StringUtils.isNotBlank(sendProNum)){
				//1.创建词条查询对象
				TermQueryBuilder termBuilder = new TermQueryBuilder("sendProNum", sendProNum);
				//2.使用词条对象组装查询条件
				boolBuiler.must(termBuilder);
			}
			if(null != signStatus && 0 != signStatus){
				//1.创建词条查询对象
				TermQueryBuilder termBuilder = new TermQueryBuilder("signStatus", signStatus);
				//2.使用词条对象组装查询条件
				boolBuiler.must(termBuilder);
			}
			//2.4调用service使用springdataes实现分页查询
//			page = wayBillService.pageQuery(spec, pageable);
		}
		//3.将查询的结果转换成json对象
		/*{                                                      
			"total":100,	
			"rows":[ 
				{"id":"001","courierNum":"09876","name":"李大","telephone":"13912345678","checkPwd":"123456","pda":"123456","standard":{"name":"10-20公斤"},"type":"小件员","vehicleType":"卡车","vehicleNum":"98765","company":"杭州分部","deltag":"0"},
				{"id":"002","courierNum":"09876","name":"李大","telephone":"13912345678","checkPwd":"123456","pda":"123456","standard":{"name":"10-20公斤"},"type":"小件员","vehicleType":"卡车","vehicleNum":"98765","company":"杭州分部","deltag":"0"}
			]
		}*/
		String[] excludes = {"order","sendArea","recArea"};
		this.writePage2JsonObject(page, excludes);
		return NONE;
	}
}
