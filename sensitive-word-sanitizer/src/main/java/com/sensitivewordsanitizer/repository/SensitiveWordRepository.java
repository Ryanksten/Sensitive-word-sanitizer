package com.sensitivewordsanitizer.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sensitivewordsanitizer.model.SensitiveWord;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Repository
public class SensitiveWordRepository {
    private static final String WORDS_FILE_PATH = "sensitive_words.json";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ReentrantLock lock = new ReentrantLock();

    private List<SensitiveWord> readWords() {
        try {
            File file = new File(WORDS_FILE_PATH);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(file, objectMapper.getTypeFactory()
                .constructCollectionType(List.class, SensitiveWord.class));
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private void writeWords(List<SensitiveWord> words) {
        try {
            lock.lock();
            objectMapper.writeValue(new File(WORDS_FILE_PATH), words);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write sensitive words", e);
        } finally {
            lock.unlock();
        }
    }

    public List<SensitiveWord> findAll() {
        return readWords();
    }

    public SensitiveWord save(SensitiveWord word) {
        List<SensitiveWord> words = readWords();
        
        // If word exists, update it
        words = words.stream()
            .filter(w -> !w.getWord().equalsIgnoreCase(word.getWord()))
            .collect(Collectors.toList());
        
        // Assign ID - use max existing ID + 1
        long maxId = words.stream()
            .mapToLong(SensitiveWord::getId)
            .max()
            .orElse(0L);
        word.setId(maxId + 1);
        
        words.add(word);
        writeWords(words);
        return word;
    }

    public void deleteById(Long id) {
        List<SensitiveWord> words = readWords();
        words.removeIf(w -> w.getId().equals(id));
        writeWords(words);
    }

    public SensitiveWord findById(Long id) {
        return readWords().stream()
            .filter(w -> w.getId().equals(id))
            .findFirst()
            .orElse(null);
    }
}