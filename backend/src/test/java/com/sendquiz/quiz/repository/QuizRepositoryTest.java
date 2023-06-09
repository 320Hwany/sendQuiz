package com.sendquiz.quiz.repository;

import com.sendquiz.member.domain.Member;
import com.sendquiz.quiz.domain.Quiz;
import com.sendquiz.quiz_filter.dto.request.QuizFilterSearch;
import com.sendquiz.util.RepositoryTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.assertj.core.api.Assertions.assertThat;

public class QuizRepositoryTest extends RepositoryTest {

    @Test
    @DisplayName("해당 퀴즈 필터로 랜덤으로 퀴즈를 뽑습니다")
    void findRandomQuizList() {
        // given
        List<Member> memberList = saveMemberList();
        saveQuizFilterList(memberList);
        saveQuizList();

        // given 2
        QuizFilterSearch quizFilterSearch1 = QuizFilterSearch.builder()
                .isNetwork(TRUE)
                .isDatabase(FALSE)
                .isOS(FALSE)
                .isDataStructure(FALSE)
                .isJava(FALSE)
                .isSpring(FALSE)
                .numOfProblem(5)
                .build();

        QuizFilterSearch quizFilterSearch2 = QuizFilterSearch.builder()
                .isNetwork(FALSE)
                .isDatabase(FALSE)
                .isOS(FALSE)
                .isDataStructure(FALSE)
                .isJava(TRUE)
                .isSpring(FALSE)
                .numOfProblem(3)
                .build();

        QuizFilterSearch quizFilterSearch3 = QuizFilterSearch.builder()
                .isNetwork(FALSE)
                .isDatabase(FALSE)
                .isOS(FALSE)
                .isDataStructure(FALSE)
                .isJava(FALSE)
                .isSpring(FALSE)
                .numOfProblem(5)
                .build();

        // when
        List<Quiz> randomQuizList1 = quizRepository.findRandomQuizList(quizFilterSearch1);
        List<Quiz> randomQuizList2 = quizRepository.findRandomQuizList(quizFilterSearch2);
        List<Quiz> randomQuizList3 = quizRepository.findRandomQuizList(quizFilterSearch3);

        // then
        assertThat(randomQuizList1).hasSize(5);
        assertThat(randomQuizList2).hasSize(3);
        assertThat(randomQuizList3).hasSize(0);
    }
}
