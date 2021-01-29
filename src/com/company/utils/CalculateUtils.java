package com.company.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculateUtils {
    final static BigDecimal ROUND_UPTO = new BigDecimal("0.05");
    public static BigDecimal getRoundedAmount(BigDecimal amount){
        BigDecimal calculatedAmount = amount;
        BigDecimal roundHelper = calculatedAmount.divide(ROUND_UPTO, 0, RoundingMode.UP);
        calculatedAmount = roundHelper.multiply(ROUND_UPTO);
        calculatedAmount = calculatedAmount.setScale(2, RoundingMode.UP);

        return calculatedAmount;
    }
}
