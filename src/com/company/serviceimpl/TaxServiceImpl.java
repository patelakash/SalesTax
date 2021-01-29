package com.company.serviceimpl;

import com.company.Tax.ImportDutyTax;
import com.company.Tax.SalesTax;
import com.company.Tax.Tax;
import com.company.checkout.Item;
import com.company.checkout.OrderLine;
import com.company.service.TaxService;
import com.company.utils.CalculateUtils;

import java.math.BigDecimal;

public class TaxServiceImpl implements TaxService {

    @Override
    public BigDecimal getTotalOrderLineTax(OrderLine orderLine, Item item) {
        BigDecimal totalTax = new BigDecimal("0");
        if(!item.isTaxExempt()){
            totalTax = getSalesTax(orderLine);
        }
        if(item.isImported()){
            totalTax = totalTax.add(getImportDutyTax(orderLine));
        }

        totalTax =  CalculateUtils.getRoundedAmount(totalTax);
        return totalTax;
    }

    private BigDecimal getSalesTax(OrderLine orderLine) {
        Tax salesTax = new SalesTax();
        return orderLine.getTotalPrice().multiply(salesTax.getTaxRate());
    }

    private BigDecimal getImportDutyTax(OrderLine orderLine) {
        Tax importDutyTax = new ImportDutyTax();
        return orderLine.getTotalPrice().multiply(importDutyTax.getTaxRate());
    }


}
