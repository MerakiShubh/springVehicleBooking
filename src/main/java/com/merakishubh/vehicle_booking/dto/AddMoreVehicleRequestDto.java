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
public class AddMoreVehicleRequestDto {

    @NotBlank(message = "Vehicle name is required")
    private String vehicleName;

    @NotBlank(message = "Model name is required")
    private String modelName;

    @NotBlank(message = "Vehicle number is required")
    private String vehicleNumber;

    @NotNull(message = "Available from time is required")
    private LocalTime availableFrom;

    @NotNull(message = "Available to time is required")
    private LocalTime availableTo;
}
