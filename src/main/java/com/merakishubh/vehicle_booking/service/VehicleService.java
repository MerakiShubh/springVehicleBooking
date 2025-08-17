package com.merakishubh.vehicle_booking.service;

import com.merakishubh.vehicle_booking.dto.*;
import com.merakishubh.vehicle_booking.entity.Owner;
import com.merakishubh.vehicle_booking.entity.Vehicle;
import jakarta.validation.Valid;

import java.util.List;

public interface VehicleService {
    Vehicle registerVehicle(VehicleOwnerRegisterRequestDto vehicleOwnerRegisterRequestDto, String imageUrl, Owner owner);

    List<GetOwnerVehiclesResponseDto> getOwnerVehicles(String token);

    void addMoreVehicle(String token, String imageUrl, @Valid AddMoreVehicleRequestDto addMoreVehicleRequestDto);

    Vehicle updateVehicle(String token, String vehicleNumber, UpdateVehicleRequestDto dto);

    void deleteVehicle(String vehicleNumber, String token);

    List<VehicleResponseDto> getAvailableVehicles();
}
