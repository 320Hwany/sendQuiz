package com.sendquiz.quiz_filter.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sendquiz.member.domain.QMember;
import com.sendquiz.quiz_filter.domain.QQuizFilter;
import com.sendquiz.quiz_filter.domain.QuizFilter;
import com.sendquiz.quiz_filter.dto.QQuizFilterSearch;
import com.sendquiz.quiz_filter.dto.QuizFilterSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sendquiz.member.domain.QMember.member;
import static com.sendquiz.quiz_filter.domain.QQuizFilter.quizFilter;

@RequiredArgsConstructor
@Repository
public class QuizFilterRepositoryImpl implements QuizFilterRepository {

    private final QuizFilterJpaRepository quizFilterJpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public void save(QuizFilter quizFilter) {
        quizFilterJpaRepository.save(quizFilter);
    }

    @Override
    public List<QuizFilterSearch> findAllQuizFilterSearch() {
        return queryFactory.select(new QQuizFilterSearch(
                        quizFilter.isNetwork,
                        quizFilter.isDatabase,
                        quizFilter.isOS,
                        quizFilter.isDataStructure,
                        quizFilter.isJava,
                        quizFilter.isSpring,
                        quizFilter.numOfProblem,
                        member.email
                ))
                .from(quizFilter)
                .innerJoin(quizFilter.member, member)
                .fetch();
    }
}
