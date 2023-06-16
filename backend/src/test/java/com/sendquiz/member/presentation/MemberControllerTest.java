package com.sendquiz.member.presentation;

import com.sendquiz.member.dto.request.MemberDelete;
import com.sendquiz.member.dto.request.MemberLogin;
import com.sendquiz.member.dto.request.MemberSignup;
import com.sendquiz.member.dto.request.MemberUpdate;
import com.sendquiz.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.sendquiz.global.eumtype.CommonConstant.ACCESS_TOKEN;
import static com.sendquiz.global.eumtype.CommonConstant.CERTIFICATION_CACHE;
import static com.sendquiz.global.eumtype.ErrorMessageConstant.*;
import static com.sendquiz.global.eumtype.ValidMessageConstant.NICKNAME_VALID_MESSAGE;
import static com.sendquiz.util.TestConstant.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class MemberControllerTest extends ControllerTest {

    @Test
    @DisplayName("입력한 조건과 인증번호가 일치하면 회원가입에 성공합니다")
    void signup201() throws Exception {
        // given 1
        MemberSignup memberSignup = MemberSignup.builder()
                .email(TEST_EMAIL)
                .certificationNum(TEST_CERTIFICATION_NUM)
                .nickname(TEST_NICKNAME)
                .password(TEST_PASSWORD)
                .build();

        // given 2
        cacheManager.getCache(CERTIFICATION_CACHE).put(TEST_EMAIL, TEST_CERTIFICATION_NUM);

        String requestBody = objectMapper.writeValueAsString(memberSignup);

        // expected
        mockMvc.perform(post("/api/signup")
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("입력한 조건에 맞는 인증번호가 일치하지 않으면 예외가 발생합니다")
    void signup400CertificationNumNotMatch() throws Exception {
        // given 1
        MemberSignup memberSignup = MemberSignup.builder()
                .email(TEST_EMAIL)
                .certificationNum(TEST_CERTIFICATION_NUM)
                .nickname(TEST_NICKNAME)
                .password(TEST_PASSWORD)
                .build();

        // given 2
        cacheManager.getCache(CERTIFICATION_CACHE).put(TEST_EMAIL, "일치하지 않는 인증번호");

        String requestBody = objectMapper.writeValueAsString(memberSignup);

        // expected
        mockMvc.perform(post("/api/signup")
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(CERTIFICATION_NOT_MATCH.message));
    }

    @Test
    @DisplayName("입력한 조건에 맞지 않으면 예외가 발생합니다")
    void signup400ValidException() throws Exception {
        // given 1
        MemberSignup memberSignup = MemberSignup.builder()
                .email(TEST_EMAIL)
                .certificationNum(TEST_CERTIFICATION_NUM)
                .nickname("")
                .password(TEST_PASSWORD)
                .build();

        // given 2
        cacheManager.getCache(CERTIFICATION_CACHE).put(TEST_EMAIL, TEST_CERTIFICATION_NUM);

        String requestBody = objectMapper.writeValueAsString(memberSignup);

        // expected
        mockMvc.perform(post("/api/signup")
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validation.nickname").value(NICKNAME_VALID_MESSAGE));
    }

    @Test
    @DisplayName("이미 가입된 회원이면 예외가 발생합니다")
    void signup400AlreadySignup() throws Exception {
        // given 1
        MemberSignup memberSignup = saveMemberInRepository();

        // given 2
        cacheManager.getCache(CERTIFICATION_CACHE).put("test@email.com", "abcdefgh");

        String requestBody = objectMapper.writeValueAsString(memberSignup);

        // expected
        mockMvc.perform(post("/api/signup")
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(MEMBER_DUPLICATION.message));
    }

    @Test
    @DisplayName("회원정보가 일치하면 Jws를 반환합니다")
    void login200() throws Exception {
        // given 1
        MemberSignup memberSignup = saveMemberInRepository();

        // given 2
        MemberLogin memberLogin = MemberLogin.builder()
                .email(memberSignup.getEmail())
                .password(memberSignup.getPassword())
                .build();

        String requestBody = objectMapper.writeValueAsString(memberLogin);

        // expected
        mockMvc.perform(post("/api/login")
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists());
    }

    @Test
    @DisplayName("이메일/비밀번호가 일치하지 않으면 예외가 발생합니다")
    void login400NotMatch() throws Exception {
        // given 1
        MemberSignup memberSignup = MemberSignup.builder()
                .email(TEST_EMAIL)
                .certificationNum(TEST_CERTIFICATION_NUM)
                .nickname(TEST_NICKNAME)
                .password(TEST_PASSWORD)
                .build();

        memberRepository.save(memberSignup.toEntity(passwordEncoder));

        // given 2
        MemberLogin memberLogin = MemberLogin.builder()
                .email(memberSignup.getEmail())
                .password("not match password")
                .build();

        String requestBody = objectMapper.writeValueAsString(memberLogin);

        // expected
        mockMvc.perform(post("/api/login")
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(MEMBER_NOT_MATCH.message));
    }

    @Test
    @DisplayName("가입된 회원이 아니면 예외가 발생합니다")
    void login404NotFoundMember() throws Exception {
        // given 1
        MemberLogin memberLogin = MemberLogin.builder()
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .build();

        String requestBody = objectMapper.writeValueAsString(memberLogin);

        // expected
        mockMvc.perform(post("/api/login")
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(MEMBER_NOT_FOUND.message));
    }

    @Test
    @DisplayName("로그인한 회원의 정보를 가져옵니다")
    void get200() throws Exception {
        // given 1
        MemberSignup memberSignup = saveMemberInRepository();

        // given 2
        String accessToken = getAccessToken(memberSignup);

        // expected
        mockMvc.perform(get("/api/member")
                        .header(ACCESS_TOKEN, accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(memberSignup.getEmail()))
                .andExpect(jsonPath("$.nickname").value(memberSignup.getNickname()));
    }

    @Test
    @DisplayName("accessToken과 refreshToken이 모두 없으면 회원을 가져올 수 없습니다")
    void getWithoutAccessTokenAndRefreshToken() throws Exception {
        // given 1
        saveMemberInRepository();

        // expected
        mockMvc.perform(get("/api/member"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("회원 로그아웃을 진행합니다")
    void logout() throws Exception {
        // given 1
        MemberSignup memberSignup = saveMemberInRepository();

        // given 2
        String accessToken = getAccessToken(memberSignup);

        // expected
        mockMvc.perform(post("/api/logout")
                        .header(ACCESS_TOKEN, accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원 탈퇴를 합니다")
    void delete() throws Exception {
        // given 1
        MemberDelete memberDelete = MemberDelete.builder()
                .password(TEST_PASSWORD)
                .passwordCheck(TEST_PASSWORD)
                .build();

        MemberSignup memberSignup = saveMemberInRepository();

        // given 2
        String accessToken = getAccessToken(memberSignup);
        String requestBody = objectMapper.writeValueAsString(memberDelete);

        // expected
        mockMvc.perform(post("/api/withdrawal")
                        .header(ACCESS_TOKEN, accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않으면 예외가 발생합니다")
    void delete400() throws Exception {
        // given 1
        MemberDelete memberDelete = MemberDelete.builder()
                .password(TEST_PASSWORD)
                .passwordCheck("wrong password")
                .build();

        MemberSignup memberSignup = saveMemberInRepository();

        // given 2
        String accessToken = getAccessToken(memberSignup);
        String requestBody = objectMapper.writeValueAsString(memberDelete);

        // expected
        mockMvc.perform(post("/api/withdrawal")
                        .header(ACCESS_TOKEN, accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(PASSWORD_NOT_MATCH.message));
    }

    @Test
    @DisplayName("회원 탈퇴는 로그인 후 이용가능합니다")
    void delete401() throws Exception {
        // given 1
        MemberDelete memberDelete = MemberDelete.builder()
                .password(TEST_PASSWORD)
                .passwordCheck(TEST_PASSWORD)
                .build();

        saveMemberInRepository();

        // given 2
        String requestBody = objectMapper.writeValueAsString(memberDelete);

        // expected
        mockMvc.perform(post("/api/withdrawal")
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value(COOKIE_EXPIRED.message));
    }

    @Test
    @DisplayName("로그인 한 회원의 이메일을 제외한 정보를 수정합니다")
    void update200() throws Exception {
        // given 1
        MemberUpdate memberUpdate = MemberUpdate.builder()
                .nickname("update nickname")
                .password("update password")
                .build();

        MemberSignup memberSignup = saveMemberInRepository();

        // given 2
        String accessToken = getAccessToken(memberSignup);
        String requestBody = objectMapper.writeValueAsString(memberUpdate);

        // expected
        mockMvc.perform(patch("/api/member")
                        .header(ACCESS_TOKEN, accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("수정을 위해 입력한 정보가 조건에 맞지 않으면 예외가 발생합니다")
    void update400() throws Exception {
        // given 1
        MemberUpdate memberUpdate = MemberUpdate.builder()
                .nickname("")
                .password("update password")
                .build();

        MemberSignup memberSignup = saveMemberInRepository();

        // given 2
        String accessToken = getAccessToken(memberSignup);
        String requestBody = objectMapper.writeValueAsString(memberUpdate);

        // expected
        mockMvc.perform(patch("/api/member")
                        .header(ACCESS_TOKEN, accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validation.nickname").value(NICKNAME_VALID_MESSAGE));
    }

    @Test
    @DisplayName("AccessToken을 토큰을 보내지 않으면 정보를 수정할 수 없습니다")
    void updateFailWithoutAccessToken() throws Exception {
        // given 1
        MemberUpdate memberUpdate = MemberUpdate.builder()
                .nickname("update nickname")
                .password("update password")
                .build();

        saveMemberInRepository();

        // given 2
        String requestBody = objectMapper.writeValueAsString(memberUpdate);

        // expected
        mockMvc.perform(patch("/api/member")
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value(COOKIE_EXPIRED.message));
    }
}