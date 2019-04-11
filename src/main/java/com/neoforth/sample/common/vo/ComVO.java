package com.neoforth.sample.common.vo;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ComVO {
	
	private String del_yn;
	private String in_date;
	private String in_user;
	private String in_ip;
	private String up_date;
	private String up_user;
	private String up_ip;
	
	/*	페이징	*/
	private int totCnt;
	private int rNum;
	private int pageNum = 1;
	private int pageSize = 20;	
	private int blockSize = 10;
	private int pageStartRow = 1;
	private int pageEndRow = 10;
	/*	페이징	*/
	
	private String searchType;
	private String searchValue;
	
	
	// VO 로그
	public String toStringVo(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE).concat("\nVO======================END");
	}


	public String getDel_yn() {
		return del_yn;
	}


	public void setDel_yn(String del_yn) {
		this.del_yn = del_yn;
	}


	public String getIn_date() {
		return in_date;
	}


	public void setIn_date(String in_date) {
		this.in_date = in_date;
	}


	public String getIn_user() {
		return in_user;
	}


	public void setIn_user(String in_user) {
		this.in_user = in_user;
	}


	public String getIn_ip() {
		return in_ip;
	}


	public void setIn_ip(String in_ip) {
		this.in_ip = in_ip;
	}


	public String getUp_date() {
		return up_date;
	}


	public void setUp_date(String up_date) {
		this.up_date = up_date;
	}


	public String getUp_user() {
		return up_user;
	}


	public void setUp_user(String up_user) {
		this.up_user = up_user;
	}


	public String getUp_ip() {
		return up_ip;
	}


	public void setUp_ip(String up_ip) {
		this.up_ip = up_ip;
	}


	public int getTotCnt() {
		return totCnt;
	}


	public void setTotCnt(int totCnt) {
		this.totCnt = totCnt;
	}


	public int getrNum() {
		return rNum;
	}


	public void setrNum(int rNum) {
		this.rNum = rNum;
	}


	public int getPageNum() {
		return pageNum;
	}


	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public int getBlockSize() {
		return blockSize;
	}


	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}


	public int getPageStartRow() {
		return pageStartRow;
	}


	public void setPageStartRow(int pageStartRow) {
		this.pageStartRow = pageStartRow;
	}


	public int getPageEndRow() {
		return pageEndRow;
	}


	public void setPageEndRow(int pageEndRow) {
		this.pageEndRow = pageEndRow;
	}


	public String getSearchType() {
		return searchType;
	}


	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}


	public String getSearchValue() {
		return searchValue;
	}


	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

}
