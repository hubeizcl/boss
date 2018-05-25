package cn.itcast.bos.web.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
import cn.itcast.bos.domain.base.Role;
import cn.itcast.bos.service.IRoleService;

@ParentPackage("struts-default")
@Namespace("/")
@Actions
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {
	
	@Resource
	private IRoleService roleService;
	
	/**
	 * 分页查询方法
	 * @return
	 * @throws IOException 
	 */
	@Action(value="roleAction_pageQuery")
	public String pageQuery() throws IOException{
		//1.封装分页查询参数
		//参数1：当前页-1
		//参数2：每页条数
		Pageable pageable = new PageRequest(page - 1, rows);
		Specification<Role> spec = new Specification<Role>(){

			/**
			 * root：保存当前查询的实体类和表的映射关系
			 * query：可以将多个查询条件组装成一个查询数据
			 * cb：可以组装一个查询条件，比如id like ?
			 */
			@Override
			public Predicate toPredicate(Root<Role> root, 
					CriteriaQuery<?> query, CriteriaBuilder cb) {
                return null;
			}
		};
		//2.调用service执行分页查询：总条数和每页要显示的数据集合
		Page<Role> page = roleService.pageQuery(spec, pageable);
		//3.将查询的结果转换成json对象
		/*{                                                      
			"total":100,	
			"rows":[ 
				{"id":"001","courierNum":"09876","name":"李大","telephone":"13912345678","checkPwd":"123456","pda":"123456","standard":{"name":"10-20公斤"},"type":"小件员","vehicleType":"卡车","vehicleNum":"98765","company":"杭州分部","deltag":"0"},
				{"id":"002","courierNum":"09876","name":"李大","telephone":"13912345678","checkPwd":"123456","pda":"123456","standard":{"name":"10-20公斤"},"type":"小件员","vehicleType":"卡车","vehicleNum":"98765","company":"杭州分部","deltag":"0"}
			]
		}*/
		String[] excludes = {"users", "permissions", "menus"};
		this.writePage2JsonObject(page, excludes);
		return NONE;
	}

	private String menuIds;
	
	private Integer[] permissionIds;
	
	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}
	
	public void setPermissionIds(Integer[] permissionIds) {
		this.permissionIds = permissionIds;
	}
	
	/**
	 * 保存角色数据，关联角色和权限，关联角色和菜单，跳转分页查询界面
	 * @return
	 */
	@Action(value="roleAction_add",
			results={
					@Result(name="success", location="/pages/system/role.jsp")
			})
	public String add(){
		roleService.add(model, permissionIds, menuIds);
		return "success";
	}
	
	/**
	 * 查询所有的角色数据，转换成json数组，返回到界面
	 * @return
	 * @throws IOException 
	 */
	@Action(value="roleAction_findAllRoles")
	public String findAllRoles() throws IOException{
		List<Role> list = roleService.findAll();
		String[] excludes = {"users","permissions","menus"};
		this.writeJava2JsonArray(list, excludes);
		return NONE;
	}
}
