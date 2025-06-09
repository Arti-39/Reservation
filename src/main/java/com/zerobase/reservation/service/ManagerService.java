package com.zerobase.reservation.service;

import com.zerobase.reservation.domain.AddStoreForm;
import com.zerobase.reservation.domain.SignUpForm;
import com.zerobase.reservation.domain.UpdateReservationForm;
import com.zerobase.reservation.domain.UpdateStoreForm;
import com.zerobase.reservation.domain.model.Manager;
import com.zerobase.reservation.domain.model.Reserve;
import com.zerobase.reservation.domain.model.Review;
import com.zerobase.reservation.domain.model.Store;
import com.zerobase.reservation.exception.CustomException;
import com.zerobase.reservation.exception.ErrorCode;
import com.zerobase.reservation.repository.ManagerRepository;
import com.zerobase.reservation.repository.ReservationRepository;
import com.zerobase.reservation.repository.ReviewRepository;
import com.zerobase.reservation.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepository managerRepository;
    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;

    public Optional<Manager> findByIdAndEmail(Long id, String email) {
        return managerRepository.findByIdAndEmail(id, email);
    }

    public  Optional<Manager> findValidManager(String email, String password) {
        return managerRepository.findByEmailAndPasswordAndVerifyIsTrue(email, password);
    }

    public Manager signUp(SignUpForm form) {
        return managerRepository.save(Manager.from(form));
    }

    public boolean isEmailExist(String email) {
        return managerRepository.findByEmail(email).isPresent();
    }

    @Transactional
    public void verifyEmail(String email, String code) {
        Manager manager = managerRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        if (manager.isVerified()) {
            throw new CustomException(ErrorCode.ALREADY_VERIFY);
        } else if (!manager.getVerificationCode().equals(code)) {
            throw new CustomException(ErrorCode.WRONG_VERIFICATION);
        } else if (manager.getVerifyExpiredAt().isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.EXPIRE_CODE);
        }
        manager.setVerified(true);
    }

    @Transactional
    public LocalDateTime changeManagerValidateEmail(Long UserId, String verificationCode) {
        Optional<Manager> managerOptional = managerRepository.findById(UserId);

        if (managerOptional.isPresent()) {
            Manager manager = managerOptional.get();
            manager.setVerificationCode(verificationCode);
            manager.setVerifyExpiredAt(LocalDateTime.now().plusDays(1));
            return manager.getVerifyExpiredAt();
        }
        throw new CustomException(ErrorCode.NOT_FOUND_USER);
    }

    // 매장 정보 등록
    @Transactional
    public Store addStore(Long managerId, AddStoreForm form) {
        return storeRepository.save(Store.of(managerId, form));
    }

    // 매장 정보 수정
    @Transactional
    public Store updateStore(Long managerId, UpdateStoreForm form) {
        Store store = storeRepository.findByManagerIdAndId(managerId, form.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_STORE));
        store.setName(form.getName());
        store.setAddress(form.getAddress());
        store.setDescription(form.getDescription());
        store.setAvailable(form.isAvailable());
        return store;
    }

    // 매장 정보 삭제
    @Transactional
    public void deleteStore(Long managerId, Long storeId) {
        Store store = storeRepository.findByManagerIdAndId(managerId, storeId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_STORE));
        storeRepository.delete(store);
    }

    // 리뷰 삭제
    @Transactional
    public void deleteReview(Long managerId, Long reviewId) {
        Review review = reviewRepository.findByManagerIdAndId(managerId, reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_REVIEW));
        reviewRepository.delete(review);
    }

    // 예약 승인
    @Transactional
    public Reserve updateReservation(Long managerId, UpdateReservationForm form) {
        Reserve reserve = reservationRepository.findByManagerIdAndId(managerId, form.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_RESERVATION));
        reserve.setConfirmed(true);
        return reserve;
    }

    // 예약 거절
    @Transactional
    public void deleteReservation(Long managerId, Long reserveId) {
        Reserve reserve = reservationRepository.findByManagerIdAndId(managerId, reserveId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_RESERVATION));
        reservationRepository.delete(reserve);
    }
}
