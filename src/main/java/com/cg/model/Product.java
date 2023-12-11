package com.cg.model;

import com.cg.utils.DateUtils;

import java.time.LocalDate;
import java.util.Objects;

public class Product implements IParser {
    public static long currentID = 0;
    private long id;
    private String name;
    private String description;
    private float price;
    private User userCreated;
    private ECategory eCategory;
    private LocalDate createAt;

    public Product(long id, String name, String description, float price, User customerCreated, ECategory eCategory, LocalDate createAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.userCreated = customerCreated;
        this.eCategory = eCategory;
        this.createAt = createAt;
    }

    public Product() {

    }

    public Product(String name, String description, float price, User customerCreated, ECategory eCategory, LocalDate createAt) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.userCreated = customerCreated;
        this.eCategory = eCategory;
        this.createAt = createAt;
    }

    public Product(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s,%s,%s", this.id, this.name, this.description, this.price, this.userCreated.getId(), this.eCategory, this.createAt);

    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public User getUserCreated() {

        return userCreated;
    }

    public void setCustomerCreated(User customerCreated) {
        this.userCreated = customerCreated;
    }

    public ECategory geteCategory() {
        return eCategory;
    }

    public void seteCategory(ECategory eCategory) {
        this.eCategory = eCategory;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && Float.compare(product.price, price) == 0 && Objects.equals(name, product.name) && Objects.equals(description, product.description) && Objects.equals(userCreated.getId(), product.userCreated.getId()) && eCategory == product.eCategory && Objects.equals(createAt, product.createAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, userCreated, eCategory, createAt);
    }

    @Override
    public void parse(String line) {
        String[] items = line.split(",");
//1,Iphone 11,Iphone 11 64GB RED,1000000.0,1,APPLE,2023-10-09

        this.id = Long.parseLong(items[0]);
        this.name = items[1];
        this.description = items[2];
        this.price = Float.parseFloat(items[3]);
        long idUser = Long.parseLong(items[4]);
        this.userCreated = new User(idUser, "quocphu", "123123", ERole.USER, DateUtils.parse("2023-10-09"));        // phải qua file user lây ra

        String category = items[5];
        this.eCategory = ECategory.getBy(category);


        this.createAt = DateUtils.parse(items[6]);


    }
}

