package jp.co.cec.vfInputDatMigrator.db.model;

import java.sql.Timestamp;

public class InterlockDataItem {
	private Timestamp exec_date_time;	
	private Byte exec_data_flag;
	private String log_id;
	private String element_index;
	private String element_index_key;
	private String element;
	private String original_item_name;
	private String subject_type;
	private String acquisition;
	private Double calculation_point;
	private String generation_flag;
	private String input_data_type;
	private String output_data_format;
	private String unit;
	private String device;
	private String master_id;
	private String changing_point;
	private String host_name;
	private Timestamp Validity_start_on;
	private Timestamp validity_end_on;
	private String created_by;
	private Timestamp created_at;
	private String updated_by;
	private Timestamp updated_at;
	public Timestamp getExec_date_time() {
		return exec_date_time;
	}
	public void setExec_date_time(Timestamp exec_date_time) {
		this.exec_date_time = exec_date_time;
	}
	public Byte getExec_data_flag() {
		return exec_data_flag;
	}
	public void setExec_data_flag(Byte exec_data_flag) {
		this.exec_data_flag = exec_data_flag;
	}
	public String getLog_id() {
		return log_id;
	}
	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}
	public String getElement_index() {
		return element_index;
	}
	public void setElement_index(String element_index) {
		this.element_index = element_index;
	}
	public String getElement_index_key() {
		return element_index_key;
	}
	public void setElement_index_key(String element_index_key) {
		this.element_index_key = element_index_key;
	}
	public String getElement() {
		return element;
	}
	public void setElement(String element) {
		this.element = element;
	}
	public String getOriginal_item_name() {
		return original_item_name;
	}
	public void setOriginal_item_name(String original_item_name) {
		this.original_item_name = original_item_name;
	}
	public String getSubject_type() {
		return subject_type;
	}
	public void setSubject_type(String subject_type) {
		this.subject_type = subject_type;
	}
	public String getAcquisition() {
		return acquisition;
	}
	public void setAcquisition(String acquisition) {
		this.acquisition = acquisition;
	}
	public Double getCalculation_point() {
		return calculation_point;
	}
	public void setCalculation_point(Double calculation_point) {
		this.calculation_point = calculation_point;
	}
	public String getGeneration_flag() {
		return generation_flag;
	}
	public void setGeneration_flag(String generation_flag) {
		this.generation_flag = generation_flag;
	}
	public String getInput_data_type() {
		return input_data_type;
	}
	public void setInput_data_type(String input_data_type) {
		this.input_data_type = input_data_type;
	}
	public String getOutput_data_format() {
		return output_data_format;
	}
	public void setOutput_data_format(String output_data_format) {
		this.output_data_format = output_data_format;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public String getMaster_id() {
		return master_id;
	}
	public void setMaster_id(String master_id) {
		this.master_id = master_id;
	}
	public String getChanging_point() {
		return changing_point;
	}
	public void setChanging_point(String changing_point) {
		this.changing_point = changing_point;
	}
	public String getHost_name() {
		return host_name;
	}
	public void setHost_name(String host_name) {
		this.host_name = host_name;
	}
	public Timestamp getValidity_start_on() {
		return Validity_start_on;
	}
	public void setValidity_start_on(Timestamp validity_start_on) {
		Validity_start_on = validity_start_on;
	}
	public Timestamp getValidity_end_on() {
		return validity_end_on;
	}
	public void setValidity_end_on(Timestamp validity_end_on) {
		this.validity_end_on = validity_end_on;
	}
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public Timestamp getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}
	public String getUpdated_by() {
		return updated_by;
	}
	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}
	public Timestamp getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Timestamp updated_at) {
		this.updated_at = updated_at;
	}
}
