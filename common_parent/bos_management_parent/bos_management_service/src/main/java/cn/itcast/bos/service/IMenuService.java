package cn.itcast.bos.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.Menu;
import cn.itcast.bos.domain.base.User;

public interface IMenuService{

	Page<Menu> pageQuery(Specification<Menu> spec, Pageable pageable);

	List<Menu> findParentMenuIsNull();

	void add(Menu model);

	List<Menu> findAll();

	List<Menu> findMenuByUser(User user);

}
