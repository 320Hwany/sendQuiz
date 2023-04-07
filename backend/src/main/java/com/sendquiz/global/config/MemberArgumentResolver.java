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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Base64;

import static com.sendquiz.global.constant.HiddenConstant.JWT_KEY;
import static com.sendquiz.member.domain.MemberSession.toMemberSession;

@RequiredArgsConstructor
public class MemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final MemberRepository memberRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isMemberSessionType = parameter.getParameterType().equals(MemberSession.class);
        boolean isMemberLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        return isMemberSessionType && isMemberLoginAnnotation;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory)  {
        String jws = getJws(webRequest);

        byte[] decodedKey = Base64.getDecoder().decode(JWT_KEY);

        try {
            Jws<Claims> claims = getClaims(jws, decodedKey);

            String memberId = claims.getBody().getSubject();
            Member member = memberRepository.getById(Long.valueOf(memberId));
            return toMemberSession(member);
        } catch (JwtException e) {
            throw new MemberAuthenticationException();
        }
    }

    private static String getJws(NativeWebRequest webRequest) {
        String jws = webRequest.getHeader("Authorization");
        if (jws == null || jws.equals("")) {
            throw new MemberAuthenticationException();
        }
        return jws;
    }

    private static Jws<Claims> getClaims(String jws, byte[] decodedKey) {
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(decodedKey)
                .build()
                .parseClaimsJws(jws);
        return claims;
    }
}
