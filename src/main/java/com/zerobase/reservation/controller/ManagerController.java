package com.zerobase.reservation.controller;

import com.zerobase.domain.common.ManagerVo;
import com.zerobase.domain.config.JwtAuthenticationProvider;
import com.zerobase.reservation.domain.AddStoreForm;
import com.zerobase.reservation.domain.UpdateReservationForm;
import com.zerobase.reservation.domain.UpdateStoreForm;
import com.zerobase.reservation.domain.dto.ManagerDto;
import com.zerobase.reservation.domain.dto.ReserveDto;
import com.zerobase.reservation.domain.dto.StoreDto;
import com.zerobase.reservation.domain.model.Manager;
import com.zerobase.reservation.domain.model.Reserve;
import com.zerobase.reservation.domain.model.Store;
import com.zerobase.reservation.exception.CustomException;
import com.zerobase.reservation.exception.ErrorCode;
import com.zerobase.reservation.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {
    private final JwtAuthenticationProvider provider;
    private final ManagerService managerService;

    // 회원 정보 확인
    @GetMapping("/getInfo")
    public ResponseEntity<ManagerDto> getInfo(@RequestHeader(name = "X-AUTH-TOKEN") String token) {
        ManagerVo vo = provider.getManagerVo(token);
        Manager manager = managerService.findByIdAndEmail(vo.getId(), vo.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return ResponseEntity.ok(ManagerDto.from(manager));
    }

    // 매장 정보 등록
    @PostMapping("/store")
    public ResponseEntity<StoreDto> addStore(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                             @RequestBody AddStoreForm form) {
        ManagerVo vo = provider.getManagerVo(token);
        Store store = managerService.addStore(vo.getId(), form);
        return ResponseEntity.ok(StoreDto.from(store));
    }

    // 매장 정보 수정
    @PutMapping("/store")
    public ResponseEntity<StoreDto> updateStore(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                             @RequestBody UpdateStoreForm form) {
        ManagerVo vo = provider.getManagerVo(token);
        Store store = managerService.updateStore(vo.getId(), form);
        return ResponseEntity.ok(StoreDto.from(store));
    }

    // 매장 정보 삭제
    @DeleteMapping("/store")
    public ResponseEntity<Void> deleteStore(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                            @RequestParam Long id) {
        ManagerVo vo = provider.getManagerVo(token);
        managerService.deleteStore(vo.getId(), id);
        return ResponseEntity.ok().build();
    }

    // 리뷰 삭제
    @DeleteMapping("/review")
    public ResponseEntity<Void> deleteReview(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                             @RequestParam Long id) {
        ManagerVo vo = provider.getManagerVo(token);
        managerService.deleteReview(vo.getId(), id);
        return ResponseEntity.ok().build();
    }

    // 예약 승인
    @PutMapping("/reservation")
    public ResponseEntity<ReserveDto> updateReservation(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                        @RequestBody UpdateReservationForm form) {
        ManagerVo vo = provider.getManagerVo(token);
        Reserve reserve = managerService.updateReservation(vo.getId(), form);
        return ResponseEntity.ok(ReserveDto.from(reserve));
    }

    // 예약 거절
    @DeleteMapping("/reservation")
    public ResponseEntity<Void> deleteReservation(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                  @RequestParam Long id) {
        ManagerVo vo = provider.getManagerVo(token);
        managerService.deleteReservation(vo.getId(), id);
        return ResponseEntity.ok().build();
    }
}
