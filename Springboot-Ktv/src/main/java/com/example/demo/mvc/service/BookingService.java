package com.example.demo.mvc.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.mvc.model.dto.BookingDto;
import com.example.demo.mvc.model.dto.TimeSlotDto;
import com.example.demo.mvc.model.po.Booking;

public interface BookingService {
	boolean checkRoomAvailability(Integer roomId, LocalDateTime startTime, int bookingHours);
	boolean isTimeSlotAvailable(int bookingId, int roomId, LocalDateTime startTime, LocalDateTime endTime);
    List<BookingDto> findAllBookings();
    List<BookingDto> findAllBookingsByUserId(Integer userId);
    BookingDto findBookingById(Integer bookingId);
    Integer cancelBooking(Integer bookingId);
    Integer addOrUpdateBooking(Booking booking);
    List<TimeSlotDto> getTimeSlotsForRoom(Integer roomId, LocalDate date);
}