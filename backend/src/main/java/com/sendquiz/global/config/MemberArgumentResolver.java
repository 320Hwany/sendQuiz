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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Base64;

import static com.sendquiz.global.constant.CommonConstant.*;
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
        byte[] decodedKey = Base64.getDecoder().decode(JWT_KEY);
        return getMemberSessionFromAccessJws(decodedKey, webRequest);
    }

    private MemberSession getMemberSessionFromAccessJws(byte[] decodedKey, NativeWebRequest webRequest) {
        try {
            String accessJws = webRequest.getHeader(ACCESS_TOKEN);
            Jws<Claims> claims = getClaimsAccessToken(accessJws, decodedKey);
            String memberId = claims.getBody().getSubject();
            Member member = memberRepository.getById(Long.valueOf(memberId));
            return toMemberSession(member, false);

        } catch (JwtException e) {
            return getMemberSessionFromRefreshJws(decodedKey, webRequest);
        }
    }

    private static Jws<Claims> getClaimsAccessToken(String jws, byte[] decodedKey) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(decodedKey)
                    .build()
                    .parseClaimsJws(jws);
        } catch (IllegalArgumentException e) {
            throw new AccessTokenAuthenticationException();
        }
    }

    private MemberSession getMemberSessionFromRefreshJws(byte[] decodedKey, NativeWebRequest webRequest) {
        try {
            String refreshTokenIdx = webRequest.getHeader(REFRESH_TOKEN_IDX);
            assert refreshTokenIdx != null;
            JwtRefreshToken jwtRefreshToken = jwtRepository.getById(Long.valueOf(refreshTokenIdx));
            String refreshJws = jwtRefreshToken.getRefreshToken();
            Jws<Claims> claims = getClaimsRefreshToken(refreshJws, decodedKey);
            String memberId = claims.getBody().getSubject();
            Member member = memberRepository.getById(Long.valueOf(memberId));

            if (refreshJws.equals(jwtRefreshToken.getRefreshToken())) {
                return toMemberSession(member, true);
            }
            throw new RefreshTokenNotMatchException();
        } catch (JwtException e) {
            throw new JwsNotMatchException();
        }
    }

    private static Jws<Claims> getClaimsRefreshToken(String jws, byte[] decodedKey) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(decodedKey)
                    .build()
                    .parseClaimsJws(jws);
        } catch (IllegalArgumentException e) {
            throw new RefreshTokenAuthenticationException();
        }
    }
}
