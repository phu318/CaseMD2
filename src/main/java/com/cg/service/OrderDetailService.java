package com.cg.service;

import com.cg.utils.Config;
import com.cg.utils.FileUtils;
import com.cg.model.Order;
import com.cg.model.OrderDetail;

import java.util.List;
import java.util.stream.Collectors;

public class OrderDetailService {
    public List<OrderDetail> getAll() {
        List<OrderDetail> orderDetails =  FileUtils.readFile(Config.PATH_FILE_ORDER_DETAIL, OrderDetail.class);
        return orderDetails;
    }
    public List<OrderDetail> getAllBy(long orderId) {
        List<OrderDetail> orderDetails =  FileUtils.readFile(Config.PATH_FILE_ORDER_DETAIL, OrderDetail.class);

        return orderDetails.stream().filter(orderDetail -> orderDetail.getOrder().getId() == orderId).collect(Collectors.toList());
    }

    public void saveOrderDetails(Order order) {
        List<OrderDetail> orderDetails = getAll();

        // Cập nhật ID cho orderDetail
        long maxId = findMaxCurrentId();
        for (int i = 0; i < order.getOrderDetails().size(); i++) {
            order.getOrderDetails().get(i).setId(maxId + i + 1);
        }

        orderDetails.addAll(order.getOrderDetails());
        FileUtils.writeFile(orderDetails, Config.PATH_FILE_ORDER_DETAIL);
    }
    public long findMaxCurrentId(){
        List<OrderDetail> orderDetails = getAll();
        orderDetails.stream().sorted((o1, o2) -> Long.compare(o1.getId(), o2.getId()));

        if (orderDetails.size() != 0) {
            return orderDetails.get(orderDetails.size() - 1).getId();
        }else{
            return 0;
        }

    }
}
