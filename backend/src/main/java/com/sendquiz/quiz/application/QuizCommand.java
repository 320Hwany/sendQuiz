package com.sendquiz.quiz.application;

import com.sendquiz.quiz.domain.Quiz;
import com.sendquiz.quiz.dto.request.QuizSave;
import com.sendquiz.quiz.dto.request.QuizUpdate;
import com.sendquiz.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class QuizCommand {

    private final QuizRepository quizRepository;

    public void save(QuizSave quizSave) {
        quizRepository.save(quizSave.toEntity());
    }

    public void update(QuizUpdate quizUpdate) {
        Quiz quiz = quizRepository.getById(quizUpdate.getQuizId());
        quiz.update(quizUpdate);
    }
}
