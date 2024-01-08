package com.cg.service;

import com.cg.model.Order;

import java.time.LocalDate;
import java.util.List;

public interface IOrderService {
    List<Order> getAll();
    long findMaxCurrentId();
    void saveOrder(Order order);
    List<Order> getOrders(LocalDate starDate, LocalDate endDate);
    double totalOrder(LocalDate starDate, LocalDate endDate);
    List<Order> getOrderOfDay(LocalDate date);
    double totalOrderOfDay(LocalDate date);
    double calculateGrowthPercentage(LocalDate thisMonth, LocalDate compareMonth);
}
