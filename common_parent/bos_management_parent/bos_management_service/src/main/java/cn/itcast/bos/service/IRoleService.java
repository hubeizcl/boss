package cn.itcast.bos.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.Role;
import cn.itcast.bos.domain.base.User;

public interface IRoleService{

	Page<Role> pageQuery(Specification<Role> spec, Pageable pageable);

	void add(Role model, Integer[] permissionIds, String menuIds);

	List<Role> findAll();

	List<Role> findRolesByUser(User user);

}
