package com.javarush.dyuzhev.crypto;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Validator {

    public boolean isValidKey(int key) {

        return key >= 0;
    }

    public boolean isFileExists(String filePath) {

        return Files.exists(Paths.get(filePath));
    }
}