package com.neoforth.sample.mysql.vo;

import java.util.List;

import com.neoforth.sample.common.vo.FileVO;

public class MysqlVO extends FileVO{
	
	private String sample_id;
	private String sample_name;
	private String sample_subject;
	private String sample_contents;
	private String sample_order;
	private String hot_yn;
	
	private List<FileVO> fileList1;
	private List<FileVO> fileList2;
	
	
	public String getSample_id() {
		return sample_id;
	}
	public void setSample_id(String sample_id) {
		this.sample_id = sample_id;
	}
	public String getSample_name() {
		return sample_name;
	}
	public void setSample_name(String sample_name) {
		this.sample_name = sample_name;
	}
	public String getSample_subject() {
		return sample_subject;
	}
	public void setSample_subject(String sample_subject) {
		this.sample_subject = sample_subject;
	}
	public String getSample_contents() {
		return sample_contents;
	}
	public void setSample_contents(String sample_contents) {
		this.sample_contents = sample_contents;
	}
	public String getSample_order() {
		return sample_order;
	}
	public void setSample_order(String sample_order) {
		this.sample_order = sample_order;
	}
	public String getHot_yn() {
		return hot_yn;
	}
	public void setHot_yn(String hot_yn) {
		this.hot_yn = hot_yn;
	}
	public List<FileVO> getFileList1() {
		return fileList1;
	}
	public void setFileList1(List<FileVO> fileList1) {
		this.fileList1 = fileList1;
	}
	public List<FileVO> getFileList2() {
		return fileList2;
	}
	public void setFileList2(List<FileVO> fileList2) {
		this.fileList2 = fileList2;
	}
	
}
