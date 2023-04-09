package com.sendquiz.quiz.application;

import com.sendquiz.global.eumtype.Subject;
import com.sendquiz.quiz.dto.request.QuizSearchDataDto;
import com.sendquiz.quiz.dto.request.QuizSearchInputDto;
import com.sendquiz.quiz.exception.SubjectNotMatchException;
import com.sendquiz.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class QuizService {

    private final QuizRepository quizRepository;

    public void getQuizList(QuizSearchInputDto quizSearchInputDto) {
        if (!Subject.validateValue(quizSearchInputDto.getSubject())) {
            throw new SubjectNotMatchException();
        }
        QuizSearchDataDto dataDto = quizSearchInputDto.toDataDto();
//        quizRepository.getQuizList(dataDto);
    }
}
