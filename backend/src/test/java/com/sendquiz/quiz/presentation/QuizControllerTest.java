package com.sendquiz.quiz.presentation;

import com.sendquiz.global.eumtype.Subject;
import com.sendquiz.member.dto.request.MemberSignup;
import com.sendquiz.quiz.dto.request.QuizSave;
import com.sendquiz.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.sendquiz.global.eumtype.CommonConstant.ACCESS_TOKEN;
import static com.sendquiz.global.eumtype.ErrorMessageConstant.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class QuizControllerTest extends ControllerTest {

    @Test
    @DisplayName("관리자로 로그인한 후 퀴즈를 저장합니다")
    void save() throws Exception {
        // given
        MemberSignup memberSignup = saveAdminMemberInRepository();
        String accessToken = getAccessToken(memberSignup);

        QuizSave quizSave = QuizSave.builder()
                .subject(Subject.NETWORK)
                .problem("퀴즈 문제")
                .answer("퀴즈 정답")
                .build();

        String requestBody = objectMapper.writeValueAsString(quizSave);

        // expected
        mockMvc.perform(post("/api/quiz")
                        .header(ACCESS_TOKEN, accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("관리자로 로그인하지 않으면 퀴즈를 저장에 실패합니다")
    void saveFailUnAuthorized() throws Exception {
        // given
        MemberSignup memberSignup = saveMemberInRepository();
        String accessToken = getAccessToken(memberSignup);

        QuizSave quizSave = QuizSave.builder()
                .subject(Subject.NETWORK)
                .problem("퀴즈 문제")
                .answer("퀴즈 정답")
                .build();

        String requestBody = objectMapper.writeValueAsString(quizSave);

        // expected
        mockMvc.perform(post("/api/quiz")
                        .header(ACCESS_TOKEN, accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value(ADMIN_AUTHENTICATION.message));
    }
}