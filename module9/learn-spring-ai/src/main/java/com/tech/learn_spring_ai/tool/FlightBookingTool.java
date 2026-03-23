package com.tech.learn_spring_ai.tool;

import com.tech.learn_spring_ai.dto.BookingListResponse;
import com.tech.learn_spring_ai.dto.BookingResponse;
import com.tech.learn_spring_ai.entity.BookingStatus;
import com.tech.learn_spring_ai.entity.FlightBooking;
import com.tech.learn_spring_ai.service.FlightBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FlightBookingTool {
    private final FlightBookingService flightBookingService;

    @Tool(
            name = "flight_booking_tool",
            description = """
                    Create a new flight booking.
                    
                    REQUIRED:
                    - userId
                    - destination
                    - departureTime
                    
                    If departure time is missing,
                    ask the user for date and time first.
                    """
    )
    public BookingResponse createBooking(
            @ToolParam(description = "The unique user id (e.g userId is user123)")
            String userId,
            @ToolParam(description = "The destination for the flight booking. (e.g city like Delhi,London etc)")
            String destination,
            @ToolParam(description = "Departure date and time in ISO-8601 format (e.g., 2025-12-25T14:30:00Z)")
            Instant departureTime) {

        FlightBooking booking = flightBookingService.createBooking(userId, destination, departureTime);
        return new BookingResponse(
                booking.getId(),
                booking.getDestination(),
                booking.getDepartureTime(),
                booking.getBookingStatus()
        );
    }

    @Tool(
            name = "get_user_bookings",
            description = "Retrieve all flight bookings for the current user, sorted by departure time (most recent first). " +
                    "Returns an empty list message if none exists."
    )
    public BookingListResponse getUserBookings(
            @ToolParam(description = "The unique user ID:")
            String userId
    ) {
        List<FlightBooking> bookings = flightBookingService.getUserBookings(userId);

        List<BookingResponse> responses = bookings.stream()
                .map(b -> new BookingResponse(
                        b.getId(),
                        b.getDestination(),
                        b.getDepartureTime(),
                        b.getBookingStatus()
                )).toList();
        String message = bookings.isEmpty() ? "You have no upcoming flight bookings" : "Here are your current flight of bookings.";
        return new BookingListResponse(responses, message);
    }

    @Tool(
            name = "update_booking_status",
            description = """
                    Update the status on an existing flight booking (e.g.,cancel it).
                               Only the owner of the booking can modify it.
                               Common use: set status to CANCELLED.
                    REQUIRED:
                    - bookingId
                    - userId
                    
                    If booking  id is missing,
                    ask the user for booking id.
                    """
    )
    public BookingResponse updateBookingStatus(

            @ToolParam(description = "The booking id returned from create or get bookings")
            Long bookingId,
            @ToolParam(description = "The user id who owns the booking")
            String userId,
            @ToolParam(description = "New status: CONFIRMED,CANCELLED,or PENDING")
            BookingStatus newStatus
    ) {
        FlightBooking updated = flightBookingService.updateBookingStatus(bookingId, userId, newStatus);
        return new BookingResponse(
                updated.getId(),
                updated.getDestination(),
                updated.getDepartureTime(),
                updated.getBookingStatus()
        );
    }

}
