package com.zaoyi.service.pub.bsc.dao.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.zaoyi.service.core.PO;
/**
 * 权限
 * @author linjiang
 */
@Entity
@Table
public class Permission extends PO {
	@Id
	@Column(unique = true, nullable = false)
	private Integer provinceId;
	private String name;

	@Override
	public Serializable getId() {
		return provinceId;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
