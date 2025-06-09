package com.zerobase.reservation.controller;

import com.zerobase.reservation.service.application.SignUpApplication;
import com.zerobase.reservation.domain.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignUpController {
    private final SignUpApplication signUpApplication;

    // 매장 이용자 회원가입
    @PostMapping("/user")
    public ResponseEntity<String> userSignUp(@RequestBody SignUpForm form) {
        return ResponseEntity.ok(signUpApplication.userSignUp(form));
    }

    // 매장 이용자 인증
    @GetMapping("/user/verify")
    public ResponseEntity<String> verifyUser(String email, String code) {
        signUpApplication.userVerify(email, code);
        return ResponseEntity.ok("인증이 완료되었습니다.");
    }

    // 매장 점장 회원가입
    @PostMapping("/manager")
    public ResponseEntity<String> managerSignUp(@RequestBody SignUpForm form) {
        return ResponseEntity.ok(signUpApplication.managerSignUp(form));
    }

    // 매장 점장 인증
    @GetMapping("/manager/verify")
    public ResponseEntity<String> verifyManager(String email, String code) {
        signUpApplication.managerVerify(email, code);
        return ResponseEntity.ok("인증이 완료되었습니다.");
    }
}
