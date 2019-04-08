9.7
ALTER TABLE `t_sys_appversion` ADD `channel` VARCHAR(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'app标识' AFTER `channel`;
ALTER TABLE `t_bus_guaranteefeeinfo` ADD `payer`  VARCHAR(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '付款人';
ALTER TABLE `t_bus_guaranteefeeinfo` ADD `pay_way` VARCHAR(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付方式(1，站长代付 2，微信支付)';

10.16
ALTER TABLE `t_sys_org` ADD `WARRANT_RATE` VARCHAR(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '担保金比例' AFTER `FILEPATH`;
ALTER TABLE `t_sys_org` ADD `ORG_TYPE` VARCHAR(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '机构类型（1，服务站 2，商户）' AFTER `FILEPATH`;


12.14
ALTER TABLE `t_app_application`
ADD COLUMN `FARMER_RATE`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '农户担保金比例' AFTER `UPD_DATE`,
ADD COLUMN `STATION_RATE`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '服务站担保金比例' AFTER `FARMER_RATE`;

新增反担保金表
DROP TABLE IF EXISTS `t_bus_guaranteereverse`;
CREATE TABLE `t_bus_guaranteereverse` (
  `id` varchar(64) NOT NULL COMMENT '服务费详情id',
  `org_id` varchar(64) DEFAULT NULL,
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织机构名称',
  `into_piece_id` varchar(64) DEFAULT NULL,
  `use` int(11) DEFAULT NULL COMMENT '客户类型(1,有移动端账号 2,无移动端账号)',
  `account_name` varchar(32) DEFAULT NULL COMMENT '客户姓名',
  `certificate_no` varchar(32) DEFAULT NULL COMMENT '身份证号',
  `account_no` varchar(32) DEFAULT NULL COMMENT '银行卡号',
  `mobile_no` varchar(11) DEFAULT NULL COMMENT '手机号',
  `total_amount` decimal(20,2) DEFAULT NULL COMMENT '服务费或担保费总金额',
  `payer` varchar(32) DEFAULT NULL COMMENT '付款人',
  `payer_idcard` varchar(18) DEFAULT NULL COMMENT '支付人身份证号',
  `payer_bankcardno` varchar(32) DEFAULT NULL COMMENT '支付人银行卡号',
  `payer_mobile` varchar(11) DEFAULT NULL COMMENT '支付人手机号',
  `type` int(1) DEFAULT NULL COMMENT '支付人类型（1客户，2站长）',
  `pay_way` varchar(4) DEFAULT NULL COMMENT '支付方式(1，站长代付 2，微信支付)',
  `status` varchar(4) DEFAULT NULL COMMENT '先锋支付交易订单状态：I（支付处理中），S（支付成功），F（支付失败）,RF(退款失败),RI（退款处理中）,RS（退款成功）,GL（放弃支付）',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建日期',
  `update_date` datetime DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`id`),
  KEY `into_piece_id` (`into_piece_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

12.19增加进件反担保金表
DROP TABLE IF EXISTS `t_bus_intopiecereverse`;
CREATE TABLE `t_bus_intopiecereverse` (
  `id` varchar(64) NOT NULL COMMENT '进件反担保金id',
  `org_id` varchar(64) DEFAULT NULL COMMENT '组织机构id',
  `into_piece_id` varchar(64) DEFAULT NULL COMMENT '进件id',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织机构名称',
  `member_name` varchar(32) DEFAULT NULL COMMENT '客户姓名',
  `id_card` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号',
  `capital` decimal(20,2) DEFAULT NULL COMMENT '贷款审核金额',
  `farmer_reverse` decimal(20,2) DEFAULT NULL COMMENT '农户反担保金',
  `station_reverse` decimal(20,2) DEFAULT NULL COMMENT '服务站反担保金',
  `status` varchar(2) DEFAULT NULL COMMENT '进件反担保金状态(1,已收取 2，退回中 3，已退回)',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

19/1/18
ALTER TABLE `t_bus_transferland`
ADD COLUMN `eastern_border`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '东边界' AFTER `sort`,
ADD COLUMN `western_border`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '西边界' AFTER `eastern_border`,
ADD COLUMN `northern_border`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '北边界' AFTER `western_border`,
ADD COLUMN `southern_border`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '南边界' AFTER `northern_border`,
ADD COLUMN `outsource`  int(1) NULL DEFAULT NULL COMMENT '是否为外包地（1,是 2，否）' AFTER `southern_border`,
ADD COLUMN `outsourcing_term`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '承包期限（单位：年）' AFTER `outsource`;