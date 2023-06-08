package com.sendquiz.suggestions.dto.request;

import com.sendquiz.member.domain.Member;
import com.sendquiz.suggestions.domain.Suggestions;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SuggestionsSave {

    private String contents;

    @Builder
    private SuggestionsSave(String contents) {
        this.contents = contents;
    }

    public Suggestions toEntity(Member member) {
        return Suggestions.builder()
                .contents(contents)
                .memberId(member.getId())
                .build();
    }
}
