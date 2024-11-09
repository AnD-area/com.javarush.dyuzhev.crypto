package com.javarush.dyuzhev.crypto;

import java.io.IOException;
import java.util.Scanner;

public class Cipher {
    private char[] alphabet;
    private final int alphabetLength;

    public Cipher(char[] alphabet) {
        this.alphabet = alphabet;
        this.alphabetLength = alphabet.length;
    }

    public String encrypt(String text, int key) {
        StringBuilder encryptedText = new StringBuilder();
        key = normalizeKey(key);
        for (char c : text.toCharArray()) {
            int index = findCharIndex(c);
            if (index != -1) {
                int newIndex = (index + key) % alphabetLength;
                encryptedText.append(alphabet[newIndex]);
            } else {
                encryptedText.append(c);
            }
        }
        return encryptedText.toString();
    }

    public String decrypt(String text, int key) {
        StringBuilder decryptedText = new StringBuilder();
        key = normalizeKey(key);
        System.out.println("Decrypting with key: " + key);
        for (char c : text.toCharArray()) {
            int index = findCharIndex(c);
            if (index != -1) {
                int newIndex = (index - key + alphabetLength) % alphabetLength;
                decryptedText.append(alphabet[newIndex]);
            } else {
                decryptedText.append(c);
            }
        }
        System.out.println("Decrypted Text: " + decryptedText.toString());

        return decryptedText.toString();
    }

    private int findCharIndex(char c) {
        for (int i = 0; i < alphabetLength; i++) {
            if (alphabet[i] == c) {
                return i;
            }
        }
        return -1;
    }

    private int normalizeKey(int key) {
        int normalizedKey = (key % alphabetLength + alphabetLength) % alphabetLength;
        System.out.println("Original Key: " + key + " | Normalized Key: " + normalizedKey);
        return normalizedKey;
    }

    public void encryptFile(FileManager fileManager, Validator validator) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите путь к файлу для шифрования:");
        String inputFilePath = scanner.nextLine();
        System.out.println("Введите путь к файлу для записи зашифрованного текста:");
        String outputFilePath = scanner.nextLine();
        System.out.println("Введите ключ шифрования:");
        int key = scanner.nextInt();

        if (validator.isFileExists(inputFilePath) && validator.isValidKey(key)) {
            try {
                String text = fileManager.readFile(inputFilePath);
                String encryptedText = encrypt(text, key);
                fileManager.writeFile(encryptedText, outputFilePath);
                System.out.println("Файл успешно зашифрован и сохранен.");
            } catch (IOException e) {
                System.out.println("Ошибка при работе с файлами: " + e.getMessage());
            }
        } else {
            System.out.println("Неверный путь к файлу или ключ.");
        }
    }

    public void decryptFile(FileManager fileManager, Validator validator) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите путь к зашифрованному файлу:");
        String encryptedFilePath = scanner.nextLine();
        System.out.println("Введите путь к файлу для записи расшифрованного текста:");
        String decryptedOutputFilePath = scanner.nextLine();
        System.out.println("Введите ключ шифрования:");
        int decryptionKey = scanner.nextInt();

        if (validator.isFileExists(encryptedFilePath) && validator.isValidKey(decryptionKey)) {
            try {
                String encryptedText = fileManager.readFile(encryptedFilePath);
                String decryptedText = decrypt(encryptedText, decryptionKey);
                fileManager.writeFile(decryptedText, decryptedOutputFilePath);
                System.out.println("Файл успешно расшифрован и сохранен.");
            } catch (IOException e) {
                System.out.println("Ошибка при работе с файлами: " + e.getMessage());
            }
        } else {
            System.out.println("Неверный путь к файлу или ключ.");
        }
    }

}