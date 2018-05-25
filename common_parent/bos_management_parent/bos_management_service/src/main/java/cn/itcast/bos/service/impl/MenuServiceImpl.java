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

import cn.itcast.bos.dao.MenuRepository;
import cn.itcast.bos.dao.UserRepository;
import cn.itcast.bos.domain.base.Menu;
import cn.itcast.bos.domain.base.Role;
import cn.itcast.bos.domain.base.User;
import cn.itcast.bos.service.IMenuService;

@Service
@Transactional
public class MenuServiceImpl implements IMenuService {

	@Resource
	private MenuRepository menuRepository;
	@Resource
	private UserRepository userRepository;

	@Override
	public Page<Menu> pageQuery(Specification<Menu> spec, Pageable pageable) {
		return menuRepository.findAll(spec, pageable);
	}

	@Override
	public List<Menu> findParentMenuIsNull() {
		return menuRepository.findByParentMenuIsNull();
	}

	@Override
	public void add(Menu model) {
		menuRepository.save(model);
	}

	@Override
	public List<Menu> findAll() {
		return menuRepository.findAll();
	}

	@Override
	public List<Menu> findMenuByUser(User user) {
		//1.判断是否是admin，超级管理员
		if("admin".equals(user.getUsername())){
			//2.是，返回所有菜单
			return menuRepository.findAll();
		} else {
			//3.否，根据用户id查询该用户的菜单，返回
			//3.1根据用户id查询用户对象，获取角色
			User user2 = userRepository.findOne(user.getId());
			Set<Role> roles = user2.getRoles();
			List<Menu> list = new ArrayList<Menu>();
			if(null != roles && roles.size() > 0){
				for(Role role : roles){
					//3.2根据角色循环查询菜单
					Set<Menu> menus = role.getMenus();
					//3.3将查询到的菜单放到list
					list.addAll(menus);
				}
			}
			return list;
		}
	}

}
