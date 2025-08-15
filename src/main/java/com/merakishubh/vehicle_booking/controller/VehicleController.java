package com.merakishubh.vehicle_booking.controller;

import com.merakishubh.vehicle_booking.dto.OwnerServiceDto;
import com.merakishubh.vehicle_booking.dto.VehicleOwnerRegisterRequestDto;
import com.merakishubh.vehicle_booking.dto.VehicleServiceDto;
import com.merakishubh.vehicle_booking.entity.Owner;
import com.merakishubh.vehicle_booking.entity.Vehicle;
import com.merakishubh.vehicle_booking.service.CloudinaryService;
import com.merakishubh.vehicle_booking.service.OwnerService;
import com.merakishubh.vehicle_booking.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vehicle")
public class VehicleController {

    private final VehicleService vehicleService;
    private final OwnerService ownerService;
    private final CloudinaryService cloudinaryService;


    @PostMapping(value = "/registerVehicleAndOwner", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> registerVehicleAndOwner(
            @ModelAttribute @Valid VehicleOwnerRegisterRequestDto vehicleOwnerRegisterRequestDto,
            @RequestPart("vehicleImage")MultipartFile vehicleImage
            ){

        String imageUrl = cloudinaryService.uploadFile(vehicleImage);
        System.out.println("Imageurl ------------------------->" + imageUrl);

        Owner owner = ownerService.registerOwner(vehicleOwnerRegisterRequestDto);

        Vehicle vehicle =  vehicleService.registerVehicle(vehicleOwnerRegisterRequestDto, imageUrl, owner);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Vehicle and Owner registered successfully!");

    }
}
