package cn.itcast.bos.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.base.Courier;

public interface CourierRepository 
	extends JpaRepository<Courier, Integer>, JpaSpecificationExecutor<Courier> {

	@Query("update Courier set deltag = '1' where id=?")
	@Modifying//当使用query执行增删改操作时，需要使用@Modifying
	void updateDeltag(Integer id);

	List<Courier> findByDeltag(char c);

}
