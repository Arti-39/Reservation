package com.zerobase.reservation.domain.dto;

import com.zerobase.reservation.domain.model.Review;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private Long id;
    private Long managerId;
    private String storeName;
    private String contents;

    public static ReviewDto from(Review review) {
        return ReviewDto.builder()
                .id(review.getId())
                .managerId(review.getManagerId())
                .storeName(review.getStoreName())
                .contents(review.getContents())
                .build();
    }
}
