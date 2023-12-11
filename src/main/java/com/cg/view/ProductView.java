package com.cg.view;

import com.cg.ShopApplication;
import com.cg.model.ECategory;
import com.cg.model.ERole;
import com.cg.model.Product;
import com.cg.model.User;
import com.cg.service.ProductService;
import com.cg.utils.Config;
import com.cg.utils.FileUtils;
import com.cg.utils.InputUtils;
import com.cg.utils.ValidateUtils;

import java.time.LocalDate;
import java.util.List;

public class ProductView extends BaseView{

    public ProductView(ShopApplication shopApplication) {
        this.context = shopApplication;
        productService = new ProductService();
        if (!FileUtils.checkFileExits(Config.PATH_FILE_PRODUCT)) {
            productService.init();
        }else{
            productService.setCurrentId();
        }
    }

    public void launcher() {

        int actionMenu;
        boolean flag = false;
        do {

            System.out.println("                                            ╔═════════════════════════════════════════════════════╗");
            System.out.println("                                            ║                   QUẢN LÝ SẢN PHẨM                  ║");
            System.out.println("                                            ╠═════════════════════════════════════════════════════╣");
            System.out.println("                                            ║ Options:                                            ║");
            System.out.println("                                            ║ ▶ 1.Xem sản phẩm                                    ║");
            System.out.println("                                            ║ ▶ 2.Thêm sản phẩm                                   ║");
            System.out.println("                                            ║ ▶ 3.Sửa sản phẩm                                    ║");
            System.out.println("                                            ║ ▶ 4.Xóa sản phẩm                                    ║");
            System.out.println("                                            ║ ▶ 5.Sắp xếp sản phẩm                                ║");
            System.out.println("                                            ║ ▶ 6.Tìm sản phẩm                                    ║");
            System.out.println("                                            ║ ▶ 7.Quay lại menu                                   ║");
            System.out.println("                                            ║ ▶ Chọn chức năng                                    ║");
            System.out.println("                                            ║                                                     ║");
            System.out.println("                                            ╚═════════════════════════════════════════════════════╝");
             actionMenu = Integer.parseInt(scanner.nextLine());
            switch (actionMenu) {
                case 1: {
                    showProducts();
                    break;
                }
                case 2: {
                    createProduct();
                    break;
                }
                case 3: {
                    editProduct();
                    break;
                }
                case 4: {
                    deleteProduct();
                    break;
                }
                case 5: {
                    sortProduct();
                    break;
                }
                case 6: {
                    searchProduct();
                    break;
                }
                case 7: flag = true;
                this.context.getAuthView().showMainMenu();
                break;
                default:
                    System.out.println("Vui lòng nhập lại");
            }
        } while (!flag);

        }



    private void searchProduct() {
        System.out.println("Nhập thông tin muốn tìm kiếm (theo tên sản phẩm, theo nhà sản xuất ) ");
        String kw = scanner.nextLine();
        List<Product> products = productService.searchProduct(kw);

        showProducts(products);
    }

    private void sortProduct() {
        System.out.println("Sắp xếp theo");
        System.out.println("1. Sắp theo tên");
        System.out.println("2. Sắp theo giá");
        int choice = getNumberMinMax("Mời nhập", 1, 2);
        switch (choice) {
            case 1:{
                sortProductByName();
                break;
            }
            case 2:{
                sortProductByPrice();
                break;
            }

        }



    }

    private void sortProductByPrice() {
        List<Product> products = productService.getAll();
        products.sort((o1, o2) -> Float.compare(o1.getPrice(), o2.getPrice()));

        showProducts(products);
    }

