package com.sendquiz.jwt.application;

import com.sendquiz.member.domain.MemberSession;
import com.sendquiz.member.dto.response.MemberResponse;
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

import static com.sendquiz.global.eumtype.constant.TimeConstant.ONE_HOUR;
import static com.sendquiz.global.eumtype.constant.TimeConstant.ONE_MONTH;
import static com.sendquiz.global.eumtype.constant.SessionConstant.REFRESH_TOKEN;
import static com.sendquiz.jwt.constant.JwtKey.JWT_KEY;
import static com.sendquiz.member.dto.response.MemberResponse.toMemberResponse;

@Service
public class JwtService {

    public static String getAccessToken(Long memberId) {
        return getToken(memberId, ONE_HOUR);
    }

    public static String getRefreshToken(Long memberId) {
        return getToken(memberId, ONE_MONTH);
    }

    private static String getToken(Long memberId, long expired) {
        SecretKey tokenKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(JWT_KEY));
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + expired);

        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(String.valueOf(memberId))
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(tokenKey)
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
        ResponseCookie cookie = ResponseCookie.from(REFRESH_TOKEN.message, refreshToken)
                .maxAge(Duration.ofDays(30))
                .httpOnly(true)
                .secure(true)
                .path("/")
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
