package com.example.demo.mvc.dao;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.mvc.model.dto.BookingDto;
import com.example.demo.mvc.model.po.Booking;

public interface BookingDao {
    Integer addBooking(Booking booking);
    Integer cancelBooking(Integer bookingId);
    BookingDto findBookingById(Integer bookingId);
    List<BookingDto> findAllBookingsByUserId(Integer userId);
    List<Booking> findBookingsByRoomAndTime(int roomId, LocalDateTime startTime, LocalDateTime endTime);
    boolean update(Booking booking);
}