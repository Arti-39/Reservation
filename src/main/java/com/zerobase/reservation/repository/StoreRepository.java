package com.zerobase.reservation.repository;

import com.zerobase.reservation.domain.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByManagerIdAndId(Long managerId, Long id);
    List<Store> searchByName(String name);
}
