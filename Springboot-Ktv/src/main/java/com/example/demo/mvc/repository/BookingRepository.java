package com.example.demo.mvc.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.mvc.model.po.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> findByUserUserId(Integer userId);

    @Query(value = "SELECT COUNT(*) FROM booking_room WHERE room_id = :roomId AND NOT ((DATE_ADD(booking_date, INTERVAL booking_hours HOUR) <= :startTime) OR booking_date >= :endTime)", nativeQuery = true)
    Long countOverlappingBookings(@Param("roomId") Integer roomId, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    // 檢查房間是否已被預約
    default boolean checkAvailability(Integer roomId, LocalDateTime startTime, int bookingHours) {
        // 計算結束時間
        LocalDateTime endTime = startTime.plusHours(bookingHours);
        // 如果已有相同的則無法預約
        return countOverlappingBookings(roomId, startTime, endTime) == 0;
    }
}