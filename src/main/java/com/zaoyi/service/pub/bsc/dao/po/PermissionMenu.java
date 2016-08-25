package com.zaoyi.service.pub.bsc.dao.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.zaoyi.service.core.PO;
/**
 * 权限菜单类
 * @author linjiang
 *
 */
@Entity
@Table
public class PermissionMenu extends PO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer provinceMenuId; 			//权限菜单Id
	private Integer provinceId;					//权限Id
	private Integer MenuId;						//菜单Id
	@Override
	public Serializable getId() {
		return provinceMenuId;
	}
	public Integer getProvinceMenuId() {
		return provinceMenuId;
	}
	public void setProvinceMenuId(Integer provinceMenuId) {
		this.provinceMenuId = provinceMenuId;
	}
	public Integer getprovinceId() {
		return provinceId;
	}
	public void setProvinceId(Integer privateId) {
		this.provinceId = privateId;
	}
	public Integer getMenuId() {
		return MenuId;
	}
	public void setMenuId(Integer menuId) {
		MenuId = menuId;
	}
	
}
