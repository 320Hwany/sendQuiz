package com.sendquiz.quiz_filter.repository;

import com.sendquiz.member.domain.Member;
import com.sendquiz.quiz_filter.dto.QuizFilterSearch;
import com.sendquiz.util.RepositoryTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class QuizFilterRepositoryImplTest extends RepositoryTest {

    @Test
    @DisplayName("모든 퀴즈 필터에 대한 정보를 dto로 가져옵니다")
    void findAllQuizFilterSearch() {
        // given
        List<Member> members = saveMemberList();
        saveQuizFilterList(members);

        // when
        List<QuizFilterSearch> quizFilterSearchList = quizFilterRepository.findAllQuizFilterSearch();

        // then
        assertThat(quizFilterSearchList).hasSize(10);
    }
}