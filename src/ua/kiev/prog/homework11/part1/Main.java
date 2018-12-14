package ua.kiev.prog.homework11.part1;

import java.util.Scanner;

public class Main {
    private static Translator translator = new Translator();

    public static void main(String[] args) {
        Scanner keyboardScanner = new Scanner(System.in);
        String choose = "";
        do {
            System.out.println("1 - Translate English.in, 2 - Add word, 3 - Print dictionary, 4 - Save, 0 - Exit");
            System.out.print(">");
            choose = keyboardScanner.nextLine();
            switch (choose) {
                case "1":
                    translator.translate();
                    break;
                case "2":
                    addWord();
                    break;
                case "3":
                    translator.printDict();
                    break;
                case "4":
                    translator.saveDict();
            }
        } while (!choose.equals("0"));
    }

    private static void addWord() {
        String key = "";
        String value = "";
        Scanner keyboardScanner = new Scanner(System.in);
        System.out.print("Enter word and translate Separated by space: ");
        String[] args = keyboardScanner.nextLine().split(" +");
        if (args.length < 1 || args.length > 2) {
            System.out.println("Wrong parameters count. " + args.length);
            return;
        }
        if (!args[0].toLowerCase().matches("[a-z]+")) {
            System.out.println("Word contains illegal symbols.");
            return;
        }
        key = args[0].toLowerCase();
        if (args.length > 1) {
            value = args[1];
        }
        translator.addWord(key, value);
        System.out.println("Added.");
    }
}
