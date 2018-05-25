package cn.itcast.bos.service.impl;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.AreaRepository;
import cn.itcast.bos.dao.FixedAreaRepository;
import cn.itcast.bos.dao.OrderRepository;
import cn.itcast.bos.dao.WorkBillRepository;
import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.domain.base.Order;
import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.domain.base.WorkBill;
import cn.itcast.bos.service.IOrderRemoteService;
import cn.itcast.bos.utils.SmsUtils;

@Transactional
public class OrderRemoteServiceImpl implements IOrderRemoteService {

	@Resource
	private OrderRepository orderRepository;
	@Resource
	private FixedAreaRepository fixedAreaRepository;
	@Resource
	private WorkBillRepository workBillRepository;
	@Resource
	private AreaRepository areaRepository;
	
	@Override
	public void saveOrder(Order order) {
		//1.设置订单号
		order.setOrderNum(UUID.randomUUID().toString());
		//2.设置订单时间
		order.setOrderTime(new Date());
		Area sendArea = order.getSendArea();//自动分单时使用，所以先放到临时变量,发件人区域
		Area recArea = order.getRecArea();
		
		order.setSendArea(null);
		order.setRecArea(null);
		//3.保存订单
		orderRepository.save(order);
		
		//4.根据客户详细地址自动分单
		//4.1获取客户详细地址
		String sendAddress = order.getSendAddress();
		//4.2根据客户详细地址查询客户所属定区
		String fixedAreaId = WebClient.create("http://localhost:8080/")
				 .path("crm_management/services/crmservice/customer/findFixedAreaIdByAddress/"+sendAddress)
				 .type(MediaType.APPLICATION_XML)
				 .accept(MediaType.APPLICATION_XML)
				 .get(String.class);
		if(StringUtils.isNotBlank(fixedAreaId)){
			//4.3如果定区存在，
			//4.4根据定区查询定区对象
			FixedArea fixedArea = fixedAreaRepository.findOne(fixedAreaId);
			//4.5根据定区对象获取关联的快递员集合
			Set<Courier> couriers = fixedArea.getCouriers();
			if(null != couriers && couriers.size() > 0){
				for(Courier courier : couriers){
					if(null != courier){
						//4.6设置订单类型
						order.setOrderType("自动分单");//分单类型：自动分单、手工分单
						//4.7绑定订单和快递员
						order.setCourier(courier);
						//4.8给快递员生成工单
						WorkBill workbill = new WorkBill();
						workbill.setAttachbilltimes(0);//追单次数，新单是0
						workbill.setBuildtime(new Date());//工单生成时间
						workbill.setCourier(courier);//关联工单和快递员
						workbill.setOrder(order);//关联订单和工单
						workbill.setPickstate("未取件");//取件状态：未取件、已取件、转运中...
						workbill.setRemark(order.getRemark());//和订单的备注一致
						workbill.setSmsNumber(RandomStringUtils.randomNumeric(4));//短信码
						workbill.setType("新单");//工单类型：新单、销单、追单
						workBillRepository.save(workbill);
						String msg = "尊敬的用户您好，本次获取的验证码为：" + workbill.toString() + ",服务电话：4006184000【传智播客】";;
						//4.9给快递员发短信/邮件
						SmsUtils.sendSmsByWebService(courier.getTelephone(), msg);
						return;
					}
				}
			}
			
		}
		
		//5.根据分区关键字匹配自动分单
		//5.1根据发件人的区域信息查询区域对象
		Area area = areaRepository.findByProvinceAndCityAndDistrict(
				sendArea.getProvince(), sendArea.getCity(), sendArea.getDistrict());
		//5.2根据区域对象获取区域关联的分区集合
		Set<SubArea> subareas = area.getSubareas();
		//5.3判断分区集合是否为null
		if(null != subareas && subareas.size() > 0){
			//5.4当分区集合不为null时，循环将分区的关键字和辅助关键字跟客户详细地址比对
			for(SubArea subarea : subareas){
				if(sendAddress.contains(subarea.getKeyWords())
						&& sendAddress.contains(subarea.getAssistKeyWords())){
					//5.5比对成功，说明客户属于该分区，根据该分区获取分区的定区
					FixedArea fixedArea = subarea.getFixedArea();
					//5.6根据定区获取快递员集合
					//5.7根据定区对象获取关联的快递员集合
					Set<Courier> couriers = fixedArea.getCouriers();
					if(null != couriers && couriers.size() > 0){
						for(Courier courier : couriers){
							if(null != courier){
								//4.6设置订单类型
								order.setOrderType("自动分单");//分单类型：自动分单、手工分单
								//4.7绑定订单和快递员
								order.setCourier(courier);
								//4.8给快递员生成工单
								WorkBill workbill = new WorkBill();
								workbill.setAttachbilltimes(0);//追单次数，新单是0
								workbill.setBuildtime(new Date());//工单生成时间
								workbill.setCourier(courier);//关联工单和快递员
								workbill.setOrder(order);//关联订单和工单
								workbill.setPickstate("未取件");//取件状态：未取件、已取件、转运中...
								workbill.setRemark(order.getRemark());//和订单的备注一致
								workbill.setSmsNumber(RandomStringUtils.randomNumeric(4));//短信码
								workbill.setType("新单");//工单类型：新单、销单、追单
								workBillRepository.save(workbill);
								String msg = "尊敬的用户您好，本次获取的验证码为：" + workbill.toString() + ",服务电话：4006184000【传智播客】";;
								//4.9给快递员发短信/邮件
								SmsUtils.sendSmsByWebService(courier.getTelephone(), msg);
								return;
							}
						}
					}
				}
			}
		}
		//6.手工分单
		//6.1设置订单类型
		order.setOrderType("手工分单");//分单类型：自动分单、手工分单
	}

}
