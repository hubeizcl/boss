package cn.itcast.bos.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.DeliveryInfoRepository;
import cn.itcast.bos.dao.TransitInfoRepository;
import cn.itcast.bos.domain.base.DeliveryInfo;
import cn.itcast.bos.domain.base.TransitInfo;
import cn.itcast.bos.service.IDeliveryInfoService;

@Service
@Transactional
public class DeliveryInfoServiceImpl implements IDeliveryInfoService {

	@Resource
	private DeliveryInfoRepository deliveryInfoRepository;
	@Resource
	private TransitInfoRepository transitInfoRepository;

	@Override
	public void save(DeliveryInfo model, Integer transitInfoId) {
		//1.保存配送信息
		deliveryInfoRepository.save(model);
		//2.关联运输配送单和配送单
		TransitInfo transitInfo = transitInfoRepository.findOne(transitInfoId);
		transitInfo.setDeliveryInfo(model);
		//3.更新运输配送单状态为：开始配送
		transitInfo.setStatus("开始配送");
	}

}
