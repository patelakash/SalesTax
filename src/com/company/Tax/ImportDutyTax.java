package com.company.Tax;

import java.math.BigDecimal;

public class ImportDutyTax implements Tax{
    final static BigDecimal IMPORT_DUTY_TAX_RATE_PERCENTAGE = new BigDecimal("0.05");
    @Override
    public BigDecimal getTaxRate() {
        return IMPORT_DUTY_TAX_RATE_PERCENTAGE;
    }
}
