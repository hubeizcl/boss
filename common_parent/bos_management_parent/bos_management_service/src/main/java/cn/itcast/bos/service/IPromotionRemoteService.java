package cn.itcast.bos.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import cn.itcast.bos.domain.base.Promotion;
import cn.itcast.bos.utils.PageBean;

@Path("/promotion")
public interface IPromotionRemoteService {

	/**
	 * @param page 当前页
	 * @param pageSize 每页条数
	 * @return 分页对象
	 */
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/pageQuery/{page}/{pageSize}")
	public PageBean<Promotion> pageQuery(@PathParam("page")Integer page, @PathParam("pageSize")Integer pageSize);

	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/findPromotionById/{id}")
	public Promotion findPromotionById(@PathParam("id")Integer id);
}
