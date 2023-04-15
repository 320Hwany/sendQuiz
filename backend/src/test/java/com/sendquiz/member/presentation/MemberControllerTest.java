package com.sendquiz.member.presentation;

import com.sendquiz.certification.domain.Certification;
import com.sendquiz.member.domain.Member;
import com.sendquiz.member.dto.request.MemberLogin;
import com.sendquiz.member.dto.request.MemberSignup;
import com.sendquiz.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockCookie;

import static com.sendquiz.global.constant.CommonConstant.REFRESH_TOKEN;
import static com.sendquiz.global.constant.ErrorMessageConstant.*;
import static com.sendquiz.global.constant.ValidMessageConstant.EMAIL_VALID_MESSAGE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class MemberControllerTest extends ControllerTest {

    @Test
    @DisplayName("조건에 맞으면 회원가입에 성공합니다")
    void signup200() throws Exception {
        // given
        MemberSignup memberSignup = MemberSignup.builder()
                .email("test@email.com")
                .certificationNum("abcdefgh")
                .nickname("test nickname")
                .password("test password")
                .build();

        // given 2
        Certification certification = Certification.builder()
                .email(memberSignup.getEmail())
                .certificationNum(memberSignup.getCertificationNum())
                .build();

        certificationRepository.save(certification);

        String requestBody = objectMapper.writeValueAsString(memberSignup);

        // expected
        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("인증번호가 일치하지 않으면 회원가입시 예외가 발생합니다")
    void signup400NotMatchCertificationNum() throws Exception {
        // given
        MemberSignup memberSignup = MemberSignup.builder()
                .email("test@email.com")
                .certificationNum("abcdefgh")
                .nickname("test nickname")
                .password("test password")
                .build();

        // given 2
        Certification certification = Certification.builder()
                .email(memberSignup.getEmail())
                .certificationNum("another num")
                .build();

        certificationRepository.save(certification);

        String requestBody = objectMapper.writeValueAsString(memberSignup);

        // expected
        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(CERTIFICATION_NOT_MATCH_MESSAGE));
    }

    @Test
    @DisplayName("입력 정보가 조건에 맞지 않으면 회원가입시 예외가 발생합니다")
    void signup400Valid() throws Exception {
        // given
        MemberSignup memberSignup = MemberSignup.builder()
                .email("")
                .certificationNum("abcdefgh")
                .nickname("test nickname")
                .password("test password")
                .build();

        // given 2
        Certification certification = Certification.builder()
                .email(memberSignup.getEmail())
                .certificationNum(memberSignup.getCertificationNum())
                .build();

        certificationRepository.save(certification);

        String requestBody = objectMapper.writeValueAsString(memberSignup);

        // expected
        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validation.email").value(EMAIL_VALID_MESSAGE));
    }

    @Test
    @DisplayName("이미 가입된 회원이면 회원가입시 예외가 발생합니다")
    void signup400AlreadySignup() throws Exception {
        // given 1
        saveMemberInRepository();

        // given 2
        MemberSignup memberSignup = MemberSignup.builder()
                .email("test@email.com")
                .certificationNum("abcdefgh")
                .nickname("test nickname")
                .password("test password")
                .build();

        // given 3
        Certification certification = Certification.builder()
                .email(memberSignup.getEmail())
                .certificationNum(memberSignup.getCertificationNum())
                .build();

        certificationRepository.save(certification);

        String requestBody = objectMapper.writeValueAsString(memberSignup);

        // expected
        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(MEMBER_DUPLICATION_MESSAGE));
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
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists());
    }

    @Test
    @DisplayName("이메일/비밀번호가 일치하지 않으면 예외가 발생합니다")
    void login400NotMatch() throws Exception {
        // given 1
        MemberSignup memberSignup = MemberSignup.builder()
                .email("test@email.com")
                .certificationNum("abcdefgh")
                .nickname("test nickname")
                .password("test password")
                .build();

        memberRepository.save(memberSignup.toEntity(passwordEncoder));

        // given 2
        MemberLogin memberLogin = MemberLogin.builder()
                .email(memberSignup.getEmail())
                .password("not match password")
                .build();

        String requestBody = objectMapper.writeValueAsString(memberLogin);

        // expected
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(MEMBER_NOT_MATCH_MESSAGE));
    }

    @Test
    @DisplayName("가입된 회원이 아니면 예외가 발생합니다")
    void login404NotFoundMember() throws Exception {
        // given 1
        MemberLogin memberLogin = MemberLogin.builder()
                .email("test@email")
                .password("test password")
                .build();

        String requestBody = objectMapper.writeValueAsString(memberLogin);

        // expected
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(MEMBER_NOT_FOUND_MESSAGE));
    }

    @Test
    @DisplayName("로그인한 회원의 정보를 가져옵니다")
    void get200() throws Exception {
        // given 1
        MemberSignup memberSignup = saveMemberInRepository();

        // given 2
        String accessToken = getAccessToken(memberSignup);

        // expected
        mockMvc.perform(get("/member")
                        .header("Authorization", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value(memberSignup.getEmail()))
                .andExpect(jsonPath("$.nickname").value(memberSignup.getNickname()));
    }

    @Test
    @DisplayName("accessToken과 refreshToken이 모두 없으면 회원을 가져올 수 없습니다")
    void getWithoutAccessTokenAndRefreshToken() throws Exception {
        // given 1
        saveMemberInRepository();

        // expected
        mockMvc.perform(get("/member"))
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
        mockMvc.perform(post("/logout")
                        .header("Authorization", accessToken))
                .andExpect(status().isOk());
    }
}