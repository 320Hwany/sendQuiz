package com.sendquiz.member.presentation.response;

import com.sendquiz.member.domain.MemberSession;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberResponse {

    private Long id;

    private String email;

    private String nickname;

    private int numOfProblem;

    private String accessToken;

    @Builder
    private MemberResponse(Long id, String email, String nickname, int numOfProblem, String accessToken) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.numOfProblem = numOfProblem;
        this.accessToken = accessToken;
    }

    public static MemberResponse toMemberResponse(MemberSession memberSession, String accessToken) {
        return MemberResponse.builder()
                .id(memberSession.getId())
                .email(memberSession.getEmail())
                .nickname(memberSession.getNickname())
                .numOfProblem(memberSession.getNumOfProblem())
                .accessToken(accessToken)
                .build();
    }

    public static MemberResponse toMemberResponse(MemberSession memberSession) {
        return MemberResponse.builder()
                .id(memberSession.getId())
                .email(memberSession.getEmail())
                .nickname(memberSession.getNickname())
                .numOfProblem(memberSession.getNumOfProblem())
                .build();
    }
}
