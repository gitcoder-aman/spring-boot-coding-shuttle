package com.tech.learn_spring_ai.service;

import com.tech.learn_spring_ai.entity.BookingStatus;
import com.tech.learn_spring_ai.entity.FlightBooking;
import com.tech.learn_spring_ai.repository.FlightBookingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FlightBookingService {
    private final FlightBookingRepository repository;
    public FlightBooking createBooking(String userId, String destination, Instant departureTime){

        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("User ID is required.");
        }

        if (destination == null || destination.isBlank()) {
            throw new IllegalArgumentException("Destination is required.");
        }

        if (departureTime == null) {
            throw new IllegalArgumentException(
                    "Departure date and time is required to create a booking."
            );
        }
        boolean exists = repository.existsByUserIdAndDestinationAndDepartureTime(
                userId, destination, departureTime);

        if(exists){
            throw new IllegalArgumentException(
                    "You already have a booking to "+destination+" on that date"
            );
        }
        FlightBooking booking = FlightBooking.builder()
                .userId(userId)
                .destination(destination)
                .departureTime(departureTime)
                .bookingStatus(BookingStatus.CONFIRMED)
                .build();
        return repository.save(booking);
    }
    public List<FlightBooking> getUserBookings(String userId){
        return repository.findByUserIdOrderByDepartureTimeDesc(userId);
    }
    public FlightBooking updateBookingStatus(Long bookingId,String userId,BookingStatus newStatus){

        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("User ID is required.");
        }

        if (bookingId == null) {
            throw new IllegalArgumentException("Booking id is required.");
        }
        FlightBooking booking = repository.findById(bookingId).orElseThrow(()-> new IllegalArgumentException("Booking not found"));

        if(!booking.getUserId().equals(userId)){
            throw new IllegalArgumentException("You can only modify your own bookings.");
        }
        booking.setBookingStatus(newStatus);
        return repository.save(booking);
    }
}
