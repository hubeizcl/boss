package cn.itcast.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.StandardRepository;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.IStandardService;

@Service
@Transactional
public class StandardServiceImpl implements IStandardService {

	@Resource
	private StandardRepository standardRepository;

	@Override
	public void add(Standard model) {
		standardRepository.save(model);
	}

	@Override
	public Page<Standard> pageQuery(Pageable pageable) {
		return standardRepository.findAll(pageable);
	}

	@Override
	public List<Standard> findAll() {
		return standardRepository.findAll();
	}
}
