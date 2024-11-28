package com.sensitivewordsanitizer.repository;

import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class SensitiveWordRepository {
    private static final String FILE_PATH = "sensitive_words.txt";

    public List<String> getAllWords() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                file.createNewFile();
                return new ArrayList<>();
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                return reader.lines()
                    .filter(line -> !line.trim().isEmpty())
                    .collect(Collectors.toList());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading sensitive words", e);
        }
    }

    public void addWord(String word) {
        try {
            // Check if word already exists
            List<String> existingWords = getAllWords();
            if (existingWords.stream().noneMatch(w -> w.equalsIgnoreCase(word))) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
                    writer.write(word);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error adding sensitive word", e);
        }
    }

    public void deleteWord(String wordToDelete) {
        try {
            List<String> words = getAllWords().stream()
                .filter(word -> !word.equalsIgnoreCase(wordToDelete))
                .collect(Collectors.toList());

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (String word : words) {
                    writer.write(word);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error deleting sensitive word", e);
        }
    }
}