package com.cg.view;

import com.cg.ShopApplication;
import com.cg.model.ERole;
import com.cg.model.User;
import com.cg.service.UserService;
import com.cg.utils.Config;
import com.cg.utils.FileUtils;
import com.cg.utils.InputUtils;
import com.cg.utils.ValidateUtils;

import java.time.LocalDate;
import java.util.List;

public class UserView extends BaseView{
    private UserService userService;
    public UserView(ShopApplication shopApplication){
        userService = new UserService();
        this.context = shopApplication;

        if (!FileUtils.checkFileExits(Config.PATH_FILE_USER)) {
            userService.init();
        }else{
            userService.setCurrentId();
        }

    }
    public void launcher() {
int choice;
boolean flag = false;
do {
    System.out.println("                                            ╔═════════════════════════════════════════════════════╗");
    System.out.println("                                            ║                   QUẢN LÝ NGƯỜI DÙNG                ║");
    System.out.println("                                            ╠═════════════════════════════════════════════════════╣");
    System.out.println("                                            ║ Options:                                            ║");
    System.out.println("                                            ║ ▶ 1.Xem danh sách                                   ║");
    System.out.println("                                            ║ ▶ 2.Thêm người dùng                                 ║");
    System.out.println("                                            ║ ▶ 3.Sửa người dùng                                  ║");
    System.out.println("                                            ║ ▶ 4.Xóa người dùng                                  ║");
    System.out.println("                                            ║ ▶ 5.Sắp xếp người dùng                              ║");
    System.out.println("                                            ║ ▶ 6.Tìm kiếm người dùng                             ║");
    System.out.println("                                            ║ ▶ 7.Quay lại menu                                   ║");
    System.out.println("                                            ║ ▶ Chọn chức năng                                    ║");
    System.out.println("                                            ║                                                     ║");
    System.out.println("                                            ╚═════════════════════════════════════════════════════╝");

    int actionMenu = Integer.parseInt(scanner.nextLine());
    switch (actionMenu){
        case 1 :{
            showUsers();
            break;
        }
        case 2:{
            insertUser();
            break;
        }
        case 3:{
//            deleteUser();
            break;
        }
        case 4:{
            deleteUser();
            break;
        }
        case 5:{
            sortUserByName();
            break;
        }
        case 6:{
            searchUser();
            break;
        }
        case 7:{
            flag =true;
            this.context.getAuthView().showMainMenu();
        }
        default:
            System.out.println("Vui lòng nhập lại");
    }
}while (!flag);

    }

    private void insertUser() {
        if (context.getUser() != null && context.getUser().geteRole().equals(ERole.ADMIN)) {
            String name = InputUtils.inputString("Nhập tên: ", ValidateUtils.USERNAME_REGEX, "Tên không hợp lệ (phải từ 8-20 kí tự)");
            String password = "123123";

            User user = new User(name, password, ERole.USER, LocalDate.now());
            userService.addUser(user);
            showUsers();
        }
    }
    private void deleteUser(){
User user = inputUserId("Nhập người dùng cần xóa");
boolean result = userService.removeUser(user.getId());
if (result= true){
    System.out.println("Xóa thành công");
    showUsers();
}
    }
    private void searchUser(){
        System.out.println("Nhập thông tin muốn tìm kiếm (theo tên sản phẩm ) ");
        String kw = scanner.nextLine();
        List<User> users = userService.searchUser(kw);
        showUsers(users);
    }

    private void sortUser(){
        System.out.println("Sắp xếp theo");
        System.out.println("1. Sắp theo tên");
        int choice = getNumberMinMax("Mời nhập",1,2);
        switch (choice){
            case 1: {
                sortUserByName();
                break;
            }
        }
    }
    private void sortUserByName(){
        List<User> users = userService.getAll();
        users.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
        showUsers(users);
    }

    public void showUsers() {
        List<User> users = userService.getAll();
        System.out.printf("%10s | %20s | %30s | %15s | %10s\n", "Id" , "Name", "Password","Role", "Create at");
        for(User user : users ){
            System.out.printf("%10s | %20s | %30s | %15s | %10s\n",
                    user.getId(),user.getName(),user.getPassword(),user.geteRole(),user.getCreateAt());
        }
    }
    public void showUsers(List<User> users) {
        System.out.printf("%10s | %20s | %30s | %15s | %10s\n", "Id" , "Name", "Password","Role", "Create at");
        for(User user : users ){
            System.out.printf("%10s | %20s | %30s | %15s | %10s\n",
                    user.getId(),user.getName(),user.getPassword(),user.geteRole(),user.getCreateAt());
        }
    }

}
