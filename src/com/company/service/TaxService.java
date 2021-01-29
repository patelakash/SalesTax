package com.company.service;

import com.company.checkout.Item;
import com.company.checkout.OrderLine;

import java.math.BigDecimal;

public interface TaxService {
    BigDecimal getTotalOrderLineTax(OrderLine orderLine, Item item);
}
