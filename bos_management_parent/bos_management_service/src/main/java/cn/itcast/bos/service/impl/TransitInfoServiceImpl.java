package cn.itcast.bos.service.impl;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.TransitInfoRepository;
import cn.itcast.bos.dao.WayBillRepository;
import cn.itcast.bos.domain.base.TransitInfo;
import cn.itcast.bos.domain.base.WayBill;
import cn.itcast.bos.index.WayBillIndexRepository;
import cn.itcast.bos.service.ITransitInfoService;

@Service
@Transactional
public class TransitInfoServiceImpl implements ITransitInfoService {

	@Resource
	private TransitInfoRepository transitInfoRepository;
	@Resource
	private WayBillRepository wayBillRepository;
	@Resource
	private WayBillIndexRepository wayBillIndexRepository;

	@Override
	public void startTransit(String waybillIds) {
		//1.根据waybillid查询运单对象
		String[] waybillArr = waybillIds.split(",");
		for(String waybillId : waybillArr){
			WayBill wayBill = wayBillRepository.findOne(Integer.valueOf(waybillId));
			//2.判断当前运单是否是1-待发货，
			Integer signStatus = wayBill.getSignStatus();
			if(null != signStatus && 1 == signStatus){
				//3.是待发货，
				//3.1更新运单状态2-派送中
				wayBill.setSignStatus(2);
				//3.2更新ES索引服务器
				wayBillIndexRepository.save(wayBill);
				//3.3创建并保存运输配送对象信息
				TransitInfo transit = new TransitInfo();
				transit.setStatus("出入库中转");//运输配送状态，未到达最终网点之前，都是出入库中转
				transit.setWayBill(wayBill);//关联运输配送信息和运单信息
				transitInfoRepository.save(transit);
			}
		}
	}

	@Override
	public Page<TransitInfo> pageQuery(Specification<TransitInfo> spec, Pageable pageable) {
		return transitInfoRepository.findAll(spec, pageable);
	}

}
