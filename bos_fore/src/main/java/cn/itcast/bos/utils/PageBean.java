package cn.itcast.bos.utils;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import cn.itcast.bos.domain.base.Promotion;

/**
 * <p>
 * Title: cn.itcast.bos.utilsPageBean.java
 * </p>
 * <p>
 * Description:分页查询工具类
 * </p>
 * <p>
 * Company: www.itcast.com
 * </p>
 * 
 * @author 传智.九纹龙
 * @date 2017年11月9日上午9:13:40
 * @version 1.0
 */
@XmlRootElement
@XmlSeeAlso(Promotion.class)
public class PageBean<T> {

	private Long totalCount;// 总条数
	private List<T> pageData;// 每页显示的数据集合

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public List<T> getPageData() {
		return pageData;
	}

	public void setPageData(List<T> pageData) {
		this.pageData = pageData;
	}

}