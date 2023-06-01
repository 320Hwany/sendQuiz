package com.sendquiz.suggestions.domain;

import com.sendquiz.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Suggestions extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "suggestions_id")
    private Long id;

    @Lob
    private String contents;

    private Long memberId;

    @Builder
    private Suggestions(String contents, Long memberId) {
        this.contents = contents;
        this.memberId = memberId;
    }
}
