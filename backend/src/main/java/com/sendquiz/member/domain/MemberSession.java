package com.sendquiz.member.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSession {

    private Long id;

    private String email;

    private String nickname;

    private String password;

    private int numOfProblem;

    private boolean needToRefresh;

    @Builder
    protected MemberSession(Long id, String email, String nickname, String password,
                         int numOfProblem, boolean needToRefresh) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.numOfProblem = numOfProblem;
        this.needToRefresh = needToRefresh;
    }

    public static MemberSession toMemberSession(Member member, boolean needToRefresh) {
        return MemberSession.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .password(member.getPassword())
                .needToRefresh(needToRefresh)
                .build();
    }
}
