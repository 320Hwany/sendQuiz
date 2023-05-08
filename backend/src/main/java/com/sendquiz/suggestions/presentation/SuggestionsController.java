package com.sendquiz.suggestions.presentation;

import com.sendquiz.global.annotation.Login;
import com.sendquiz.member.domain.MemberSession;
import com.sendquiz.suggestions.application.SuggestionsService;
import com.sendquiz.suggestions.dto.request.SuggestionsSave;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SuggestionsController {

    private final SuggestionsService suggestionsService;

    @PostMapping("/suggestions")
    public ResponseEntity<Void> save(@Login MemberSession memberSession,
                                     @RequestBody SuggestionsSave suggestionsSave) {
        suggestionsService.save(memberSession, suggestionsSave);
        return ResponseEntity.ok().build();
    }
}