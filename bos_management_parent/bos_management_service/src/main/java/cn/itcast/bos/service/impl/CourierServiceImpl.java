package cn.itcast.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.CourierRepository;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.ICourierService;

@Service
@Transactional
public class CourierServiceImpl implements ICourierService {

	@Resource
	private CourierRepository courierRepository;

	@RequiresPermissions("courier")
	@Override
	public void add(Courier model) {
		courierRepository.save(model);
	}

	@Override
	public Page<Courier> pageQuery(Specification<Courier> spec, Pageable pageable) {
		return courierRepository.findAll(spec, pageable);
	}

	@Override
	public void delete(String ids) {
		Subject subject = SecurityUtils.getSubject();//已认证状态
		subject.checkPermission("abc");
		//1.将ids切割
		String[] idsArr = ids.split(",");
		//2.循环将指定id的deltag更新成1-已作废
		for(String id : idsArr){
			//update Courier set deltag = 1 where id=?
			courierRepository.updateDeltag(Integer.valueOf(id));
		}
	}

	@Override
	public List<Courier> findNoDel() {
		return courierRepository.findByDeltag('0');
	}
}
