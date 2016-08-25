package com.zaoyi.service.core;

import java.io.Serializable;

/**
 * 功能：所有持久化对象都要继承这个类
 * 
 * @author xiongliang
 *
 *         mobile enterprise application platform Version 0.1
 */
public abstract class PO {

	abstract public Serializable getId();

}
