package cn.itcast.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.AreaRepository;
import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.service.IAreaService;

@Service
@Transactional
public class AreaServiceImpl implements IAreaService {

	@Resource
	private AreaRepository areaRepository;

	@Override
	public void batchSave(List<Area> list) {
		for(Area area : list){
			areaRepository.save(area);
		}
	}

	@Override
	public Page<Area> pageQuery(Specification<Area> spec, Pageable pageable) {
		return areaRepository.findAll(spec, pageable);
	}

	@Override
	public List<Area> findAll() {
		return areaRepository.findAll();
	}
}
