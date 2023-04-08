package com.sendquiz.quiz_filter.application;

import com.sendquiz.member.domain.Member;
import com.sendquiz.member.domain.MemberSession;
import com.sendquiz.member.repository.MemberRepository;
import com.sendquiz.quiz_filter.dto.QuizFilterSave;
import com.sendquiz.quiz_filter.repository.QuizFilterRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuizFilterServiceTest {

    @InjectMocks
    private QuizFilterService quizFilterService;

    @Mock
    private QuizFilterRepository quizFilterRepository;

    @Mock
    private MemberRepository memberRepository;

    @Test
    @DisplayName("로그인한 회원과 선택한 정보로 퀴즈 필터를 생성합니다")
    void save() {
        // given
        QuizFilterSave quizFilterSave = QuizFilterSave.builder()
                .isNetwork(true)
                .build();

        Member member = Member.builder()
                .email("test@email")
                .nickname("test nickname")
                .password("test password")
                .build();

        MemberSession memberSession = MemberSession.builder()
                .build();

        // stub
        when(memberRepository.getById(any())).thenReturn(member);

        // when
        quizFilterService.save(quizFilterSave, memberSession);

        // then
        verify(quizFilterRepository, times(1)).save(any());
    }
}