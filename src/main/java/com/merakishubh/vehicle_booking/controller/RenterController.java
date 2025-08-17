package com.merakishubh.vehicle_booking.controller;

import com.merakishubh.vehicle_booking.dto.CreateRenterRequestDto;
import com.merakishubh.vehicle_booking.dto.RenterLoginRequestDto;
import com.merakishubh.vehicle_booking.dto.RenterLoginResponseDto;
import com.merakishubh.vehicle_booking.service.RenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
