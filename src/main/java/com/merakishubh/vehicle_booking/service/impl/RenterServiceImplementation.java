package com.merakishubh.vehicle_booking.service.impl;

import com.merakishubh.vehicle_booking.dto.CreateRenterRequestDto;
import com.merakishubh.vehicle_booking.dto.RenterLoginRequestDto;
import com.merakishubh.vehicle_booking.dto.RenterLoginResponseDto;
import com.merakishubh.vehicle_booking.entity.Renter;
import com.merakishubh.vehicle_booking.error.EmailAlreadyExistsException;
import com.merakishubh.vehicle_booking.repository.RenterRepository;
import com.merakishubh.vehicle_booking.security.AuthUtil;
import com.merakishubh.vehicle_booking.service.RenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RenterServiceImplementation implements RenterService {

    private final RenterRepository renterRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;

    @Override
    public void createRenter(CreateRenterRequestDto createRenterRequestDto) {
        if(renterRepository.findByEmail(createRenterRequestDto.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists: " + createRenterRequestDto.getEmail());
        }
        Renter renter = new Renter();
        renter.setEmail(createRenterRequestDto.getEmail());
        renter.setPassword(passwordEncoder.encode(createRenterRequestDto.getPassword()));
        renter.setName(createRenterRequestDto.getName());
        renterRepository.save(renter);
    }

    @Override
    public RenterLoginResponseDto renterLogin(RenterLoginRequestDto renterLoginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(renterLoginRequestDto.getEmail(), renterLoginRequestDto.getPassword())
        );

        Renter renter = (Renter) authentication.getPrincipal();
        String token = authUtil.generateAccessToken(renter);

        return new RenterLoginResponseDto(token, renter.getId());
    }


}
