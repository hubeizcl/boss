package cn.itcast.bos.test.jpa;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.bos.dao.StandardRepository;
import cn.itcast.bos.domain.base.Standard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SpringDataJpaTest {

	@Autowired
	private StandardRepository standardRepository;
	
	/**
	 * 查询所有取派标准数据
	 */
	@Test
	public void testDemo1(){
		List<Standard> list = standardRepository.findAll();
		for(Standard st : list){
			System.out.println(st.getName());
		}
	}
	
	/**
	 * 使用方法名解析实现根据取派标准的名称查询取派标准
	 */
	@Test
	public void testDemo2(){
		List<Standard> list = standardRepository.findByName("10~20公斤");
		for(Standard st : list){
			System.out.println(st.getName());
		}
	}
	
	/**
	 * 使用方法名解析实现根据取派标准的名称模糊查询取派标准
	 */
	@Test
	public void testDemo3(){
		List<Standard> list = standardRepository.findByNameLike("%20%");
		for(Standard st : list){
			System.out.println(st.getName());
		}
	}
	
	/**
	 * 使用query实现根据取派标准的名称模糊查询取派标准
	 */
	@Test
	public void testDemo4(){
		List<Standard> list = standardRepository.getStandardByName("%20%");
		for(Standard st : list){
			System.out.println(st.getName());
		}
	}
}
