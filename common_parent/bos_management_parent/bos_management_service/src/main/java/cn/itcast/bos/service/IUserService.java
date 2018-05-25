package cn.itcast.bos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.User;

public interface IUserService{

	User findUserByUsername(String username);

	Page<User> pageQuery(Specification<User> spec, Pageable pageable);

	void add(User model, Integer[] roleIds);

}
