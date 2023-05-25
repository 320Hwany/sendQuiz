package com.sendquiz.jwt.application;

import com.sendquiz.member.domain.MemberSession;
import com.sendquiz.member.presentation.response.MemberResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import static com.sendquiz.global.constant.CommonConstant.*;
import static com.sendquiz.jwt.constant.JwtKey.JWT_KEY;
import static com.sendquiz.member.presentation.response.MemberResponse.toMemberResponse;

@Service
public class JwtService {

    public static String getAccessToken(Long memberId) {
        SecretKey accessTokenKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(JWT_KEY));
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(String.valueOf(memberId))
                .setExpiration(new Date(AFTER_ONE_HOUR))
                .signWith(accessTokenKey)
                .compact();
    }

    public static String getRefreshToken(Long memberId) {
        SecretKey refreshTokenKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(JWT_KEY));
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(String.valueOf(memberId))
                .setExpiration(new Date(AFTER_ONE_MONTH))
                .signWith(refreshTokenKey)
                .compact();
    }

    public static MemberResponse getMemberResponse(MemberSession memberSession) {
        MemberResponse memberResponse;
        if (memberSession.isNeedToRefresh()) {
            String accessToken = getAccessToken(memberSession.getId());
            memberResponse = toMemberResponse(memberSession, accessToken);
        } else {
            memberResponse = toMemberResponse(memberSession);
        }
        return memberResponse;
    }

    public static void makeCookie(HttpServletResponse response, String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from(REFRESH_TOKEN, refreshToken)
                .maxAge(Duration.ofDays(30))
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite(SAME_SITE_NONE)
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
