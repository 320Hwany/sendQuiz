package com.sendquiz.quiz.application;

import com.sendquiz.global.eumtype.Subject;
import com.sendquiz.quiz.domain.Quiz;
import com.sendquiz.quiz.exception.SubjectNotMatchException;
import com.sendquiz.quiz.presentation.request.QuizUpdate;
import com.sendquiz.quiz.repository.QuizRepository;
import com.sendquiz.quiz_filter.application.request.QuizFilterSearch;
import com.sendquiz.quiz_filter.repository.QuizFilterRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.sendquiz.global.constant.CommonConstant.QUIZ_LIST;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuizServiceTest {

    @InjectMocks
    private QuizService quizService;

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private CacheManager cacheManager;

    @Mock
    private QuizFilterRepository quizFilterRepository;


    @Test
    @DisplayName("퀴즈를 퀴즈 필터 조건에 맞는 퀴즈만 있도록 필터링합니다")
    void getFilteredQuizList() {
        // given 1
        Cache cache = mock(Cache.class);

        List<Quiz> quizList = IntStream.range(1, 11)
                .mapToObj(i -> Quiz.builder()
                        .subject(Subject.NETWORK)
                        .build())
                .toList();

        // given 2
        QuizFilterSearch quizFilterSearch1 = QuizFilterSearch.builder()
                .isNetwork(true)
                .build();

        QuizFilterSearch quizFilterSearch2 = QuizFilterSearch.builder()
                .isDatabase(true)
                .build();

        // stub
        when(cacheManager.getCache(any())).thenReturn(cache);
        when(quizRepository.findAll()).thenReturn(quizList);

        // when
        List<Quiz> filteredQuizList1 = quizService.getFilteredQuizList(quizFilterSearch1);
        List<Quiz> filteredQuizList2 = quizService.getFilteredQuizList(quizFilterSearch2);

        // then
        assertThat(filteredQuizList1).hasSize(10);
        assertThat(filteredQuizList2).hasSize(0);
    }

    @Test
    @DisplayName("캐시에 퀴즈 정보가 없으면 DB를 통해 가져옵니다")
    void findAllByDB() {
        // given
        List<Quiz> quizList = new ArrayList<>();
        Cache cache = mock(Cache.class);

        // stub
        when(cacheManager.getCache(any())).thenReturn(cache);
        when(quizRepository.findAll()).thenReturn(quizList);

        // when
        quizService.findAll();

        // expected
        verify(cache, times(1)).put(QUIZ_LIST, quizList);
    }

    @Test
    @DisplayName("캐시에 퀴즈 정보가 있으면 캐시에서 가져옵니다")
    void findAllByCache() {
        // given
        List<Quiz> quizList = new ArrayList<>();
        Cache cache = mock(Cache.class);
        cache.put(QUIZ_LIST, quizList);

        // stub
        when(cacheManager.getCache(any())).thenReturn(cache);

        // when
        quizService.findAll();

        // expected
        verify(cache, times(0)).put(QUIZ_LIST, List.class);
    }

    @Test
    @DisplayName("퀴즈가 존재하고 입력한 분야에 맞다면 퀴즈를 수정합니다")
    void update() {
        // given
        Quiz quiz = Quiz.builder()
                .problem("수정전 문제")
                .answer("수정전 답")
                .subject(Subject.JAVA)
                .build();

        QuizUpdate quizUpdate = QuizUpdate.builder()
                .subject("네트워크")
                .problem("수정후 문제")
                .answer("수정후 답")
                .build();

        // stub
        when(quizRepository.getById(any())).thenReturn(quiz);

        // when
        quizService.update(quizUpdate);

        // then
        assertThat(quiz).extracting("problem", "answer", "subject")
                .contains("수정후 문제", "수정후 답", Subject.NETWORK);
    }

    @Test
    @DisplayName("입력한 퀴즈 분야가 맞지 않으면 퀴즈를 수정할 수 없습니다")
    void updateFailNotMatchSubject() {
        // given
        Quiz quiz = Quiz.builder()
                .problem("수정전 문제")
                .answer("수정전 답")
                .subject(Subject.JAVA)
                .build();

        QuizUpdate quizUpdate = QuizUpdate.builder()
                .subject("분야가 맞지 않음")
                .problem("수정후 문제")
                .answer("수정후 답")
                .build();

        // stub
        when(quizRepository.getById(any())).thenReturn(quiz);

        // then
        assertThatThrownBy(() -> quizService.update(quizUpdate))
                .isInstanceOf(SubjectNotMatchException.class);
    }
}