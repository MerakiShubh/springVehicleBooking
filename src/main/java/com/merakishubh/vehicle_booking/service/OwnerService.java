package com.merakishubh.vehicle_booking.service;

import com.merakishubh.vehicle_booking.dto.GetOwnerVehiclesResponseDto;
import com.merakishubh.vehicle_booking.dto.OwnerLoginRequestDto;
import com.merakishubh.vehicle_booking.dto.OwnerLoginResponseDto;
import com.merakishubh.vehicle_booking.dto.VehicleOwnerRegisterRequestDto;
import com.merakishubh.vehicle_booking.entity.Owner;

import java.util.List;

public interface OwnerService {
    Owner registerOwner(VehicleOwnerRegisterRequestDto vehicleOwnerRegisterRequestDto);
    OwnerLoginResponseDto loginOwner(OwnerLoginRequestDto ownerLoginRequestDto);
}
