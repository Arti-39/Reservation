package com.zerobase.reservation.domain.dto;

import com.zerobase.reservation.domain.model.Manager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ManagerDto {
    private Long id;
    private String email;

    public static ManagerDto from(Manager manager) {
        return new ManagerDto(manager.getId(), manager.getEmail());
    }
}
