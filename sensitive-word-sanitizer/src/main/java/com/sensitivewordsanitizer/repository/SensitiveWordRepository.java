package com.sensitivewordsanitizer.repository;

import com.sensitivewordsanitizer.model.SensitiveWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensitiveWordRepository extends JpaRepository<SensitiveWord, Long> {
}