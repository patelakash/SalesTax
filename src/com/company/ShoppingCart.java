package com.company;

import com.company.serviceimpl.CheckOutServiceImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ShoppingCart {

    public static void main(String[] args) {
        try {
            if(args.length == 0 ) {
                System.out.println("Missing ItemsInCart file, please include file name.");
            }
            if(args.length == 1 ) {
                System.out.println("Missing Exempt Goods file, please include file name.");
            }
            else{
                String itemsInBasketFileName = args[0];
                String exemptGoodsFileName = args[1];
                ShoppingCart shoppingCart = new ShoppingCart();
                shoppingCart.processShoppingCart(itemsInBasketFileName, exemptGoodsFileName);
            }
        } catch (Exception e) {
            System.out.println("Error occurred while doing checkout");
        }
    }

    private void processShoppingCart(String itemsInBasketFileName, String exemptGoodsFileName) {

        List<String> itemsInBasket = new ArrayList();
        List<String> exemptGoods = new ArrayList<>();
        try (Stream<String> itemsStream = Files.lines(new File(itemsInBasketFileName).toPath());
             Stream<String> exemptGoodsStream = Files.lines(new File(exemptGoodsFileName).toPath())) {

            itemsInBasket = itemsStream.collect(Collectors.toList());
            exemptGoods = exemptGoodsStream.collect(Collectors.toList());

        } catch (IOException e) {
            System.out.println("Error occurred while reading input file:" + itemsInBasketFileName);
        }

        if(itemsInBasket == null || itemsInBasket.isEmpty()){
            System.out.println("File " + itemsInBasketFileName + " is empty. please use file with content in it.");
        }

        CheckOutServiceImpl checkOutService = new CheckOutServiceImpl();
        checkOutService.processCheckOut(itemsInBasket, exemptGoods);

    }
}
