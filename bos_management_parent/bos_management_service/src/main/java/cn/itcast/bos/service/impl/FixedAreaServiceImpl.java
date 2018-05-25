package cn.itcast.bos.service.impl;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.CourierRepository;
import cn.itcast.bos.dao.FixedAreaRepository;
import cn.itcast.bos.dao.TakeTimeRepository;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.domain.base.TakeTime;
import cn.itcast.bos.service.IFixedAreaService;

@Service
@Transactional
public class FixedAreaServiceImpl implements IFixedAreaService {

	@Resource
	private FixedAreaRepository fixedAreaRepository;
	@Resource
	private CourierRepository courierRepository;
	@Resource
	private TakeTimeRepository takeTimeRepository;

	@Override
	public void add(FixedArea model) {
		fixedAreaRepository.save(model);
	}

	@Override
	public Page<FixedArea> pageQuery(Specification<FixedArea> spec, Pageable pageable) {
		return fixedAreaRepository.findAll(spec, pageable);
	}

	@Override
	public void associationCourierToFixedArea(String id, Integer courierId, Integer takeTimeId) {
		//1.定区和快递员关联
		//通过查看实体类映射关系，发现快递员放弃了中间表维护选，更新中间表将快递员对象，设置到定区的对象中
		FixedArea fixedArea = fixedAreaRepository.findOne(id);
		/**
		 * hibernate实体类对象三个状态：
		 * 	 瞬时态：没有id，没有被session，一般通过new 创建的对象
		 *  持久态：有id，被session管理，一般通过查询、更新、保存获取到的对象，可以自动更新数据库
		 *  游离态：有id，没有被session，一般调用删除方法获取的对象
		 */
		Courier courier = courierRepository.findOne(courierId);//持久态
		fixedArea.getCouriers().add(courier);
		//2.快递员和时间关联：参考定区关联快递员
		TakeTime takeTime = takeTimeRepository.findOne(takeTimeId);
		courier.setTakeTime(takeTime);
	}

}
