package com.zerobase.reservation.domain.model;

import com.zerobase.reservation.domain.AddReviewForm;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Audited
@AuditOverride(forClass = BaseEntity.class)
public class Review extends BaseEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long managerId;
    private String storeName;
    private String contents;

    public static Review of(Long userId,AddReviewForm form) {
        return Review.builder()
                .userId(userId)
                .managerId(form.getManagerId())
                .storeName(form.getStoreName())
                .contents(form.getContents())
                .build();
    }
}
