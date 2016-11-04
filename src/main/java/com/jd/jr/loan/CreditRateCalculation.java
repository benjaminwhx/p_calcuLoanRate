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
    private static void printWXWldYearInterestRate() {
        System.out.println("微信微粒贷年化利率为: " + getYearInterestRateStr(0.0005));
    }

    /**
     * 打印出【中信圆梦金】的年化利率
     * @param principal 本金
     * @param period 期数
     * @param rateOfMonth 月费率
     */
    @Flag(organizationName = "中信圆梦金")
    private static void printZSYMJYearInterestRate(long principal, int period, double rateOfMonth) {
        System.out.println("中信圆梦金年化利率为: " + getPercentageMode(getInterestRate(principal, period, rateOfMonth)));
    }

    /**
     * 打印出【51人品贷】的年化利率
     * 它的计算方式比较特别，因为加上了合同金额这个概念,合同金额 = 原贷款金额+5%的原贷款金额
     * 然后每期应还 = 合同金额/期数 + 合同金额*月费率
     * @param principal 本金
     * @param period 期数
     * @param rateOfMonth 月费率
     */
    @Flag(organizationName = "51人品贷")
    private static void print51RPDYearInterestRate(long principal, int period, double rateOfMonth) {
        long preCharge = (long)(principal * 0.05);  // 前期费用5%
        long contractPrincipal = principal + preCharge;    // 合同金额，按这个算本金
        double monthRepaymentOfPrincipal = contractPrincipal / period;  // 每月需要偿还的本金
        double monthRepaymentOfInterest = BigDecimalUtils.mul(contractPrincipal, rateOfMonth);  // 每月需要偿还的利息
        double yearRepymentOfInterest = BigDecimalUtils.mul(monthRepaymentOfInterest, 12);  // 每年需要偿还的利息
        double totalPrincipal = 0.0;
        for (int i = 0; i < period; ++i) {
            // 每月剩余的本金 = 总本金 - 第几期 * (每月应该偿还的本金+利息)
            double a = principal - i * (monthRepaymentOfPrincipal + monthRepaymentOfInterest);
//            System.out.println(String.format("第%d个月剩余本金为: %.2f", (i + 1), a));
            totalPrincipal += a;
        }
        double yearInterestRate = BigDecimalUtils.div(BigDecimalUtils.mul(yearRepymentOfInterest, 12), totalPrincipal, 4);
        System.out.println("51人品贷年化利率为: " + getPercentageMode(yearInterestRate));
    }

    /**
     * 适用于计算利息提前算好，每月还所还本金+利息，每月利息相同的情况
     * @param principal
     * @param period
     * @param rateOfMonth
     * @return
     */
    private static double getInterestRate(long principal, int period, double rateOfMonth) {
        double monthRepaymentOfPrincipal = principal / period;  // 每月需要偿还的本金
        double monthRepaymentOfInterest = BigDecimalUtils.mul(principal, rateOfMonth);  // 每月需要偿还的利息
        double yearRepymentOfInterest = BigDecimalUtils.mul(monthRepaymentOfInterest, 12);  // 每年需要偿还的利息
        double totalPrincipal = 0.0;
        for (int i = 0; i < period; ++i) {
            // 每月剩余的本金 = 总本金 - 第几期 * (每月应该偿还的本金+利息)
            double a = principal - i * (monthRepaymentOfPrincipal + monthRepaymentOfInterest);
//            System.out.println(String.format("第%d个月剩余本金为: %.2f", (i + 1), a));
            totalPrincipal += a;
        }
        // 如果选用类似借贷宝的模式，就是借款期限到了连本带息一起付
        // 那么计算公式就应该是: 每月剩下的本金(a)*月利率(b) = 每月需要付的利息(c)
        // 如果把12个月的c相加等于现在需要付的利息，那么b就能够反算出来
        double yearInterestRate = BigDecimalUtils.div(BigDecimalUtils.mul(yearRepymentOfInterest, 12), totalPrincipal, 4);
        return yearInterestRate;
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

    /**
     * 根据年利率得到日利率
     * @param yearInterestRate 年利率 例如: 1%输入0.01
     * @return
     */
    private static String getDayInterestRateStr(double yearInterestRate) {
        double dayInterestRate = BigDecimalUtils.div(yearInterestRate, 365, 10);
        return getPercentageMode(dayInterestRate);
    }

    private static String getPercentageMode(double num) {
        return getPercentageMode(num, 2);
    }

    private static String getPercentageMode(double num, int scale) {
        return String.format("%." + scale + "f%%", BigDecimalUtils.mul(num, 100));
    }

    public static void main(String[] args) {
        printWXWldYearInterestRate();
        printZSYMJYearInterestRate(12000, 12, 0.0075);
        print51RPDYearInterestRate(12000, 12, 0.0065);
    }
}
