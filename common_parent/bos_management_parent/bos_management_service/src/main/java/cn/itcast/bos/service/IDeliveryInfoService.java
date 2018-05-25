package cn.itcast.bos.service;

import cn.itcast.bos.domain.base.DeliveryInfo;

public interface IDeliveryInfoService{

	void save(DeliveryInfo model, Integer transitInfoId);

}
