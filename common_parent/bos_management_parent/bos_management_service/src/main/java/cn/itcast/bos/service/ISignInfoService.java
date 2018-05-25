package cn.itcast.bos.service;

import cn.itcast.bos.domain.base.SignInfo;

public interface ISignInfoService{

	void save(SignInfo model, Integer transitInfoId);

}
