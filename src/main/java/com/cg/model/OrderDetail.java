package com.cg.model;

public class OrderDetail implements IParser {
    private long id;
    private int quantity;
    private float price;

    private Product product;
    private Order order;

    public OrderDetail(long id, int quantity, float price, Product product, Order order) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.product = product;
        this.order = order;
    }

    public OrderDetail() {
    }

    public OrderDetail( int quantity, float price, Product product, Order order) {
        this.quantity = quantity;
        this.price = price;
        this.product = product;
        this.order = order;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public void parse(String line) {
        // line:1,1,1,2,1000000
        String[] items = line.split(",");
        this.id = Long.parseLong(items[0]);
        this.order = new Order(Long.parseLong(items[1]));
        this.product = new Product(Long.parseLong(items[2]));
        this.quantity = Integer.parseInt(items[3]);
        this.price = Float.parseFloat(items[4]);

    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s", this.id, this.order.getId(), this.product.getId(), this.quantity, this.price);
    }
}
