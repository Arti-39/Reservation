package com.zerobase.reservation.service.application;

import com.zerobase.reservation.client.MailgunClient;
import com.zerobase.reservation.client.mailgun.SendMailForm;
import com.zerobase.reservation.domain.SignUpForm;
import com.zerobase.reservation.domain.model.Manager;
import com.zerobase.reservation.domain.model.User;
import com.zerobase.reservation.exception.CustomException;
import com.zerobase.reservation.exception.ErrorCode;
import com.zerobase.reservation.service.ManagerService;
import com.zerobase.reservation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SignUpApplication {
    private final MailgunClient mailgunClient;
    private final UserService userService;
    private final ManagerService managerService;

    // 매장 이용자 인증
    public void userVerify(String email, String code) {
        userService.verifyEmail(email, code);
    }

    // 매장 이용자 회원가입
    public String userSignUp(SignUpForm form) {
        if (userService.isEmailExists(form.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);
        } else {
            User user = userService.signUp(form);
            LocalDateTime now = LocalDateTime.now();

            String code = getRandomCode();
            SendMailForm sendMailForm = SendMailForm.builder()
                    .from("test@test.com")
                    .to(form.getEmail())
                    .subject("Verification Email")
                    .text(getVerificationEmailBody(user.getEmail(), user.getName(), "user", code))
                    .build();

            mailgunClient.sendEmail(sendMailForm);
            userService.changeUserValidateEmail(user.getId(), code);
            return "회원 가입 성공";
        }
    }

    // 매장 점장 인증
    public void managerVerify(String email, String code) {
        managerService.verifyEmail(email, code);
    }

    // 매장 점장 회원가입
    public String managerSignUp(SignUpForm form) {
        if (managerService.isEmailExist(form.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);
        } else {
            Manager manager = managerService.signUp(form);
            LocalDateTime now = LocalDateTime.now();

            String code = getRandomCode();
            SendMailForm sendMailForm = SendMailForm.builder()
                    .from("test@test.com")
                    .to(form.getEmail())
                    .subject("Verification Email")
                    .text(getVerificationEmailBody(manager.getEmail(), manager.getName(), "manager", code))
                    .build();

            mailgunClient.sendEmail(sendMailForm);
            managerService.changeManagerValidateEmail(manager.getId(), code);
            return "회원 가입 성공";
        }
    }

    private String getRandomCode() {
        return RandomStringUtils.random(10, true, true);
    }

    private String getVerificationEmailBody(String email, String name, String type, String code) {
        StringBuilder sb = new StringBuilder();
        return sb.append("Please Click Link for verification.\n\n")
                .append("http://localhost:8080/signup" + type + "/verify?email=")
                .append(email)
                .append("&code=")
                .append(code).toString();
    }

}
