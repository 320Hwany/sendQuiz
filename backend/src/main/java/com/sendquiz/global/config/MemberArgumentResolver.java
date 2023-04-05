package com.sendquiz.global.config;

import com.sendquiz.global.annotation.Login;
import com.sendquiz.member.domain.MemberSession;
import com.sendquiz.member.exception.MemberAuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.sendquiz.global.constant.CommonConstant.MEMBER_SESSION;

public class MemberArgumentResolver implements HandlerMethodArgumentResolver {


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isMemberSessionType = parameter.getParameterType().equals(MemberSession.class);
        boolean isMemberLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        return isMemberSessionType && isMemberLoginAnnotation;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory)  {
        HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);

        HttpSession session = httpServletRequest.getSession(false);

        if (session == null) {
            throw new MemberAuthenticationException();
        }

        return (MemberSession) session.getAttribute(MEMBER_SESSION);
    }
}
