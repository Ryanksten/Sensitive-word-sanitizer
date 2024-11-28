package com.sensitivewordsanitizer.service;

import com.sensitivewordsanitizer.model.SensitiveWord;
import com.sensitivewordsanitizer.repository.SensitiveWordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

        List<SensitiveWord> sensitiveWords = repository.findAll();
        String sanitizedText = text;

        for (SensitiveWord sensitiveWord : sensitiveWords) {
            String word = sensitiveWord.getWord();
            // Case-insensitive replacement
            sanitizedText = sanitizedText.replaceAll("(?i)" + word, "*".repeat(word.length()));
        }

        return sanitizedText;
    }

    public SensitiveWord addSensitiveWord(String word) {
        SensitiveWord sensitiveWord = new SensitiveWord(word.trim());
        return repository.save(sensitiveWord);
    }

    public List<String> getAllSensitiveWords() {
        return repository.findAll().stream()
                .map(SensitiveWord::getWord)
                .collect(Collectors.toList());
    }

    public void deleteSensitiveWord(Long id) {
        repository.deleteById(id);
    }

    public SensitiveWord updateSensitiveWord(Long id, String newWord) {
        SensitiveWord existingWord = repository.findById(id);
        if (existingWord == null) {
            throw new RuntimeException("Sensitive word not found");
        }
        
        existingWord.setWord(newWord.trim());
        return repository.save(existingWord);
    }
}