package com.sendquiz.global.config;

import com.sendquiz.global.annotation.AdminLogin;
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
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Base64;

import static com.sendquiz.global.constant.CommonConstant.*;
import static com.sendquiz.jwt.constant.JwtKey.JWT_KEY;
import static com.sendquiz.member.domain.AdminSession.toAdminSession;
import static com.sendquiz.member.domain.MemberSession.toMemberSession;

@RequiredArgsConstructor
public class AdminArgumentResolver implements HandlerMethodArgumentResolver {

    private final MemberRepository memberRepository;
    private final JwtRepository jwtRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isAdminSessionType = parameter.getParameterType().equals(AdminSession.class);
        boolean isAdminLoginAnnotation = parameter.hasParameterAnnotation(AdminLogin.class);
        return isAdminSessionType && isAdminLoginAnnotation;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory)  {
        String accessJws = webRequest.getHeader(ACCESS_TOKEN);
        byte[] decodedKey = Base64.getDecoder().decode(JWT_KEY);
        return getAdminSessionFromAccessJws(accessJws, decodedKey, webRequest);
    }

    private AdminSession getAdminSessionFromAccessJws(String jws, byte[] decodedKey, NativeWebRequest webRequest) {
        try {
            Jws<Claims> claims = getClaims(jws, decodedKey);
            String memberId = claims.getBody().getSubject();
            Member member = memberRepository.getById(Long.valueOf(memberId));
            return toAdminSession(member);

        } catch (JwtException e) {
            HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
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
            throw new AccessTokenAuthenticationException();
        }
    }

    private static Cookie[] getCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
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
                return toAdminSession(member);
            }
            throw new RefreshTokenNotMatchException();
        } catch (JwtException e) {
            throw new JwsNotMatchException();
        }
    }
}
