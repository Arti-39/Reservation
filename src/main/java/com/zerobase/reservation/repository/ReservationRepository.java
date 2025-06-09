package com.zerobase.reservation.repository;

import com.zerobase.reservation.domain.model.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reserve, Long> {
    Optional<Reserve> findByUserIdAndId(Long userId, Long id);
    Optional<Reserve> findByManagerIdAndId(Long managerId, Long id);
}
