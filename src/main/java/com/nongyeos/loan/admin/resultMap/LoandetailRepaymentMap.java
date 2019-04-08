package com.nongyeos.loan.admin.resultMap;

import java.math.BigDecimal;


/**
 * 还款记录表
 * 
 * @author wcyong
 * 
 * @date 2018-08-02
 */
public class LoandetailRepaymentMap{

	String member_name;//客户姓名
	String id;//还款明细记录id
	String info;//还款明细记录备注
	String loan_id;//贷款id
	String into_piece_id;//进件id
	String loan_detail_id;//贷款明细id
	String id_card;//身份证号
	String repayment_way;//还款方式
	BigDecimal receive_capital;//还款本金（单位：元）
	BigDecimal receive_interest;//还款利息（单位：元）
	BigDecimal receive_overdue;//还款逾期费用（单位：元）
	BigDecimal receive_capital_interest;//本息合计（单位：元）
	
    /**
     * 状态（1：还款中  2：复审中  3：还款失败  4：还款完成）
     */
    private Integer status;
    /**
     * 删除标识符
     */
    private Integer delFlag;    
    private String personId;
	
	public String getMember_name() {
		return member_name;
	}
	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getLoan_id() {
		return loan_id;
	}
	public void setLoan_id(String loan_id) {
		this.loan_id = loan_id;
	}
	public String getInto_piece_id() {
		return into_piece_id;
	}
	public void setInto_piece_id(String into_piece_id) {
		this.into_piece_id = into_piece_id;
	}
	public String getId_card() {
		return id_card;
	}
	public void setId_card(String id_card) {
		this.id_card = id_card;
	}
	public String getRepayment_way() {
		return repayment_way;
	}
	public void setRepayment_way(String repayment_way) {
		this.repayment_way = repayment_way;
	}
	public BigDecimal getReceive_capital() {
		return receive_capital;
	}
	public void setReceive_capital(BigDecimal receive_capital) {
		this.receive_capital = receive_capital;
	}
	public BigDecimal getReceive_interest() {
		return receive_interest;
	}
	public void setReceive_interest(BigDecimal receive_interest) {
		this.receive_interest = receive_interest;
	}
	public BigDecimal getReceive_overdue() {
		return receive_overdue;
	}
	public void setReceive_overdue(BigDecimal receive_overdue) {
		this.receive_overdue = receive_overdue;
	}
	public BigDecimal getReceive_capital_interest() {
		return receive_capital_interest;
	}
	public void setReceive_capital_interest(BigDecimal receive_capital_interest) {
		this.receive_capital_interest = receive_capital_interest;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public String getLoan_detail_id() {
		return loan_detail_id;
	}
	public void setLoan_detail_id(String loan_detail_id) {
		this.loan_detail_id = loan_detail_id;
	}
	
}