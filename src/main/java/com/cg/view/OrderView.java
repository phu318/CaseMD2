package com.cg.view;

import com.cg.ShopApplication;
import com.cg.model.EStatus;
import com.cg.model.Order;
import com.cg.model.OrderDetail;
import com.cg.model.Product;
import com.cg.service.OrderService;
import com.cg.service.ProductService;
import com.cg.utils.InputUtils;
import com.cg.utils.ValidateUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class OrderView extends BaseView {
    public OrderView(ShopApplication context) {
        orderService = new OrderService();
        productService = new ProductService();
        this.context = context;
    }

    private OrderService orderService;
    private ProductService productService;


    public void launcher() {
        int choice;
        boolean flag = false;
        do {
            System.out.println("                                            ╔═════════════════════════════════════════════════════╗");
            System.out.println("                                            ║                    QUẢN LÝ ĐƠN HÀNG                 ║");
            System.out.println("                                            ╠═════════════════════════════════════════════════════╣");
            System.out.println("                                            ║ Options:                                            ║");
            System.out.println("                                            ║ ▶ 1.Xem danh sách hóa đơn                           ║");
            System.out.println("                                            ║ ▶ 2.Tạo đơn hàng                                    ║");
            System.out.println("                                            ║ ▶ 3.Cập nhật đơn hàng                               ║");
            System.out.println("                                            ║ ▶ 4.Kiểm tra doanh thu tháng                        ║");
            System.out.println("                                            ║ ▶ 5.Kiểm tra doanh thu theo ngày                    ║");
            System.out.println("                                            ║ ▶ 6.Độ tăng trưởng của doanh thu theo tháng         ║");
            System.out.println("                                            ║ ▶ 7.Quay lại menu                                   ║");
            System.out.println("                                            ║ ▶ Chon chức năng                                    ║");
            System.out.println("                                            ╚═════════════════════════════════════════════════════╝");

            System.out.println("Mời nhập");
            int actionMenu = Integer.parseInt(scanner.nextLine());
            switch (actionMenu) {
                case 1: {
                    showOrders();
                    break;
                }
                case 2: {
                    createOrder();
                    break;
                }
                case 3: {
//                    updateProductQuantityInOrder(product,);
                    break;
                }
                case 4: {
                    showRevenue();
                    break;
                }
                case 5: {
                    showRevenueOfDay();
                    break;
                }
                case 6 : {
                    showGrowthRate();
                    break;
                }
                case 7: {
                    flag = true;
                    this.context.getAuthView().showMainMenu();
                    break;
                }
                default:
                    System.out.println("Vui lòng nhập lại !!!");
            }
        } while (!flag);

    }


    private void createOrder() {


        System.out.println("");
        Order order = new Order();
        order.setId(orderService.findMaxCurrentId() + 1);

        boolean flag = false;
        do {
            Product product = inputProductId("Nhập mã sản phẩm:");

            System.out.println("Nhập số lượng");
            int quantity = Integer.parseInt(scanner.nextLine());

            OrderDetail orderDetail = new OrderDetail(quantity, product.getPrice(), product, order);

            if (!isProductExitsInOrder(product, order)) {
                order.getOrderDetails().add(orderDetail);
            } else {
                updateProductQuantityInOrder(product, quantity, order);
            }
            int choice = getNumberMinMax(" Chọn 1 để mua tiếp\n Chọn 2 để thanh toán", 1, 2);
            switch (choice) {
                case 1:
                    flag = true;
                    break;
                case 2:
                    flag = false;
            }
        } while (flag);
        String fullName = InputUtils.inputString("Nhập tên khách hàng: ", ValidateUtils.USERNAME_REGEX, "Tên khách hàng không hợp lệ");
        String phone = InputUtils.inputString("Nhập số điện thoại khách hàng: ", ValidateUtils.PHONE_REGEX, "Số điện thoại không hợp lệ");

        order.setFullName(fullName);
        order.setPhone(phone);
        order.seteStatus(EStatus.PAYED);
        order.setCreateAt(LocalDate.now());
        order.updateTotal();

        orderService.saveOrder(order);

        showOrders();
    }

    private void updateProductQuantityInOrder(Product product, int quantity, Order order) {
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            if (orderDetail.getProduct().getId() == product.getId()) {
                orderDetail.setQuantity(quantity + orderDetail.getQuantity());
            }
        }
    }

    private boolean isProductExitsInOrder(Product product, Order order) {
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            if (orderDetail.getProduct().getId() == product.getId()) {
                return true;
            }
        }
        return false;
    }

    private void calculateRevenue(int month,int yeah) {
        LocalDate startDate = LocalDate.of(yeah, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        System.out.println("Doanh thu của tháng này là : " + orderService.totalOrder(startDate, endDate));
    }

    private void showRevenue() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập tháng bạn muốn kiểm tra doanh thu: ");
        int month = scanner.nextInt();
        System.out.println("Nhập năm : ");
        int yeah = scanner.nextInt();
        if (month < 1 || month > 12) {
            System.out.println("Tháng không hợp lệ !!!");
            return;
        }
        calculateRevenue(month,yeah);
    }

    private void caculateRevenueOfDay(int day, int month, int year) {
        LocalDate date = LocalDate.of(year, month, day);
        System.out.println("Doanh thu ngày " + date + " : " + orderService.totalOrderOfDay(date));
    }

    private void showRevenueOfDay() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập ngày bạn muốn kiểm doanh thu : ");
        int day = scanner.nextInt();
        System.out.println("Nhập tháng : ");
        int month = scanner.nextInt();
        System.out.println("Nhập năm :");
        int year = scanner.nextInt();
        if (day < 1 || day > 31 || month < 1 || month > 12) {
            System.out.println("Ngày tháng không hợp lệ !!!");
            return;
        }
        caculateRevenueOfDay(day, month, year);
    }

    private void caculateGrowthRate(int thisMonth, int compareMonth,int yeah,int yeah1) {
        LocalDate date = LocalDate.of(yeah, thisMonth, 1);
        LocalDate date1 = LocalDate.of(yeah1, compareMonth, 1);
        double growthRate = orderService.calculateGrowthPercentage(date, date1);
        if (growthRate > 0) {
            System.out.println("Tăng trưởng: " + growthRate + "%");
        } else if (growthRate < 0) {
            System.out.println("Giảm: " + Math.abs(growthRate) + "%");
        } else {
            System.out.println("Không có sự thay đổi so với tháng trước");
        }

    }

    private void showGrowthRate() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập tháng bạn muốn kiểm tra : ");
        int thisMonth = scanner.nextInt();
        System.out.println("Nhập năm : ");
        int yeah = scanner.nextInt();
        System.out.println("Nhập tháng bạn muốn so sánh : ");
        int compareMonth = scanner.nextInt();
        System.out.println("Nhập năm : ");
        int yeah1 = scanner.nextInt();
        if (thisMonth < 1 || thisMonth > 12 || compareMonth < 1 || compareMonth > 12 || yeah < 0|| yeah1 < 0) {
            System.out.println("Tháng không hợp lệ !!!");
            return;
        }
        caculateGrowthRate(thisMonth,compareMonth,yeah,yeah1);
    }


    private void showOrders() {
        List<Order> orders = orderService.getAll();
        System.out.printf("%-10s |%-10s |%-20s |%-30s \n ", "ID", "Họ tên", "Tổng tiền", "Ngày đặt");
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            System.out.printf("%-10s|%-10s |%-20s |%-30s \n ", order.getId(), order.getFullName(), order.getTotal(), order.getCreateAt());
            System.out.println("---------------------------------------------------------------");
            System.out.printf("%-10s |%-10s |%-20s \n", "Tên Sản phẩm", "Số lượng", "Giá");
            for (int j = 0; j < order.getOrderDetails().size(); j++) {
                OrderDetail orderDetail = order.getOrderDetails().get(j);
                Product product = productService.findBy(orderDetail.getProduct().getId());
                System.out.printf("-%11s |%-10s |%-20s \n", product.getName(), orderDetail.getQuantity(), orderDetail.getPrice());
            }
            System.out.println();
            System.out.println();
        }

    }


}
