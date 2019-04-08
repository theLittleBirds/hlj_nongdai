package com.nongyeos.loan.base.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

import com.nongyeos.loan.admin.entity.BusProduct;


public class GetRepaymentUtils {
	

	
    /**
     * 先息后本
     * 根据还款方式，借款金额，借款期限以及借款利率，计算出借款利息并返还。
     *
     * @param money      借贷总金额
     * @param duration   借款期限
     * @param rate       借款利率（月利率）,由于传入的是百分比，所以在使用之前先除以100
     * @param borrowTime 借款时间（type为all的时候可以传null）
     * @param type       计算方式
     * @return 返回计算结果
     */
    public static Map<String, Object> equalEndMonth(BigDecimal money, int duration, BigDecimal rate, Long borrowTime, String type) {

        Map<String, Object> result = new HashMap<String, Object>();

        if (borrowTime == null) {
            borrowTime = DateUtils.getSysTime();
        }
        // 计算出月利率
        BigDecimal monthApr = rate.divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP);
        //总还款利息
        BigDecimal allInterest = new BigDecimal(0);
        //计算出每月利息
        BigDecimal interest = money.multiply(monthApr);

        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        Map<String, Object> tempMap = null;

        for (int i = 0; i < duration; ++i) {
            //每月还款本金
            BigDecimal capital = new BigDecimal(0);
            //如果还款时间到期，计算出还款本金capital：还款本金=借款总金额
            if (i + 1 == duration) {
                capital = money;
            }

            tempMap = new HashMap<String, Object>();

            //计算出每月还款金额repaymentAccount：每月还款金额 = 每月利息 + 每月还款本金
            tempMap.put("repaymentMoney", interest.add(capital).setScale(0, RoundingMode.HALF_UP));
            // 每月还款时间
            Date BorrowStartTime = DateUtils.parseDateByTime(borrowTime);
            tempMap.put("repaymentTime", DateUtils.getUnixTime(DateUtils.addMonths(BorrowStartTime, i + 1)));
            //每月利息
            tempMap.put("interest", interest.setScale(0, RoundingMode.HALF_UP));
            //每月还款本金
            tempMap.put("capital", capital.setScale(0, RoundingMode.HALF_UP));

            //统计出总还款利息allInterest：总还款利息 = 上月总还款利息+本月利息
            allInterest = allInterest.add(interest);
            if(i + 1 == duration){
            	tempMap.put("capitalAndInterest", allInterest.add(money));
            }
            // 循环下标
            tempMap.put("index", i);

            resultList.add(tempMap);
        }

