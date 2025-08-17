package com.merakishubh.vehicle_booking.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Data
@Getter
@Setter
public class BookingRequestDto {
    private String source;
    private String destination;
    private LocalTime startTime;
    private LocalTime endTime;
}
