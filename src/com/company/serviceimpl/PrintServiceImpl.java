package com.company.serviceimpl;

import com.company.checkout.Item;
import com.company.checkout.Order;
import com.company.checkout.OrderLine;
import com.company.service.PrintService;

import java.text.DecimalFormat;
import java.text.MessageFormat;

public class PrintServiceImpl implements PrintService {
    @Override
    public void printReceipt(Order order){

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

        for(OrderLine orderline : order.getOrderLines()){
            Item item = orderline.getItem();
            String itemName = (item.isImported()? "imported " : "" )+ item.getName();

            System.out.println(MessageFormat.format(
                    "{0} {1} : {2}",
                    orderline.getQuantity(), itemName, decimalFormat.format(orderline.getTotalPrice())));
        }


        System.out.println(MessageFormat.format(
                "Sales Taxes: {0}",
                decimalFormat.format(order.getTotalTax())));

        System.out.println(MessageFormat.format(
                "Total: {0}",
                decimalFormat.format(order.getOrderTotal())));
    }
}
