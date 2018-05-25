package cn.itcast.bos.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.itcast.bos.domain.base.WorkBill;

public interface WorkBillRepository extends JpaRepository<WorkBill, Integer>, JpaSpecificationExecutor<WorkBill> {

	List<WorkBill> findByType(String string);

}
