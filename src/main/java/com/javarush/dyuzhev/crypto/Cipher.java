package com.javarush.dyuzhev.crypto;

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
}