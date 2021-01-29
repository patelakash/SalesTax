package com.company.checkout;

import java.math.BigDecimal;

public class Item {
    private String name;
    private boolean imported;
    private boolean isTaxExempt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isImported() {
        return imported;
    }

    public void setImported(boolean imported) {
        this.imported = imported;
    }

    public boolean isTaxExempt() {
        return isTaxExempt;
    }

    public void setTaxExempt(boolean taxExempt) {
        isTaxExempt = taxExempt;
    }

}
