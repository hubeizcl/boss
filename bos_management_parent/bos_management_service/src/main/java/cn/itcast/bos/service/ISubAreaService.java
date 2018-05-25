package cn.itcast.bos.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.SubArea;

public interface ISubAreaService{

	void add(SubArea model);

	Page<SubArea> pageQuery(Specification<SubArea> spec, Pageable pageable);

	List<SubArea> findAll();

	List<Object> findGroupedSubareas();

}
