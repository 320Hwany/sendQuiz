package com.sendquiz.quiz_filter.application;

import com.sendquiz.member.domain.Member;
import com.sendquiz.member.domain.MemberSession;
import com.sendquiz.member.repository.MemberRepository;
import com.sendquiz.quiz_filter.domain.QuizFilter;
import com.sendquiz.quiz_filter.dto.QuizFilterSave;
import com.sendquiz.quiz_filter.dto.QuizFilterSearch;
import com.sendquiz.quiz_filter.repository.QuizFilterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class QuizFilterService {

    private final QuizFilterRepository quizFilterRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void save(QuizFilterSave quizFilterSave, MemberSession memberSession) {
        Member member = memberRepository.getById(memberSession.getId());
        QuizFilter quizFilter = quizFilterSave.toEntity(member);
        quizFilterRepository.save(quizFilter);
    }

    public void findAll() {
        List<QuizFilterSearch> quizFilterSearchList = quizFilterRepository.findAllQuizFilterSearch();
        for (QuizFilterSearch quizFilterSearch : quizFilterSearchList) {

        }
    }
}
