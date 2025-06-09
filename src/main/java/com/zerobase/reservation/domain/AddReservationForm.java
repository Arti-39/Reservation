package com.zerobase.reservation.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddReservationForm {
    private Long userId;
    private Long storeId;
    private String storeName;
    private LocalDateTime time;
}
