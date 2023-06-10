package com.sendquiz.member.presentation;

import com.sendquiz.global.annotation.Login;
import com.sendquiz.jwt.dto.response.JwtResponse;
import com.sendquiz.member.application.MemberCommand;
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

import static com.sendquiz.jwt.application.JwtService.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class MemberController {

    private final MemberCommand memberCommand;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody @Valid MemberSignup memberSignup) {
        memberCommand.signup(memberSignup);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody MemberLogin memberLogin,
                                             HttpServletResponse response) {
        JwtResponse jwtResponse = memberCommand.login(memberLogin, response);
        return ResponseEntity.ok(jwtResponse);
    }

    @GetMapping("/member")
    public ResponseEntity<MemberResponse> getMember(@Login MemberSession memberSession) {
        MemberResponse memberResponse = getMemberResponse(memberSession);
        return ResponseEntity.ok(memberResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Login MemberSession memberSession) {
        memberCommand.logout(memberSession);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<Void> deleteMember(@Login MemberSession memberSession,
                                       @RequestBody MemberDelete memberDelete) {
        memberCommand.delete(memberSession, memberDelete);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/member")
    public ResponseEntity<Void> updateMember(@Login MemberSession memberSession,
                                       @RequestBody @Valid MemberUpdate memberUpdate) {
        memberCommand.update(memberSession, memberUpdate);
        return ResponseEntity.ok().build();
    }
}
