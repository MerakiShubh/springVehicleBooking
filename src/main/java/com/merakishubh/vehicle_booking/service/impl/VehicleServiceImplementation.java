package com.merakishubh.vehicle_booking.service.impl;

import com.merakishubh.vehicle_booking.dto.OwnerServiceDto;
import com.merakishubh.vehicle_booking.dto.VehicleOwnerRegisterRequestDto;
import com.merakishubh.vehicle_booking.dto.VehicleServiceDto;
import com.merakishubh.vehicle_booking.entity.Owner;
import com.merakishubh.vehicle_booking.entity.Vehicle;
import com.merakishubh.vehicle_booking.repository.VehicleRepository;
import com.merakishubh.vehicle_booking.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleServiceImplementation implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final ModelMapper modelMapper;

    @Override
    public Vehicle registerVehicle(VehicleOwnerRegisterRequestDto vehicleOwnerRegisterRequestDto, String imageUrl, Owner owner) {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleName(vehicleOwnerRegisterRequestDto.getVehicleName());
        vehicle.setModelName(vehicleOwnerRegisterRequestDto.getModelName());
        vehicle.setVehicleNumber(vehicleOwnerRegisterRequestDto.getVehicleNumber());
        vehicle.setAvailableFrom(vehicleOwnerRegisterRequestDto.getAvailableFrom());
        vehicle.setAvailableTo(vehicleOwnerRegisterRequestDto.getAvailableTo());
        vehicle.setVehiclePicture(imageUrl);
        vehicle.setOwner(owner);
        return vehicleRepository.save(vehicle);
    }
}
