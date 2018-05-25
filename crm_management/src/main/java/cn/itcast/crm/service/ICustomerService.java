package cn.itcast.crm.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import cn.itcast.crm.domain.Customer;

@Path("/customer")
public interface ICustomerService {

	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/findAll")
	public List<Customer> findAll();
	
	//查询所有未关联定区(fixedAreaId is null)的客户信息，返回
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/findNoGuanLianCustomers")
	public List<Customer> findNoGuanLianCustomers();
	
	//查询所有关联选中定区(fixedAreaId = ?1)的客户信息，返回
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/findGuanLianCustomers/{fixedAreaId}")
	public List<Customer> findGuanLianCustomers(@PathParam("fixedAreaId")String fixedAreaId);
	
	//将客户绑定到指定的定区上
	@PUT
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/assignCustomers2FixedArea/{customerIds}/{fixedAreaId}")
	public void assignCustomers2FixedArea(@PathParam("customerIds")String customerIds, @PathParam("fixedAreaId")String fixedAreaId);

	//客户注册方法
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/regist")
	public void regist(Customer customer);
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/findCustomerByTelephone/{telephone}")
	public Customer findCustomerByTelephone(@PathParam("telephone")String telephone);
	
	@PUT
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/activeMail/{id}")
	public void activeMail(@PathParam("id")Integer id);
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/findCustomerByTelephoneAndPassword/{telephone}/{password}")
	public Customer findCustomerByTelephoneAndPassword(@PathParam("telephone")String telephone, @PathParam("password")String password);

	//根据客户地址查询客户所属定区
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/findFixedAreaIdByAddress/{address}")
	public String findFixedAreaIdByAddress(@PathParam("address")String address);
}
