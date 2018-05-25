package cn.itcast.bos.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.itcast.bos.domain.base.Order;

public interface OrderRepository 
	extends JpaRepository<Order, Integer>, JpaSpecificationExecutor<Order> {

}
