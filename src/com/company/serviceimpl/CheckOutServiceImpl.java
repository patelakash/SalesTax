package com.company.serviceimpl;

import com.company.checkout.Item;
import com.company.checkout.Order;
import com.company.checkout.OrderLine;
import com.company.service.CheckOutService;
import com.company.utils.CalculateUtils;

import java.math.BigDecimal;
import java.util.*;

public class CheckOutServiceImpl implements CheckOutService {
    final static String IMPORTED = "imported";
    @Override
    public void processCheckOut(List<String> itemsInBasket, List<String> exemptGoods) {

        Order order = new Order();
        List<OrderLine> orderLines = generateOrderLine(itemsInBasket, exemptGoods);

        if(!orderLines.isEmpty()){
            order.setOrderLines(orderLines);
        }

        BigDecimal totalOrderTax = getOrderTotalTax(order.getOrderLines());
        order.setTotalTax(totalOrderTax);

        BigDecimal totalOrderTotal = getOrderTotal(order.getOrderLines());
        order.setOrderTotal(totalOrderTotal);

        PrintServiceImpl printServiceImpl = new PrintServiceImpl();
        printServiceImpl.printReceipt(order);

    }

    private List<OrderLine> generateOrderLine(List<String> itemsInBasket, List<String> exemptGoods){
        TaxServiceImpl taxServiceImpl = new TaxServiceImpl();
        List<OrderLine> orderLines = new ArrayList();
        for(String itemInBasket : itemsInBasket){
            OrderLine orderLine = createOrderLine(taxServiceImpl, itemInBasket, exemptGoods);
            if(orderLine != null){
                orderLines.add(orderLine);
            }
        }
        return orderLines;
    }

    private OrderLine createOrderLine(TaxServiceImpl taxServiceImpl, String itemInBasket, List<String> exemptGoods){
        OrderLine orderLine = new OrderLine();
        try {
            String qtyAndNamePrice[] = itemInBasket.split(" ", 2);
            String nameAndPrice[] = qtyAndNamePrice[1].split(" at ", 2);
            String itemName = nameAndPrice[0].trim();
            BigDecimal price = new BigDecimal(nameAndPrice[1].trim());

            String qtyInput = qtyAndNamePrice[0].trim();
            boolean qtyIsNumeric = qtyInput.chars().allMatch( Character::isDigit );

            if(!qtyIsNumeric){
                throw new Exception("Qty is not valid for item: " + itemName);
            }

            int quantity = Integer.parseInt(qtyInput);
            boolean isItemImported = isItemImported(itemName);

            Item item = new Item();
            item.setImported(isItemImported);
            itemName = itemName.replace("imported ", "");
            item.setName(itemName);
            item.setTaxExempt(isItemTaxExempt(itemName, exemptGoods));

            orderLine.setItem(item);
            orderLine.setPrice(price);
            orderLine.setQuantity(quantity);
            orderLine.setTotalPrice(price.multiply(BigDecimal.valueOf(quantity)));
            orderLine.setTotalTax(taxServiceImpl.getTotalOrderLineTax(orderLine, item));
            orderLine.setTotalPrice(orderLine.getTotalPrice().add(orderLine.getTotalTax()));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderLine;
    }

    private boolean isItemTaxExempt(String itemName, List<String> exemptGoods){
       boolean isItemTaxExempt = false;
        for(String exemptGood : exemptGoods){
            if(itemName.toLowerCase().indexOf(exemptGood.toLowerCase()) > -1){
                isItemTaxExempt = true;
                break;
            }
        }
        return isItemTaxExempt;
    }

    private boolean isItemImported(String itemName){
        return itemName.toLowerCase().indexOf(IMPORTED.toLowerCase()) > -1;
    }

    public BigDecimal getOrderTotalTax(List<OrderLine> orderLines){
        BigDecimal orderTotalTax = orderLines.stream()
                .map(ol -> ol.getTotalTax())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        orderTotalTax = CalculateUtils.getRoundedAmount(orderTotalTax);
        return orderTotalTax;
    }

    public BigDecimal getOrderTotal(List<OrderLine> orderLines){
        return orderLines.stream()
                .map(ol -> ol.getTotalPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
