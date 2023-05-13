package com.sendquiz.quiz_filter.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sendquiz.quiz_filter.application.request.QQuizFilterSearch;
import com.sendquiz.quiz_filter.domain.QuizFilter;
import com.sendquiz.quiz_filter.application.request.QuizFilterSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
    public void saveAll(List<QuizFilter> quizFilterList) {
        quizFilterJpaRepository.saveAll(quizFilterList);
    }

    @Override
    public Optional<QuizFilter> findByMemberId(Long memberId) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(quizFilter)
                        .innerJoin(quizFilter.member, member).fetchJoin()
                        .where(member.id.eq(memberId))
                        .fetchOne());
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

    @Override
    public void delete(QuizFilter quizFilter) {
        quizFilterJpaRepository.delete(quizFilter);
    }
}
