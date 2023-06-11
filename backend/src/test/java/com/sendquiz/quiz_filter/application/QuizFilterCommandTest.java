package com.sendquiz.quiz_filter.application;

import com.sendquiz.member.domain.Member;
import com.sendquiz.member.domain.MemberSession;
import com.sendquiz.member.repository.MemberRepository;
import com.sendquiz.quiz_filter.domain.QuizFilter;
import com.sendquiz.quiz_filter.dto.request.QuizFilterSave;
import com.sendquiz.quiz_filter.repository.QuizFilterRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuizFilterCommandTest {

    @InjectMocks
    private QuizFilterCommand quizFilterCommand;

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
        when(quizFilterRepository.findByMemberId(any())).thenReturn(Optional.empty());

        // when
        quizFilterCommand.save(quizFilterSave, memberSession);

        // then
        verify(quizFilterRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("로그인한 회원에 대한 퀴즈 필터가 이미 존재하면 퀴즈 필터를 업데이트합니다")
    void saveAlreadyExist() {
        // given
        QuizFilter quizFilter = QuizFilter.builder()
                .isNetwork(FALSE)
                .build();

        QuizFilterSave quizFilterSave = QuizFilterSave.builder()
                .isNetwork(TRUE)
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
        when(quizFilterRepository.findByMemberId(any())).thenReturn(Optional.of(quizFilter));

        // when
        quizFilterCommand.save(quizFilterSave, memberSession);

        // then
        verify(quizFilterRepository, times(0)).save(any());
        assertThat(quizFilter.getIsNetwork()).isTrue();
    }
}