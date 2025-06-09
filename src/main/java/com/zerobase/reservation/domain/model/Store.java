package com.zerobase.reservation.domain.model;

import com.zerobase.reservation.domain.AddStoreForm;
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
public class Store extends BaseEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long managerId;
    private String name;
    private String address;
    private String description;
    private boolean available;

    public static Store of(Long managerId, AddStoreForm form) {
        return Store.builder()
                .managerId(managerId)
                .name(form.getName())
                .address(form.getAddress())
                .description(form.getDescription())
                .available(form.isAvailable())
                .build();
    }
}
