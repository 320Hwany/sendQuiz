package com.sendquiz.member.domain;

import com.sendquiz.global.BaseTimeEntity;
import com.sendquiz.global.eumtype.Role;
import com.sendquiz.member.dto.request.MemberUpdate;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import static jakarta.persistence.GenerationType.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String email;

    private String nickname;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    private Member(String email, String nickname, String password, Role role) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
    }

    public void update(MemberUpdate memberUpdate, PasswordEncoder passwordEncoder) {
        this.nickname = memberUpdate.getNickname();
        this.password = passwordEncoder.encode(memberUpdate.getPassword());
    }

    public void updateToTemporaryPassword(String temporaryPassword, PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(temporaryPassword);
    }
}
