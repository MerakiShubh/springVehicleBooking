package com.merakishubh.vehicle_booking.service.impl;

import com.merakishubh.vehicle_booking.dto.AddMoreVehicleRequestDto;
import com.merakishubh.vehicle_booking.dto.GetOwnerVehiclesResponseDto;
import com.merakishubh.vehicle_booking.dto.UpdateVehicleRequestDto;
import com.merakishubh.vehicle_booking.dto.VehicleOwnerRegisterRequestDto;
import com.merakishubh.vehicle_booking.entity.Owner;
import com.merakishubh.vehicle_booking.entity.Vehicle;
import com.merakishubh.vehicle_booking.repository.OwnerRepository;
import com.merakishubh.vehicle_booking.repository.VehicleRepository;
import com.merakishubh.vehicle_booking.security.AuthUtil;
import com.merakishubh.vehicle_booking.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleServiceImplementation implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final OwnerRepository ownerRepository;
    private final ModelMapper modelMapper;

    @Override
    public Vehicle registerVehicle(VehicleOwnerRegisterRequestDto vehicleOwnerRegisterRequestDto, String imageUrl, Owner owner) {
        if (vehicleRepository.existsByVehicleNumber(vehicleOwnerRegisterRequestDto.getVehicleNumber())) {
            throw new RuntimeException("Vehicle number already exists!");
        }
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleName(vehicleOwnerRegisterRequestDto.getVehicleName());
        vehicle.setModelName(vehicleOwnerRegisterRequestDto.getModelName());
        vehicle.setVehicleNumber(vehicleOwnerRegisterRequestDto.getVehicleNumber());
        vehicle.setAvailableFrom(vehicleOwnerRegisterRequestDto.getAvailableFrom());
        vehicle.setAvailableTo(vehicleOwnerRegisterRequestDto.getAvailableTo());
        vehicle.setVehiclePicture(imageUrl);
        vehicle.setOwner(owner);
        return vehicleRepository.save(vehicle);
    }

    private final AuthUtil authUtil;

    @Override
    public List<GetOwnerVehiclesResponseDto> getOwnerVehicles(String token) {
        String jwt = token.startsWith("Bearer ") ? token.substring(7) : token;

        String email = authUtil.getEmailFromToken(jwt);

        List<Vehicle> vehicles = vehicleRepository.findByOwnerEmail(email);

        return vehicles.stream().map(v -> {
            GetOwnerVehiclesResponseDto dto = new GetOwnerVehiclesResponseDto();
            dto.setId(v.getId());
            dto.setVehicleName(v.getVehicleName());
            dto.setModelName(v.getModelName());
            dto.setVehicleNumber(v.getVehicleNumber());
            dto.setVehiclePicture(v.getVehiclePicture());
            dto.setAvailableFrom(v.getAvailableFrom());
            dto.setAvailableTo(v.getAvailableTo());
            dto.setIsBooked(v.getIsBooked());
            return dto;
        }).toList();
    }

    @Override
    public void addMoreVehicle(String token, String imageUrl, AddMoreVehicleRequestDto addMoreVehicleRequestDto) {
        String jwt = token.startsWith("Bearer ") ? token.substring(7) : token;
        String email = authUtil.getEmailFromToken(jwt);

        // Find owner

        Owner owner = ownerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        if (vehicleRepository.existsByVehicleNumber(addMoreVehicleRequestDto.getVehicleNumber())) {
            throw new RuntimeException("Vehicle number already exists!");
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleName(addMoreVehicleRequestDto.getVehicleName());
        vehicle.setModelName(addMoreVehicleRequestDto.getModelName());
        vehicle.setVehicleNumber(addMoreVehicleRequestDto.getVehicleNumber());
        vehicle.setAvailableFrom(addMoreVehicleRequestDto.getAvailableFrom());
        vehicle.setAvailableTo(addMoreVehicleRequestDto.getAvailableTo());
        vehicle.setVehiclePicture(imageUrl);

        vehicle.setOwner(owner);

        vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle updateVehicle(String token, String vehicleNumber, UpdateVehicleRequestDto dto) {
        String jwt = token.startsWith("Bearer ") ? token.substring(7) : token;
        String email = authUtil.getEmailFromToken(jwt);

        Owner owner = ownerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        Vehicle vehicle = vehicleRepository.findByVehicleNumber(vehicleNumber)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        if (!vehicle.getOwner().getId().equals(owner.getId())) {
            throw new RuntimeException("You are not authorized to update this vehicle");
        }

        if (dto.getVehicleName() != null) vehicle.setVehicleName(dto.getVehicleName());
        if (dto.getModelName() != null) vehicle.setModelName(dto.getModelName());

        if (dto.getVehicleNumber() != null && !dto.getVehicleNumber().equals(vehicle.getVehicleNumber())) {
            // Checking this vehicle number already exists ?
            vehicleRepository.findByVehicleNumber(dto.getVehicleNumber())
                    .ifPresent(v -> { throw new RuntimeException("Vehicle number already exists"); });
            vehicle.setVehicleNumber(dto.getVehicleNumber());
        }

        if (dto.getAvailableFrom() != null) vehicle.setAvailableFrom(dto.getAvailableFrom());
        if (dto.getAvailableTo() != null) vehicle.setAvailableTo(dto.getAvailableTo());
        if (dto.getIsBooked() != null) vehicle.setIsBooked(dto.getIsBooked());

        return vehicleRepository.save(vehicle);
    }

    @Override
    public void deleteVehicle(String vehicleNumber, String token) {
        String jwt = token.startsWith("Bearer ") ? token.substring(7) : token;
        String email = authUtil.getEmailFromToken(jwt);

        Owner owner = ownerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        Vehicle vehicle = vehicleRepository.findByVehicleNumber(vehicleNumber)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        if (!vehicle.getOwner().getId().equals(owner.getId())) {
            throw new RuntimeException("You are not authorized to delete this vehicle");
        }
        vehicleRepository.delete(vehicle);
    }
}
