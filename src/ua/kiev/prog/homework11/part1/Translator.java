package ua.kiev.prog.homework11.part1;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Translator implements Serializable {
    private Map<String, String> dictionary = new HashMap<>();

    public Translator(){
        loadDict();
    }

    public void addWord(String word, String translate) {
        dictionary.put(word.toLowerCase(), translate);
    }

    public String translateWord(String word){
        return dictionary.getOrDefault(word, word);
    }

    public void printDict(){
        dictionary.forEach((k, v) -> System.out.println(k + " - " + v));
    }

    private void loadDict() {
        File dictSave = new File("dictionary.sav");
        if (dictSave.exists()) {
            try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(dictSave))) {
                dictionary = (HashMap<String, String>) input.readObject();
            } catch (IOException e) {
                System.out.println("Error load file.");
            } catch (ClassNotFoundException cnfe) {
                System.out.println("Can not load object.");
            }
        } else {
            System.out.println("Warning: Translator dictionary is empty");
        }
    }

    public void saveDict() {
        File file = new File("dictionary.sav");
        if (file.exists() && !file.isDirectory()) file.delete();
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file))) {
            output.writeObject(dictionary);
            System.out.println("Saved to: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }

    public void translate() {
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
                    result.append(translateWord(word.toLowerCase()));
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
