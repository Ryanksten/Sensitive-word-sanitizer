package com.sensitivewordsanitizer.controller;

import com.sensitivewordsanitizer.service.SensitiveWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensitive-words")
@CrossOrigin(origins = "http://localhost:3000")
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
    public ResponseEntity<Void> addSensitiveWord(@RequestBody String word) {
        service.addSensitiveWord(word);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<String>> getAllSensitiveWords() {
        return ResponseEntity.ok(service.getAllSensitiveWords());
    }

    @DeleteMapping("/{word}")
    public ResponseEntity<Void> deleteSensitiveWord(@PathVariable String word) {
        service.deleteSensitiveWord(word);
        return ResponseEntity.ok().build();
    }
}