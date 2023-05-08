package com.sendquiz.suggestions.repository;

import com.sendquiz.suggestions.domain.Suggestions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class SuggestionsRepositoryImpl implements SuggestionsRepository {

    private final SuggestionsJpaRepository suggestionsJpaRepository;

    @Override
    public void save(Suggestions suggestions) {
        suggestionsJpaRepository.save(suggestions);
    }
}
