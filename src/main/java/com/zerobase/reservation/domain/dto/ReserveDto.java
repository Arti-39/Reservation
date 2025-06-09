package com.zerobase.reservation.domain.dto;

import com.zerobase.reservation.domain.model.Reserve;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReserveDto {
    private Long id;
    private Long storeId;
    private String storeName;
    private LocalDateTime time;
    private boolean confirmed;

    public static ReserveDto from(Reserve reserve) {
        return ReserveDto.builder()
                .id(reserve.getId())
                .storeId(reserve.getStoreId())
                .storeName(reserve.getStoreName())
                .time(reserve.getTime())
                .confirmed(false)
                .build();
    }
}
