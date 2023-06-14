package com.sendquiz.global.config.web;

import com.sendquiz.jwt.domain.JwtRefreshToken;
import com.sendquiz.jwt.exception.CookieExpiredException;
import com.sendquiz.jwt.exception.JwsNotMatchException;
import com.sendquiz.jwt.exception.RefreshTokenAuthenticationException;
import com.sendquiz.jwt.exception.RefreshTokenNotMatchException;
import com.sendquiz.jwt.repository.JwtRepository;
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

import static com.sendquiz.global.constant.CommonConstant.ACCESS_TOKEN;
import static com.sendquiz.global.constant.CommonConstant.MEMBER_SESSION;
import static com.sendquiz.global.constant.ErrorMessageConstant.ACCESS_TOKEN_AUTHENTICATION;
import static com.sendquiz.jwt.constant.JwtKey.JWT_KEY;
import static com.sendquiz.member.domain.MemberSession.toMemberSession;

@Slf4j
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final MemberRepository memberRepository;
    private final JwtRepository jwtRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String accessToken = request.getHeader(ACCESS_TOKEN);
        byte[] decodedKey = Base64.getDecoder().decode(JWT_KEY);
        MemberSession memberSession = getMemberSessionFromAccessJws(accessToken, decodedKey, request);
        request.setAttribute(MEMBER_SESSION, memberSession);
        return true;
    }

    private MemberSession getMemberSessionFromAccessJws(String jws, byte[] decodedKey, HttpServletRequest request) {
        try {
            Jws<Claims> claims = getClaims(jws, decodedKey);
            String memberId = claims.getBody().getSubject();
            Member member = memberRepository.getById(Long.valueOf(memberId));
            return toMemberSession(member, false);

        } catch (JwtException e) {
            log.info("LoginInterceptor JwtException");
            Cookie[] cookies = getCookies(request);
            String refreshJws = getRefreshJws(cookies);
            return getMemberSessionFromRefreshJws(refreshJws, decodedKey);
        }
    }

    private static Jws<Claims> getClaims(String jws, byte[] decodedKey) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(decodedKey)
                    .build()
                    .parseClaimsJws(jws);
        } catch (IllegalArgumentException e) {
            log.info("LoginInterceptor ACCESS_TOKEN_AUTHENTICATION");
            throw new JwtException(ACCESS_TOKEN_AUTHENTICATION);
        }
    }

    private static Cookie[] getCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            log.info("LoginInterceptor CookieExpiredException");
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

    private MemberSession getMemberSessionFromRefreshJws(String jws, byte[] decodedKey) {
        try {
            Jws<Claims> claims = getClaims(jws, decodedKey);
            String memberId = claims.getBody().getSubject();
            Member member = memberRepository.getById(Long.valueOf(memberId));
            JwtRefreshToken jwtRefreshToken = jwtRepository.getByMemberId(Long.valueOf(memberId));
            if (jws.equals(jwtRefreshToken.getRefreshToken())) {
                log.info("LoginInterceptor getMemberSessionFromRefreshJws");
                return toMemberSession(member, true);
            }
            throw new RefreshTokenNotMatchException();
        } catch (JwtException e) {
            throw new JwsNotMatchException();
        }
    }
}
