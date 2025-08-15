package com.merakishubh.vehicle_booking.repository;

import com.merakishubh.vehicle_booking.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository <Owner, Long> {
}
