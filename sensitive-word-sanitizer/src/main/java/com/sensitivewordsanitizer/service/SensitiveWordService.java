package com.sensitivewordsanitizer.service;

import com.sensitivewordsanitizer.repository.SensitiveWordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensitiveWordService {
    private final SensitiveWordRepository repository;

    @Autowired
    public SensitiveWordService(SensitiveWordRepository repository) {
        this.repository = repository;
    }

    public String sanitizeText(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        List<String> sensitiveWords = repository.getAllWords();
        String sanitizedText = text;

        for (String word : sensitiveWords) {
            // Case-insensitive replacement
            sanitizedText = sanitizedText.replaceAll("(?i)" + word, "*".repeat(word.length()));
        }

        return sanitizedText;
    }

    public void addSensitiveWord(String word) {
        repository.addWord(word.trim());
    }

    public List<String> getAllSensitiveWords() {
        return repository.getAllWords();
    }

    public void deleteSensitiveWord(String word) {
        repository.deleteWord(word);
    }
}