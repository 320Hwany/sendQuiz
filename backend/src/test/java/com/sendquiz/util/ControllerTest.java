package com.sendquiz.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.sendquiz.global.eumtype.Role;
import com.sendquiz.jwt.repository.JwtRepository;
import com.sendquiz.member.application.MemberCommand;
import com.sendquiz.member.domain.Member;
import com.sendquiz.member.dto.request.MemberLogin;
import com.sendquiz.member.dto.request.MemberSignup;
import com.sendquiz.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static com.sendquiz.util.TestConstant.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AcceptanceTest
public class ControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MemberCommand memberCommand;

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected JwtRepository jwtRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected CacheManager cacheManager;

    protected MemberSignup saveMemberInRepository() {
        MemberSignup memberSignup = MemberSignup.builder()
                .email(TEST_EMAIL)
                .certificationNum(TEST_CERTIFICATION_NUM)
                .nickname(TEST_NICKNAME)
                .password(TEST_PASSWORD)
                .build();

        memberRepository.save(memberSignup.toEntity(passwordEncoder));
        return memberSignup;
    }

    protected MemberSignup saveAdminMemberInRepository() {
        MemberSignup memberSignup = MemberSignup.builder()
                .email(TEST_EMAIL)
                .certificationNum(TEST_CERTIFICATION_NUM)
                .nickname(TEST_NICKNAME)
                .password(TEST_PASSWORD)
                .build();

        Member member = Member.builder()
                .email(TEST_EMAIL)
                .nickname(TEST_NICKNAME)
                .password(passwordEncoder.encode(TEST_PASSWORD))
                .role(Role.ADMIN)
                .build();

        memberRepository.save(member);
        return memberSignup;
    }

    protected String getAccessToken(MemberSignup memberSignup) throws Exception {
        MemberLogin memberLogin = MemberLogin.builder()
                .email(memberSignup.getEmail())
                .password(memberSignup.getPassword())
                .build();

        String requestBody = objectMapper.writeValueAsString(memberLogin);

        MockHttpServletResponse response = mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        return JsonPath.read(response.getContentAsString(), "$.accessToken");
    }
}
