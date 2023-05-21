package com.sendquiz.quiz_filter.presentation;

import com.sendquiz.member.presentation.request.MemberSignup;
import com.sendquiz.quiz_filter.presentation.request.QuizFilterSave;
import com.sendquiz.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.sendquiz.global.constant.CommonConstant.ACCESS_TOKEN;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        mockMvc.perform(MockMvcRequestBuilders.post("/api/quizFilter")
                        .header(ACCESS_TOKEN, accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());
    }
}