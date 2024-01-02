package com.cg.utils;

import java.util.Scanner;

public class InputUtils {
    public static Scanner scanner = new Scanner(System.in);
    public static String inputString(String title, String regex, String errorMessage) {
        String name;
        boolean flag = false;
        do {
            System.out.println(title);
            name = scanner.nextLine();
            flag = ValidateUtils.isProductStringValid(name, regex);

            if(!flag){
                System.out.println(errorMessage);
            }
        } while (!flag);
        return name;
    }
}
