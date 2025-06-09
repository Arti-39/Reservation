package com.zerobase.reservation.domain.dto;

import com.zerobase.reservation.domain.model.Store;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StoreDto {
    private Long id;
    private String name;
    private String address;
    private String description;
    private boolean available;

    public static StoreDto from(Store store) {
        return StoreDto.builder()
                .id(store.getId())
                .name(store.getName())
                .address(store.getAddress())
                .description(store.getDescription())
                .available(store.isAvailable())
                .build();
    }
}
