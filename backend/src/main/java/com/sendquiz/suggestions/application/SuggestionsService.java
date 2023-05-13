package com.sendquiz.suggestions.application;

import com.sendquiz.member.domain.Member;
import com.sendquiz.member.domain.MemberSession;
import com.sendquiz.member.repository.MemberRepository;
import com.sendquiz.suggestions.domain.Suggestions;
import com.sendquiz.suggestions.presentation.request.SuggestionsSave;
import com.sendquiz.suggestions.repository.SuggestionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SuggestionsService {

    private final SuggestionsRepository suggestionsRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void save(MemberSession memberSession, SuggestionsSave suggestionsSave) {
        Member member = memberRepository.getById(memberSession.getId());
        Suggestions suggestions = suggestionsSave.toEntity(member);
        suggestionsRepository.save(suggestions);
    }
}
