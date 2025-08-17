package com.merakishubh.vehicle_booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Data
@Getter
@Setter
@AllArgsConstructor
public class VehicleResponseDto {
    private String vehicleName;
    private String modelName;
    private String vehicleNumber;
    private String vehiclePicture;
    private LocalTime availableFrom;
    private LocalTime availableTo;
}
