package cn.itcast.crm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.crm.dao.CustomerRepository;
import cn.itcast.crm.domain.Customer;
import cn.itcast.crm.service.ICustomerService;

@Transactional
public class CustomerServiceImpl implements ICustomerService {

	@Resource
	private CustomerRepository customerRepository;
	
	@Override
	public List<Customer> findAll() {
		return customerRepository.findAll();
	}

	@Override
	public List<Customer> findNoGuanLianCustomers() {
		return customerRepository.findByFixedAreaIdIsNull();
	}

	@Override
	public List<Customer> findGuanLianCustomers(String fixedAreaId) {
		return customerRepository.findGuanLianCustomers(fixedAreaId);
	}

	/**
	 * update Customer set fixedAreaId = null where fixedAreaId=?
	 * update Customer set fixedAreaId = ? where id = ?
	 */
	@Override
	public void assignCustomers2FixedArea(String customerIds, String fixedAreaId) {
		//1.根据定区id将该定区原有的客户解除绑定（fixedAreaId = null）
		customerRepository.updateFixedAreaId2Null(fixedAreaId);
		//2.循环根据客户id，将客户的定区外键更新为选中的定区（fixedAreaId = dq001）
		if(StringUtils.isNotBlank(customerIds)){
			String[] custIds = customerIds.split(",");
			for(String id : custIds){
				customerRepository.updateFixedAreaIdById(Integer.valueOf(id), fixedAreaId);
			}
		}
	}

	@Override
	public void regist(Customer customer) {
		customerRepository.save(customer);
	}

	@Override
	public Customer findCustomerByTelephone(String telephone) {
		return customerRepository.findByTelephone(telephone);
	}

	@Override
	public void activeMail(Integer id) {
		customerRepository.activeMail(id);
	}

	@Override
	public Customer findCustomerByTelephoneAndPassword(String telephone, String password) {
		return customerRepository.findByTelephoneAndPassword(telephone, password);
	}

	@Override
	public String findFixedAreaIdByAddress(String address) {
		return customerRepository.findFixedAreaIdByAddress(address);
	}

}
