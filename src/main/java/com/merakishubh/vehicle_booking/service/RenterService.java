package com.merakishubh.vehicle_booking.service;

import com.merakishubh.vehicle_booking.dto.BookingRequestDto;
import com.merakishubh.vehicle_booking.dto.CreateRenterRequestDto;
import com.merakishubh.vehicle_booking.dto.RenterLoginRequestDto;
import com.merakishubh.vehicle_booking.dto.RenterLoginResponseDto;

public interface RenterService {

    void createRenter(CreateRenterRequestDto createRenterRequestDto);

    RenterLoginResponseDto renterLogin(RenterLoginRequestDto renterLoginRequestDto);

    void bookVehicle(String token, String vehicleNumber, BookingRequestDto bookingRequestDto);
}
