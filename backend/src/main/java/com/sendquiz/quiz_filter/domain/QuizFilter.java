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

    private boolean isNetwork;

    private boolean isDatabase;

    @Column(name = "is_os")
    @NotNull
    private boolean isOS;

    private boolean isDataStructure;

    private boolean isJava;

    private boolean isSpring;

    private int numOfProblem;

    @Builder
    private QuizFilter(Member member, boolean isNetwork, boolean isDatabase, boolean isOS,
                      boolean isDataStructure, boolean isJava, boolean isSpring, int numOfProblem) {
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
        this.isNetwork = quizFilterUpdate.isNetwork();
        this.isDatabase = quizFilterUpdate.isDatabase();
        this.isOS = quizFilterUpdate.isOperatingSystem();
        this.isDataStructure = quizFilterUpdate.isDataStructure();
        this.isJava = quizFilterUpdate.isJava();
        this.isSpring = quizFilterUpdate.isSpring();
        this.numOfProblem = quizFilterUpdate.getNumOfProblem();
    }
}
