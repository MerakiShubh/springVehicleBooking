package com.merakishubh.vehicle_booking.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vehicleName;
    private String modelName;
    private String vehicleNumber;
    private String vehiclePicture;

    private LocalTime availableFrom;
    private LocalTime availableTo;
    private Boolean isBooked = false;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;
}
