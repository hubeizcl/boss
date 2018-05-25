package cn.itcast.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.WorkBillRepository;
import cn.itcast.bos.domain.base.WorkBill;
import cn.itcast.bos.service.IWorkBillService;

@Service
@Transactional
public class WorkBillServiceImpl implements IWorkBillService {

	@Resource
	private WorkBillRepository workBillRepository;

	@Override
	public List<WorkBill> findNewWorkBill() {
		return workBillRepository.findByType("新单");
	}
}
