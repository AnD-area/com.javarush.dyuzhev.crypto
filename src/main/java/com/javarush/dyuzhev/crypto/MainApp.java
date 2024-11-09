package com.javarush.dyuzhev.crypto;


import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

public class MainApp {

    private static final char[] ALPHABET = {
            'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У',
            'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я',
            'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у',
            'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я',
            '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите режим работы:");
        System.out.println("1. Шифровка текста");
        System.out.println("2. Расшифровка текста с известным ключом");
        System.out.println("3. Расшифровка методом brute force");
        int choice = scanner.nextInt();
        scanner.nextLine();

        Cipher cipher = new Cipher(ALPHABET);
        FileManager fileManager = new FileManager();
        Validator validator = new Validator();

        String outputFilePath = null;
        String encryptedFilePath = null;
        String decryptedOutputFilePath = null;

        try {
            Set<String> dictionary = DictionaryLoader.loadDictionary("/dictionary/dictionary_russian.txt");
            BruteForce bruteForce = new BruteForce(dictionary);

            switch (choice) {
                case 1:
                    System.out.println("Введите путь к файлу для шифрования:");
                    String inputFilePath = scanner.nextLine();
                    System.out.println("Введите путь к файлу для записи зашифрованного текста:");
                    outputFilePath = scanner.nextLine();
                    System.out.println("Введите ключ шифрования:");
                    int key = scanner.nextInt();

                    if (validator.isFileExists(inputFilePath) && validator.isValidKey(key)) {
                        try {
                            String text = fileManager.readFile(inputFilePath);
                            String encryptedText = cipher.encrypt(text, key);
                            fileManager.writeFile(encryptedText, outputFilePath);
                            System.out.println("Файл успешно зашифрован и сохранен.");
                        } catch (IOException e) {
                            System.out.println("Ошибка при работе с файлами: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Неверный путь к файлу или ключ.");
                    }
                    break;
                case 2:
                    System.out.println("Введите путь к зашифрованному файлу:");
                    encryptedFilePath = scanner.nextLine();
                    System.out.println("Введите путь к файлу для записи расшифрованного текста:");
                    decryptedOutputFilePath = scanner.nextLine();
                    System.out.println("Введите ключ шифрования:");
                    int decryptionKey = scanner.nextInt();

                    if (validator.isFileExists(encryptedFilePath) && validator.isValidKey(decryptionKey)) {
                        try {
                            String encryptedText = fileManager.readFile(encryptedFilePath);
                            String decryptedText = cipher.decrypt(encryptedText, decryptionKey);
                            fileManager.writeFile(decryptedText, decryptedOutputFilePath);
                            System.out.println("Файл успешно расшифрован и сохранен.");
                        } catch (IOException e) {
                            System.out.println("Ошибка при работе с файлами: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Неверный путь к файлу или ключ.");
                    }
                    break;
                case 3:
                    System.out.println("Введите путь к зашифрованному файлу:");
                    encryptedFilePath = scanner.nextLine();
                    System.out.println("Введите путь к файлу для записи расшифрованного текста:");
                    decryptedOutputFilePath = scanner.nextLine();

                    if (validator.isFileExists(encryptedFilePath)) {
                        try {
                            String encryptedText = fileManager.readFile(encryptedFilePath);
                            String decryptedText = bruteForce.decryptByBruteForce(encryptedText, ALPHABET);
                            fileManager.writeFile(decryptedText, decryptedOutputFilePath);
                            System.out.println("Файл успешно расшифрован и сохранен.");
                        } catch (IOException e) {
                            System.out.println("Ошибка при работе с файлами: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Неверный путь к файлу.");
                    }
                    break;
                default:
                    System.out.println("Неверный выбор режима.");
                    break;
            }
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке словаря: " + e.getMessage());
        }
    }
}