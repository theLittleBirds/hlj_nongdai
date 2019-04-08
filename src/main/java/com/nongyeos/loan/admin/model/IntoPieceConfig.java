package com.nongyeos.loan.admin.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="intopiece")
@PropertySource("classpath:busintopiece.properties")
public class IntoPieceConfig {
	
		//查询同盾百融用到的用户账号
		private String userName;
		//查询同盾的url
		private String tongdunurl;
		//查询百融的url
		private String bairongurl;
		//查询鹏元的url
		private String pengyuanurl;
		//四要素接口
		private String xianfengurl;
		//先锋新协议支付（免短免密代扣）
		private String xfNoSmsNoContractNo;
		//上传合同到君子签
		private String junziqianUpload;
		//君子签发送签约短信
		private String junziqianSms;
		//君子签下载合同
		private String junziqiandown;
		//君子签签约地址获取
		private String junziqianSignUrl;
		//君子签企业授权
		private String junziqianAuthorization;
		//先锋服务费代扣
		private String xfGuaranteeFeeUrl;
		//微信支付
		private String wxPayCheck;
		//人脸识别
		private String personFace;
		//微信服务费（担保费）支付
		private String wxPayGuaranteeFeeCheck;
		
		/**
		 * 异步回调地址
		 * @return
		 */
		//先锋新协议支付异步
		private String bgXFSignPayNoSms;
		//君子签异步
		private String bgJzqRet;
		//微信支付异步
		private String bgWXPayNotice;
		//七陌异步推送
		private String bgQMRet;
		//先锋服务费（担保费）支付异步
		private String bgXFGuaranteeFee;
		//微信服务费（担保费）支付异步回调
		private String bgWXPayGuaranteeFeeNotice;
		//微信退款申请异步回调
		private String bgWXRefundNotice;
		
		//微信退款申请同步
		private String wxRefundCheck;

		
		public String getUserName() {
			return userName;
		}

		public void setUserName(String username) {
			this.userName = username;
		}

		public String getTongdunurl() {
			return tongdunurl;
		}

		public void setTongdunurl(String tongdunurl) {
			this.tongdunurl = tongdunurl;
		}

		public String getBairongurl() {
			return bairongurl;
		}

		public void setBairongurl(String bairongurl) {
			this.bairongurl = bairongurl;
		}

		public String getPengyuanurl() {
			return pengyuanurl;
		}

		public void setPengyuanurl(String pengyuanurl) {
			this.pengyuanurl = pengyuanurl;
		}

		public String getXianfengurl() {
			return xianfengurl;
		}

		public void setXianfengurl(String xianfengurl) {
			this.xianfengurl = xianfengurl;
		}

		public String getXfNoSmsNoContractNo() {
			return xfNoSmsNoContractNo;
		}

		public void setXfNoSmsNoContractNo(String xfNoSmsNoContractNo) {
			this.xfNoSmsNoContractNo = xfNoSmsNoContractNo;
		}
		
		public String getJunziqianUpload() {
			return junziqianUpload;
		}

		public void setJunziqianUpload(String junziqianUpload) {
			this.junziqianUpload = junziqianUpload;
		}
		
		public String getJunziqianSms() {
			return junziqianSms;
		}

		public void setJunziqianSms(String junziqianSms) {
			this.junziqianSms = junziqianSms;
		}
		
		public String getJunziqiandown() {
			return junziqiandown;
		}

		public void setJunziqiandown(String junziqiandown) {
			this.junziqiandown = junziqiandown;
		}
		
		public String getJunziqianSignUrl() {
			return junziqianSignUrl;
		}

		public void setJunziqianSignUrl(String junziqianSignUrl) {
			this.junziqianSignUrl = junziqianSignUrl;
		}
		
		public String getJunziqianAuthorization() {
			return junziqianAuthorization;
		}

		public void setJunziqianAuthorization(String junziqianAuthorization) {
			this.junziqianAuthorization = junziqianAuthorization;
		}
		
		public String getBgXFSignPayNoSms() {
			return bgXFSignPayNoSms;
		}

		public void setBgXFSignPayNoSms(String bgXFSignPayNoSms) {
			this.bgXFSignPayNoSms = bgXFSignPayNoSms;
		}

		public String getBgJzqRet() {
			return bgJzqRet;
		}

		public void setBgJzqRet(String bgJzqRet) {
			this.bgJzqRet = bgJzqRet;
		}

		public String getBgWXPayNotice() {
			return bgWXPayNotice;
		}

		public void setBgWXPayNotice(String bgWXPayNotice) {
			this.bgWXPayNotice = bgWXPayNotice;
		}

		public String getWxPayCheck() {
			return wxPayCheck;
		}

		public void setWxPayCheck(String wxPayCheck) {
			this.wxPayCheck = wxPayCheck;
		}

		public String getBgQMRet() {
			return bgQMRet;
		}

		public void setBgQMRet(String bgQMRet) {
			this.bgQMRet = bgQMRet;
		}

		public String getBgXFGuaranteeFee() {
			return bgXFGuaranteeFee;
		}

		public void setBgXFGuaranteeFee(String bgXFGuaranteeFee) {
			this.bgXFGuaranteeFee = bgXFGuaranteeFee;
		}

		public String getXfGuaranteeFeeUrl() {
			return xfGuaranteeFeeUrl;
		}

		public void setXfGuaranteeFeeUrl(String xfGuaranteeFeeUrl) {
			this.xfGuaranteeFeeUrl = xfGuaranteeFeeUrl;
		}

		public String getPersonFace() {
			return personFace;
		}

		public void setPersonFace(String personFace) {
			this.personFace = personFace;
		}

		public String getBgWXPayGuaranteeFeeNotice() {
			return bgWXPayGuaranteeFeeNotice;
		}

		public void setBgWXPayGuaranteeFeeNotice(String bgWXPayGuaranteeFeeNotice) {
			this.bgWXPayGuaranteeFeeNotice = bgWXPayGuaranteeFeeNotice;
		}

		public String getWxPayGuaranteeFeeCheck() {
			return wxPayGuaranteeFeeCheck;
		}

		public void setWxPayGuaranteeFeeCheck(String wxPayGuaranteeFeeCheck) {
			this.wxPayGuaranteeFeeCheck = wxPayGuaranteeFeeCheck;
		}

		public String getBgWXRefundNotice() {
			return bgWXRefundNotice;
		}

		public void setBgWXRefundNotice(String bgWXRefundNotice) {
			this.bgWXRefundNotice = bgWXRefundNotice;
		}

		public String getWxRefundCheck() {
			return wxRefundCheck;
		}

		public void setWxRefundCheck(String wxRefundCheck) {
			this.wxRefundCheck = wxRefundCheck;
		}
		
}
