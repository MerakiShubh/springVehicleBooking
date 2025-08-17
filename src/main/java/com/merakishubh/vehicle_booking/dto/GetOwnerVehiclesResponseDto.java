package com.merakishubh.vehicle_booking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Data
@Getter
@Setter
public class GetOwnerVehiclesResponseDto {
    private Long id;

    private String vehicleName;
    private String modelName;
    private String vehicleNumber;
    private String vehiclePicture;
    private LocalTime availableFrom;
    private LocalTime availableTo;
    private Boolean isBooked;
}
