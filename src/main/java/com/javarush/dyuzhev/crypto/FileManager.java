package com.javarush.dyuzhev.crypto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

public class FileManager {

    public String readFile(String filePath) throws IOException {
        return Files.readString(Paths.get(filePath), StandardCharsets.UTF_8);
    }

    public void writeFile(String text, String filePath) throws IOException {
        Files.writeString(Paths.get(filePath), text, StandardCharsets.UTF_8);
    }
}