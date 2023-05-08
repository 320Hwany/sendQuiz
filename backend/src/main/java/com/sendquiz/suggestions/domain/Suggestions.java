package com.sendquiz.suggestions.domain;

import com.sendquiz.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Suggestions {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "suggestions_id")
    private Long id;

    @Lob
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    private Suggestions(String contents, Member member) {
        this.contents = contents;
        this.member = member;
    }
}
