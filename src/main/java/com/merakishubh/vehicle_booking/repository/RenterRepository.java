package com.merakishubh.vehicle_booking.repository;

import com.merakishubh.vehicle_booking.entity.Renter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RenterRepository extends JpaRepository<Renter, Long> {
    Optional<Renter> findByEmail(String email);
}
