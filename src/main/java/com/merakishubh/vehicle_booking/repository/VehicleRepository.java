package com.merakishubh.vehicle_booking.repository;

import com.merakishubh.vehicle_booking.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByOwnerEmail(String email);
    boolean existsByVehicleNumber(String vehicleNumber);
    Optional<Vehicle> findByVehicleNumber(String vehicleNumber);
}
