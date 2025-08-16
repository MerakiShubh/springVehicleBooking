package com.merakishubh.vehicle_booking.service.impl;

import com.merakishubh.vehicle_booking.dto.OwnerServiceDto;
import com.merakishubh.vehicle_booking.dto.VehicleOwnerRegisterRequestDto;
import com.merakishubh.vehicle_booking.entity.Owner;
import com.merakishubh.vehicle_booking.repository.OwnerRepository;
import com.merakishubh.vehicle_booking.service.EmailService;
import com.merakishubh.vehicle_booking.service.OwnerService;
import com.merakishubh.vehicle_booking.service.PasswordService;
import com.merakishubh.vehicle_booking.utils.PasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnerServiceImplementation implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final ModelMapper modelMapper;
    private final PasswordService passwordService;
    private final EmailService emailService;

    @Override
    public Owner registerOwner(VehicleOwnerRegisterRequestDto vehicleOwnerRegisterRequestDto) {
       Owner owner = new Owner();
       owner.setName(vehicleOwnerRegisterRequestDto.getName());
       owner.setEmail(vehicleOwnerRegisterRequestDto.getEmail());
       owner.setPhoneNo(vehicleOwnerRegisterRequestDto.getPhoneNo());

        String plainPassword = PasswordGenerator.generatePassword();

        emailService.sendPasswordEmail(vehicleOwnerRegisterRequestDto.getEmail(), plainPassword);

        String hashedPassword = passwordService.hashPassword(plainPassword);

        owner.setPassword(hashedPassword);
       return ownerRepository.save(owner);
    }
}
