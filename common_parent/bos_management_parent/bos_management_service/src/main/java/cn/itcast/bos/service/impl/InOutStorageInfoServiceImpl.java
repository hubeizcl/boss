package cn.itcast.bos.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.InOutStorageInfoRepository;
import cn.itcast.bos.dao.TransitInfoRepository;
import cn.itcast.bos.domain.base.InOutStorageInfo;
import cn.itcast.bos.domain.base.TransitInfo;
import cn.itcast.bos.service.IInOutStorageInfoService;

@Service
@Transactional
public class InOutStorageInfoServiceImpl implements IInOutStorageInfoService {

	@Resource
	private InOutStorageInfoRepository inOutStorageInfoRepository;
	@Resource
	private TransitInfoRepository transitInfoRepository;

	@Override
	public void save(InOutStorageInfo model, Integer transitInfoId) {
		//1.保存出入库信息
		inOutStorageInfoRepository.save(model);
		//2.关联运输配送单和出入库信息
		TransitInfo transitInfo = transitInfoRepository.findOne(transitInfoId);
		transitInfo.getInOutStorageInfos().add(model);
		//3.判断当前的出入库信息操作是否是“到达网点”，
		if("到达网点".equals(model.getOperation())){
			//4.是到达网点，需要更新运输配送状态，将运输配送单状态，更新为“到达网点”，更新网点地址
			transitInfo.setStatus("到达网点");
			transitInfo.setOutletAddress(model.getAddress());
		}
	}

}
