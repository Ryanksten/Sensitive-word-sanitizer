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

    /**
     * Sanitize a given text by replacing sensitive words with asterisks
     * @param text Input text to sanitize
     * @return Sanitized text
     */
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

    /**
     * Add a new sensitive word
     * @param word Word to add
     * @return Saved sensitive word
     */
    public SensitiveWord addSensitiveWord(String word) {
        SensitiveWord sensitiveWord = new SensitiveWord(word.trim());
        return repository.save(sensitiveWord);
    }

    /**
     * Get all sensitive words
     * @return List of sensitive words
     */
    public List<String> getAllSensitiveWords() {
        return repository.findAll().stream()
                .map(SensitiveWord::getWord)
                .collect(Collectors.toList());
    }

    /**
     * Delete a sensitive word by its ID
     * @param id ID of the word to delete
     */
    public void deleteSensitiveWord(Long id) {
        repository.deleteById(id);
    }

    /**
     * Update an existing sensitive word
     * @param id ID of the word to update
     * @param newWord New word value
     * @return Updated sensitive word
     */
    public SensitiveWord updateSensitiveWord(Long id, String newWord) {
        SensitiveWord existingWord = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sensitive word not found"));
        
        existingWord.setWord(newWord.trim());
        return repository.save(existingWord);
    }
}