package cn.itcast.bos.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.SignInfoRepository;
import cn.itcast.bos.dao.TransitInfoRepository;
import cn.itcast.bos.domain.base.SignInfo;
import cn.itcast.bos.domain.base.TransitInfo;
import cn.itcast.bos.index.WayBillIndexRepository;
import cn.itcast.bos.service.ISignInfoService;

@Service
@Transactional
public class SignInfoServiceImpl implements ISignInfoService {

	@Resource
	private SignInfoRepository signInfoRepository;
	@Resource
	private TransitInfoRepository transitinfoRepository;
	@Resource
	private WayBillIndexRepository wayBillIndexRepository;

	@Override
	public void save(SignInfo model, Integer transitInfoId) {
		//1.保存签收信息
		signInfoRepository.save(model);
		//2.关联运输配送单和签收单
		TransitInfo transitInfo = transitinfoRepository.findOne(transitInfoId);
		transitInfo.setSignInfo(model);
		//3.判断签收类型是否是正常
		if("正常".equals(model.getSignType())){
			//3.1是，将运输配送单状态更新成：正常签收
			//同时更新运单为：3-已签收
			transitInfo.setStatus("正常签收");
			transitInfo.getWayBill().setSignStatus(3);
		} else {
			//3.2否，将运输配送单状态更新成：异常
			//同时更新运单为：4-异常
			transitInfo.setStatus("异常");
			transitInfo.getWayBill().setSignStatus(4);
		}
		//4.更新索引库
		wayBillIndexRepository.save(transitInfo.getWayBill());
	}

}
