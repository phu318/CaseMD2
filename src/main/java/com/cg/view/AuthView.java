package com.cg.view;

import com.cg.ShopApplication;
import com.cg.model.User;
import com.cg.service.UserService;

import java.util.Scanner;

public class AuthView extends BaseView{
    private UserService userService;
    public AuthView(ShopApplication context){
        userService = new UserService();
        this.context = context;
    }
    @Override
    public void launcher() {
        login();
    }
    public void login(){
        System.out.println("Nhập tên đăng nhập: ");
        String username = scanner.nextLine();

        System.out.println("Nhập mật khẩu: ");
        String password = scanner.nextLine();

        User user = userService.checkUserNamePasswordCorrect(username, password);
        if (user!=null) {
            this.context.setUser(user);
            showMainMenu();
        }else{
//            login();
            // neu sai thi bat login lai
        }
    }
    public void showMainMenu(){
        int choice;
        boolean flag = false;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("                                            ╔═════════════════════════════════════════════════════╗");
            System.out.println("                                            ║                     MENU QUẢN LÝ                    ║");
            System.out.println("                                            ╠═════════════════════════════════════════════════════╣");
            System.out.println("                                            ║ Options:                                            ║");
            System.out.println("                                            ║ ▶ 1.Quản lý sản phẩm                                ║");
            System.out.println("                                            ║ ▶ 2.Quản lý đơn hàng                                ║");
            System.out.println("                                            ║ ▶ 3.Quản lý đơn người dùng                          ║");
            System.out.println("                                            ║ ▶ Chon chức năng                                    ║");
            System.out.println("                                            ║                                                     ║");
            System.out.println("                                            ╚═════════════════════════════════════════════════════╝");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice){
                case 1:
                    context.getProductView().launcher();
                    break;
                case 2:
                    context.getOrderView().launcher();
                    break;
                case 3:
                    context.getUserView().launcher();
                    break;
                case 4:
                    flag = true;
                    break;
                default:
                    System.out.println(" Sụ lựa chọn của bạn không hợp lệ");
            }
        }while (!flag);
    }

}
