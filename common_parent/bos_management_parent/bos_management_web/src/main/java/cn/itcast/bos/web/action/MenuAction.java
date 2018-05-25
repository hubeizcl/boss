package cn.itcast.bos.web.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
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

import cn.itcast.bos.domain.base.Menu;
import cn.itcast.bos.domain.base.User;
import cn.itcast.bos.service.IMenuService;

@ParentPackage("struts-default")
@Namespace("/")
@Actions
@Controller
@Scope("prototype")
public class MenuAction extends BaseAction<Menu> {
	
	@Resource
	private IMenuService menuService;
	
	/**
	 * 分页查询方法
	 * @return
	 * @throws IOException 
	 */
	@Action(value="menuAction_pageQuery")
	public String pageQuery() throws IOException{
		String pageM = model.getPage();
		//1.封装分页查询参数
		//参数1：当前页-1
		//参数2：每页条数
		Pageable pageable = new PageRequest(Integer.parseInt(pageM) - 1, rows);
		Specification<Menu> spec = new Specification<Menu>(){

			/**
			 * root：保存当前查询的实体类和表的映射关系
			 * query：可以将多个查询条件组装成一个查询数据
			 * cb：可以组装一个查询条件，比如id like ?
			 */
			@Override
			public Predicate toPredicate(Root<Menu> root, 
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				//查询所有根节点数据（parentMenu is null）
                return cb.isNull(root.get("parentMenu").as(Menu.class));
			}
		};
		//2.调用service执行分页查询：总条数和每页要显示的数据集合
		Page<Menu> page = menuService.pageQuery(spec, pageable);
		//3.将查询的结果转换成json对象
		/*{                                                      
			"total":100,	
			"rows":[ 
				{"id":"001","courierNum":"09876","name":"李大","telephone":"13912345678","checkPwd":"123456","pda":"123456","standard":{"name":"10-20公斤"},"type":"小件员","vehicleType":"卡车","vehicleNum":"98765","company":"杭州分部","deltag":"0"},
				{"id":"002","courierNum":"09876","name":"李大","telephone":"13912345678","checkPwd":"123456","pda":"123456","standard":{"name":"10-20公斤"},"type":"小件员","vehicleType":"卡车","vehicleNum":"98765","company":"杭州分部","deltag":"0"}
			]
		}*/
		String[] excludes = {"roles", "childrenMenus", "parentMenu"};
		this.writePage2JsonObject(page, excludes);
		return NONE;
	}
	
	/**
	 * 查询根节点菜单数据（parentMenu is null），转换成json，返回到界面
	 * @return
	 * @throws IOException 
	 */
	@Action(value="menuAction_findMenus")
	public String findMenus() throws IOException{
		//1.查询根节点菜单数据（parentMenu is null）
		List<Menu> list = menuService.findParentMenuIsNull();
		String[] excludes = {"roles", "childrenMenus", "parentMenu"};
		//2.转换成json，返回到界面
		this.writeJava2JsonArray(list, excludes);
		return NONE;
	}
	
	@Action(value="menuAction_add",
			results={
					@Result(name="success",location="/pages/system/menu.jsp")
			})
	public String add(){
		//判断瞬时态对象，将其设置为null
		Menu parentMenu = model.getParentMenu();
		if(null != parentMenu && null == parentMenu.getId()){
			model.setParentMenu(null);
		}
		menuService.add(model);
		return "success";
	}
	/**
	 * 查询所有的菜单信息，转换成json数组，返回到界面
	 * @return
	 * @throws IOException 
	 */
	@Action(value="menuAction_findAllMenus")
	public String findAllMenus() throws IOException{
		List<Menu> list = menuService.findAll();
		String[] excludes = {"roles", "childrenMenus", "parentMenu", "children"};
		this.writeJava2JsonArray(list, excludes);
		return NONE;
	}
	
	/**
	 * 根据用户查询该用户的菜单，转换成json，返回到界面
	 * @return
	 * @throws IOException 
	 */
	@Action(value="menuAction_findMenuByUser")
	public String findMenuByUser() throws IOException{
		//1.根据用户查询菜单
		User user = (User) ServletActionContext
				.getRequest().getSession().getAttribute("loginUser");
		List<Menu> list = menuService.findMenuByUser(user);
		String[] excludes = {"roles", "childrenMenus", "parentMenu", "children"};
		//2.将菜单转换成json返回到界面
		this.writeJava2JsonArray(list, excludes);
		return NONE;
	}
}
