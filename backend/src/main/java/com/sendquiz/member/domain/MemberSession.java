package com.sendquiz.member.domain;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSession implements Serializable {

    private Long id;

    private String email;

    private String nickname;

    private String password;

    private int numOfProblem;

    @Builder
    public MemberSession(Long id, String email, String nickname, String password, int numOfProblem) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.numOfProblem = numOfProblem;
    }

    public static MemberSession toMemberSession(Member member) {
        return MemberSession.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .password(member.getPassword())
                .numOfProblem(member.getNumOfProblem())
                .build();
    }
}
