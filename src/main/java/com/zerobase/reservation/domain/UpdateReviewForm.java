package com.zerobase.reservation.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateReviewForm {
    private Long id;
    private Long userId;
    private String storeName;
    private String contents;
}
