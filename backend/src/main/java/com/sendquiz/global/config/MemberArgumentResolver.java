package com.sendquiz.global.config;

import com.sendquiz.global.annotation.Login;
import com.sendquiz.jwt.domain.JwtRefreshToken;
import com.sendquiz.jwt.exception.*;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Base64;

import static com.sendquiz.global.constant.CommonConstant.*;
import static com.sendquiz.global.constant.ErrorMessageConstant.ACCESS_TOKEN_AUTHENTICATION;
import static com.sendquiz.jwt.constant.JwtKey.JWT_KEY;
import static com.sendquiz.member.domain.MemberSession.toMemberSession;

@Slf4j
@RequiredArgsConstructor
public class MemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final MemberRepository memberRepository;
    private final JwtRepository jwtRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isMemberSessionType = parameter.getParameterType().equals(MemberSession.class);
        boolean isLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        return isMemberSessionType && isLoginAnnotation;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory)  {
        String accessJws = webRequest.getHeader(ACCESS_TOKEN);
        byte[] decodedKey = Base64.getDecoder().decode(JWT_KEY);
        return getMemberSessionFromAccessJws(accessJws, decodedKey, webRequest);
    }

    private MemberSession getMemberSessionFromAccessJws(String jws, byte[] decodedKey, NativeWebRequest webRequest) {
        try {
            Jws<Claims> claims = getClaims(jws, decodedKey);
            String memberId = claims.getBody().getSubject();
            Member member = memberRepository.getById(Long.valueOf(memberId));
            log.info("getMemberSessionFromAccessJws");
            return toMemberSession(member, false);

        } catch (JwtException e) {
            HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
            Cookie[] cookies = getCookies(request);
            String refreshJws = getRefreshJws(cookies);
            log.info("getMemberSessionFromRefreshJws");
            return getMemberSessionFromRefreshJws(refreshJws, decodedKey);
        }
    }

    private static Jws<Claims> getClaims(String jws, byte[] decodedKey) {
        try {
            log.info("getClaims");
            return Jwts.parserBuilder()
                    .setSigningKey(decodedKey)
                    .build()
                    .parseClaimsJws(jws);
        } catch (IllegalArgumentException e) {
            log.info("ACCESS_TOKEN_AUTHENTICATION");
            throw new JwtException(ACCESS_TOKEN_AUTHENTICATION);
        }
    }

    private static Cookie[] getCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            log.info("CookieExpiredException");
            throw new CookieExpiredException();
        }
        for (Cookie cookie : cookies) {
            log.info("cookies={}", cookie.toString());
        }
        return cookies;
    }

    private static String getRefreshJws(Cookie[] cookies) {
        String refreshJws = cookies[0].getValue();
        log.info("refreshJws={}", refreshJws);
        if (refreshJws == null || refreshJws.equals("")) {
            log.info("RefreshTokenAuthenticationException");
            throw new RefreshTokenAuthenticationException();
        }
        log.info("getRefreshJws");
        return refreshJws;
    }

    private MemberSession getMemberSessionFromRefreshJws(String jws, byte[] decodedKey) {
        try {
            Jws<Claims> claims = getClaims(jws, decodedKey);
            String memberId = claims.getBody().getSubject();
            Member member = memberRepository.getById(Long.valueOf(memberId));
            JwtRefreshToken jwtRefreshToken = jwtRepository.getByMemberId(Long.valueOf(memberId));
            log.info("jwtRepository.getByMemberId");
            if (jws.equals(jwtRefreshToken.getRefreshToken())) {
                log.info("jws.equals(jwtRefreshToken.getRefreshToken())");
                return toMemberSession(member, true);
            }
            log.info("RefreshTokenNotMatchException");
            throw new RefreshTokenNotMatchException();
        } catch (JwtException e) {
            log.info("JwsNotMatchException");
            throw new JwsNotMatchException();
        }
    }
}
