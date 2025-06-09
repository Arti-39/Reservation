package com.zerobase.reservation.controller;

import com.zerobase.reservation.service.application.SignInApplication;
import com.zerobase.reservation.domain.SignInForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/signIn")
@RequiredArgsConstructor
public class SignInController {
    private final SignInApplication signInApplication;

    // 매장 이용자 로그인
    @PostMapping("/user")
    public ResponseEntity<String> signInUser(@RequestBody SignInForm form) {
        return ResponseEntity.ok(signInApplication.userLoginToken(form));
    }

    // 매장 점장 로그인
    @PostMapping("/manager")
    public ResponseEntity<String> signInManager(@RequestBody SignInForm form) {
        return ResponseEntity.ok(signInApplication.managerLoginToken(form));
    }
}
