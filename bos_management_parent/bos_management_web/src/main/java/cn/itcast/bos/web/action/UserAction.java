package cn.itcast.bos.web.action;

import java.io.IOException;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.domain.base.User;
import cn.itcast.bos.service.IUserService;
import cn.itcast.bos.utils.MD5Utils;

@ParentPackage("struts-default")
@Namespace("/")
@Actions
@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {
	
	@Resource
	private IUserService userService;
	
	private String checkcode;

	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	
	/**
	 * 登录方法
	 * @return
	 */
	@Action(value="userAction_login",
			results={
					@Result(name="success",location="/index.jsp"),
					@Result(name="failure",location="/login.jsp"),
			})
	public String login(){
		//1.校验验证码是否正确
		String checkcodeS = (String) ServletActionContext
				.getRequest().getSession().getAttribute("key");
		if(StringUtils.isNotBlank(checkcode)
				&& checkcode.equals(checkcodeS)){
			//2.如果正确，调用shiro实现认证
			//2.1获取subject对象
			Subject subject = SecurityUtils.getSubject();//未认证状态
			AuthenticationToken authenticationtoken = new UsernamePasswordToken(
					model.getUsername(), MD5Utils.md5(model.getPassword()));
			//2.2使用subject实现认证
			try {
				subject.login(authenticationtoken);
				//2.3登录成功，将用户放到session，返回成功界面
				User user = (User) subject.getPrincipal();
				ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
				return "success";
			} catch (Exception e) {
				e.printStackTrace();
				//2.4登录失败，跳转登录界面
				return "failure";
			}
		} else {
			//3.错误，跳转到登录界面
			return "failure";
		}
	}
	
	/**
	 * 登出系统方法
	 * @return
	 */
	@Action(value="userAction_logout",
			results={
					@Result(name="success", location="/login.jsp")
			})
	public String logout(){
		//1.清理subject缓存
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		//2.跳转登录界面
		return "success";
	}
	
	/**
	 * 分页查询方法
	 * @return
	 * @throws IOException 
	 */
	@Action(value="userAction_pageQuery")
	public String pageQuery() throws IOException{
		//1.封装分页查询参数
		//参数1：当前页-1
		//参数2：每页条数
		Pageable pageable = new PageRequest(page - 1, rows);
		Specification<User> spec = new Specification<User>(){

			/**
			 * root：保存当前查询的实体类和表的映射关系
			 * query：可以将多个查询条件组装成一个查询数据
			 * cb：可以组装一个查询条件，比如id like ?
			 */
			@Override
			public Predicate toPredicate(Root<User> root, 
					CriteriaQuery<?> query, CriteriaBuilder cb) {
                return null;
			}
		};
		//2.调用service执行分页查询：总条数和每页要显示的数据集合
		Page<User> page = userService.pageQuery(spec, pageable);
		//3.将查询的结果转换成json对象
		/*{                                                      
			"total":100,	
			"rows":[ 
				{"id":"001","courierNum":"09876","name":"李大","telephone":"13912345678","checkPwd":"123456","pda":"123456","standard":{"name":"10-20公斤"},"type":"小件员","vehicleType":"卡车","vehicleNum":"98765","company":"杭州分部","deltag":"0"},
				{"id":"002","courierNum":"09876","name":"李大","telephone":"13912345678","checkPwd":"123456","pda":"123456","standard":{"name":"10-20公斤"},"type":"小件员","vehicleType":"卡车","vehicleNum":"98765","company":"杭州分部","deltag":"0"}
			]
		}*/
		String[] excludes = {"roles"};
		this.writePage2JsonObject(page, excludes);
		return NONE;
	}

	private Integer[] roleIds;
	
	public void setRoleIds(Integer[] roleIds) {
		this.roleIds = roleIds;
	}
	
	/**
	 * 保存用户数据，关联用户和角色，跳转到分页界面
	 * @return
	 */
	@Action(value="userAction_add",
			results={
					@Result(name="success",location="/pages/system/user.jsp")
			})
	public String add(){
		userService.add(model, roleIds);
		return "success";
	}
}
