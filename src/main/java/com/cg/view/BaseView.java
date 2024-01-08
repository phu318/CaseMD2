package com.cg.view;

import com.cg.ShopApplication;
import com.cg.model.Product;
import com.cg.model.User;
import com.cg.service.*;

import java.util.NoSuchElementException;
import java.util.Scanner;

public abstract class BaseView {
    protected Scanner scanner = new Scanner(System.in);
    protected ShopApplication context;
    protected IProductService productService;
    protected IUserService userService;
    protected IOrderService orderService;
    protected IOrderDetailService orderDetailService;


    public BaseView() {
        productService = ProductService.getInstance();
        userService = UserService.getInstance();
        orderDetailService = OrderDetailService.getInstance();
        orderService = OrderService.getInstance();
    }

    public Product inputProductId(String title) {
        this.context.getProductView().showProducts();
        System.out.println(title);
        Product pEdit = null;
        try {
            long idProduct = Long.parseLong(scanner.nextLine());
            pEdit = productService.findBy(idProduct);

        } catch (NumberFormatException numberFormatException) {
            System.out.println("ID không hop le. Vui lòng nhập lại");
            inputProductId(title);
        } catch (NoSuchElementException noSuchElementException) {
            System.out.println("Không tìm thấy ID sản phẩm. Vui lòng nhập lại");
            inputProductId(title);
        }
        return pEdit;
    }
    public User inputUserId(String title){
        this.context.getUserView().showUsers();
        System.out.println(title);
        User uEdit = null;
        try {
            long idUser = Long.parseLong(scanner.nextLine());
            uEdit = userService.findBy(idUser);
        }catch (NumberFormatException numberFormatException){
            System.out.println("ID không hop le. Vui lòng nhập lại");
            inputUserId(title);
        }catch (NoSuchElementException noSuchElementException){
            System.out.println("Không tìm thấy ID sản phẩm. Vui lòng nhập lại");
            inputUserId(title);
        }
        return uEdit;
    }

    public abstract void launcher();


    public int getNumberMinMax(String str, int min, int max) throws IndexOutOfBoundsException {
        System.out.println(str);
        int num;
        try {
            num = Integer.parseInt(scanner.nextLine());
            if(num < min || num > max) {
                System.err.println("Chọn từ khoảng " + min + " và " + max);
                return getNumberMinMax(str,min,max);
            }
            return num;
        } catch (Exception e) {
            System.err.println("Không đúng định dạng");
            return getNumberMinMax(str,min,max);
        }
    }
}
