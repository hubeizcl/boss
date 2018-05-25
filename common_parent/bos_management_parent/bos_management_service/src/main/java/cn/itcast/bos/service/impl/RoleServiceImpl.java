package cn.itcast.bos.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.MenuRepository;
import cn.itcast.bos.dao.PermissionRepository;
import cn.itcast.bos.dao.RoleRepository;
import cn.itcast.bos.dao.UserRepository;
import cn.itcast.bos.domain.base.Menu;
import cn.itcast.bos.domain.base.Permission;
import cn.itcast.bos.domain.base.Role;
import cn.itcast.bos.domain.base.User;
import cn.itcast.bos.service.IRoleService;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService {

	@Resource
	private RoleRepository roleRepository;
	@Resource
	private MenuRepository menuRepository;
	@Resource
	private PermissionRepository permissionRepository;
	@Resource
	private UserRepository userRepository;

	@Override
	public Page<Role> pageQuery(Specification<Role> spec, Pageable pageable) {
		return roleRepository.findAll(spec, pageable);
	}

	@Override
	public void add(Role model, Integer[] permissionIds, String menuIds) {
		//1.保存角色数据
		roleRepository.save(model);//model持久态
		//2.角色关联菜单
		if(StringUtils.isNotBlank(menuIds)){
			String[] menuIdArr = menuIds.split(",");
			for(String id : menuIdArr){
				//2.1查询菜单对象
				Menu menu = menuRepository.findOne(Integer.valueOf(id));
				//2.2放到角色中菜单的集合中
				model.getMenus().add(menu);
			}
		}
		//3.角色关联权限
		if(null != permissionIds && permissionIds.length > 0){
			for(Integer id : permissionIds){
				//2.1查询权限对象
				Permission perm = permissionRepository.findOne(id);
				//2.2放到角色中权限的集合中
				model.getPermissions().add(perm);
			}
		}
	}

	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	@Override
	public List<Role> findRolesByUser(User user) {
		// 1.判断用户是否是admin超级管理员
		if ("admin".equals(user.getUsername())) {
			// 2.是，直接返回所有角色
			return roleRepository.findAll();
		} else {
			// 3.否，根据用户的id查询该用户的角色
			//4.根据用户id查询该用户的角色
			User user2 = userRepository.findOne(user.getId());
			Set<Role> roles = user2.getRoles();
			List<Role> list = new ArrayList<Role>();
			if(null != roles && roles.size() > 0){
				list.addAll(roles);
			}
			return list;
		}
	}

}
