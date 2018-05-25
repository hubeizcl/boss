package cn.itcast.bos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.FixedArea;

public interface IFixedAreaService{

	void add(FixedArea model);

	Page<FixedArea> pageQuery(Specification<FixedArea> spec, Pageable pageable);

	void associationCourierToFixedArea(String id, Integer courierId, Integer takeTimeId);

}
