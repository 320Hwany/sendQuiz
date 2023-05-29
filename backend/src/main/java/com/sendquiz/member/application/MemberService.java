package com.sendquiz.member.application;

import com.sendquiz.certification.domain.Certification;
import com.sendquiz.certification.exception.CertificationNotMatchException;
import com.sendquiz.certification.repository.CertificationRepository;
import com.sendquiz.jwt.application.response.JwtResponse;
import com.sendquiz.jwt.domain.JwtRefreshToken;
import com.sendquiz.jwt.repository.JwtRepository;
import com.sendquiz.member.domain.Member;
import com.sendquiz.member.domain.MemberSession;
import com.sendquiz.member.presentation.request.MemberDelete;
import com.sendquiz.member.presentation.request.MemberLogin;
import com.sendquiz.member.presentation.request.MemberSignup;
import com.sendquiz.member.presentation.request.MemberUpdate;
import com.sendquiz.member.exception.MemberDuplicationException;
import com.sendquiz.member.exception.MemberNotMatchException;
import com.sendquiz.member.exception.PasswordNotMatchException;
import com.sendquiz.member.repository.MemberRepository;
import com.sendquiz.quiz_filter.domain.QuizFilter;
import com.sendquiz.quiz_filter.repository.QuizFilterRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.sendquiz.jwt.application.JwtService.*;
import static com.sendquiz.jwt.application.response.JwtResponse.toJwtResponse;
import static com.sendquiz.jwt.domain.JwtRefreshToken.toEntity;


@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final CertificationRepository certificationRepository;
    private final QuizFilterRepository quizFilterRepository;
    private final JwtRepository jwtRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(MemberSignup memberSignup) {
        validateDuplicate(memberSignup);
        validateCertificationNum(memberSignup);
        Member member = memberSignup.toEntity(passwordEncoder);
        memberRepository.save(member);
    }

    protected void validateDuplicate(MemberSignup memberSignup) {
        Optional<Member> byEmail = memberRepository.findByEmail(memberSignup.getEmail());
        Optional<Member> byNickname = memberRepository.findByNickname(memberSignup.getNickname());
        if (byEmail.isPresent() || byNickname.isPresent()) {
            throw new MemberDuplicationException();
        }
    }

    protected void validateCertificationNum(MemberSignup memberSignup) {
        Certification certification = certificationRepository.getByEmail(memberSignup.getEmail());
        String certificationNum = certification.getCertificationNum();
        String inputCertificationNum = memberSignup.getCertificationNum();
        if (!certificationNum.equals(inputCertificationNum)) {
            throw new CertificationNotMatchException();
        }
    }

    @Transactional
    public JwtResponse login(MemberLogin memberLogin, HttpServletResponse response) {
        Member member = memberRepository.getByEmail(memberLogin.getEmail());
        if (passwordEncoder.matches(memberLogin.getPassword(), member.getPassword())) {
            String accessToken = getAccessToken(member.getId());
            String refreshToken = getRefreshToken(member.getId());
            JwtRefreshToken jwtRefreshToken = toEntity(refreshToken, member.getId());
            Optional<JwtRefreshToken> optionalJwtRefreshToken = jwtRepository.findByMemberId(member.getId());

            if (optionalJwtRefreshToken.isPresent()) {
                JwtRefreshToken jwtRefreshTokenPS = optionalJwtRefreshToken.get();
                jwtRefreshTokenPS.update(refreshToken);
                makeCookie(response, refreshToken);
                return toJwtResponse(accessToken);
            }

            makeCookie(response, refreshToken);
            jwtRepository.save(jwtRefreshToken);
            return toJwtResponse(accessToken);
        }
        throw new MemberNotMatchException();
    }

    @Transactional
    public void logout(MemberSession memberSession) {
        JwtRefreshToken jwtRefreshToken = jwtRepository.getByMemberId(memberSession.getId());
        jwtRepository.delete(jwtRefreshToken);
    }

    @Transactional
    public void delete(MemberSession memberSession, MemberDelete memberDelete) {
        Member member = memberRepository.getById(memberSession.getId());
        if (!passwordEncoder.matches(memberDelete.getPassword(), member.getPassword()) ||
                !passwordEncoder.matches(memberDelete.getPasswordCheck(), member.getPassword())) {
            throw new PasswordNotMatchException();
        }

        Optional<QuizFilter> optionalQuizFilter = quizFilterRepository.findByMemberId(member.getId());
        if (optionalQuizFilter.isPresent()) {
            QuizFilter quizFilter = optionalQuizFilter.get();
            quizFilterRepository.delete(quizFilter);
        }

        memberRepository.delete(member);
        JwtRefreshToken jwtRefreshToken = jwtRepository.getByMemberId(memberSession.getId());
        jwtRepository.delete(jwtRefreshToken);
    }

    @Transactional
    public void update(MemberSession memberSession, MemberUpdate memberUpdate) {
        Member member = memberRepository.getById(memberSession.getId());
        member.update(memberUpdate, passwordEncoder);
    }
}
