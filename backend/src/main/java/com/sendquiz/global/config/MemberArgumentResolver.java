package com.sendquiz.global.config;

import com.sendquiz.global.annotation.Login;
import com.sendquiz.member.domain.Member;
import com.sendquiz.member.domain.MemberSession;
import com.sendquiz.member.exception.MemberAuthenticationException;
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

import static com.sendquiz.global.constant.CommonConstant.AUTHORIZATION;
import static com.sendquiz.global.constant.HiddenConstant.JWT_KEY;
import static com.sendquiz.member.domain.MemberSession.toMemberSession;

@RequiredArgsConstructor
public class MemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final MemberRepository memberRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isMemberSessionType = parameter.getParameterType().equals(MemberSession.class);
        boolean isLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        return isMemberSessionType && isLoginAnnotation;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory)  {
        String accessJws = webRequest.getHeader(AUTHORIZATION);
        byte[] decodedKey = Base64.getDecoder().decode(JWT_KEY);
        if (accessJws == null || accessJws.equals("")) {
            HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
            Cookie[] cookies = getCookies(request);
            String refreshJws = getRefreshJws(cookies);
            return getMemberSessionFromRefreshJws(refreshJws, decodedKey);
        }

        return getMemberSessionFromAccessJws(accessJws, decodedKey);
    }

    private static Cookie[] getCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new MemberAuthenticationException();
        }
        return cookies;
    }

    private static String getRefreshJws(Cookie[] cookies) {
        String refreshJws = cookies[0].getValue();
        if (refreshJws == null || refreshJws.equals("")) {
            throw new MemberAuthenticationException();
        }
        return refreshJws;
    }

    private MemberSession getMemberSessionFromAccessJws(String jws, byte[] decodedKey) {
        try {
            Jws<Claims> claims = getClaims(jws, decodedKey);
            String memberId = claims.getBody().getSubject();
            Member member = memberRepository.getById(Long.valueOf(memberId));
            return toMemberSession(member, false);

        } catch (JwtException e) {
            throw new MemberAuthenticationException();
        }
    }

    private MemberSession getMemberSessionFromRefreshJws(String jws, byte[] decodedKey) {
        try {
            Jws<Claims> claims = getClaims(jws, decodedKey);
            String memberId = claims.getBody().getSubject();
            Member member = memberRepository.getById(Long.valueOf(memberId));
            if (jws.equals(member.getRefreshToken())) {
                return toMemberSession(member, true);
            }
            throw new MemberAuthenticationException();
        } catch (JwtException e) {
            throw new MemberAuthenticationException();
        }
    }

    private static Jws<Claims> getClaims(String jws, byte[] decodedKey) {
        return Jwts.parserBuilder()
                .setSigningKey(decodedKey)
                .build()
                .parseClaimsJws(jws);
    }
}
