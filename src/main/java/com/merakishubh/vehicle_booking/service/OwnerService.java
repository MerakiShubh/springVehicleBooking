package com.merakishubh.vehicle_booking.service;

import com.merakishubh.vehicle_booking.dto.OwnerServiceDto;
import com.merakishubh.vehicle_booking.dto.VehicleOwnerRegisterRequestDto;
import com.merakishubh.vehicle_booking.entity.Owner;

public interface OwnerService {
    Owner registerOwner(VehicleOwnerRegisterRequestDto vehicleOwnerRegisterRequestDto);
}
