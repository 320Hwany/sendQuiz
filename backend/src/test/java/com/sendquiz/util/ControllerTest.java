package com.sendquiz.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.sendquiz.certification.repository.CertificationRepository;
import com.sendquiz.member.application.MemberService;
import com.sendquiz.member.dto.request.MemberLogin;
import com.sendquiz.member.dto.request.MemberSignup;
import com.sendquiz.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

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
    protected MemberService memberService;

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected CertificationRepository certificationRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    protected MemberSignup saveMemberInRepository() {
        MemberSignup memberSignup = MemberSignup.builder()
                .email("test@email.com")
                .certificationNum("abcdefgh")
                .nickname("test nickname")
                .password("test password")
                .build();

        memberRepository.save(memberSignup.toEntity(passwordEncoder));
        return memberSignup;
    }

    protected String getAccessToken(MemberSignup memberSignup) throws Exception {
        MemberLogin memberLogin = MemberLogin.builder()
                .email(memberSignup.getEmail())
                .password(memberSignup.getPassword())
                .build();

        String requestBody = objectMapper.writeValueAsString(memberLogin);

        MockHttpServletResponse response = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        return JsonPath.read(response.getContentAsString(), "$.accessToken");
    }
}