package cn.itcast.bos.service.impl;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.PromotionRepository;
import cn.itcast.bos.domain.base.Promotion;
import cn.itcast.bos.service.IPromotionService;

@Service
@Transactional
public class PromotionServiceImpl implements IPromotionService {

	@Resource
	private PromotionRepository promotionRepository;

	@Override
	public void save(Promotion model) {
		promotionRepository.save(model);
	}

	@Override
	public Page<Promotion> pageQuery(Specification<Promotion> spec, Pageable pageable) {
		return promotionRepository.findAll(spec, pageable);
	}

}
