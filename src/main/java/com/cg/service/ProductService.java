package com.cg.service;

import com.cg.model.Product;
import com.cg.model.ERole;
import com.cg.model.User;
import com.cg.model.ECategory;
import com.cg.utils.Config;
import com.cg.utils.DateUtils;
import com.cg.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

public class ProductService implements IProductService {
    private static ProductService productService = null;
    private ProductService(){

    }
    public static ProductService getInstance(){
        if (productService == null) {
            productService = new ProductService();
        }
        return productService;
    }
    public List<Product> getAll() {
        return FileUtils.readFile(Config.PATH_FILE_PRODUCT, Product.class);
    }

    public void init() {
        List<Product> products = new ArrayList<>();
        User user = new User(1L, "quocphu", "123123", ERole.ADMIN, DateUtils.parse("2023-10-09"));

        products.add(new Product(++Product.currentID, "Iphone 11", "Iphone 11 64GB RED", 1000000f,
                user, ECategory.APPLE, DateUtils.parse("2023-10-09")));
        products.add(new Product(++Product.currentID, "Iphone 12", "Iphone 11 64GB RED", 1000000f,
                user, ECategory.APPLE, DateUtils.parse("2023-10-09")));
        products.add(new Product(++Product.currentID, "Iphone 13", "Iphone 11 64GB RED", 1000000f,
                user, ECategory.APPLE, DateUtils.parse("2023-10-09")));

        FileUtils.writeFile(products, Config.PATH_FILE_PRODUCT);

    }
    public void setCurrentId(){
        List<Product> products = getAll();

        products.sort((o1, o2) -> Long.compare(o1.getId(), o2.getId()));
        Product.currentID = products.get(products.size()-1).getId();
    }

    public void addProduct(Product pNew) {
        List<Product> products = getAll();
        pNew.setId(++Product.currentID);
        products.add(pNew);

        FileUtils.writeFile(products, Config.PATH_FILE_PRODUCT);
    }

    public List<Product> searchProduct(String kw) {         // 1
        List<Product> products = getAll();

        List<Product> results = new ArrayList<>();
        for (int i = 0; i<  products.size(); i++) {
            if (products.get(i).getName().toLowerCase().contains(kw.toLowerCase()) ||
                    products.get(i).geteCategory().getName().toLowerCase().contains(kw.toLowerCase())) {
                results.add(products.get(i));
            }
        }
//        products.stream().filter(product -> product.getName().toLowerCase().contains(kw.toLowerCase()) ||
//                product.geteCategory().getName().toLowerCase().contains(kw.toLowerCase()));

        return results;
    }

    public Product findBy(long id) {
        List<Product> products = getAll();
        return products.stream().filter(product -> product.getId() == id).findFirst().get();
    }

    public void updateProduct(long id, Product productEdit) {
        List<Product> products = getAll();
        for (int i = 0 ; i < products.size(); i++) {
            if (products.get(i).getId() == id) {
                products.set(i, productEdit);
            }
        }
        FileUtils.writeFile(products, Config.PATH_FILE_PRODUCT);
    }


    public boolean removeProduct(long id) {
        List<Product> products = getAll();
        boolean result =  products.removeIf(product -> product.getId() == id);

        FileUtils.writeFile(products, Config.PATH_FILE_PRODUCT);
        return result;
    }

}

