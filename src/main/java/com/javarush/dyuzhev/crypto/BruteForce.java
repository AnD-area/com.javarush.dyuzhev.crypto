package com.javarush.dyuzhev.crypto;

import java.io.IOException;
import java.util.Scanner;
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

    public void decryptFileByBruteForce(FileManager fileManager, Validator validator, char[] alphabet) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите путь к зашифрованному файлу:");
        String encryptedFilePath = scanner.nextLine();
        System.out.println("Введите путь к файлу для записи расшифрованного текста:");
        String decryptedOutputFilePath = scanner.nextLine();

        if (validator.isFileExists(encryptedFilePath)) {
            try {
                String encryptedText = fileManager.readFile(encryptedFilePath);
                String decryptedText = decryptByBruteForce(encryptedText, alphabet);
                fileManager.writeFile(decryptedText, decryptedOutputFilePath);
                System.out.println("Файл успешно расшифрован и сохранен.");
            } catch (IOException e) {
                System.out.println("Ошибка при работе с файлами: " + e.getMessage());
            }
        } else {
            System.out.println("Неверный путь к файлу.");
        }
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