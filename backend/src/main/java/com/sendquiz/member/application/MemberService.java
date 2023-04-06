package com.sendquiz.member.application;

import com.sendquiz.certification.domain.Certification;
import com.sendquiz.certification.exception.CertificationNotMatchException;
import com.sendquiz.certification.repository.CertificationRepository;
import com.sendquiz.member.domain.Member;
import com.sendquiz.member.domain.MemberSession;
import com.sendquiz.member.dto.request.MemberLogin;
import com.sendquiz.member.dto.request.MemberSignup;
import com.sendquiz.member.dto.response.MemberResponse;
import com.sendquiz.member.exception.MemberDuplicationException;
import com.sendquiz.member.exception.MemberNotMatchException;
import com.sendquiz.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.sendquiz.member.domain.MemberSession.*;
import static com.sendquiz.member.dto.response.MemberResponse.*;

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

    public MemberResponse login(MemberLogin memberLogin, HttpServletRequest request) {
        Member member = memberRepository.getByEmail(memberLogin.getEmail());
        if (passwordEncoder.matches(memberLogin.getPassword(), member.getPassword())) {
            MemberSession memberSession = toMemberSession(member);
            memberSession.makeSession(request);
            return toMemberResponse(memberSession);
        }
        throw new MemberNotMatchException();
    }
}
