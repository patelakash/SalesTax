package test;

import com.company.checkout.Item;
import com.company.checkout.OrderLine;
import com.company.serviceimpl.TaxServiceImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class CheckOutCartTest {

    @Test
    public void testCalculateItemExemptAndImportedTax(){
        //1 imported box of chocolates at 10.00
        TaxServiceImpl taxServiceImpl = new TaxServiceImpl();

        OrderLine orderLine = new OrderLine();
        Item item = new Item();
        item.setTaxExempt(true);
        item.setImported(true);
        item.setName("box of imported chocolates");

        orderLine.setItem(item);
        orderLine.setPrice(new BigDecimal("10.00"));
        orderLine.setTotalPrice(new BigDecimal("10.00"));
        orderLine.setQuantity(1);

        BigDecimal orderLineTax = taxServiceImpl.getTotalOrderLineTax(orderLine,item);
        assertEquals(orderLineTax, new BigDecimal("0.50"), "Tax calculated is not correct");
    }

    @Test
    public void testCalculateItemImportedTax(){
        //1 imported bottle of perfume at 47.50
        TaxServiceImpl taxServiceImpl = new TaxServiceImpl();

        OrderLine orderLine = new OrderLine();
        Item item = new Item();
        item.setTaxExempt(false);
        item.setImported(true);
        item.setName("imported bottle of perfume");

        orderLine.setItem(item);
        orderLine.setPrice(new BigDecimal("47.50"));
        orderLine.setTotalPrice(new BigDecimal("47.50"));
        orderLine.setQuantity(1);

        BigDecimal orderLineTax = taxServiceImpl.getTotalOrderLineTax(orderLine,item);
        assertEquals(orderLineTax, new BigDecimal("7.15"), "Tax calculated is not correct");
    }
}
