package com.zerobase.reservation.service;

import com.zerobase.reservation.domain.*;
import com.zerobase.reservation.domain.model.Review;
import com.zerobase.reservation.domain.model.Store;
import com.zerobase.reservation.domain.model.User;
import com.zerobase.reservation.domain.model.Reserve;
import com.zerobase.reservation.exception.CustomException;
import com.zerobase.reservation.exception.ErrorCode;
import com.zerobase.reservation.repository.ReservationRepository;
import com.zerobase.reservation.repository.ReviewRepository;
import com.zerobase.reservation.repository.StoreRepository;
import com.zerobase.reservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;


    public Optional<User> findByIdAndEmail(Long id, String email) {
        return userRepository.findById(id)
                .stream().filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    public Optional<User> findValidUser(String email, String password) {
        return userRepository.findByEmail(email)
                .stream()
                .filter(user -> user.getPassword().equals(password) && user.isVerified())
                .findFirst();
    }

    public User signUp(SignUpForm form) {
        return userRepository.save(User.from(form));
    }

    public boolean isEmailExists(String email) {
        return userRepository.findByEmail(email.toLowerCase(Locale.ROOT))
                .isPresent();
    }

    @Transactional
    public void verifyEmail(String email, String code) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        if (user.isVerified()) {
            throw new CustomException(ErrorCode.ALREADY_VERIFY);
        } else if (!user.getVerificationCode().equals(code)) {
            throw new CustomException(ErrorCode.WRONG_VERIFICATION);
        } else if (user.getVerifyExpiredAt().isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.EXPIRE_CODE);
        }
        user.setVerified(true);
    }

    @Transactional
    public LocalDateTime changeUserValidateEmail(Long UserId, String verificationCode) {
        Optional<User> userOptional = userRepository.findById(UserId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setVerificationCode(verificationCode);
            user.setVerifyExpiredAt(LocalDateTime.now().plusDays(1));
            return user.getVerifyExpiredAt();
        }
        throw new CustomException(ErrorCode.NOT_FOUND_USER);
    }

    // 예약
    @Transactional
    public Reserve addReservation(Long userId, AddReservationForm form) {
        Store store = storeRepository.findById(form.getStoreId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_STORE));
        if (!store.isAvailable()) {
            throw new CustomException(ErrorCode.UNAVAILABLE_STORE);
        }
        return reservationRepository.save(Reserve.of(userId, form));
    }

    // 도착 확인
    @Transactional
    public void confirmation(Long userId, Long reserveId) {
        Reserve reserve = reservationRepository.findByUserIdAndId(userId, reserveId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_RESERVATION));

        reservationRepository.delete(reserve);

        // 예약 10분전에 도착하지 않았을 시
        if (LocalDateTime.now().isAfter(reserve.getTime().minusMinutes(10))) {
            throw new CustomException(ErrorCode.CANCELED_RESERVATION);
        }
    }

    // 리뷰 등록
    @Transactional
    public Review addReview(Long userId, AddReviewForm form) {
        return reviewRepository.save(Review.of(userId, form));
    }

    // 리뷰 수정
    @Transactional
    public Review updateReview(Long userId, UpdateReviewForm form) {
        Review review = reviewRepository.findByUserIdAndId(userId, form.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_REVIEW));
        review.setContents(form.getContents());
        return review;
    }

    // 리뷰 삭제
    @Transactional
    public void deleteReview(Long userId, Long reviewId) {
        Review review = reviewRepository.findByUserIdAndId(userId, reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_REVIEW));
        reviewRepository.delete(review);
    }
}
