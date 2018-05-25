package cn.itcast.bos.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.PermissionRepository;
import cn.itcast.bos.dao.UserRepository;
import cn.itcast.bos.domain.base.Permission;
import cn.itcast.bos.domain.base.Role;
import cn.itcast.bos.domain.base.User;
import cn.itcast.bos.service.IPermissionService;

@Service
@Transactional
public class PermissionServiceImpl implements IPermissionService {

	@Resource
	private PermissionRepository permissionRepository;
	@Resource
	private UserRepository userRepository;

	@Override
	public Page<Permission> pageQuery(Specification<Permission> spec, Pageable pageable) {
		return permissionRepository.findAll(spec, pageable);
	}

	@Override
	public void save(Permission model) {
		permissionRepository.save(model);
	}

	@Override
	public List<Permission> findAll() {
		return permissionRepository.findAll();
	}

	@Override
	public List<Permission> findPermsByUser(User user) {
		//1.判断用户是否是admin超级管理员
		if("admin".equals(user.getUsername())){
			//2.是，直接返回所有权限
			return permissionRepository.findAll();
		} else {
			//3.否，根据用户的id查询该用户的权限
			//4.根据用户id查询该用户的角色
			User user2 = userRepository.findOne(user.getId());
			Set<Role> roles = user2.getRoles();
			List<Permission> list = new ArrayList<Permission>();
			if(null != roles && roles.size() > 0){
				for(Role role : roles){
					//5.遍历角色，查询该角色权限
					Set<Permission> permissions = role.getPermissions();
					//6.将角色权限放到list中，返回
					list.addAll(permissions);
				}
			}
			return list;
		}
	}

}
