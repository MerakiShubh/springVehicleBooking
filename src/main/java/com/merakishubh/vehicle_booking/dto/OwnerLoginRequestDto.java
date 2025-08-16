package com.merakishubh.vehicle_booking.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class OwnerLoginRequestDto {
    private String email;
    private String password;
}
