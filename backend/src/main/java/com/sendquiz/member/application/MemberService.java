package com.sendquiz.member.application;

import com.sendquiz.certification.domain.Certification;
import com.sendquiz.certification.exception.CertificationNotMatchException;
import com.sendquiz.certification.repository.CertificationRepository;
import com.sendquiz.jwt.dto.JwtResponse;
import com.sendquiz.member.domain.Member;
import com.sendquiz.member.dto.request.MemberLogin;
import com.sendquiz.member.dto.request.MemberSignup;
import com.sendquiz.member.exception.MemberDuplicationException;
import com.sendquiz.member.exception.MemberNotMatchException;
import com.sendquiz.member.repository.MemberRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

import static com.sendquiz.global.constant.CommonConstant.AFTER_ONE_HOUR;
import static com.sendquiz.global.constant.CommonConstant.AFTER_ONE_MONTH;
import static com.sendquiz.global.constant.HiddenConstant.*;
import static com.sendquiz.jwt.dto.JwtResponse.toJwtResponse;


@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final CertificationRepository certificationRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(MemberSignup memberSignup) {
        validateDuplicate(memberSignup);
        validateCertificationNum(memberSignup);
        Member member = memberSignup.toEntity(passwordEncoder);
        memberRepository.save(member);
    }

    protected void validateDuplicate(MemberSignup memberSignup) {
        Optional<Member> byEmail = memberRepository.findByEmail(memberSignup.getEmail());
        Optional<Member> byNickname = memberRepository.findByNickname(memberSignup.getNickname());
        if (byEmail.isPresent() || byNickname.isPresent()) {
            throw new MemberDuplicationException();
        }
    }

    protected void validateCertificationNum(MemberSignup memberSignup) {
        Certification certification = certificationRepository.getByEmail(memberSignup.getEmail());
        String certificationNum = certification.getCertificationNum();
        String inputCertificationNum = memberSignup.getCertificationNum();
        if (!certificationNum.equals(inputCertificationNum)) {
            throw new CertificationNotMatchException();
        }
    }

    @Transactional
    public JwtResponse login(MemberLogin memberLogin, HttpServletResponse response) {
        Member member = memberRepository.getByEmail(memberLogin.getEmail());
        if (passwordEncoder.matches(memberLogin.getPassword(), member.getPassword())) {
            String accessToken = getAccessToken(member);
            String refreshToken = getRefreshToken(member);
            member.updateRefreshToken(refreshToken);
            makeCookie(response, refreshToken);
            return toJwtResponse(accessToken);
        }
        throw new MemberNotMatchException();
    }

    protected static void makeCookie(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setMaxAge(7 * 24 * 60 * 60 * 4);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);
    }

    public static String getAccessToken(Member member) {
        SecretKey accessTokenKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(JWT_KEY));
        return Jwts.builder()
                .setSubject(String.valueOf(member.getId()))
                .setExpiration(new Date(AFTER_ONE_HOUR))
                .signWith(accessTokenKey)
                .compact();
    }

    protected static String getRefreshToken(Member member) {
        SecretKey refreshTokenKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(JWT_KEY));
        return Jwts.builder()
                .setSubject(String.valueOf(member.getId()))
                .setExpiration(new Date(AFTER_ONE_MONTH))
                .signWith(refreshTokenKey)
                .compact();
    }
}
