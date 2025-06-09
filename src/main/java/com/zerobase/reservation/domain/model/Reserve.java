package com.zerobase.reservation.domain.model;

import com.zerobase.reservation.domain.AddReservationForm;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Audited
@AuditOverride(forClass = BaseEntity.class)
public class Reserve extends BaseEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long storeId;
    private String storeName;
    private LocalDateTime time;
    private boolean confirmed;

    public static Reserve of(Long userId,AddReservationForm form) {
        return Reserve.builder()
                .userId(userId)
                .storeId(form.getStoreId())
                .storeName(form.getStoreName())
                .time(form.getTime())
                .confirmed(false)
                .build();
    }
}
