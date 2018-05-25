package cn.itcast.crm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.crm.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>, JpaSpecificationExecutor<Customer> {

    List<Customer> findByFixedAreaIdIsNull();

    @Query("from Customer where fixedAreaId = ?1")
    List<Customer> findGuanLianCustomers(String fixedAreaId);

    @Query("update Customer set fixedAreaId = null where fixedAreaId = ?1")
    @Modifying
    void updateFixedAreaId2Null(String fixedAreaId);

    @Query("update Customer set fixedAreaId = ?2 where id = ?1")
    @Modifying
    void updateFixedAreaIdById(Integer id, String fixedAreaId);

    Customer findByTelephone(String telephone);

    @Query("update Customer set type = 1 where id = ?1")
    @Modifying
    void activeMail(Integer id);

    Customer findByTelephoneAndPassword(String telephone, String password);

    @Query("select fixedAreaId from Customer where address=?1")
    String findFixedAreaIdByAddress(String address);

}
