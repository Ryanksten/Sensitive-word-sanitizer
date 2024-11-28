package com.sensitivewordsanitizer.controller;


import com.sensitivewordsanitizer.model.SensitiveWord;
import com.sensitivewordsanitizer.service.SensitiveWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensitive-words")
@CrossOrigin(origins = "http://localhost:3000") // Allow frontend to access
public class SensitiveWordController {
    private final SensitiveWordService service;

    @Autowired
    public SensitiveWordController(SensitiveWordService service) {
        this.service = service;
    }

    @PostMapping("/sanitize")
    public ResponseEntity<String> sanitizeText(@RequestBody String text) {
        return ResponseEntity.ok(service.sanitizeText(text));
    }

    @PostMapping
    public ResponseEntity<SensitiveWord> addSensitiveWord(@RequestBody String word) {
        return ResponseEntity.ok(service.addSensitiveWord(word));
    }

    @GetMapping
    public ResponseEntity<List<String>> getAllSensitiveWords() {
        return ResponseEntity.ok(service.getAllSensitiveWords());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSensitiveWord(@PathVariable Long id) {
        service.deleteSensitiveWord(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SensitiveWord> updateSensitiveWord(
            @PathVariable Long id, 
            @RequestBody String newWord
    ) {
        return ResponseEntity.ok(service.updateSensitiveWord(id, newWord));
    }
}