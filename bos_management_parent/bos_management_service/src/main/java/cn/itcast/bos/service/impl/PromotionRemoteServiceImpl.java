package cn.itcast.bos.service.impl;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.PromotionRepository;
import cn.itcast.bos.domain.base.Promotion;
import cn.itcast.bos.service.IPromotionRemoteService;
import cn.itcast.bos.utils.PageBean;

@Transactional
public class PromotionRemoteServiceImpl implements IPromotionRemoteService {

	@Resource
	private PromotionRepository promotionRepository;
	
	@Override
	public PageBean<Promotion> pageQuery(Integer page, Integer pageSize) {
		Pageable pageable = new PageRequest(page - 1, pageSize);
		Page<Promotion> pagePro = promotionRepository.findAll(pageable);
		
		//将查询结果放到PageBean中
		PageBean<Promotion> pageBean = new PageBean<Promotion>();
		pageBean.setTotalCount(pagePro.getTotalElements());//总条数
		pageBean.setPageData(pagePro.getContent());//当前页数据集合
		return pageBean;
	}

	@Override
	public Promotion findPromotionById(Integer id) {
		try {
			Promotion promotion = promotionRepository.findOne(id);
			return promotion;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
