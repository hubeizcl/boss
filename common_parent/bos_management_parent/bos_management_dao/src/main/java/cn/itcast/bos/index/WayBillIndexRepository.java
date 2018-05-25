package cn.itcast.bos.index;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import cn.itcast.bos.domain.base.WayBill;

public interface WayBillIndexRepository extends ElasticsearchRepository<WayBill, Integer> {

}
