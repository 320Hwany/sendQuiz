package com.sendquiz.quiz_filter.presentation;

import com.sendquiz.member.dto.request.MemberSignup;
import com.sendquiz.quiz_filter.dto.QuizFilterSave;
import com.sendquiz.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.http.MediaType.APPLICATION_JSON;

class QuizFilterControllerTest extends ControllerTest {

    @Test
    @DisplayName("로그인 한 회원으로 퀴즈 필터를 생성합니다")
    void save() throws Exception {
        // given
        MemberSignup memberSignup = saveMemberInRepository();
        String accessToken = getAccessToken(memberSignup);

        QuizFilterSave quizFilterSave = QuizFilterSave.builder()
                .network(true)
                .build();

        String requestBody = objectMapper.writeValueAsString(quizFilterSave);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.post("/quizFilter")
                        .header("Authorization", accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
}