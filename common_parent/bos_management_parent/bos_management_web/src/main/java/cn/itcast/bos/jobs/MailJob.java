package cn.itcast.bos.jobs;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.WorkBill;
import cn.itcast.bos.service.IWorkBillService;
import cn.itcast.bos.utils.MailUtils;

/**
 * <p>Title: cn.itcast.bos.jobsMailJob.java</p>
 * <p>Description:定时任务的任务类</p>
 * <p>Company: www.itcast.com</p>
 * @author  传智.九纹龙
 * @date	2017年11月19日上午10:52:04
 * @version 1.0
 */
public class MailJob {

	@Resource
	private IWorkBillService workBillService;
	
	public void sendMail(){
		//1.查询所有状态是新单的工单信息
		List<WorkBill> list = workBillService.findNewWorkBill();
		if(null != list && list.size() > 0){
			//2.循环工单，获取该工单关联的快递员
			for(WorkBill workbill : list){
				Courier courier = workbill.getCourier();
				//3.给快递员发邮件
				MailUtils.sendMail("速运快递-新单通知", workbill.toString(), courier.getEmail());
			}
		}
	}
}
