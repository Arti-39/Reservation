package com.zerobase.reservation.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStoreForm {
    private Long id;
    private Long managerId;
    private String name;
    private String address;
    private String description;
    private boolean available;
}
