package com.zerobase.reservation.domain.model;

import com.zerobase.reservation.domain.SignUpForm;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.AuditOverride;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AuditOverride(forClass = BaseEntity.class)
public class User extends BaseEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String name;
    private String password;
    private LocalDate birthday;
    private String phoneNumber;

    private LocalDateTime verifyExpiredAt;
    private String verificationCode;
    private boolean verified;

    public static User from(SignUpForm form) {
        return User.builder()
                .email(form.getEmail().toLowerCase(Locale.ROOT))
                .name(form.getName())
                .password(form.getPassword())
                .birthday(form.getBirthday())
                .phoneNumber(form.getPhoneNumber())
                .verified(false)
                .build();
    }
}
