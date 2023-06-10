package com.sendquiz.quiz.application;

import com.sendquiz.email.application.EmailQuizSender;
import com.sendquiz.global.eumtype.Subject;
import com.sendquiz.quiz.domain.Quiz;
import com.sendquiz.quiz.dto.request.QuizSearch;
import com.sendquiz.quiz.dto.response.QuizPagingResponse;
import com.sendquiz.quiz.repository.QuizRepository;
import com.sendquiz.quiz_filter.dto.request.QuizFilterSearch;
import com.sendquiz.quiz_filter.repository.QuizFilterRepository;
import com.sendquiz.util.AcceptanceTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static com.sendquiz.global.constant.CommonConstant.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@AcceptanceTest
class QuizQueryTest {

    @Autowired
    private QuizQuery quizQuery;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private QuizRepository quizRepository;

    @BeforeEach
    public void clearCache() {
        Cache cache = cacheManager.getCache(QUIZ_CACHE);
        Objects.requireNonNull(cache).clear();
    }

    @Test
    @DisplayName("퀴즈를 퀴즈 필터 조건에 맞는 퀴즈만 있도록 필터링합니다")
    void getFilteredQuizList() {
        // given 1
        List<Quiz> quizList = IntStream.range(1, 11)
                .mapToObj(i -> Quiz.builder()
                        .subject(Subject.NETWORK)
                        .build())
                .toList();

        quizRepository.saveAll(quizList);

        // given 2
        QuizFilterSearch quizFilterSearch1 = QuizFilterSearch.builder()
                .isNetwork(true)
                .build();

        QuizFilterSearch quizFilterSearch2 = QuizFilterSearch.builder()
                .isDatabase(true)
                .build();

        // given 3
        Cache cache = cacheManager.getCache(QUIZ_CACHE);
        cache.put(QUIZ_LIST, quizList);

        // when
        List<Quiz> filteredQuizList1 = quizQuery.getFilteredQuizzes(quizFilterSearch1);
        List<Quiz> filteredQuizList2 = quizQuery.getFilteredQuizzes(quizFilterSearch2);

        // then
        assertThat(filteredQuizList1).hasSize(10);
        assertThat(filteredQuizList2).hasSize(0);
    }

    @Test
    @DisplayName("캐시에 퀴즈 정보가 없으면 DB를 통해 가져오고 캐시에 저장됩니다")
    void findAllByDB() {
        // given 1
        List<Quiz> quizList = IntStream.range(1, 11)
                .mapToObj(i -> Quiz.builder()
                        .subject(Subject.NETWORK)
                        .build())
                .toList();

        quizRepository.saveAll(quizList);

        // given 2
        Cache cache = cacheManager.getCache(QUIZ_CACHE);
        assertThat(cache.get(QUIZ_LIST, List.class)).isNull();

        // when
        List<Quiz> quizzes = quizQuery.findAll();

        // then
        assertThat(cache.get(QUIZ_LIST, List.class).size()).isEqualTo(10);
        assertThat(quizzes.size()).isEqualTo(10);
    }

    @Test
    @DisplayName("캐시에 퀴즈 정보가 있으면 캐시에서 가져옵니다")
    void findAllByCache() {
        // given 1
        List<Quiz> quizList = IntStream.range(1, 11)
                .mapToObj(i -> Quiz.builder()
                        .subject(Subject.NETWORK)
                        .build())
                .toList();

        quizRepository.saveAll(quizList);

        // given 2
        Cache cache = cacheManager.getCache(QUIZ_CACHE);
        cache.put(QUIZ_LIST, quizList);

        // when
        List<Quiz> quizzes = quizQuery.findAll();

        // then
        assertThat(cache.get(QUIZ_LIST, List.class).size()).isEqualTo(10);
        assertThat(quizzes.size()).isEqualTo(10);
    }

    @Test
    @DisplayName("퀴즈를 페이징 처리하여 가져옵니다")
    void findAllWithFilter() {
        // given 1
        List<Quiz> networkQuizList = LongStream.range(1, 6)
                .mapToObj(i -> Quiz.builder()
                        .problem("문제입니다 " + i)
                        .answer("정답입니다 " + i)
                        .subject(Subject.NETWORK)
                        .build())
                .toList();

        List<Quiz> javaQuizList = LongStream.range(6, 11)
                .mapToObj(i -> Quiz.builder()
                        .problem("문제입니다 " + i)
                        .answer("정답입니다 " + i)
                        .subject(Subject.JAVA)
                        .build())
                .toList();

        quizRepository.saveAll(networkQuizList);
        quizRepository.saveAll(javaQuizList);

        // given 2
        QuizSearch quizSearch = QuizSearch.builder()
                .network(true)
                .database(false)
                .spring(false)
                .page(1)
                .build();

        // when
        List<QuizPagingResponse> quizzesPagingResponse = quizQuery.findAllWithFilter(quizSearch);

        // then
        assertThat(quizzesPagingResponse.size()).isEqualTo(5);
        assertThat(quizzesPagingResponse.get(0).getNumber()).isEqualTo(1);
    }
}