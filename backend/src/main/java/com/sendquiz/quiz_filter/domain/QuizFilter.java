package com.sendquiz.quiz_filter.domain;

import com.sendquiz.global.BaseTimeEntity;
import com.sendquiz.member.domain.Member;
import com.sendquiz.quiz_filter.dto.request.QuizFilterUpdate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class QuizFilter extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_filter_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    private Boolean isNetwork;

    @NotNull
    private Boolean isDatabase;

    @NotNull
    @Column(name = "is_os")
    private Boolean isOS;

    @NotNull
    private Boolean isDataStructure;

    @NotNull
    private Boolean isJava;

    @NotNull
    private Boolean isSpring;

    private int numOfProblem;

    @Builder
    private QuizFilter(Member member, Boolean isNetwork, Boolean isDatabase, Boolean isOS,
                       Boolean isDataStructure, Boolean isJava, Boolean isSpring, int numOfProblem) {
        this.member = member;
        this.isNetwork = isNetwork;
        this.isDatabase = isDatabase;
        this.isOS = isOS;
        this.isDataStructure = isDataStructure;
        this.isJava = isJava;
        this.isSpring = isSpring;
        this.numOfProblem = numOfProblem;
    }

    public void update(QuizFilterUpdate quizFilterUpdate) {
        this.isNetwork = quizFilterUpdate.getIsNetwork();
        this.isDatabase = quizFilterUpdate.getIsDatabase();
        this.isOS = quizFilterUpdate.getIsOS();
        this.isDataStructure = quizFilterUpdate.getIsDataStructure();
        this.isJava = quizFilterUpdate.getIsJava();
        this.isSpring = quizFilterUpdate.getIsSpring();
        this.numOfProblem = quizFilterUpdate.getNumOfProblem();
    }
}
