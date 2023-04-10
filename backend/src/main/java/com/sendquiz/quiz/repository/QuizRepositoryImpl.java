package com.sendquiz.quiz.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sendquiz.global.eumtype.Subject;
import com.sendquiz.quiz.domain.Quiz;
import com.sendquiz.quiz_filter.dto.QuizFilterSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

import static com.sendquiz.quiz.domain.QQuiz.quiz;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Repository
public class QuizRepositoryImpl implements QuizRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Quiz> findRandomQuizList(QuizFilterSearch quizFilterSearch) {
        List<Quiz> quizList = queryFactory.selectFrom(quiz)
                .where(
                        networkTrue(quizFilterSearch)
                                .or(databaseTrue(quizFilterSearch))
                                .or(operatingSystemTrue(quizFilterSearch))
                                .or(dataStructureTrue(quizFilterSearch))
                                .or(javaTrue(quizFilterSearch))
                                .or(springTrue(quizFilterSearch))
                ).fetch();

        Collections.shuffle(quizList);

        return quizList.stream()
                .limit(quizFilterSearch.getNumOfProblem())
                .collect(toList());
    }

    private BooleanExpression networkTrue(QuizFilterSearch quizFilterSearch) {
        return quizFilterSearch.isNetwork() ? quiz.subject.eq(Subject.NETWORK) : null;
    }

    private BooleanExpression databaseTrue(QuizFilterSearch quizFilterSearch) {
        return quizFilterSearch.isDatabase() ? quiz.subject.eq(Subject.DATA_BASE) : null;
    }

    private BooleanExpression operatingSystemTrue(QuizFilterSearch quizFilterSearch) {
        return quizFilterSearch.isOS() ? quiz.subject.eq(Subject.OPERATING_SYSTEM) : null;
    }

    private BooleanExpression dataStructureTrue(QuizFilterSearch quizFilterSearch) {
        return quizFilterSearch.isDataStructure() ? quiz.subject.eq(Subject.DATA_STRUCTURE) : null;
    }

    private BooleanExpression javaTrue(QuizFilterSearch quizFilterSearch) {
        return quizFilterSearch.isJava() ? quiz.subject.eq(Subject.JAVA) : null;
    }

    private BooleanExpression springTrue(QuizFilterSearch quizFilterSearch) {
        return quizFilterSearch.isSpring() ? quiz.subject.eq(Subject.SPRING) : null;
    }
}
