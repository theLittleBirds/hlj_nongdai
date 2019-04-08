package com.nongyeos.loan.core.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ResMsg {
	private int code;	
	// 0: OK
	// 2000 - 成功，不需要做进一步处理
	// 2XXX - 成功， 
	// 3XXX - 成功，需要跳转
	// 4XXX - 错误，输入不合法 
	// 5XXX - 运行时错误，未知错误
	// 6XXX - 修改数据项
	// 7XXX - 改变流向
	// 8XXX - 判断是否为空
	
	private String msg;		// 可读的消息
	private String processorCn;	// 处理者中文名
	private String processor;	// 处理者英文名
	private Date date;		// 调用完成时间
	private int duration;	// 调用消耗时长
	private Map<String, Object> data = new HashMap<>();	// 数据
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getProcessorCn() {
		return processorCn;
	}
	public void setProcessorCn(String processorCn) {
		this.processorCn = processorCn;
	}
	public String getProcessor() {
		return processor;
	}
	public void setProcessor(String processor) {
		this.processor = processor;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
}
