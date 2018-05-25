package cn.itcast.bos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.Promotion;

public interface IPromotionService{

	void save(Promotion model);

	Page<Promotion> pageQuery(Specification<Promotion> spec, Pageable pageable);

}
