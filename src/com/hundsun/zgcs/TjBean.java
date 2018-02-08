package com.hundsun.zgcs;

/**
 * 需要被统计的实体类
 * 
 * @author xiayu20540
 *
 */
public class TjBean {

	public String name;

	public int bdNum = 0;

	public int bdSum = 0;

	public int xqNum = 0;

	public int xqSum = 0;

	public String percent = "";

	public TjBean() {
		super();
	}

	public TjBean(String name, int bdNum, int bdSum, int xqNum, int xqSum, String percent) {
		super();
		this.name = name;
		this.bdNum = bdNum;
		this.bdSum = bdSum;
		this.xqNum = xqNum;
		this.xqSum = xqSum;
		this.percent = percent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBdNum() {
		return bdNum;
	}

	public void setBdNum(int bdNum) {
		this.bdNum = bdNum;
	}

	public int getBdSum() {
		return bdSum;
	}

	public void setBdSum(int bdSum) {
		this.bdSum = bdSum;
	}

	public int getXqNum() {
		return xqNum;
	}

	public void setXqNum(int xqNum) {
		this.xqNum = xqNum;
	}

	public int getXqSum() {
		return xqSum;
	}

	public void setXqSum(int xqSum) {
		this.xqSum = xqSum;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

}