        // type为"all",则按照等额本息的计算方式算出一下结果
        if (StringUtils.equals(type, "all")) {
            // 还款总金额
            result.put("repaymentMoney", money.add(interest.multiply(NumberUtils.toBigDecimal(duration))).setScale(0, RoundingMode.HALF_UP));
            // 每月还款金额
            result.put("monthlyRepayment", interest.setScale(0, RoundingMode.HALF_UP));
            // 月利率
            result.put("monthlyRate", NumberUtils.round(monthApr.multiply(NumberUtils.toBigDecimal(100)), 4, RoundingMode.HALF_UP));
            //总还款利息
            result.put("interest", allInterest.setScale(0, RoundingMode.HALF_UP));
            // 否则，进行每月还款情况计算：
        } else {
            result.put("list", resultList);
        }
        return result;
    }

    
    /**8
     * 等额本息(惠农)只能贷服务费
     * 根据还款方式，借款金额，借款期限以及借款利率，计算出借款利息并返还。
     *
     * @param money      借贷总金额
     * @param duration   借款期限
     * @param rate       借款利率（月利率）,由于传入的是百分比，所以在使用之前先除以100
     * @param serviceRate       服务费利率,由于传入的是百分比，所以在使用之前先除以100
     * @param borrowTime 借款时间（type为all的时候可以传null）
     * @param type       计算方式
     * @return 返回计算结果
     */
    public static Map<String, Object> equalMonthHN(BigDecimal money, int duration, BigDecimal rate, BigDecimal serviceRate, Long borrowTime, String type) {


        Map<String, Object> result = new HashMap<String, Object>();

        // 计算出月利率
        BigDecimal monthApr = rate.divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP);
        // 计算出总服务费利率
        serviceRate = serviceRate.divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP);

        // 总服务费
        BigDecimal serviceMoney = money.multiply(serviceRate);
        
        //服务费加贷款总额（待贷服务费时的合同金额）
        BigDecimal contractMoney=money.add(serviceMoney);
        
        // 月还款金额（不含服务费）
        BigDecimal monthMoney = money.divide(new BigDecimal(duration), 20, RoundingMode.HALF_UP).add(money.multiply(monthApr));
        // 月还款金额（含服务费）
          BigDecimal monthMoneyWithService = contractMoney.divide(new BigDecimal(duration), 20, RoundingMode.HALF_UP).add(money.add(serviceMoney).multiply(monthApr));
          //剩余本金（不含服务费）
          BigDecimal shenbenjin = money;
          //剩余本金（含服务费）
          BigDecimal shenbenjinWithService = contractMoney;
        // type为"all",则按照等额本息的计算方式算出一下结果
        if (StringUtils.equals(type, "all")) {

            // 还款总金额 （不含服务费）
            result.put("repaymentMoney", monthMoney.multiply(new BigDecimal(duration)).setScale(0, RoundingMode.HALF_UP));
            // 还款总金额（含服务费）
            result.put("repaymentMoneyWithService", monthMoneyWithService.multiply(new BigDecimal(duration)).setScale(0, RoundingMode.HALF_UP));
            // 服务费
            result.put("serviceMoney", serviceMoney.setScale(0, RoundingMode.HALF_UP));
            // 每月还款金额（不含服务费）
            result.put("monthlyRepayment", monthMoney.setScale(0, RoundingMode.HALF_UP));
            // 每月还款金额（含服务费）
            result.put("monthlyRepaymentWithService", monthMoneyWithService.setScale(0, RoundingMode.HALF_UP));
            // 否则，进行每月还款情况计算：
        } else {
            BigDecimal interest = null;

            List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
            Map<String, Object> tempMap = null;

            if (borrowTime == null) {
                borrowTime = DateUtils.getSysTime();
            }

            Date borrowStartTime = DateUtils.parseDateByTime(borrowTime);
            BigDecimal repaymentCapital = new BigDecimal("0.00");
            for (int i = 0; i < duration; ++i) {

                tempMap = new HashMap<String, Object>();

                // 每月还款本金（不含服务费）
                tempMap.put("monthMoney", money.divide(new BigDecimal(duration), 0, RoundingMode.HALF_UP));
                // 每月还款本金（含服务费）
                tempMap.put("capital", (money.add(serviceMoney)).divide(new BigDecimal(duration), 0, RoundingMode.HALF_UP));
                // 每月还款利息（不含服务费）
                tempMap.put("monthFee", money.multiply(monthApr).setScale(0, RoundingMode.HALF_UP));
                // 每月还款利息（含服务费）
                // tempMap.put("interest", (money.add(serviceMoney)).multiply(monthApr).setScale(0, RoundingMode.HALF_UP));
                tempMap.put("interest", monthMoneyWithService.setScale(0, RoundingMode.HALF_UP).subtract(money.add(serviceMoney).divide(new BigDecimal(duration), 0, RoundingMode.HALF_UP)));
                // 每月还款总金额（不含服务费）
                tempMap.put("monthCapital", monthMoney.setScale(0, RoundingMode.HALF_UP));
                // 每月还款总金额（含服务费）
                tempMap.put("monthCapitalWithService", monthMoneyWithService.setScale(0, RoundingMode.HALF_UP));
                // 每月还款时间
                tempMap.put("repaymentTime", DateUtils.getUnixTime(DateUtils.addMonths(borrowStartTime, i + 1)));
                if(i + 1 == duration){
                	tempMap.put("capitalAndInterest", monthMoney.multiply(new BigDecimal(duration)).setScale(0, RoundingMode.HALF_UP));
                	tempMap.put("capitalAndInterestWithService", monthMoneyWithService.multiply(new BigDecimal(duration)).setScale(0, RoundingMode.HALF_UP));
                }
                // 循环下标
                tempMap.put("index", i);
                resultList.add(tempMap);
                shenbenjin =shenbenjin.subtract(monthMoney.subtract(shenbenjin.multiply(monthApr)).setScale(0, RoundingMode.HALF_UP));
                shenbenjinWithService =shenbenjinWithService.subtract(monthMoneyWithService.subtract(shenbenjinWithService.multiply(monthApr)).setScale(0, RoundingMode.HALF_UP));
            }

            result.put("list", resultList);
        }
        return result;
    }

    /**8
     * 等额本息
     * 根据还款方式，借款金额，借款期限以及借款利率，计算出借款利息并返还。
     *
     * @param money      借贷总金额
     * @param duration   借款期限
     * @param rate       借款利率（月利率）,由于传入的是百分比，所以在使用之前先除以100
     * @param serviceRate       服务费利率,由于传入的是百分比，所以在使用之前先除以100
     * @param borrowTime 借款时间（type为all的时候可以传null）
     * @param type       计算方式
     * @return 返回计算结果
     */
    public static Map<String, Object> equalMonth(BigDecimal money, int duration, BigDecimal rate, BigDecimal serviceRate, Long borrowTime, String type) {


        Map<String, Object> result = new HashMap<String, Object>();

        // 计算出月利率
        BigDecimal monthApr = rate.divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP);
        // 计算出服务费利率
        serviceRate = serviceRate.divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP);

        // 总服务费
        BigDecimal serviceMoney = money.multiply(serviceRate);
        
        //服务费加贷款总额（待贷服务费时的合同金额）
        BigDecimal contractMoney=money.add(serviceMoney);
        
        // 月还款金额（不含服务费）
        BigDecimal monthMoney = money.multiply(monthApr).multiply((monthApr.add(new BigDecimal(1))).pow(duration)).divide((monthApr.add(new BigDecimal(1))).pow(duration).subtract(new BigDecimal(1)),20,RoundingMode.HALF_UP);  
        // 月还款金额（含服务费）
          BigDecimal monthMoneyWithService = contractMoney.multiply(monthApr).multiply((monthApr.add(new BigDecimal(1))).pow(duration)).divide((monthApr.add(new BigDecimal(1))).pow(duration).subtract(new BigDecimal(1)),20,RoundingMode.HALF_UP);  
          //剩余本金（不含服务费）
          BigDecimal shenbenjin = money;
          //剩余本金（含服务费）
          BigDecimal shenbenjinWithService = contractMoney;
        // type为"all",则按照等额本息的计算方式算出一下结果
        if (StringUtils.equals(type, "all")) {

            // 还款总金额 （不含服务费）
            result.put("repaymentMoney", monthMoney.multiply(new BigDecimal(duration)).setScale(0, RoundingMode.HALF_UP));
            // 还款总金额（含服务费）
            result.put("repaymentMoneyWithService", monthMoneyWithService.multiply(new BigDecimal(duration)).setScale(0, RoundingMode.HALF_UP));
            // 服务费
            result.put("serviceMoney", serviceMoney.setScale(0, RoundingMode.HALF_UP));
            // 每月还款金额（不含服务费）
            result.put("monthlyRepayment", monthMoney.setScale(0, RoundingMode.HALF_UP));
            // 每月还款金额（含服务费）
            result.put("monthlyRepaymentWithService", monthMoneyWithService.setScale(0, RoundingMode.HALF_UP));
            // 否则，进行每月还款情况计算：
        } else {
            BigDecimal interest = null;

            List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
            Map<String, Object> tempMap = null;

            if (borrowTime == null) {
                borrowTime = DateUtils.getSysTime();
            }

            Date borrowStartTime = DateUtils.parseDateByTime(borrowTime);
            BigDecimal repaymentCapital = new BigDecimal("0.00");
            for (int i = 0; i < duration; ++i) {

                tempMap = new HashMap<String, Object>();

                // 每月还款本金（不含服务费）
                tempMap.put("monthMoney", monthMoney.subtract(shenbenjin.multiply(monthApr).setScale(0, RoundingMode.HALF_UP)).setScale(0, RoundingMode.HALF_UP));
                // 每月还款本金（含服务费）
                tempMap.put("capital", monthMoneyWithService.subtract(shenbenjinWithService.multiply(monthApr).setScale(0, RoundingMode.HALF_UP)).setScale(0, RoundingMode.HALF_UP));
                // 每月还款利息（不含服务费）
                tempMap.put("monthFee", shenbenjin.multiply(monthApr).setScale(0, RoundingMode.HALF_UP));
                // 每月还款利息（含服务费）
                // tempMap.put("interest", (money.add(serviceMoney)).multiply(monthApr).setScale(0, RoundingMode.HALF_UP));
                tempMap.put("interest", shenbenjinWithService.multiply(monthApr).setScale(0, RoundingMode.HALF_UP));
                // 每月还款总金额（不含服务费）
                tempMap.put("monthCapital", monthMoney.setScale(0, RoundingMode.HALF_UP));
                // 每月还款总金额（含服务费）
                tempMap.put("monthCapitalWithService", monthMoneyWithService.setScale(0, RoundingMode.HALF_UP));
                // 每月还款时间
                tempMap.put("repaymentTime", DateUtils.getUnixTime(DateUtils.addMonths(borrowStartTime, i + 1)));
                if(i + 1 == duration){
                	tempMap.put("capitalAndInterest", monthMoney.multiply(new BigDecimal(duration)).setScale(0, RoundingMode.HALF_UP));
                	tempMap.put("capitalAndInterestWithService", monthMoneyWithService.multiply(new BigDecimal(duration)).setScale(0, RoundingMode.HALF_UP));
                }
                // 循环下标
                tempMap.put("index", i);
                resultList.add(tempMap);
                shenbenjin =shenbenjin.subtract(monthMoney.subtract(shenbenjin.multiply(monthApr)).setScale(0, RoundingMode.HALF_UP));
                shenbenjinWithService =shenbenjinWithService.subtract(monthMoneyWithService.subtract(shenbenjinWithService.multiply(monthApr)).setScale(0, RoundingMode.HALF_UP));
            }

            result.put("list", resultList);
        }
        return result;
    }

    public static void main(String[] args) {
        Map<String, Object> resultList = equalMonth(NumberUtils.toBigDecimal("20000"), 12, NumberUtils.toBigDecimal("0.6"),NumberUtils.toBigDecimal("1"), DateUtils.getSysTime(), null);
        List<Map<String, Object>> list = (List<Map<String, Object>>) resultList.get("list");

        /*System.err.println(resultList.get("repaymentMoney"));
        System.err.println(resultList.get("repaymentMoneyWithService"));
        System.err.println(resultList.get("serviceMoney"));
        System.err.println(resultList.get("monthlyRepayment"));
        System.err.println(resultList.get("monthlyRepaymentWithService"));*/
        System.out.println("通用等额本息");
        for (Map<String, Object> result : list) {
            System.err.println("下标" + result.get("index") + "，每月还款本金（不含服务费）：" + result.get("monthMoney") + "，每月还款本金（含服务费）：" + result.get("capital") +
                    "，每月还款利息（不含服务费）" + result.get("monthFee") + "，每月还款利息（含服务费）" + result.get("interest") + "，每月还款总金额（不含服务费）" + result.get("monthCapital") +
                    "，每月还款总金额（含服务费）" + result.get("monthCapitalWithService") + "，每月还款日期" + result.get("repaymentTime")+result.get("capitalAndInterest")+"还款总金额（不含服务费）："+result.get("capitalAndInterestWithService"));
        }
        System.out.println("按季度");
        Map<String, Object> resultList1 = quarterlyRepayment(NumberUtils.toBigDecimal("20000"), 12, NumberUtils.toBigDecimal("0.6"),NumberUtils.toBigDecimal("1"), DateUtils.getSysTime(), null);
        List<Map<String, Object>> list1 = (List<Map<String, Object>>) resultList1.get("list");

        /*System.err.println(resultList.get("repaymentMoney"));
        System.err.println(resultList.get("repaymentMoneyWithService"));
        System.err.println(resultList.get("serviceMoney"));
        System.err.println(resultList.get("monthlyRepayment"));
        System.err.println(resultList.get("monthlyRepaymentWithService"));*/

        for (Map<String, Object> result : list1) {
            System.err.println("下标" + result.get("index") + "，每月还款本金（不含服务费）：" + result.get("monthMoney") + "，每月还款本金（含服务费）：" + result.get("capital") +
                    "，每月还款利息（不含服务费）" + result.get("monthFee") + "，每月还款利息（含服务费）" + result.get("interest") + "，每月还款总金额（不含服务费）" + result.get("monthCapital") +
                    "，每月还款总金额（含服务费）" + result.get("monthCapitalWithService") + "，每月还款日期" + result.get("repaymentTime")+result.get("capitalAndInterest")+"还款总金额（不含服务费）："+result.get("capitalAndInterestWithService"));
        }
        System.out.println("先息后本包含通用和惠农");
        Map<String, Object> resultList2 = equalEndMonth(NumberUtils.toBigDecimal("20000"), 12, NumberUtils.toBigDecimal("0.6"), DateUtils.getSysTime(), null);
        List<Map<String, Object>> list2 = (List<Map<String, Object>>) resultList2.get("list");

        /*System.err.println(resultList.get("repaymentMoney"));
        System.err.println(resultList.get("repaymentMoneyWithService"));
        System.err.println(resultList.get("serviceMoney"));
        System.err.println(resultList.get("monthlyRepayment"));
        System.err.println(resultList.get("monthlyRepaymentWithService"));*/
        for (Map<String, Object> result : list2) {
            System.err.println("下标" + result.get("index") + "，还款总金额："+result.get("repaymentMoney")+"，还款时间："+result.get("repaymentTime")+"，还款利息："+result.get("interest")+"，还款本金："+result.get("capital")+result.get("capitalAndInterest")+"还款总金额（不含服务费）："+result.get("capitalAndInterestWithService"));
        }
        System.out.println("等额本息惠农");
        Map<String, Object> resultList3 = equalMonthHN(NumberUtils.toBigDecimal("20000"), 12, NumberUtils.toBigDecimal("0.6"),NumberUtils.toBigDecimal("4.8"), DateUtils.getSysTime(), null);
        List<Map<String, Object>> list3 = (List<Map<String, Object>>) resultList3.get("list");

        /*System.err.println(resultList.get("repaymentMoney"));
        System.err.println(resultList.get("repaymentMoneyWithService"));
        System.err.println(resultList.get("serviceMoney"));
        System.err.println(resultList.get("monthlyRepayment"));
        System.err.println(resultList.get("monthlyRepaymentWithService"));*/

        for (Map<String, Object> result : list3) {
            System.err.println("下标" + result.get("index") + "，每月还款本金（不含服务费）：" + result.get("monthMoney") + "，每月还款本金（含服务费）：" + result.get("capital") +
                    "，每月还款利息（不含服务费）" + result.get("monthFee") + "，每月还款利息（含服务费）" + result.get("interest") + "，每月还款总金额（不含服务费）" + result.get("monthCapital") +
                    "，每月还款总金额（含服务费）" + result.get("monthCapitalWithService") + "，每月还款日期" + result.get("repaymentTime")+"还款总金额（不含服务费）："+result.get("capitalAndInterest")+"还款总金额（不含服务费）："+result.get("capitalAndInterestWithService"));
        }
    }



    /**2017.8.29 新增产品------------------------------------------
     * 先息后等
     * 根据还款方式，借款金额，借款期限以及借款利率，计算出借款利息并返还。
     *
     * @param money      借贷总金额
     * @param duration   借款期限 总期限
     * @param rate       借款利率（月利率）,由于传入的是百分比，所以在使用之前先除以100
     * @param serviceRate       服务费利率,由于传入的是百分比，所以在使用之前先除以100
     * @param borrowTime 借款时间（type为all的时候可以传null）
     * @param type       计算方式
     * @param duration2  先息后本部分
     * @return 返回计算结果
     */

    public static Map<String, Object>  endMonthAndequalMonth(BigDecimal money, int duration, BigDecimal rate, BigDecimal serviceRate, Long borrowTime, String type,int duration2) {


        Map<String, Object> result = new HashMap<String, Object>();

        // 计算出月利率
        BigDecimal monthApr = rate.divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP);
        // 计算出服务费利率
        serviceRate = serviceRate.divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP);

        // 总服务费
        BigDecimal serviceMoney = money.multiply(serviceRate);
        // 月还款金额（不含服务费）
        //    BigDecimal monthMoney = money.multiply(monthApr).add(money.divide(new BigDecimal(2), 20, RoundingMode.HALF_UP).divide(new BigDecimal(duration), 20, RoundingMode.HALF_UP));
        // 月还款金额（含服务费 先息后本部分）
        //divide(new BigDecimal(duration), 20, RoundingMode.HALF_UP).add(money.add(serviceMoney).multiply(monthApr));
        BigDecimal monthMoneyWithService = money.add(serviceMoney).multiply(monthApr).add(money.add(serviceMoney).divide(new BigDecimal(2), 20, RoundingMode.HALF_UP).divide(new BigDecimal(duration), 20, RoundingMode.HALF_UP)).setScale(20, RoundingMode.HALF_UP);

        // 月还款金额（含服务费 等额本息部分）

        BigDecimal monthMoneyWithServicequalMonth=money.add(serviceMoney).divide(new BigDecimal(2), 20, RoundingMode.HALF_UP).multiply(monthApr).add(money.add(serviceMoney).divide(new BigDecimal(2), 20, RoundingMode.HALF_UP).divide(new BigDecimal(duration), 20, RoundingMode.HALF_UP)).setScale(20, RoundingMode.HALF_UP);

        //还款总额
        Double repaymentMoney=0.0;


        BigDecimal interest = null;

        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        Map<String, Object> tempMap = null;


        if (borrowTime == null) {
              borrowTime = DateUtils.getSysTime();
        }

         Date borrowStartTime = DateUtils.parseDateByTime(borrowTime);

        BigDecimal repaymentCapital = new BigDecimal("0.00");
        for (int i = 0; i < duration; ++i) {

            tempMap = new HashMap<String, Object>();


            //先息后本部分

            if(i<duration2-1)
            {

                // 每月还款利息（含服务费）

                tempMap.put("interest",    money.add(serviceMoney).multiply(monthApr).setScale(0,RoundingMode.HALF_UP));
                // 每月还款本金（含服务费）
                tempMap.put("capital", money.add(serviceMoney).divide(new BigDecimal(2)).setScale(20, RoundingMode.HALF_UP).divide(new BigDecimal(duration)).setScale(0, RoundingMode.HALF_UP));
                // 每月还款总金额（含服务费）
                tempMap.put("repaymentMoneyWithService", monthMoneyWithService.setScale(0,RoundingMode.HALF_UP));

                // 每月还款时间
                tempMap.put("repaymentTime", DateUtils.getUnixTime(DateUtils.addMonths(borrowStartTime, i + 1)));
                // 循环下标
                tempMap.put("index", i);
                //还款总额
                repaymentMoney+=monthMoneyWithService.setScale(0,RoundingMode.HALF_UP).floatValue();


                resultList.add(tempMap);
                continue;

            }
            else if (i==duration2-1)//先息部分最后一期
            {

                // 每月还款利息（含服务费）
                BigDecimal last_interest=money.add(serviceMoney).multiply(monthApr).setScale(0,RoundingMode.HALF_UP);
                BigDecimal last_capital= money.add(serviceMoney).divide(new BigDecimal(2)).setScale(20,RoundingMode.HALF_UP).divide(new BigDecimal(duration)).setScale(0,RoundingMode.HALF_UP);

                tempMap.put("interest",   last_interest  );
                // 每月还款本金（含服务费）
             //   tempMap.put("capital", money.add(serviceMoney).divide(new BigDecimal(2)).setScale(0, RoundingMode.HALF_UP).divide(new BigDecimal(duration)).setScale(0, RoundingMode.HALF_UP));
                tempMap.put("capital",last_capital.add(money.add(serviceMoney).divide(new BigDecimal(2)).setScale(0,RoundingMode.HALF_UP)));
                // 每月还款总金额（含服务费）// monthMoneyWithService.add(money).divide(new BigDecimal(2)).setScale(0, RoundingMode.HALF_UP)
                tempMap.put("repaymentMoneyWithService",money.add(serviceMoney).divide(new BigDecimal(2)).add(monthMoneyWithService).setScale(0,RoundingMode.HALF_UP));


                /**
                 *

                // 每月还款利息（含服务费）
                tempMap.put("interest", monthMoneyWithService);
                // 每月还款本金（含服务费）
                tempMap.put("capital", money.add(serviceMoney).divide(new BigDecimal(2)).setScale(0, RoundingMode.HALF_UP));
                // 每月还款总金额（含服务费）// monthMoneyWithService.add(money).divide(new BigDecimal(2)).setScale(0, RoundingMode.HALF_UP)
                tempMap.put("repaymentMoneyWithService",money.add(serviceMoney).divide(new BigDecimal(2)).setScale(0, RoundingMode.HALF_UP).add(monthMoneyWithService));
                 */



                // 每月还款时间
                       tempMap.put("repaymentTime", DateUtils.getUnixTime(DateUtils.addMonths(borrowStartTime, i + 1)));
                // 循环下标
                tempMap.put("index", i);
                //还款总额
                repaymentMoney+=monthMoneyWithService.setScale(0,RoundingMode.HALF_UP).floatValue();
                resultList.add(tempMap);
                continue;


            }else //等额本息部分
            {


                // 每月还款本金（含服务费）
                tempMap.put("capital", (money.add(serviceMoney)).divide(new BigDecimal(duration), 20, RoundingMode.HALF_UP).divide(new BigDecimal(2), 0, RoundingMode.HALF_UP));

                // 每月还款利息（含服务费）
                // tempMap.put("interest", (money.add(serviceMoney)).multiply(monthApr).setScale(0, RoundingMode.HALF_UP));
                tempMap.put("interest", (money.add(serviceMoney)).multiply(monthApr).divide(new BigDecimal(2), 0, RoundingMode.HALF_UP) );

                // 每月还款总金额（含服务费）
                tempMap.put("monthCapitalWithService", monthMoneyWithServicequalMonth.setScale(0, RoundingMode.HALF_UP));
                // 每月还款时间
                    tempMap.put("repaymentTime", DateUtils.getUnixTime(DateUtils.addMonths(borrowStartTime, i + 1)));
                // 循环下标
                tempMap.put("index", i);

                //还款总额
                repaymentMoney+=monthMoneyWithServicequalMonth.setScale(0, RoundingMode.HALF_UP).floatValue();
                if(i + 1 == duration){
                	//无服务费的总还款
                   	tempMap.put("capitalAndInterest", new BigDecimal(repaymentMoney).subtract(serviceMoney).setScale(0, RoundingMode.HALF_UP));
                   	//带服务费的总还款
                   	tempMap.put("capitalAndInterestWithService", new BigDecimal(repaymentMoney).setScale(0, RoundingMode.HALF_UP));
                }
                resultList.add(tempMap);
            }




        }

        // type为"all",则按照等额本息的计算方式算出一下结果
        if (type.equals("all"))
        {
            // 还款总金额（含服务费）
            result.put("repaymentMoneyWithService",repaymentMoney);
            // 服务费
            result.put("serviceMoney", serviceMoney.setScale(0, RoundingMode.HALF_UP));

            // 每月还款金额（含服务费）
            result.put("monthlyRepaymentWithService",monthMoneyWithService.setScale(0, RoundingMode.HALF_UP));
        }
        else // 否则，进行每月还款情况计算
        {

            result.put("list", resultList);
        }



        return result;

    }

    /**
     * 
     * @Title: quarterlyRepayment 
     * @Description: 
     * @param money      借贷总金额
     * @param duration   借款期限 总期限
     * @param rate       借款利率（月利率）,由于传入的是百分比，所以在使用之前先除以100
     * @param serviceRate       服务费利率,由于传入的是百分比，所以在使用之前先除以100
     * @param borrowTime 借款时间（type为all的时候可以传null）
     * @param type       计算方式
     * @return 返回计算结果
     * @throws
     */
    public static 	Map<String, Object> quarterlyRepayment(BigDecimal money, int duration, BigDecimal rate, BigDecimal serviceRate, Long borrowTime, String type){
    	Map<String, Object> result = new HashMap<String, Object>();
    	// 计算出月利率
        BigDecimal monthApr = rate.divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP);
        // 计算出服务费利率
        serviceRate = serviceRate.divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP);
        // 总服务费
        BigDecimal serviceMoney = money.multiply(serviceRate);
        //月利息（不含服务费）
        BigDecimal mothInterest = money.multiply(monthApr);
        // 季度末还款金额（不含服务费）
        BigDecimal quarterlyMoney = mothInterest.multiply(new BigDecimal("3"));
        // 正常季度末还款金额（含服务费）
        BigDecimal quarterlyMoneyWithService = money.add(serviceMoney).multiply(monthApr).multiply(new BigDecimal("3"));
        // 最后一期季度末还款金额（不含服务费）
        BigDecimal lastQuarterlyMoney = money.add(quarterlyMoney);
        // 最后一期季度末还款金额（含服务费）
        BigDecimal lastQuarterlyMoneyWithService = money.add(quarterlyMoneyWithService);
        // type为"all",则按照等额本息的计算方式算出一下结果
        if (StringUtils.equals(type, "all")) {

            // 还款总金额 （不含服务费）
            result.put("repaymentMoney", money.add(mothInterest.multiply(new BigDecimal(duration))).setScale(0, RoundingMode.HALF_UP));
            // 还款总金额（含服务费）
            result.put("repaymentMoneyWithService", money.add(mothInterest.multiply(new BigDecimal(duration))).add(serviceMoney).setScale(0, RoundingMode.HALF_UP));
            // 服务费
            result.put("serviceMoney", serviceMoney.setScale(0, RoundingMode.HALF_UP));
            // 首期还款金额（不含服务费）
            result.put("monthlyRepayment", quarterlyMoney.setScale(0, RoundingMode.HALF_UP));
            // 每月还款金额（含服务费）
            result.put("monthlyRepaymentWithService", quarterlyMoneyWithService.setScale(0, RoundingMode.HALF_UP));
            // 否则，进行每月还款情况计算：
        } else {

            List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
            Map<String, Object> tempMap = null;

            if (borrowTime == null) {
                borrowTime = DateUtils.getSysTime();
            }

            Date borrowStartTime = DateUtils.parseDateByTime(borrowTime);
            int count = 0;
            for (int i = 0; i < duration; ++i) {

                tempMap = new HashMap<String, Object>();

                // 季度末但非最后一个季度还款
                if((i+1)%3==0&&i+1<duration){
                	//当月还款本金（不含服务费）
                	tempMap.put("monthMoney", new BigDecimal("0.0").setScale(0, RoundingMode.HALF_UP));
                	// 当月还款本金（含服务费）
                    tempMap.put("capital",new BigDecimal("0.0").setScale(0, RoundingMode.HALF_UP));
                    // 当月还款利息（不含服务费）
                    tempMap.put("monthFee", quarterlyMoney.setScale(0, RoundingMode.HALF_UP));
                    // 当月还款利息（含服务费）
                    // tempMap.put("interest", (money.add(serviceMoney)).multiply(monthApr).setScale(0, RoundingMode.HALF_UP));
                    tempMap.put("interest", quarterlyMoneyWithService.setScale(0, RoundingMode.HALF_UP));
                    // 当月还款总金额（不含服务费）
                    tempMap.put("monthCapital", quarterlyMoney.setScale(0, RoundingMode.HALF_UP));
                    // 当月还款总金额（含服务费）
                    tempMap.put("monthCapitalWithService", quarterlyMoneyWithService.setScale(0, RoundingMode.HALF_UP));
                    // 当月还款时间
                    tempMap.put("repaymentTime", DateUtils.getUnixTime(DateUtils.addMonths(borrowStartTime, i + 1)));
                    //最后一个季度还款
                    count++;
                 // 循环下标
                    if(!StringUtils.isEmpty(String.valueOf(tempMap.get("repaymentTime")) ) ){
                    	tempMap.put("index", count-1);
                        resultList.add(tempMap);
                    }
                }else if ((i+1)%3==0&&i+1==duration){
                	//当月还款本金（不含服务费）
                	tempMap.put("monthMoney", money.setScale(0, RoundingMode.HALF_UP));
                	// 当月还款本金（含服务费）
                    tempMap.put("capital", money.add(serviceMoney).setScale(0, RoundingMode.HALF_UP));
                    // 当月还款利息（不含服务费）
                    tempMap.put("monthFee", quarterlyMoney.setScale(0, RoundingMode.HALF_UP));
                    // 当月还款利息（含服务费）
                    // tempMap.put("interest", (money.add(serviceMoney)).multiply(monthApr).setScale(0, RoundingMode.HALF_UP));
                    tempMap.put("interest", quarterlyMoneyWithService.setScale(0, RoundingMode.HALF_UP));
                    // 当月还款总金额（不含服务费）
                    tempMap.put("monthCapital", lastQuarterlyMoney.setScale(0, RoundingMode.HALF_UP));
                    // 当月还款总金额（含服务费）
                    tempMap.put("monthCapitalWithService", lastQuarterlyMoneyWithService.setScale(0, RoundingMode.HALF_UP));
                    // 当月还款时间
                    tempMap.put("repaymentTime", DateUtils.getUnixTime(DateUtils.addMonths(borrowStartTime, i + 1)));
                    count++;
                 // 循环下标
                    	//无服务费的总还款
                       	tempMap.put("capitalAndInterest", money.add(mothInterest.multiply(new BigDecimal(duration))).setScale(0, RoundingMode.HALF_UP));
                       	//带服务费的总还款
                       	tempMap.put("capitalAndInterestWithService", money.add(mothInterest.multiply(new BigDecimal(duration))).add(serviceMoney).setScale(0, RoundingMode.HALF_UP));
                    if(!StringUtils.isEmpty(String.valueOf(tempMap.get("repaymentTime")) ) ){
                    	tempMap.put("index", count-1);
                        resultList.add(tempMap);
                    }
                }
                
            }

            result.put("list", resultList);
        }
    	return result;
    }

    /**
     * 一次性还清本息
     * @param money 借款总金额
     * @param term  借款总期限（年）
     * @param rate	年利率（年利率）
     * @param serviceRate 服务费年利率
     * @param date 借款日期
     * @return
     */
	public static Map<String, Object> shouXinYearsPayBack(BigDecimal money, int duration, BigDecimal rate, BigDecimal serviceRate, Long borrowTime, String type) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> tempMap = null;
        // 计算出年利率
        BigDecimal yearApr = rate.divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP);
        // 计算出服务费利率
        serviceRate = serviceRate.divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP);
    	//服务费总额
        Double serviceMoney=0.0;
        //还款总额
        Double repaymentMoney=0.0;
        int count = 0;
      for(int i = 0;i<duration;i++){
    	tempMap = new HashMap<String, Object>();
        count++;
    	tempMap = new HashMap<String, Object>();
    	//每年应还本金
    	BigDecimal yearCapital = money.divide(new BigDecimal(duration), 20,RoundingMode.HALF_UP);
    	//剩余本金
    	BigDecimal shenbenjin = money.divide(new BigDecimal(duration), 20,RoundingMode.HALF_UP).multiply(new BigDecimal(duration-i));
    	//本期应交服务费
    	BigDecimal serviceFee = shenbenjin.multiply(serviceRate);
    	//本期应还利息
    	BigDecimal interest = shenbenjin.multiply(yearApr);
    	//应还本金利息及服务费总额
    	BigDecimal capital = yearCapital.add(serviceFee).add(interest);
    	//本期还款时间
    	if (borrowTime == null) {
            borrowTime = DateUtils.getSysTime();
        }
    	Date borrowStartTime = DateUtils.parseDateByTime(borrowTime);
    	
    	//下标
    	tempMap.put("yearCapital", yearCapital.setScale(0, RoundingMode.HALF_UP));
    	tempMap.put("serviceFee", serviceFee.setScale(0, RoundingMode.HALF_UP));
    	//剩余本金
    	tempMap.put("shenbenjin", shenbenjin.setScale(0, RoundingMode.HALF_UP));
    	tempMap.put("interest", interest.setScale(0, RoundingMode.HALF_UP));
    	tempMap.put("capital", capital.setScale(0, RoundingMode.HALF_UP));
    	tempMap.put("payBackDate",  DateUtils.getUnixTime(DateUtils.addYears(borrowStartTime, i + 1)));
    	tempMap.put("index", count-1);
    	resultList.add(tempMap);
    	repaymentMoney+=capital.setScale(0, RoundingMode.HALF_UP).floatValue();
    	serviceMoney+=serviceFee.setScale(0, RoundingMode.HALF_UP).floatValue();
    	 if(i + 1 == duration){
         	//无服务费的总还款
            	tempMap.put("capitalAndInterest", repaymentMoney);
            	//带服务费的总还款
            	tempMap.put("capitalAndInterestWithService", repaymentMoney+serviceMoney);
         }
    }
        if (StringUtils.equals(type, "all")) {

            // 还款总金额 （不含服务费）
            result.put("repaymentMoney", new BigDecimal(repaymentMoney).setScale(0, RoundingMode.HALF_UP));
            // 还款总金额（含服务费）
            result.put("repaymentMoneyWithService", new BigDecimal(repaymentMoney+serviceMoney).setScale(0, RoundingMode.HALF_UP));
            // 服务费
            result.put("serviceMoney", new BigDecimal(serviceMoney).setScale(0, RoundingMode.HALF_UP));
            // 首期还款金额（不含服务费）
            result.put("monthlyRepayment", money.divide(new BigDecimal(duration), 20,RoundingMode.HALF_UP).add(money.multiply(yearApr)).setScale(0, RoundingMode.HALF_UP));
            // 首期还款金额（含服务费）
            result.put("monthlyRepaymentWithService", money.divide(new BigDecimal(duration), 20,RoundingMode.HALF_UP).add(money.multiply(serviceRate)).add(money.multiply(yearApr)).setScale(0, RoundingMode.HALF_UP));
        }else{
        	result.put("list", resultList);
        }
		return result;
	}

	/**
	 * 
	 * @Title: getServiceFee 
	 * @Description: 
	 * @param      
	 * @return void     
	 * @throws
	 */
	public static Map<String, BigDecimal> getServiceFee(BigDecimal money, int duration,BusProduct p){
		Short repaymentWay = p.getRepaymentWay();
		BigDecimal serviceFee=new BigDecimal(0);
		BigDecimal firstServiceFee=new BigDecimal(0);
		BigDecimal shengyubenjin=new BigDecimal(money.toString());
		BigDecimal serviceRate = p.getServiceRate();
		if(repaymentWay.equals(Constants.SHOUXIN_YEARS_REPAYMENT)){
			int years= duration/12;
			if(p.getServiceRateType().equals(Constants.YEAR)){
				for (int i = 0; i < years; i++) {
					if(i==0){
						firstServiceFee=shengyubenjin.multiply(serviceRate.divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP));
					}
					serviceFee=serviceFee.add(shengyubenjin.multiply(serviceRate.divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP)));
					shengyubenjin=shengyubenjin.subtract(money.divide(new BigDecimal(years), 20, RoundingMode.HALF_UP));
				}
			}else{
				for (int i = 0; i < years; i++) {
					if(i==0){
						firstServiceFee=shengyubenjin.multiply(serviceRate.multiply(new BigDecimal(12).divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP)));
					}
					serviceFee=serviceFee.add(shengyubenjin.multiply(serviceRate.multiply(new BigDecimal(12).divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP))));
					shengyubenjin=shengyubenjin.subtract(money.divide(new BigDecimal(years), 20, RoundingMode.HALF_UP));
				}
			}
		}else{
			if(p.getServiceRateType().equals(Constants.YEAR)){
				if(repaymentWay.equals(Constants.QUARTERLY_REPAYMENT)){
					BigDecimal serviceRatefir=serviceRate.divide(new BigDecimal(12), 20, RoundingMode.HALF_UP).divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP).multiply(new BigDecimal(3));
					firstServiceFee=money.multiply(serviceRatefir).setScale(0, RoundingMode.HALF_UP);
				}
				BigDecimal serviceRate1 = serviceRate.divide(new BigDecimal(12), 20, RoundingMode.HALF_UP).divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP).multiply(new BigDecimal(duration));
				serviceFee=money.multiply(serviceRate1).setScale(0, RoundingMode.HALF_UP);
			}else{
				if(repaymentWay.equals(Constants.QUARTERLY_REPAYMENT)){
					BigDecimal serviceRatefir=serviceRate.divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP).multiply(new BigDecimal(3));
					firstServiceFee=money.multiply(serviceRatefir).setScale(0, RoundingMode.HALF_UP);
				}
				BigDecimal serviceRate1 = serviceRate.divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP).multiply(new BigDecimal(duration));
				serviceFee=money.multiply(serviceRate1).setScale(0, RoundingMode.HALF_UP);
			}
		}
		Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
		map.put("sum", serviceFee.setScale(0,RoundingMode.HALF_UP));
		map.put("first", firstServiceFee.setScale(0,RoundingMode.HALF_UP));
		return map;
	}
	
	// 放款详情订单号
	public static String createLoanDetailOrderId() {
    	char[] codeSeq = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        Random random = new Random();
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            String r = String.valueOf(codeSeq[random.nextInt(codeSeq.length)]);//random.nextInt(10));
            s.append(r);
        }
        String number = s.toString();
        return "LD" + DateUtils.getNow(DateUtils.REGEX_YYYY_MM_DD_HH_MM_SS) + number;
    }
	
