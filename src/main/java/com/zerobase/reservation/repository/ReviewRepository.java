package com.zerobase.reservation.repository;

import com.zerobase.reservation.domain.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByUserIdAndId(Long userId, Long id);
    Optional<Review> findByManagerIdAndId(Long managerId, Long id);
}
