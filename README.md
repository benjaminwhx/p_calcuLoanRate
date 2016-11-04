# calcuLoanRate
方便计算贷款的年利率，包括市面上主流的贷款软件以及银行

## 微粒贷
微信推出的借贷功能，日利率0.05%，可以提前还款

## 51人品贷
51信用卡管家自带的贷款功能，它的计算方式比较特别，因为加上了合同金额这个概念, `合同金额 = 原贷款金额+5%的原贷款金额`
```
每期应还 = 合同金额/期数 + 合同金额*月费率
```

## 中信圆梦金
某一天接到中信银行客服打来的电话，说有福利送给我，我以为多么大的福利呢，原来是贷款金不占固额，但是利率很高，月利率7.5%，不要以为年华就是9%了，因为你的本金每个月在减少，而你还是以原始本金计算的利息

为了以后方便计算利息，特地写了个程序来计算。
