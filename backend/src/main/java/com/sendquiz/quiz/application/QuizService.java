package com.sendquiz.quiz.application;

import com.sendquiz.email.application.EmailQuizSender;
import com.sendquiz.global.eumtype.Subject;
import com.sendquiz.quiz.domain.Quiz;
import com.sendquiz.quiz.dto.request.QuizSave;
import com.sendquiz.quiz.exception.SubjectNotMatchException;
import com.sendquiz.quiz.repository.QuizRepository;
import com.sendquiz.quiz_filter.dto.QuizFilterSearch;
import com.sendquiz.quiz_filter.repository.QuizFilterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.sendquiz.global.eumtype.Subject.validateValue;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizFilterRepository quizFilterRepository;
    private final EmailQuizSender emailQuizSender;

    public void sendRandomQuizList() {
        List<QuizFilterSearch> quizFilterSearchList = quizFilterRepository.findAllQuizFilterSearch();
        for (QuizFilterSearch quizFilterSearch : quizFilterSearchList) {
            List<Quiz> randomQuizList = quizRepository.findRandomQuizList(quizFilterSearch);
            emailQuizSender.sendQuizList(randomQuizList, quizFilterSearch.getEmail());
        }
    }

    @Transactional
    public void save(QuizSave quizSave) {
        if (!validateValue(quizSave.getSubject())) {
            throw new SubjectNotMatchException();
        }
        quizRepository.save(quizSave.toEntity());
    }
}
