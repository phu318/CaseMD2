package com.cg;

import com.cg.model.ERole;
import com.cg.model.User;
import com.cg.utils.DateUtils;
import com.cg.view.AuthView;
import com.cg.view.OrderView;
import com.cg.view.ProductView;
import com.cg.view.UserView;

import java.util.Scanner;

//package view;
//
public class ShopApplication {
    private ProductView productView;
    private UserView userView;
    private OrderView orderView;
    private AuthView authView;
    private User user;


    public ShopApplication() {
        productView = new ProductView(this);
        userView = new UserView(this);
        orderView = new OrderView(this);
        authView = new AuthView(this);
        user = new User(1, "quocphu", "123123", ERole.ADMIN, DateUtils.parse("2023-10-09"));

    }


    public static void main(String[] args) {
        ShopApplication shopApplication = new ShopApplication();
        shopApplication.run();
    }

    public void run() {
        boolean flag = false;
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("                                            ╔═════════════════════════════════════════════════════╗");
            System.out.println("                                            ║                  CỬA HÀNG ĐIỆN THOẠI                ║");
            System.out.println("                                            ╠═════════════════════════════════════════════════════╣");
            System.out.println("                                            ║ Options:                                            ║");
            System.out.println("                                            ║ ▶ 1.Đăng nhập                                       ║");
            System.out.println("                                            ║ ▶ 0.Thoát                                           ║");
            System.out.println("                                            ║ ▶ Chọn chức năng                                    ║");
            System.out.println("                                            ╚═════════════════════════════════════════════════════╝");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        authView.login();
                        break;
                    case 0:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Nhập sai !!! Vui lòng nhập lại !!!");
                }
            }catch (Exception e){
                System.out.println("Nhập sai cú pháp . Vui lòng đăng nhập lại (chỉ chọn các số có ở menu)");
            }
        }while (!flag);

    }

    public AuthView getAuthView() {
        return authView;
    }

    public void setAuthView(AuthView authView) {
        this.authView = authView;
    }

    public ProductView getProductView() {
        return productView;
    }

    public void setProductView(ProductView productView) {
        this.productView = productView;
    }

    public UserView getUserView() {
        return userView;
    }

    public void setUserView(UserView userView) {
        this.userView = userView;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OrderView getOrderView() {
        return orderView;
    }

    public void setOrderView(OrderView orderView) {
        this.orderView = orderView;
    }
}
