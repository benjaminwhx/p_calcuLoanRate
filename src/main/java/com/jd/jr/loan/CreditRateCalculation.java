package com.jd.jr.loan;

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
    @Flag(organizationName = "京东金融金条")
    private static void printJDJRJTYearInterestRate(double rate) {
        System.out.println("京东金融金条年化利率为: " + getYearInterestRateStr(rate));
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
        // 微粒贷
        printWXWldYearInterestRate(0.0003);
        // 金条
        printJDJRJTYearInterestRate(0.00025);

        printBankInterestRate("招商银行", 0.007, 12);
    }
}
