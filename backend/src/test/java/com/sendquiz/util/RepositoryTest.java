package com.sendquiz.util;

import com.sendquiz.global.eumtype.Subject;
import com.sendquiz.jwt.repository.JwtRepository;
import com.sendquiz.member.domain.Member;
import com.sendquiz.member.repository.MemberRepository;
import com.sendquiz.quiz.domain.Quiz;
import com.sendquiz.quiz.repository.QuizRepository;
import com.sendquiz.quiz_filter.domain.QuizFilter;
import com.sendquiz.quiz_filter.repository.QuizFilterRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.LongStream;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@AcceptanceTest
public class RepositoryTest {

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected QuizRepository quizRepository;

    @Autowired
    protected QuizFilterRepository quizFilterRepository;

    @Autowired
    protected JwtRepository jwtRepository;

    protected Member saveMemberInRepository() {
        Member member = Member.builder()
                .email("test@email.com")
                .nickname("test nickname")
                .password("test password")
                .build();

        memberRepository.save(member);
        return member;
    }

    protected List<Member> saveMemberList() {
        List<Member> memberList = LongStream.range(1, 11)
                .mapToObj(i -> Member.builder()
                        .email("test@email.com " + i)
                        .nickname("test nickname " + i)
                        .password("test password " + i)
                        .build())
                .toList();

        memberRepository.saveAll(memberList);
        return memberList;
    }

    protected void saveQuizList() {
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
    }

    protected void saveQuizFilterList(List<Member> memberList) {
        List<QuizFilter> networkQuizFilterList = LongStream.range(1, 6)
                .mapToObj(i -> QuizFilter.builder()
                        .member(memberList.get((int) (i - 1)))
                        .isNetwork(TRUE)
                        .isDatabase(FALSE)
                        .isOS(FALSE)
                        .isDataStructure(FALSE)
                        .isJava(FALSE)
                        .isSpring(FALSE)
                        .numOfProblem(5)
                        .build())
                .toList();

        List<QuizFilter> databaseQuizFilterList = LongStream.range(6, 11)
                .mapToObj(i -> QuizFilter.builder()
                        .member(memberList.get((int) (i - 1)))
                        .isNetwork(FALSE)
                        .isDatabase(TRUE)
                        .isOS(FALSE)
                        .isDataStructure(FALSE)
                        .isJava(FALSE)
                        .isSpring(FALSE)
                        .numOfProblem(5)
                        .build())
                .toList();

        quizFilterRepository.saveAll(networkQuizFilterList);
        quizFilterRepository.saveAll(databaseQuizFilterList);
    }
}