    private void sortProductByName() {
        List<Product> products = productService.getAll();
        products.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));

        showProducts(products);
    }


    private void deleteProduct() {
        Product product = inputProductId("Nhập sản phẩm cần xóa: ");
        boolean result = productService.removeProduct(product.getId());
        if (result == true) {
            System.out.println("Xóa thành công!");
            showProducts();
        }
    }

    public void editProduct() {
        Product pEdit = inputProductId("Nhập ID sản phẩn cần sửa: ");
        String name = InputUtils.inputString("Nhập tên sản phẩm mới: ", ValidateUtils.USERNAME_REGEX,
                "Tên sản phẩm không hợp lệ (chỉ từ 8-20 kí tự)");
        String description = InputUtils.inputString("Nhập mô tả mới: ", ValidateUtils.DESCRIPTION_REGEX,
                "Mô tả sản phẩm không hợp lệ (chỉ từ 8-50 kí tự)");

        float price = inputProductPrice("Nhập giá mới", "Giá từ 0 - 10000000");

        System.out.println("Sửa danh mục");
        for (ECategory eCategory : ECategory.values()) {
            System.out.printf("Nhập %s. %s \n", eCategory.getId(), eCategory.getName());
        }
        long idECategory = Long.parseLong(scanner.nextLine());
        ECategory eCategory = ECategory.getBy(idECategory);


        pEdit.setName(name);
        pEdit.setDescription(description);
        pEdit.setPrice(price);
        pEdit.seteCategory(eCategory);


        productService.updateProduct(pEdit.getId(), pEdit);

        System.out.println("Cập nhật thành công!");
        showProducts();

    }






    public void createProduct() {

        String name = InputUtils.inputString("Nhập tên sản phẩm: ", ValidateUtils.USERNAME_REGEX,
                "Tên sản phẩm không hợp lệ (chỉ từ 8-20 kí tự)");
        String description = InputUtils.inputString("Nhập mô tả: ", ValidateUtils.DESCRIPTION_REGEX,
                "Mô tả sản phẩm không hợp lệ (chỉ từ 8-50 kí tự)");

        float price = inputProductPrice("Nhập giá", "Giá từ 0 - 10000000");


        System.out.println("Nhập mã nhân viên tạo: ");
        long idUser = Long.parseLong(scanner.nextLine());
        User user = new User(idUser, "quocphu", "123123", ERole.USER, LocalDate.now());


        for (ECategory eCategory : ECategory.values()) {
            System.out.printf("Nhập %s %s \n", eCategory.getId(), eCategory.getName());
        }
        long idECategory = Long.parseLong(scanner.nextLine());
        ECategory eCategory = ECategory.getBy(idECategory);

        Product p = new Product(name, description, price, user, eCategory, LocalDate.now());
        productService.addProduct(p);

        showProducts();
    }

    private float inputProductPrice(String title, String s) {
        float price = 0;
        boolean flag = false;
        do {
            flag = false;
            try {
                System.out.println(title);
                price = Float.parseFloat(scanner.nextLine());

                if (price < 0 || price > 100000000) {
                    flag = true;
                }
            } catch (NumberFormatException numberFormatException) {
                flag = true;
            }
        } while (flag);
        return price;
    }

    public void showProducts(){
        List<Product> products = productService.getAll();
        System.out.printf("%10s | %20s | %30s | %15s | %10s | %20s | %20s\n", "ID", "Name", "Description", "Price", "User", "Category", "Create at");
        for (Product p : products) {
            System.out.printf("%10s | %20s | %30s | %15s | %10s | %20s | %20s\n",
                    p.getId(), p.getName(), p.getDescription(), p.getPrice(), p.getUserCreated().getName(), p.geteCategory(),p.getCreateAt() );
        }
    }
    public void showProducts(List<Product> products){
        System.out.printf("%10s | %20s | %30s | %15s | %10s | %20s | %20s\n", "ID", "Name", "Description", "Price", "User", "Category", "Create at");
        for (Product p : products) {
            System.out.printf("%10s | %20s | %30s | %15s | %10s | %20s | %20s\n",
                    p.getId(), p.getName(), p.getDescription(), p.getPrice(), p.getUserCreated().getName(), p.geteCategory(),p.getCreateAt() );
        }
    }
}
