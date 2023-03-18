package org.example.container;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ComponentContainer {
    public static Double amountPrice=1400.00;
    public static String companyCard= 7777+"";

    public static Scanner intScanner = new Scanner(System.in);
    public static Scanner stringScanner = new Scanner(System.in);


    public static int getAction() {
        System.out.print("Action >> ");
        /*agar menuda raqam tanlamasdan harf yoki belgi tanlasa exception tashlaydi*/
        try {
            return ComponentContainer.intScanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Error");
        }
        return 0;
    }

}
