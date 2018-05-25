package cn.itcast.bos.web.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.base.DeliveryInfo;
import cn.itcast.bos.service.IDeliveryInfoService;

@ParentPackage("struts-default")
@Namespace("/")
@Actions
@Controller
@Scope("prototype")
public class DeliveryInfoAction extends BaseAction<DeliveryInfo> {
	
	@Resource
	private IDeliveryInfoService deliveryInfoService;
	
	private Integer transitInfoId;

	public void setTransitInfoId(Integer transitInfoId) {
		this.transitInfoId = transitInfoId;
	}
	
	/**
	 * 开始配送方法
	 * @return
	 */
	@Action(value="deliveryAction_save",
			results={
					@Result(name="success", location="/pages/transit/transitinfo.jsp")
			})
	public String save(){
		deliveryInfoService.save(model, transitInfoId);
		return "success";
	}
}
