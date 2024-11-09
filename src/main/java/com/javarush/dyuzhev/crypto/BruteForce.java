package com.javarush.dyuzhev.crypto;

import java.util.Set;

public class BruteForce {
    private final Set<String> dictionary;

    public BruteForce(Set<String> dictionary) {
        this.dictionary = dictionary;
    }

    public String decryptByBruteForce(String encryptedText, char[] alphabet) {
        Cipher cipher = new Cipher(alphabet);
        int alphabetLength = alphabet.length;
        for (int key = 0; key < alphabetLength; key++) {
            String decryptedText = cipher.decrypt(encryptedText, key);

            // Логирование текущего ключа и первых 100 символов дешифрованного текста для отладки
            System.out.println("Key: " + key + " | Decrypted Text: " + decryptedText.substring(0, Math.min(100, decryptedText.length())));

            if (isValidText(decryptedText)) {
                System.out.println("Найден правильный ключ: " + key);
                return decryptedText;
            }
        }
        return null;
    }

    private boolean isValidText(String text) {
        int matches = countDictionaryMatches(text);
        boolean isValid = matches >= 5; // Условие: должно быть найдено хотя бы 5 слов из словаря

        System.out.println("Text matches dictionary pattern: " + isValid + " | matches: " + matches);
        return isValid;
    }

    private int countDictionaryMatches(String text) {
        int matches = 0;
        String[] words = text.split("\\s+");
        for (String word : words) {
            if (dictionary.contains(word.toLowerCase())) {
                matches++;
            }
        }
        return matches;
    }
}