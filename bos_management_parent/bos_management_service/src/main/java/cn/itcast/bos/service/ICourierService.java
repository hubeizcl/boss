package cn.itcast.bos.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.Courier;

public interface ICourierService {

	void add(Courier model);

	Page<Courier> pageQuery(Specification<Courier> spec,Pageable pageable);

	void delete(String ids);

	List<Courier> findNoDel();

}
