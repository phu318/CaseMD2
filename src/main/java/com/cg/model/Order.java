package com.cg.model;

import com.cg.utils.DateUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order implements IParser {
    private long id;
    private LocalDate createAt;

    private String fullName;
    private String phone;
    private Double total;

    private EStatus eStatus;

    private List<OrderDetail> orderDetails;

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public EStatus geteStatus() {
        return eStatus;
    }

    public void seteStatus(EStatus eStatus) {
        this.eStatus = eStatus;
    }

    public Order() {
        orderDetails = new ArrayList<>();
    }
    public Order(long id) {

        this.id = id;
    }

    public Order(long id, LocalDate createAt, String fullName, String phone, Double total) {
        this.id = id;
        this.createAt = createAt;
        this.fullName = fullName;
        this.phone = phone;
        this.total = total;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public void parse(String line) {
        // line: 1,Ngan99,012345678,4500000,2023-10-10
        String[] items = line.split(",");
        this.id = Long.parseLong(items[0]);
        this.fullName = items[1];
        this.phone = items[2];
        this.total = Double.parseDouble(items[3]);

        this.createAt = DateUtils.parse(items[4]);

    }

    @Override
    public String toString() {
        //2,Quang88,012345678,500000,2023-10-10
        return String.format("%s,%s,%s,%s,%s", this.id, this.fullName, this.phone, this.total, this.createAt);
    }

    public void updateTotal(){
        double total = 0;
        for(OrderDetail od : this.getOrderDetails()){
            total += od.getQuantity() * od.getPrice();
        }

        this.total = total;
    }
}
