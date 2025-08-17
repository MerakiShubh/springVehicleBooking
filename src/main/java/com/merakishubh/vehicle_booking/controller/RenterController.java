package com.merakishubh.vehicle_booking.controller;

import com.merakishubh.vehicle_booking.dto.*;
import com.merakishubh.vehicle_booking.service.RenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/renter")
public class RenterController {

    private final RenterService renterService;
    @PostMapping("/createRenter")
    public ResponseEntity<String> createRenter(@RequestBody CreateRenterRequestDto createRenterRequestDto){
        renterService.createRenter(createRenterRequestDto);
        return ResponseEntity.ok("Renter created successfully!");
    }

    @PostMapping("/loginRenter")
    public ResponseEntity<RenterLoginResponseDto> renterLogin(@RequestBody RenterLoginRequestDto renterLoginRequestDto){
        return ResponseEntity.ok(  renterService.renterLogin(renterLoginRequestDto));
    }

    @PostMapping("/booking/{vehicleNumber}")
    public ResponseEntity<String> bookVehicle(
            @RequestHeader("Authorization") String token,
            @PathVariable String vehicleNumber,
            @RequestBody BookingRequestDto bookingRequestDto
            ){
        renterService.bookVehicle(token, vehicleNumber, bookingRequestDto);
        return ResponseEntity.ok("Vehicle booked successfully!");
    }
}
