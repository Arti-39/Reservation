package com.zerobase.reservation.service.application;

import com.zerobase.domain.common.UserType;
import com.zerobase.domain.config.JwtAuthenticationProvider;
import com.zerobase.reservation.domain.SignInForm;
import com.zerobase.reservation.domain.model.Manager;
import com.zerobase.reservation.domain.model.User;
import com.zerobase.reservation.exception.CustomException;
import com.zerobase.reservation.exception.ErrorCode;
import com.zerobase.reservation.service.ManagerService;
import com.zerobase.reservation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignInApplication {
    private final UserService userService;
    private final ManagerService managerService;
    private final JwtAuthenticationProvider provider;

    // 매장 이용자 로그인
    public String userLoginToken(SignInForm form) {
        // 로그인 가능 여부
        User user = userService.findValidUser(form.getEmail(), form.getPassword())
                .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_CHECK_FAIL));

        return provider.createToken(user.getEmail(), user.getId(), UserType.USER);
    }

    // 매장 점장 로그인
    public String managerLoginToken(SignInForm form) {
        // 로그인 가능 여부
        Manager manager = managerService.findValidManager(form.getEmail(), form.getPassword())
                .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_CHECK_FAIL));

        return provider.createToken(manager.getEmail(), manager.getId(), UserType.MANAGER);
    }
}
