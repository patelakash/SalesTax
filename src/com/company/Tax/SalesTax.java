package com.company.Tax;

import java.math.BigDecimal;

public class SalesTax implements Tax{
    final static BigDecimal SALES_TAX_RATE_PERCENTAGE = new BigDecimal("0.1");
    @Override
    public BigDecimal getTaxRate() {
        return SALES_TAX_RATE_PERCENTAGE;
    }
}
