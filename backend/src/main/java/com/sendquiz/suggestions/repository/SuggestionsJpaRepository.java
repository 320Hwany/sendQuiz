package com.sendquiz.suggestions.repository;

import com.sendquiz.suggestions.domain.Suggestions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuggestionsJpaRepository extends JpaRepository<Suggestions, Long> {
}
