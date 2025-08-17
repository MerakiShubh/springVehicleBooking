package com.merakishubh.vehicle_booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RenterLoginResponseDto {
    String jwt;
    Long id;
}
