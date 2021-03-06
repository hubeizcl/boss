package cn.itcast.bos.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.base.Standard;

public interface StandardRepository extends JpaRepository<Standard, Integer> {

	List<Standard> findByName(String string);

	List<Standard> findByNameLike(String string);

	@Query("from Standard where name like ?1")
	List<Standard> getStandardByName(String string);

}
