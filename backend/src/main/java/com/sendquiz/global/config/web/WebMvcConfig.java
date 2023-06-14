package com.sendquiz.global.config.web;

import com.sendquiz.jwt.repository.JwtRepository;
import com.sendquiz.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final MemberRepository memberRepository;
    private final JwtRepository jwtRepository;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowCredentials(true)
                .allowedOrigins("http://3.34.119.43:3000", "http://3.34.119.43:80", "http://3.34.119.43",
                        "https://send-quiz.store");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdminLoginInterceptor(memberRepository, jwtRepository))
                .order(1)
                .addPathPatterns("/api/quiz/**");

        registry.addInterceptor(new LoginInterceptor(memberRepository, jwtRepository))
                .order(2)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/signup", "/api/login", "/api/quiz/**", "/api/email/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new MemberArgumentResolver());
        resolvers.add(new AdminArgumentResolver());
    }
}
