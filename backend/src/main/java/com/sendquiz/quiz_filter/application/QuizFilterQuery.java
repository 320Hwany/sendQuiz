package com.sendquiz.quiz_filter.application;

import com.sendquiz.quiz_filter.domain.QuizFilter;
import com.sendquiz.quiz_filter.application.response.QuizFilterResponse;
import com.sendquiz.quiz_filter.exception.QuizFilterNotFoundException;
import com.sendquiz.quiz_filter.repository.QuizFilterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sendquiz.quiz_filter.application.response.QuizFilterResponse.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class QuizFilterQuery {

    private final QuizFilterRepository quizFilterRepository;

    public QuizFilterResponse get(Long memberId) {
        QuizFilter quizFilter = quizFilterRepository.findByMemberId(memberId)
                .orElseThrow(QuizFilterNotFoundException::new);
        return toQuizFilterResponse(quizFilter);
    }
}
