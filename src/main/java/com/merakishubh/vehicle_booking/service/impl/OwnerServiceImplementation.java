package com.merakishubh.vehicle_booking.service.impl;

import com.merakishubh.vehicle_booking.dto.OwnerLoginRequestDto;
import com.merakishubh.vehicle_booking.dto.OwnerLoginResponseDto;
import com.merakishubh.vehicle_booking.dto.OwnerServiceDto;
import com.merakishubh.vehicle_booking.dto.VehicleOwnerRegisterRequestDto;
import com.merakishubh.vehicle_booking.entity.Owner;
import com.merakishubh.vehicle_booking.error.ApiError;
import com.merakishubh.vehicle_booking.error.EmailAlreadyExistsException;
import com.merakishubh.vehicle_booking.repository.OwnerRepository;
import com.merakishubh.vehicle_booking.security.AuthUtil;
import com.merakishubh.vehicle_booking.service.EmailService;
import com.merakishubh.vehicle_booking.service.OwnerService;
import com.merakishubh.vehicle_booking.service.PasswordService;
import com.merakishubh.vehicle_booking.utils.PasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

        if(ownerRepository.findByEmail(vehicleOwnerRegisterRequestDto.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists: " + vehicleOwnerRegisterRequestDto.getEmail());
        }
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

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;

    @Override
    public OwnerLoginResponseDto loginOwner(OwnerLoginRequestDto ownerLoginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(ownerLoginRequestDto.getEmail(), ownerLoginRequestDto.getPassword())
        );

        Owner owner = (Owner) authentication.getPrincipal();

        String token = authUtil.generateAccessToken(owner);
        return new OwnerLoginResponseDto(token, owner.getId());
    }
}