//	public static void main(String[] args) {
//		BusProduct p = new BusProduct();
//		p.setServiceRateType(new Short((short) 2));
//		p.setRepaymentWay((short)5);
//		p.setServiceRate(new BigDecimal("12"));
//		BigDecimal money=new BigDecimal("30000");
//		BigDecimal shengyubenjin=new BigDecimal(money.toString());
//		Short repaymentWay = p.getRepaymentWay();
//		BigDecimal serviceFee=new BigDecimal(0);
//		BigDecimal serviceRate = p.getServiceRate();
//		if(repaymentWay.equals(Constants.SHOUXIN_YEARS_REPAYMENT)){
//			int years= 36/12;
//			if(p.getServiceRateType().equals(Constants.YEAR)){
//				for (int i = 0; i < years; i++) {
//					serviceFee=serviceFee.add(shengyubenjin.multiply(serviceRate.divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP)));
//					shengyubenjin=shengyubenjin.subtract(money.divide(new BigDecimal(years), 20, RoundingMode.HALF_UP));
//					System.err.println("shengyubenjin"+shengyubenjin+"还款："+serviceFee);
//				}
//			}else{
//				for (int i = 0; i < years; i++) {
//					serviceFee=serviceFee.add(shengyubenjin.multiply(serviceRate.multiply(new BigDecimal(12).divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP))));
//					shengyubenjin=shengyubenjin.subtract(money.divide(new BigDecimal(years), 20, RoundingMode.HALF_UP));
//					System.err.println("shengyubenjin"+shengyubenjin+"还款："+serviceFee);
//				}
//			}
//			
//		}else{
//			if(p.getServiceRateType().equals(Constants.YEAR)){
//				BigDecimal serviceRate1 = serviceRate.divide(new BigDecimal(12), 20, RoundingMode.HALF_UP).divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP).multiply(new BigDecimal(36));
//				serviceFee=money.multiply(serviceRate1).setScale(0, RoundingMode.HALF_UP);
//			}else{
//				BigDecimal serviceRate1 = serviceRate.divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP).multiply(new BigDecimal(36));
//				serviceFee=money.multiply(serviceRate1).setScale(0, RoundingMode.HALF_UP);
//			}
//		}
//		System.err.println(serviceFee.setScale(0,RoundingMode.HALF_UP));
//	}
}
