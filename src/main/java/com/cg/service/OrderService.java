package com.cg.service;

import com.cg.utils.Config;
import com.cg.utils.FileUtils;
import com.cg.model.Order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderService implements IOrderService {
    private static OrderService orderService = null;

    public static OrderService getInstance() {
        if (orderService == null) {
            orderService = new OrderService();
        }
        return orderService;
    }

    private OrderDetailService orderDetailService;

    private OrderService() {
        this.orderDetailService = OrderDetailService.getInstance();
    }

    public List<Order> getAll() {
        List<Order> orders = FileUtils.readFile(Config.PATH_FILE_ORDER, Order.class);

        orders.stream().forEach(order -> {
            order.setOrderDetails(orderDetailService.getAllBy(order.getId()));
        });
        return orders;
    }


    public long findMaxCurrentId() {
        List<Order> orders = getAll();
        orders.stream().sorted((o1, o2) -> Long.compare(o1.getId(), o2.getId()));

        if (orders.size() != 0) {
            return orders.get(orders.size() - 1).getId();
        } else {
            return 0;
        }

    }

    public void saveOrder(Order order) {
        List<Order> orders = getAll();
        orders.add(order);
        FileUtils.writeFile(orders, Config.PATH_FILE_ORDER);
        orderDetailService.saveOrderDetails(order);

    }

    public List<Order> getOrders(LocalDate starDate, LocalDate endDate) {
        List<Order> fillorders = getAll();

        List<Order> resuls = new ArrayList<>();
        for (Order ord : fillorders) {
            LocalDate creatAt = ord.getCreateAt();
            if (creatAt.isBefore(endDate) && creatAt.isAfter(starDate)) {
                resuls.add(ord);
            }
        }
        return resuls;
    }


    public double totalOrder(LocalDate starDate, LocalDate endDate) {
        double total = 0.0;
        List<Order> orders = getOrders(starDate, endDate);
        for (Order order : orders) {
            total += order.getTotal();
        }
        return total;
    }

    public List<Order> getOrderOfDay(LocalDate date) {
        List<Order> orders = getAll();
        List<Order> result = new ArrayList<>();
        for (Order order : orders) {
            if (order.getCreateAt().equals(date)) {
                result.add(order);
            }
        }
        return result;
    }


    public double totalOrderOfDay(LocalDate date) {
        List<Order> orders = getOrderOfDay(date);
        double total = 0.0;
        for (Order order : orders) {
            total += order.getTotal();
        }
        return total;
    }

    public double calculateGrowthPercentage(LocalDate thisMonth, LocalDate compareMonth) {
        double thisMonthTotal = totalOrder(thisMonth, thisMonth.plusMonths(1).minusDays(1));
        double compareMonthTotal = totalOrder(compareMonth, compareMonth.plusMonths(1).minusDays(1));
        if (compareMonthTotal != 0) {
            return ((thisMonthTotal - compareMonthTotal) / compareMonthTotal) * 100;
        } else {
            return 0;
        }

    }


}
