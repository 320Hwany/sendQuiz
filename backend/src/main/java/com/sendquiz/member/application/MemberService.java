package com.sendquiz.member.application;

import com.sendquiz.certification.domain.Certification;
import com.sendquiz.certification.exception.CertificationNotMatchException;
import com.sendquiz.certification.repository.CertificationRepository;
import com.sendquiz.member.domain.Member;
import com.sendquiz.member.dto.request.MemberLogin;
import com.sendquiz.member.dto.request.MemberSignup;
import com.sendquiz.member.exception.MemberDuplicationException;
import com.sendquiz.member.exception.MemberNotMatchException;
import com.sendquiz.member.repository.MemberRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Optional;

import static com.sendquiz.global.constant.HiddenConstant.JWT_KEY;


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

    public String login(MemberLogin memberLogin) {
        Member member = memberRepository.getByEmail(memberLogin.getEmail());
        if (passwordEncoder.matches(memberLogin.getPassword(), member.getPassword())) {
            SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(JWT_KEY));
            return Jwts.builder()
                    .setSubject(String.valueOf(member.getId()))
                    .signWith(key)
                    .compact();
        }
        throw new MemberNotMatchException();
    }
}
