package com.hundsun.zgcs;

/**
 * 需要被统计的实体类
 * 
 * @author xiayu20540
 *
 */
public class TjBean {

	public int number;

	public String name;

	public TjBean() {
		// TODO Auto-generated constructor stub
	}
	
	

	public TjBean(int number, String name) {
		super();
		this.number = number;
		this.name = name;
	}



	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
