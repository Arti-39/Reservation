package com.zerobase.reservation.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    ALREADY_REGISTER_USER(HttpStatus.BAD_REQUEST, "이미 가입된 회원입니다."),
    WRONG_VERIFICATION(HttpStatus.BAD_REQUEST, "잘못된 인증 시도입니다."),
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "일치하는 회원이 없습니다."),
    EXPIRE_CODE(HttpStatus.BAD_REQUEST, "인증 시간이 만료되었습니다."),
    ALREADY_VERIFY(HttpStatus.BAD_REQUEST, "이미 인증이 완료되었습니다."),
    LOGIN_CHECK_FAIL(HttpStatus.BAD_REQUEST, "아이디나 패스워드를 확인해주세요."),

    NOT_FOUND_STORE(HttpStatus.BAD_REQUEST, "매장명을 찾을 수 없습니다."),
    NOT_FOUND_RESERVATION(HttpStatus.BAD_REQUEST, "예약을 찾을 수 없습니다."),
    NOT_FOUND_REVIEW(HttpStatus.BAD_REQUEST, "리뷰를 찾을 수 없습니다."),
    ALREADY_RESERVED(HttpStatus.BAD_REQUEST, "이미 예약 되어있습니다."),
    CANCELED_RESERVATION(HttpStatus.BAD_REQUEST, "예약이 취소되었습니다."),
    UNAVAILABLE_STORE(HttpStatus.BAD_REQUEST, "매장을 이용할 수 없습니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
