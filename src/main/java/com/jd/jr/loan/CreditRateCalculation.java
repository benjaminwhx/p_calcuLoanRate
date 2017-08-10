package com.jd.jr.loan;

import java.util.Calendar;
import java.util.Date;

/**
 * User: 吴海旭
 * Date: 2016-11-04
 * Time: 下午-2:47
 * 信用卡分期费率计算
 */
public class CreditRateCalculation {

    /**
     * 打印出微信【微粒贷】的年化利率
     * 现在默认利率每天0.05%
     */
    @Flag(organizationName = "微粒贷")
    private static void printWXWldYearInterestRate(double rate) {
        System.out.println("微信微粒贷年化利率为: " + getYearInterestRateStr(rate));
    }

    /**
     * 打印出京东金融【金条】的年化利率
     * 现在默认利率每天0.04%
     */
    @Flag(organizationName = "金条")
    private static void printJTYearInterestRate(double rate) {
        System.out.println("京东金融金条年化利率为: " + getYearInterestRateStr(rate));
    }

	/**
     * 金条微粒贷还款计划
     * @param principal
     * @param rate
     * @param num
     */
    private static void printRepaymentPlan(double principal, double rate, int num) {
        // 每一期的本金
        double eachPrincipal = BigDecimalUtils.div(principal, num, 2);
        // 剩余本金
        double remainPrincipal = principal;
        Calendar calendar = Calendar.getInstance();

        Date before = calendar.getTime();

        for (int i = 0; i < num; ++i) {
            calendar.add(Calendar.MONTH, 1);
            Date after = calendar.getTime();
            String currentDate = DateUtils.dateToStr(after, "yyyy-MM-dd"); // 还款计划的日期
            double p;   // 需要还款的本金
            if (i == num - 1) {
                p = BigDecimalUtils.sub(principal, BigDecimalUtils.mul(eachPrincipal, num - 1));
            } else {
                p = eachPrincipal;
            }

            // 本月和下月相差的天数
            int days = DateUtils.differentDaysByMillisecond(before, after);
            // 利息 = 剩余本金 * 日利率 * 每月的天数
            double r = BigDecimalUtils.mul(remainPrincipal, BigDecimalUtils.mul(rate, days));
            // 合计
            double sum = BigDecimalUtils.add(p, r);

            System.out.println(String.format("%s 需要还款本金: %.2f, 利息: %.2f, 合计: %.2f", currentDate, p, r, sum));
            // 剩余本金
            remainPrincipal = remainPrincipal - p;
            before = after;
        }
    }

    /**
     * 打印出【银行分期】的年化利率
     * @param bankName 银行名字
     * @param rateOfMonth 月费率
     * @param num 期数
     */
    @Flag(organizationName = "银行分期")
    private static void printBankInterestRate(String bankName, double rateOfMonth, int num) {
        double result = BigDecimalUtils.div(BigDecimalUtils.mul(24, BigDecimalUtils.mul(rateOfMonth, num)), num + 1, 4);
        System.out.println(bankName + "分期年化利率为: " + getPercentageMode(result));
    }

    /**
     * 根据日利率得到年利率
     * @param dayInterestRate 日利率 例如: 1%输入0.01
     * @return
     */
    private static String getYearInterestRateStr(double dayInterestRate) {
        double yearInterestRate = BigDecimalUtils.mul(dayInterestRate, 365);
        return getPercentageMode(yearInterestRate);
    }

    private static String getPercentageMode(double num) {
        return getPercentageMode(num, 2);
    }

    private static String getPercentageMode(double num, int scale) {
        return String.format("%." + scale + "f%%", BigDecimalUtils.mul(num, 100));
    }

    public static void main(String[] args) {
        // 金条
        printJTYearInterestRate(0.00025);
        printRepaymentPlan(10000, 0.00025, 3);

        // 微粒贷
        printWXWldYearInterestRate(0.0003);
        printRepaymentPlan(10000, 0.0003, 20);

        // 银行分期
        printBankInterestRate("招商银行", 0.007, 12);
    }
}
