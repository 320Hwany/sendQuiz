package com.sendquiz.quiz.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sendquiz.global.eumtype.Subject;
import com.sendquiz.quiz.domain.Quiz;
import com.sendquiz.quiz.exception.QuizNotFoundException;
import com.sendquiz.quiz_filter.application.request.QuizFilterSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.sendquiz.quiz.domain.QQuiz.quiz;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Repository
public class QuizRepositoryImpl implements QuizRepository {

    private final QuizJpaRepository quizJpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Quiz> findRandomQuizList(QuizFilterSearch quizFilterSearch) {
        List<BooleanExpression> expressions = findExpressionList(quizFilterSearch);
        if (expressions.isEmpty()) {
            return new ArrayList<>();
        }

        BooleanExpression finalExpression = expressions.get(0);
        for (int i = 1; i < expressions.size(); i++) {
            finalExpression = finalExpression.or(expressions.get(i));
        }

        List<Quiz> quizList = queryFactory.selectFrom(quiz)
                .where(finalExpression)
                .fetch();

        Collections.shuffle(quizList);

        return quizList.stream()
                .limit(quizFilterSearch.getNumOfProblem())
                .collect(toList());
    }

    @Override
    public List<Quiz> findAll() {
        return quizJpaRepository.findAll();
    }

    @Override
    public Quiz getById(Long quizId) {
        return quizJpaRepository.findById(quizId)
                .orElseThrow(QuizNotFoundException::new);
    }

    @Override
    public void save(Quiz quiz) {
        quizJpaRepository.save(quiz);
    }

    @Override
    public void saveAll(List<Quiz> quizList) {
        quizJpaRepository.saveAll(quizList);
    }

    private List<BooleanExpression> findExpressionList(QuizFilterSearch quizFilterSearch) {
        BooleanExpression networkExpression = networkTrue(quizFilterSearch);
        BooleanExpression databaseExpression = databaseTrue(quizFilterSearch);
        BooleanExpression operatingSystemExpression = operatingSystemTrue(quizFilterSearch);
        BooleanExpression dataStructureExpression = dataStructureTrue(quizFilterSearch);
        BooleanExpression javaExpression = javaTrue(quizFilterSearch);
        BooleanExpression springExpression = springTrue(quizFilterSearch);

        List<BooleanExpression> expressions = new ArrayList<>();
        if (networkExpression != null) expressions.add(networkExpression);
        if (databaseExpression != null) expressions.add(databaseExpression);
        if (operatingSystemExpression != null) expressions.add(operatingSystemExpression);
        if (dataStructureExpression != null) expressions.add(dataStructureExpression);
        if (javaExpression != null) expressions.add(javaExpression);
        if (springExpression != null) expressions.add(springExpression);

        return expressions;
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
