package com.example.demo.mvc.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mvc.dao.BookingDao;
import com.example.demo.mvc.model.dto.BookingDto;
import com.example.demo.mvc.model.dto.TimeSlotDto;
import com.example.demo.mvc.model.po.Booking;
import com.example.demo.mvc.repository.BookingRepository;

@Service
public class BookingServiceImpl implements BookingService {
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private BookingDao bookingDao;
    
    @Override
    public boolean isTimeSlotAvailable(int bookingId, int roomId, LocalDateTime startTime, LocalDateTime endTime) {
        List<Booking> bookings = bookingDao.findBookingsByRoomAndTime(roomId, startTime, endTime);
        for (Booking booking : bookings) {
            if (booking.getBookingId() != bookingId) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<BookingDto> findAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<BookingDto> findAllBookingsByUserId(Integer userId) {
    	List<Booking> bookings = bookingRepository.findByUserUserId(userId);
    	return bookings.stream()
    			.map(this::convertToDto)
    			.collect(Collectors.toList());
    }

    @Override
    public BookingDto findBookingById(Integer bookingId) {
        Booking bookingRoom = bookingRepository.findById(bookingId).orElse(null);
        return convertToDto(bookingRoom);
    }

    @Override
    public Integer cancelBooking(Integer bookingId) {
        bookingRepository.deleteById(bookingId);
        return 1;
    }

    @Override
    public boolean checkRoomAvailability(Integer roomId, LocalDateTime startTime, int bookingHours) {
        boolean available = bookingRepository.checkAvailability(roomId, startTime, bookingHours);
        System.out.println("Room availability for roomId " + roomId + " from " + startTime + " for " + bookingHours + " hours: " + available);
        return available;
    }
    
    @Override
    public Integer addOrUpdateBooking(Booking booking) {
	    bookingRepository.save(booking);
	    return 1;
    }
    
    @Override
    public List<TimeSlotDto> getTimeSlotsForRoom(Integer roomId, LocalDate date) {
        List<TimeSlotDto> timeSlots = new ArrayList<>();
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(20, 0);

        for (LocalTime time = startTime; !time.isAfter(endTime); time = time.plusHours(1)) {
            LocalDateTime dateTime = LocalDateTime.of(date, time);
            boolean isAvailable = bookingDao.findBookingsByRoomAndTime(roomId, dateTime, dateTime.plusHours(1)).isEmpty();
            timeSlots.add(new TimeSlotDto(time.toString(), isAvailable ? "available" : "unavailable"));
        }

        return timeSlots;
    }

    private BookingDto convertToDto(Booking bookingRoom) {
        if (bookingRoom == null) {
            return null;
        }
        BookingDto dto = new BookingDto();
        dto.setBookingId(bookingRoom.getBookingId());
        dto.setRoomId(bookingRoom.getRoom().getRoomId());
        dto.setUserId(bookingRoom.getUser().getUserId());
        dto.setBookingDate(bookingRoom.getBookingDate().toString());
        dto.setBookingHours(bookingRoom.getBookingHours());
        dto.setRoom(bookingRoom.getRoom());
        dto.setUser(bookingRoom.getUser());
        return dto;
    }
}