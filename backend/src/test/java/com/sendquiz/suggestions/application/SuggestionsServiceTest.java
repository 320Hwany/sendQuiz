package com.sendquiz.suggestions.application;

import com.sendquiz.member.domain.Member;
import com.sendquiz.member.domain.MemberSession;
import com.sendquiz.member.repository.MemberRepository;
import com.sendquiz.suggestions.dto.request.SuggestionsSave;
import com.sendquiz.suggestions.repository.SuggestionsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SuggestionsServiceTest {

    @InjectMocks
    private SuggestionsService suggestionsService;

    @Mock
    private SuggestionsRepository suggestionsRepository;

    @Mock
    private MemberRepository memberRepository;

    @Test
    @DisplayName("건의사항을 입력하면 저장됩니다")
    void save() {
        // given
        Member member = Member.builder().build();
        MemberSession memberSession = MemberSession.builder()
                .id(1L)
                .build();

        ReflectionTestUtils.setField(member, "id", 1L);

        // given 2
        SuggestionsSave suggestionsSave = SuggestionsSave.builder()
                .contents("건의사항 내용입니다")
                .build();

        // stub
        when(memberRepository.getById(any())).thenReturn(member);

        // when
        suggestionsService.save(memberSession, suggestionsSave);

        // then
        verify(suggestionsRepository, times(1)).save(any());
    }
}