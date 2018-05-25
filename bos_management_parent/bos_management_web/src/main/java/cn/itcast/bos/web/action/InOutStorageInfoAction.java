package cn.itcast.bos.web.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.base.InOutStorageInfo;
import cn.itcast.bos.service.IInOutStorageInfoService;

@ParentPackage("struts-default")
@Namespace("/")
@Actions
@Controller
@Scope("prototype")
public class InOutStorageInfoAction extends BaseAction<InOutStorageInfo> {
	
	@Resource
	private IInOutStorageInfoService inOutStorageInfoService;
	
	private Integer transitInfoId;

	public void setTransitInfoId(Integer transitInfoId) {
		this.transitInfoId = transitInfoId;
	}
	
	/**
	 * 出入库方法
	 * @return
	 */
	@Action(value="inoutstoreAction_save",
			results={
					@Result(name="success", location="/pages/transit/transitinfo.jsp")
			})
	public String save(){
		inOutStorageInfoService.save(model, transitInfoId);
		return "success";
	}
}
