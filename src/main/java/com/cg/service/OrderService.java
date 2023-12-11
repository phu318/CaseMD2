package com.cg.service;
import com.cg.utils.Config;
import com.cg.utils.FileUtils;
import com.cg.model.Order;

import java.util.List;

public class OrderService {
    private OrderDetailService orderDetailService;
    public OrderService(){
        this.orderDetailService = new OrderDetailService();
    }

    public List<Order> getAll() {
        List<Order> orders =  FileUtils.readFile(Config.PATH_FILE_ORDER, Order.class);

        orders.stream().forEach(order -> {
            order.setOrderDetails(orderDetailService.getAllBy(order.getId()));
        });
        return orders;
    }


    public long findMaxCurrentId(){
        List<Order> orders = getAll();
        orders.stream().sorted((o1, o2) -> Long.compare(o1.getId(), o2.getId()));

        if (orders.size() != 0) {
            return orders.get(orders.size() - 1).getId();
        }else{
            return 0;
        }

    }

    public void saveOrder(Order order) {
        List<Order> orders = getAll();
        orders.add(order);
        FileUtils.writeFile(orders, Config.PATH_FILE_ORDER);


        // lưu order detail
        //3,2,1,5,900000
        orderDetailService.saveOrderDetails(order);

    }
}
