package com.neoforth.sample.common.vo;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class FileVO extends ComVO{
	
	private String file_id;
	private String upload_group;
	private String upload_group_sub;
	private String master_table;
	private String master_id;
	private String org_file_name;
	private String sys_file_name;
	private String file_path;
	private String file_size;
	private String file_ext;
	private String element_nm;
	
	// VO 로그
	public String toStringVo(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE).concat("\nVO======================END");
	}

	public String getFile_id() {
		return file_id;
	}

	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}

	public String getUpload_group() {
		return upload_group;
	}

	public void setUpload_group(String upload_group) {
		this.upload_group = upload_group;
	}

	public String getUpload_group_sub() {
		return upload_group_sub;
	}

	public void setUpload_group_sub(String upload_group_sub) {
		this.upload_group_sub = upload_group_sub;
	}

	public String getMaster_table() {
		return master_table;
	}

	public void setMaster_table(String master_table) {
		this.master_table = master_table;
	}

	public String getMaster_id() {
		return master_id;
	}

	public void setMaster_id(String master_id) {
		this.master_id = master_id;
	}

	public String getOrg_file_name() {
		return org_file_name;
	}

	public void setOrg_file_name(String org_file_name) {
		this.org_file_name = org_file_name;
	}

	public String getSys_file_name() {
		return sys_file_name;
	}

	public void setSys_file_name(String sys_file_name) {
		this.sys_file_name = sys_file_name;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public String getFile_size() {
		return file_size;
	}

	public void setFile_size(String file_size) {
		this.file_size = file_size;
	}

	public String getFile_ext() {
		return file_ext;
	}

	public void setFile_ext(String file_ext) {
		this.file_ext = file_ext;
	}

	public String getElement_nm() {
		return element_nm;
	}

	public void setElement_nm(String element_nm) {
		this.element_nm = element_nm;
	}
	
}
