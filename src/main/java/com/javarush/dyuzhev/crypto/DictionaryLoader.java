package com.javarush.dyuzhev.crypto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class DictionaryLoader {
    public static Set<String> loadDictionary(String filePath) throws IOException {
        Set<String> dictionary = new HashSet<>();
        try (InputStream inputStream = DictionaryLoader.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                dictionary.add(line.trim().toLowerCase());
            }
        }
        return dictionary;
    }
}