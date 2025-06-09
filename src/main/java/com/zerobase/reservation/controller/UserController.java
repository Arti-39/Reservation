package com.zerobase.reservation.controller;

import com.zerobase.domain.common.UserVo;
import com.zerobase.domain.config.JwtAuthenticationProvider;
import com.zerobase.reservation.domain.AddReservationForm;
import com.zerobase.reservation.domain.AddReviewForm;
import com.zerobase.reservation.domain.UpdateReviewForm;
import com.zerobase.reservation.domain.dto.ReserveDto;
import com.zerobase.reservation.domain.dto.ReviewDto;
import com.zerobase.reservation.domain.dto.StoreDto;
import com.zerobase.reservation.domain.model.Review;
import com.zerobase.reservation.domain.model.User;
import com.zerobase.reservation.domain.dto.UserDto;
import com.zerobase.reservation.domain.model.Reserve;
import com.zerobase.reservation.exception.CustomException;
import com.zerobase.reservation.exception.ErrorCode;
import com.zerobase.reservation.repository.StoreRepository;
import com.zerobase.reservation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final JwtAuthenticationProvider provider;
    private final UserService userService;
    private final StoreRepository storeRepository;

    // 회원 정보 확인
    @GetMapping("/getInfo")
    public ResponseEntity<UserDto> getInfo(@RequestHeader(name = "X-AUTH-TOKEN") String token) {
        UserVo vo = provider.getUserVo(token);
        User user = userService.findByIdAndEmail(vo.getId(), vo.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return ResponseEntity.ok(UserDto.from(user));
    }

    // 매장 검색
    @GetMapping("/store")
    public ResponseEntity<List<StoreDto>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(
                storeRepository.searchByName(name)
                        .stream().map(StoreDto::from).collect(Collectors.toList()));
    }

    // 매장 상세 정보
    @GetMapping("/store/detail")
    public ResponseEntity<StoreDto> getDetail(@RequestParam Long storeId) {
        return ResponseEntity.ok(
                StoreDto.from(storeRepository.getById(storeId)));
    }

    // 예약
    @PostMapping("/reservation")
    public ResponseEntity<ReserveDto> addReservation(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                     @RequestBody AddReservationForm form) {
        UserVo vo = provider.getUserVo(token);
        Reserve reserve = userService.addReservation(vo.getId(), form);
        return ResponseEntity.ok(ReserveDto.from(reserve));
    }

    // 도착 확인
    @DeleteMapping("/reservation")
    public ResponseEntity<Void> confirmReservation(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                   @RequestParam Long id) {
        UserVo vo = provider.getUserVo(token);
        userService.confirmation(vo.getId(), id);
        return ResponseEntity.ok().build();
    }

    // 리뷰 작성
    @PostMapping("/review")
    public ResponseEntity<ReviewDto> addReview(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                               @RequestBody AddReviewForm form) {
        UserVo vo = provider.getUserVo(token);
        Review review = userService.addReview(vo.getId(), form);
        return ResponseEntity.ok(ReviewDto.from(review));
    }

    // 리뷰 수정
    @PutMapping("/review")
    public ResponseEntity<ReviewDto> updateReview(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                  @RequestBody UpdateReviewForm form) {
        UserVo vo = provider.getUserVo(token);
        Review review = userService.updateReview(vo.getId(), form);
        return ResponseEntity.ok(ReviewDto.from(review));
    }

    // 리뷰 삭제
    @DeleteMapping("/review")
    public ResponseEntity<Void> deleteReview(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                             @RequestParam Long id) {
        UserVo vo = provider.getUserVo(token);
        userService.deleteReview(vo.getId(), id);
        return ResponseEntity.ok().build();
    }
}
