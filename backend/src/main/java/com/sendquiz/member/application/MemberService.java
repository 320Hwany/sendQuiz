package com.sendquiz.member.application;

import com.sendquiz.certification.domain.Certification;
import com.sendquiz.certification.exception.CertificationNotMatchException;
import com.sendquiz.certification.repository.CertificationRepository;
import com.sendquiz.jwt.dto.JwtResponse;
import com.sendquiz.member.domain.Member;
import com.sendquiz.member.domain.MemberSession;
import com.sendquiz.member.dto.request.MemberDelete;
import com.sendquiz.member.dto.request.MemberLogin;
import com.sendquiz.member.dto.request.MemberSignup;
import com.sendquiz.member.exception.MemberDuplicationException;
import com.sendquiz.member.exception.MemberNotMatchException;
import com.sendquiz.member.exception.PasswordNotMatchException;
import com.sendquiz.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.sendquiz.jwt.application.JwtService.*;
import static com.sendquiz.jwt.dto.JwtResponse.toJwtResponse;


@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final CertificationRepository certificationRepository;
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
            member.updateRefreshToken(refreshToken);
            makeCookie(response, refreshToken);
            return toJwtResponse(accessToken);
        }
        throw new MemberNotMatchException();
    }

    @Transactional
    public void logout(MemberSession memberSession) {
        Member member = memberRepository.getById(memberSession.getId());
        member.deleteRefreshToken();
    }

    @Transactional
    public void delete(MemberSession memberSession, MemberDelete memberDelete) {
        Member member = memberRepository.getById(memberSession.getId());
        if (!passwordEncoder.matches(memberDelete.getPassword(), member.getPassword())) {
            throw new PasswordNotMatchException();
        }
        memberRepository.delete(member);
    }
}
