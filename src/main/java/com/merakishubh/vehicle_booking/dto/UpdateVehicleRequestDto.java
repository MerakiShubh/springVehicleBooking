package com.merakishubh.vehicle_booking.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Data
@Getter
@Setter
public class UpdateVehicleRequestDto {
    private String vehicleName;
    private String modelName;
    private String vehicleNumber;
    private LocalTime availableFrom;
    private LocalTime availableTo;
    private Boolean isBooked;
}
