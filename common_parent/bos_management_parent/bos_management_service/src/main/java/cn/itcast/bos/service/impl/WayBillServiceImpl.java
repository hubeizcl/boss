package cn.itcast.bos.service.impl;

import javax.annotation.Resource;

import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.WayBillRepository;
import cn.itcast.bos.domain.base.WayBill;
import cn.itcast.bos.index.WayBillIndexRepository;
import cn.itcast.bos.service.IWayBillService;

@Service
@Transactional
public class WayBillServiceImpl implements IWayBillService {

	@Resource
	private WayBillRepository wayBillRepository;
	@Resource
	private WayBillIndexRepository wayBillIndexRepository;

	@Override
	public void add(WayBill model) {
		wayBillRepository.save(model);//将数据保存到oracle数据库
		wayBillIndexRepository.save(model);//将同步到es中
	}

	@Override
	public Page<WayBill> pageQuery(Specification<WayBill> spec, Pageable pageable) {
		return wayBillRepository.findAll(spec, pageable);
	}

	@Override
	public Page<WayBill> pageQuery(QueryBuilder builer, Pageable pageable) {
		return wayBillIndexRepository.search(builer, pageable);
	}

}
