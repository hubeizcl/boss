package cn.itcast.bos.realm;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import cn.itcast.bos.domain.base.Permission;
import cn.itcast.bos.domain.base.Role;
import cn.itcast.bos.domain.base.User;
import cn.itcast.bos.service.IPermissionService;
import cn.itcast.bos.service.IRoleService;
import cn.itcast.bos.service.IUserService;

public class BosLoginRealm extends AuthorizingRealm{

	@Resource
	private IUserService userService;
	@Resource
	private IPermissionService permissionService;
	@Resource
	private IRoleService roleService;
	
	/**
	 * 授权方法
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalcollection) {
		//1.获取当前用户
		User user = (User) principalcollection.getPrimaryPrincipal();
		//2.根据用户查询权限和角色
		//2.1根据用户查询该用户的权限
		List<Permission> permsList = permissionService.findPermsByUser(user);
		//2.2根据用户查询该用户的角色
		List<Role> rolesList = roleService.findRolesByUser(user);
		//3.将权限和角色授予用户
		//3.1将权限授予用户
		SimpleAuthorizationInfo sai = new SimpleAuthorizationInfo();
		if(null != permsList && permsList.size() > 0){
			for(Permission perm : permsList){
				sai.addStringPermission(perm.getKeyword());
			}
		}
		//3.2将角色授予用户
		if(null != rolesList && rolesList.size() > 0){
			for(Role role : rolesList){
				sai.addRole(role.getKeyword());
			}
		}
		return sai;
	}

	/**
	 * 认证方法
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationtoken)
			throws AuthenticationException {
		//1.根据用户名查询用户信息对象
		UsernamePasswordToken upt = (UsernamePasswordToken)authenticationtoken;
		String username = upt.getUsername();
		User user = userService.findUserByUsername(username);
		if(null != user){
			//2.查到，将查询的用户对象、密码封装到AuthenticationInfo，返回给安全管理器，安全管理器会自动校验密码，如果校验成功，正常执行；如果校验失败，抛异常
			//参数1：放到该参数的对象，可以在系统的任何位置获取到，一般放查询到的用户对象
			//参数2：需要验证的密码
			//参数3：当前realm的类名
			return new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
		}
		//3.查询不到，直接返回null给安全管理器，安全管理器认为用户不存在，直接抛异常
		return null;
	}

}
