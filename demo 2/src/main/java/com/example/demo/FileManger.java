package com.example.demo;

public class FileManger {
    private static String currentFilePath;

    public static void currentFilePath(String FileName) {
        currentFilePath = FileName;
    }

    public static String getCurrentFilePath() {
        return currentFilePath;
    }
}
