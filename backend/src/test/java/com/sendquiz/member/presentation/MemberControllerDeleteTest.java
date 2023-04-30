package com.sendquiz.member.presentation;

import com.sendquiz.member.dto.request.MemberDelete;
import com.sendquiz.member.dto.request.MemberSignup;
import com.sendquiz.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.sendquiz.global.constant.ErrorMessageConstant.MEMBER_AUTHENTICATION_MESSAGE;
import static com.sendquiz.global.constant.ErrorMessageConstant.PASSWORD_NOT_MATCH_MESSAGE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberControllerDeleteTest extends ControllerTest {

    @Test
    @DisplayName("회원 탈퇴를 합니다")
    void delete() throws Exception {
        // given 1
        MemberDelete memberDelete = MemberDelete.builder()
                .password("test password")
                .passwordCheck("test password")
                .build();

        MemberSignup memberSignup = saveMemberInRepository();

        // given 2
        String accessToken = getAccessToken(memberSignup);
        String requestBody = objectMapper.writeValueAsString(memberDelete);

        // expected
        mockMvc.perform(post("/withdrawal")
                        .header("Authorization", accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않으면 예외가 발생합니다")
    void delete400() throws Exception {
        // given 1
        MemberDelete memberDelete = MemberDelete.builder()
                .password("test password")
                .passwordCheck("wrong password")
                .build();

        MemberSignup memberSignup = saveMemberInRepository();

        // given 2
        String accessToken = getAccessToken(memberSignup);
        String requestBody = objectMapper.writeValueAsString(memberDelete);

        // expected
        mockMvc.perform(post("/withdrawal")
                        .header("Authorization", accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(PASSWORD_NOT_MATCH_MESSAGE));
    }

    @Test
    @DisplayName("회원 탈퇴는 로그인 후 이용가능합니다")
    void delete401() throws Exception {
        // given 1
        MemberDelete memberDelete = MemberDelete.builder()
                .password("test password")
                .passwordCheck("wrong password")
                .build();

        MemberSignup memberSignup = saveMemberInRepository();

        // given 2
        String requestBody = objectMapper.writeValueAsString(memberDelete);

        // expected
        mockMvc.perform(post("/withdrawal")
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value(MEMBER_AUTHENTICATION_MESSAGE));
    }
}
