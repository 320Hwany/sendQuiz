package com.sendquiz.global.config.web;

import com.sendquiz.jwt.domain.JwtRefreshToken;
import com.sendquiz.jwt.exception.*;
import com.sendquiz.jwt.repository.JwtRepository;
import com.sendquiz.member.domain.AdminSession;
import com.sendquiz.member.domain.Member;
import com.sendquiz.member.domain.MemberSession;
import com.sendquiz.member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Base64;

import static com.sendquiz.global.constant.CommonConstant.*;
import static com.sendquiz.jwt.constant.JwtKey.JWT_KEY;
import static com.sendquiz.member.domain.AdminSession.toAdminSession;

@Slf4j
@RequiredArgsConstructor
public class AdminLoginInterceptor implements HandlerInterceptor {

    private final MemberRepository memberRepository;
    private final JwtRepository jwtRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String accessToken = request.getHeader(ACCESS_TOKEN);
        byte[] decodedKey = Base64.getDecoder().decode(JWT_KEY);
        AdminSession adminSession = getAdminSessionFromAccessJws(accessToken, decodedKey, request);
        request.setAttribute(MEMBER_SESSION, adminSession);
        return true;
    }

    private AdminSession getAdminSessionFromAccessJws(String jws, byte[] decodedKey, HttpServletRequest request) {
        try {
            Jws<Claims> claims = getClaims(jws, decodedKey);
            String memberId = claims.getBody().getSubject();
            Member member = memberRepository.getById(Long.valueOf(memberId));
            return toAdminSession(member);

        } catch (JwtException e) {
            log.info("AdminLoginInterceptor JwtException");
            Cookie[] cookies = getCookies(request);
            String refreshJws = getRefreshJws(cookies);
            return getAdminSessionFromRefreshJws(refreshJws, decodedKey);
        }
    }

    private static Jws<Claims> getClaims(String jws, byte[] decodedKey) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(decodedKey)
                    .build()
                    .parseClaimsJws(jws);
        } catch (IllegalArgumentException e) {
            log.info("AdminLoginInterceptor ACCESS_TOKEN_AUTHENTICATION");
            throw new AccessTokenAuthenticationException();
        }
    }

    private static Cookie[] getCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            log.info("AdminLoginInterceptor CookieExpiredException");
            throw new CookieExpiredException();
        }
        return cookies;
    }

    private static String getRefreshJws(Cookie[] cookies) {
        String refreshJws = cookies[0].getValue();
        if (refreshJws == null || refreshJws.equals("")) {
            throw new RefreshTokenAuthenticationException();
        }
        return refreshJws;
    }

    private AdminSession getAdminSessionFromRefreshJws(String jws, byte[] decodedKey) {
        try {
            Jws<Claims> claims = getClaims(jws, decodedKey);
            String memberId = claims.getBody().getSubject();
            Member member = memberRepository.getById(Long.valueOf(memberId));
            JwtRefreshToken jwtRefreshToken = jwtRepository.getByMemberId(Long.valueOf(memberId));
            if (jws.equals(jwtRefreshToken.getRefreshToken())) {
                log.info("AdminLoginInterceptor getMemberSessionFromRefreshJws");
                return toAdminSession(member);
            }
            throw new RefreshTokenNotMatchException();
        } catch (JwtException e) {
            throw new JwsNotMatchException();
        }
    }
}
