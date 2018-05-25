package cn.itcast.bos.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import cn.itcast.bos.domain.base.Order;

@Path("/order")
public interface IOrderRemoteService {

	@POST
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Path("/saveOrder")
	public void saveOrder(Order order);
}
