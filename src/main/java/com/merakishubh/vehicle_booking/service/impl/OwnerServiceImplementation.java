package com.merakishubh.vehicle_booking.service.impl;

import com.merakishubh.vehicle_booking.dto.OwnerServiceDto;
import com.merakishubh.vehicle_booking.dto.VehicleOwnerRegisterRequestDto;
import com.merakishubh.vehicle_booking.entity.Owner;
import com.merakishubh.vehicle_booking.repository.OwnerRepository;
import com.merakishubh.vehicle_booking.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnerServiceImplementation implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final ModelMapper modelMapper;

    @Override
    public Owner registerOwner(VehicleOwnerRegisterRequestDto vehicleOwnerRegisterRequestDto) {
       Owner owner = new Owner();
       owner.setName(vehicleOwnerRegisterRequestDto.getName());
       owner.setEmail(vehicleOwnerRegisterRequestDto.getEmail());
       owner.setPhoneNo(vehicleOwnerRegisterRequestDto.getPhoneNo());
       return ownerRepository.save(owner);
    }
}
