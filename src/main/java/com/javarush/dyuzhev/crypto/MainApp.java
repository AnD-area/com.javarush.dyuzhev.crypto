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

        try {
            Set<String> dictionary = DictionaryLoader.loadDictionary("/dictionary/dictionary_russian.txt");
            BruteForce bruteForce = new BruteForce(dictionary);

            switch (choice) {
                case 1 -> cipher.encryptFile(fileManager, validator);
                case 2 -> cipher.decryptFile(fileManager, validator);
                case 3 -> bruteForce.decryptFileByBruteForce(fileManager, validator, ALPHABET);
                default -> System.out.println("Неизвестный режим. Попробуйте ещё.");
            }
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке словаря: " + e.getMessage());
        }
    }
}