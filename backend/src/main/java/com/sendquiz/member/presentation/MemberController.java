package com.sendquiz.member.presentation;

import com.sendquiz.global.annotation.Login;
import com.sendquiz.jwt.dto.JwtResponse;
import com.sendquiz.member.application.MemberService;
import com.sendquiz.member.domain.MemberSession;
import com.sendquiz.member.dto.request.MemberDelete;
import com.sendquiz.member.dto.request.MemberLogin;
import com.sendquiz.member.dto.request.MemberSignup;
import com.sendquiz.member.dto.request.MemberUpdate;
import com.sendquiz.member.dto.response.MemberResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.sendquiz.jwt.application.JwtService.getMemberResponse;


@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody @Valid MemberSignup memberSignup) {
        memberService.signup(memberSignup);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody MemberLogin memberLogin,
                                             HttpServletResponse response) {
        JwtResponse jwtResponse = memberService.login(memberLogin, response);
        return ResponseEntity.ok(jwtResponse);
    }

    @GetMapping("/member")
    public ResponseEntity<MemberResponse> get(@Login MemberSession memberSession) {
        MemberResponse memberResponse = getMemberResponse(memberSession);
        return ResponseEntity.ok(memberResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Login MemberSession memberSession) {
        memberService.logout(memberSession);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<Void> delete(@Login MemberSession memberSession,
                                       @RequestBody MemberDelete memberDelete) {
        memberService.delete(memberSession, memberDelete);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/member")
    public ResponseEntity<Void> update(@Login MemberSession memberSession,
                                       @RequestBody @Valid MemberUpdate memberUpdate) {
        memberService.update(memberSession, memberUpdate);
        return ResponseEntity.ok().build();
    }
}
