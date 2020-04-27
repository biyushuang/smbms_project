package com.jcz.entity;

public class Param {

	private Integer currentPageNo;/*当前页*/
	private Integer totalPageCount;/*总页数*/
	private Integer totalCount;/*总数*/
	private Integer size=5;
	public Integer getCurrentPageNo() {
		return currentPageNo;
	}
	public void setCurrentPageNo(Integer currentPageNo) {
		this.currentPageNo = currentPageNo;
	}
	public Integer getTotalPageCount() {
		return totalPageCount=(this.totalCount%this.size==0?this.totalCount/this.size:this.totalCount/this.size+1);
	}
	public void setTotalPageCount(Integer totalPageCount) {
		this.totalPageCount = totalPageCount;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	
	
	
	
}
