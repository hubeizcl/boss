package cn.itcast.bos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.TransitInfo;

public interface ITransitInfoService{

	void startTransit(String waybillIds);

	Page<TransitInfo> pageQuery(Specification<TransitInfo> spec, Pageable pageable);

}
