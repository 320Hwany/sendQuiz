package com.sendquiz.global.config;

import com.sendquiz.global.annotation.AdminLogin;
import com.sendquiz.member.domain.AdminSession;
import com.sendquiz.member.domain.Member;
import com.sendquiz.member.exception.AdminAuthenticationException;
import com.sendquiz.member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Base64;

import static com.sendquiz.global.constant.HiddenConstant.JWT_KEY;
import static com.sendquiz.member.domain.AdminSession.toAdminSession;

@RequiredArgsConstructor
public class AdminArgumentResolver implements HandlerMethodArgumentResolver {

    private final MemberRepository memberRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isAdminSessionType = parameter.getParameterType().equals(AdminSession.class);
        boolean isAdminLoginAnnotation = parameter.hasParameterAnnotation(AdminLogin.class);
        return isAdminSessionType && isAdminLoginAnnotation;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String jws = getJws(webRequest);
        byte[] decodedKey = Base64.getDecoder().decode(JWT_KEY);
        return getMemberSession(jws, decodedKey);
    }

    private static String getJws(NativeWebRequest webRequest) {
        String jws = webRequest.getHeader("Authorization");
        if (jws == null || jws.equals("")) {
            throw new AdminAuthenticationException();
        }
        return jws;
    }

    private static Jws<Claims> getClaims(String jws, byte[] decodedKey) {
        return Jwts.parserBuilder()
                .setSigningKey(decodedKey)
                .build()
                .parseClaimsJws(jws);
    }

    private AdminSession getMemberSession(String jws, byte[] decodedKey) {
        try {
            Jws<Claims> claims = getClaims(jws, decodedKey);
            String memberId = claims.getBody().getSubject();
            Member member = memberRepository.getById(Long.valueOf(memberId));
            return toAdminSession(member);

        } catch (JwtException e) {
            throw new AdminAuthenticationException();
        }
    }
}
