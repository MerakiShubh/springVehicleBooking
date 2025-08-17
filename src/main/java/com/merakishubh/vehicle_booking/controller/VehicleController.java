package com.merakishubh.vehicle_booking.controller;

import com.merakishubh.vehicle_booking.dto.*;
import com.merakishubh.vehicle_booking.entity.Owner;
import com.merakishubh.vehicle_booking.entity.Vehicle;
import com.merakishubh.vehicle_booking.service.CloudinaryService;
import com.merakishubh.vehicle_booking.service.OwnerService;
import com.merakishubh.vehicle_booking.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.util.ToStringUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vehicle")
public class VehicleController {

    @GetMapping("/hello")
    public String Hello(){
        return "hello";
    }

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

    @PostMapping("/ownerLogin")
    public ResponseEntity<OwnerLoginResponseDto> ownerLogin(@RequestBody OwnerLoginRequestDto ownerLoginRequestDto){
        System.out.println("Login api -------------------------------------->" + ownerLoginRequestDto.getEmail() + ownerLoginRequestDto.getPassword());
        return ResponseEntity.ok(ownerService.loginOwner(ownerLoginRequestDto));
    }

    @GetMapping("/getVehicles")
    public ResponseEntity<List<GetOwnerVehiclesResponseDto>> getOwnerVehiclesByEmail(
            @RequestHeader("Authorization") String token) {

        List<GetOwnerVehiclesResponseDto> response = vehicleService.getOwnerVehicles(token);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/addMoreVehicle", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addMoreVehicle(
            @RequestHeader("Authorization") String token,
            @ModelAttribute @Valid AddMoreVehicleRequestDto addMoreVehicleRequestDto,
            @RequestPart("vehicleImage")MultipartFile vehicleImage
    ){
        String imageUrl = cloudinaryService.uploadFile(vehicleImage);
        vehicleService.addMoreVehicle(token, imageUrl, addMoreVehicleRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Vehicle added successfully!");
    }

    @PutMapping("/updateVehicle/{vehicleNumber}")
    public ResponseEntity<Vehicle> updateVehicle(
            @RequestHeader("Authorization") String token,
            @PathVariable String vehicleNumber,
            @RequestBody UpdateVehicleRequestDto dto) {

        Vehicle updatedVehicle = vehicleService.updateVehicle(token, vehicleNumber, dto);
        return ResponseEntity.ok(updatedVehicle);
    }

    @DeleteMapping("/delete/{vehicleNumber}")
    public ResponseEntity<String> deleteVehicle(
            @PathVariable String vehicleNumber,
            @RequestHeader("Authorization") String token
    ){
        vehicleService.deleteVehicle(vehicleNumber, token);
        return ResponseEntity.ok("Vehicle deleted successfully");
    }

    @GetMapping("/available")
    public ResponseEntity<List<VehicleResponseDto>> getAvailableVehicles() {
        return ResponseEntity.ok(vehicleService.getAvailableVehicles());
    }
}
