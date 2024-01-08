package com.cg.service;

import com.cg.model.Product;

import java.util.List;

public interface IProductService {
    List<Product> getAll();
    void init();
    void setCurrentId();
    void addProduct(Product pNew);
    List<Product> searchProduct(String kw);
    Product findBy(long id);
    void updateProduct(long id, Product productEdit);
    boolean removeProduct(long id);
}
