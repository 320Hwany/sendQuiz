package com.sendquiz.quiz.application;

import com.sendquiz.global.eumtype.Subject;
import com.sendquiz.quiz.domain.Quiz;
import com.sendquiz.quiz.repository.QuizRepository;
import com.sendquiz.quiz_filter.dto.request.QuizFilterSearch;
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
class QuizQueryTest {

    @InjectMocks
    private QuizQuery quizQuery;

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
        List<Quiz> filteredQuizList1 = quizQuery.getFilteredQuizzes(quizFilterSearch1);
        List<Quiz> filteredQuizList2 = quizQuery.getFilteredQuizzes(quizFilterSearch2);

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
        quizQuery.findAll();

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
        quizQuery.findAll();

        // expected
        verify(cache, times(0)).put(QUIZ_LIST, List.class);
    }
}