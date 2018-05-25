package cn.itcast.bos.web.action;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.domain.base.Order;
import cn.itcast.crm.domain.Customer;

@ParentPackage("struts-default")
@Namespace("/")
@Actions
@Controller
@Scope("prototype")
public class OrderAction extends BaseAction<Order> {

	private String sendAreaInfo;//发件人区域
	
	private String recAreaInfo;//收件人区域

	public void setSendAreaInfo(String sendAreaInfo) {
		this.sendAreaInfo = sendAreaInfo;
	}

	public void setRecAreaInfo(String recAreaInfo) {
		this.recAreaInfo = recAreaInfo;
	}
	
	/**
	 * 订单保存方法
	 * @return
	 */
	@Action(value="orderAction_saveOrder",
			results={
					@Result(name="success",location="/index.html#/myhome", type="redirect")
			})
	public String saveOrder(){
		//1.处理区域信息
		if(StringUtils.isNotBlank(sendAreaInfo)){
			String[] sendAreaArr = sendAreaInfo.split("/");//[省,市,区]
			Area sendArea = new Area();
			sendArea.setProvince(sendAreaArr[0]);
			sendArea.setCity(sendAreaArr[1]);
			sendArea.setDistrict(sendAreaArr[2]);
			
			model.setSendArea(sendArea);
		}
		
		if(StringUtils.isNotBlank(recAreaInfo)){
			String[] recAreaArr = recAreaInfo.split("/");//[省,市,区]
			Area recArea = new Area();
			recArea.setProvince(recAreaArr[0]);
			recArea.setCity(recAreaArr[1]);
			recArea.setDistrict(recAreaArr[2]);
			
			model.setRecArea(recArea);
		}
		//2.将客户和订单关联
		Customer customer = (Customer) ServletActionContext
				.getRequest().getSession().getAttribute("loginCust");
		if(null != customer){
			model.setCustomer_id(customer.getId());
		}
		//3.将订单保存
		WebClient.create("http://localhost:8080/")
				 .path("bos_management_web/services/orderservice/order/saveOrder")
				 .type(MediaType.APPLICATION_JSON)
				 .accept(MediaType.APPLICATION_JSON)
				 .post(model);
		return "success";
	}
}
