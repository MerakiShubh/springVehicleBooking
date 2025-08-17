package com.merakishubh.vehicle_booking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendPasswordEmail(String toEmail, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your Account Password");
        message.setText("Hello,\n\nYour account password is: " + password + "\n\nPlease change it after logging in.");

        mailSender.send(message);
    }

    public void sendBookingConfirmationEmail(String toEmail, String name, String vehicleNumber, String source,
                                             String destination, LocalTime startTime, LocalTime endTime) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Vehicle Booking Confirmation");
        message.setText("Hello "+ name + "\n\nYour booking was successful!\n\n"
                + "Vehicle Number: " + vehicleNumber + "\n"
                + "Source: " + source + "\n"
                + "Destination: " + destination + "\n"
                + "Start Time: " + startTime + "\n"
                + "End Time: " + endTime + "\n\n"
                + "Thank you for booking with us.");

        mailSender.send(message);

    }
}
