package com.company.service;

import java.util.List;

public interface CheckOutService {
    void processCheckOut(List<String> exemptGoods, List<String> itemsInbasket);
}
