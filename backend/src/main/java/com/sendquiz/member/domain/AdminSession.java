package com.sendquiz.member.domain;

import com.sendquiz.global.eumtype.Role;
import com.sendquiz.member.exception.AdminAuthenticationException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminSession {

    private Long id;

    private String email;

    private String password;

    private Role role;

    @Builder
    public AdminSession(Long id, String email, String password, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public static AdminSession toAdminSession(Member member) {
        if (Role.BASIC.equals(member.getRole())) {
            throw new AdminAuthenticationException();
        }
        return AdminSession.builder()
                .id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .build();
    }
}
