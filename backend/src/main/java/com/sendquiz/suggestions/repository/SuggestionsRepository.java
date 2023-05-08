package com.sendquiz.suggestions.repository;

import com.sendquiz.suggestions.domain.Suggestions;

public interface SuggestionsRepository {

    void save(Suggestions suggestions);
}
