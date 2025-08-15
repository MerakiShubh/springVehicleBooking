package com.merakishubh.vehicle_booking.service;

import com.merakishubh.vehicle_booking.dto.OwnerServiceDto;
import com.merakishubh.vehicle_booking.dto.VehicleOwnerRegisterRequestDto;
import com.merakishubh.vehicle_booking.dto.VehicleServiceDto;
import com.merakishubh.vehicle_booking.entity.Owner;
import com.merakishubh.vehicle_booking.entity.Vehicle;

public interface VehicleService {
    Vehicle registerVehicle(VehicleOwnerRegisterRequestDto vehicleOwnerRegisterRequestDto, String imageUrl, Owner owner);
}
