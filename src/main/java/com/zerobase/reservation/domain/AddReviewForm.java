package com.zerobase.reservation.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddReviewForm {
    private Long userId;
    private Long managerId;
    private String storeName;
    private String contents;
}
