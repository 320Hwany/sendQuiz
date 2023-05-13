package com.sendquiz.jwt.application;

import com.sendquiz.member.domain.MemberSession;
import com.sendquiz.member.presentation.response.MemberResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
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

    public static void makeCookie(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie(REFRESH_TOKEN, refreshToken);
        cookie.setMaxAge(ONE_MONTH);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);
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
}
