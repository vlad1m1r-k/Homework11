package ua.kiev.prog.homework11.part1;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Translator translator;

    public static void main(String[] args) {
        if (!loadDict()) {
            translator = new Translator();
            System.out.println("Warning: Translator dictionary is empty");
        }
        Scanner keyboardScanner = new Scanner(System.in);
        String choose = "";
        do {
            System.out.println("1 - Translate English.in, 2 - Add word, 3 - Print dictionary, 4 - Save, 0 - Exit");
            System.out.print(">");
            choose = keyboardScanner.nextLine();
            switch (choose) {
                case "1":
                    translate();
                    break;
                case "2":
                    addWord();
                    break;
                case "3":
                    printDictionary();
                    break;
                case "4":
                    saveDict();
            }
        } while (!choose.equals("0"));
    }

    private static boolean loadDict() {
        File dictSave = new File("translator.sav");
        if (dictSave.exists()) {
            try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(dictSave))) {
                translator = (Translator) input.readObject();
                return true;
            } catch (IOException e) {
                System.out.println("Error load file.");
                return false;
            } catch (ClassNotFoundException cnfe) {
                System.out.println("Can not load object.");
                return false;
            }
        }
        return false;
    }

    private static void saveDict() {
        File file = new File("translator.sav");
        if (file.exists() && !file.isDirectory()) file.delete();
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file))) {
            output.writeObject(translator);
            System.out.println("Saved to: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }

    private static void printDictionary() {
        translator.printDict();
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

    private static void translate() {
        StringBuilder result = new StringBuilder();
        try {
            List<String> strings = Files.readAllLines(Paths.get("English.in"));
            for (String string : strings) {
                String[] words = string.split(" +");
                for (String word : words) {
                    String punct = "";
                    if (("" + word.charAt(word.length() - 1)).matches("\\W")) {
                        punct = "" + word.charAt(word.length() - 1);
                        word = word.substring(0, word.length() - 1);
                    }
                    result.append(translator.translateWord(word.toLowerCase()));
                    result.append(punct);
                    result.append(" ");
                }
            }
            result = new StringBuilder(result.toString().replaceAll(" +", " "));
            Files.write(Paths.get("Ukrainian.out"),result.toString().getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
