package com.merakishubh.vehicle_booking.service.impl;

import com.merakishubh.vehicle_booking.dto.BookingRequestDto;
import com.merakishubh.vehicle_booking.dto.CreateRenterRequestDto;
import com.merakishubh.vehicle_booking.dto.RenterLoginRequestDto;
import com.merakishubh.vehicle_booking.dto.RenterLoginResponseDto;
import com.merakishubh.vehicle_booking.entity.Renter;
import com.merakishubh.vehicle_booking.entity.Vehicle;
import com.merakishubh.vehicle_booking.error.EmailAlreadyExistsException;
import com.merakishubh.vehicle_booking.repository.RenterRepository;
import com.merakishubh.vehicle_booking.repository.VehicleRepository;
import com.merakishubh.vehicle_booking.security.AuthUtil;
import com.merakishubh.vehicle_booking.service.EmailService;
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
    private final VehicleRepository vehicleRepository;
    private final EmailService emailService;

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

    @Override
    public void bookVehicle(String token, String vehicleNumber, BookingRequestDto bookingRequestDto) {
        String jwt = token.startsWith("Bearer ") ? token.substring(7) : token;
        String email = authUtil.getEmailFromToken(jwt);

        Renter renter = renterRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Renter not found"));

        Vehicle vehicle = vehicleRepository.findByVehicleNumber(vehicleNumber)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        // check time availability
        if (bookingRequestDto.getStartTime().isBefore(vehicle.getAvailableFrom()) ||
                bookingRequestDto.getEndTime().isAfter(vehicle.getAvailableTo())) {
            throw new RuntimeException("Vehicle not available in the requested time span");
        }

        if (vehicle.getIsBooked()) {
            throw new RuntimeException("Vehicle is already booked");
        }

        vehicle.setIsBooked(true);
        vehicleRepository.save(vehicle);

        // send confirmation email
        emailService.sendBookingConfirmationEmail(
                email,
                renter.getName(),
                vehicle.getVehicleNumber(),
                bookingRequestDto.getSource(),
                bookingRequestDto.getDestination(),
                bookingRequestDto.getStartTime(),
                bookingRequestDto.getEndTime()
        );
    }



}
