package ua.kiev.prog.homework11.part1;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Translator implements Serializable {
    private Map<String, String> dictionary = new HashMap<>();

    public void addWord(String word, String translate) {
        dictionary.put(word.toLowerCase(), translate);
    }

    public String translateWord(String word){
        return dictionary.getOrDefault(word, word);
    }

    public void printDict(){
        dictionary.forEach((k, v) -> System.out.println(k + " - " + v));
    }
}
