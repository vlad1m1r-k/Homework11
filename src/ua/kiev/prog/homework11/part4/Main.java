package ua.kiev.prog.homework11.part4;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CharToArt charToArt = new CharToArt();
        System.out.print("Enter text:> ");
        charToArt.print(new Scanner(System.in).nextLine());
    }
}
