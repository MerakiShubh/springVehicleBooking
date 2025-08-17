package com.merakishubh.vehicle_booking.error;

public class EmailAlreadyExistsException  extends RuntimeException{
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
