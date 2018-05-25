package cn.itcast.bos.service;

import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.WayBill;

public interface IWayBillService{

	void add(WayBill model);

	Page<WayBill> pageQuery(Specification<WayBill> spec, Pageable pageable);

	Page<WayBill>  pageQuery(QueryBuilder builer, Pageable pageable);

}
