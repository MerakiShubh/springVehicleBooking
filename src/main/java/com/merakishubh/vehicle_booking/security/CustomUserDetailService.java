package com.merakishubh.vehicle_booking.security;

import com.merakishubh.vehicle_booking.repository.OwnerRepository;
import com.merakishubh.vehicle_booking.repository.RenterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final OwnerRepository ownerRepository;
    private final RenterRepository renterRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return ownerRepository.findByEmail(email)
                .map(owner -> (UserDetails) owner)
                .or(() -> renterRepository.findByEmail(email).map(renter -> (UserDetails) renter))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }
}
