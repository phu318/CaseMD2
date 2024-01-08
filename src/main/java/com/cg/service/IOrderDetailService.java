package com.cg.service;

import com.cg.model.Order;
import com.cg.model.OrderDetail;

import java.util.List;

public interface IOrderDetailService {
    List<OrderDetail> getAll();
    List<OrderDetail> getAllBy(long orderId);
    void saveOrderDetails(Order order);
    long findMaxCurrentId();
}
