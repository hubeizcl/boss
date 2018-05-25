package cn.itcast.bos.service;

import cn.itcast.bos.domain.base.InOutStorageInfo;

public interface IInOutStorageInfoService{

	void save(InOutStorageInfo model, Integer transitInfoId);

}
