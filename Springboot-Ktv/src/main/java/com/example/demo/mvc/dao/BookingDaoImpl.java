package com.example.demo.mvc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.mvc.model.dto.BookingDto;
import com.example.demo.mvc.model.po.Booking;
import com.example.demo.mvc.model.po.Room;
import com.example.demo.mvc.model.po.User;

@Repository
public class BookingDaoImpl implements BookingDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Integer addBooking(Booking booking) {
        String sql = "INSERT INTO booking_room (user_id, room_id, booking_date, booking_hours) VALUES (?, ?, ?, ?)";
		return jdbcTemplate.update(sql, booking.getUser().getUserId(), 
                                                booking.getRoom().getRoomId(),
                                                booking.getBookingDate(),
                                                booking.getBookingHours());
    }

    @Override
    public Integer cancelBooking(Integer bookingId) {
        String sql = "DELETE FROM booking_room WHERE booking_id = ?";
        return jdbcTemplate.update(sql, bookingId);
    }

    @Override
    public BookingDto findBookingById(Integer bookingId) {
        String sql = "SELECT  "
                + "    b.booking_id, b.room_id, b.user_id, b.booking_date, b.booking_hours, "
                + "    r.room_name, "
                + "    u.user_name "
                + "FROM  "
                + "booking_room b "
                + "LEFT JOIN room r ON b.room_id = r.room_id "
                + "LEFT JOIN users u ON b.user_id = u.user_id "
                + "WHERE b.booking_id = ?";
        
        return jdbcTemplate.queryForObject(sql, new BookingDtoRowMapper(), bookingId);
    }

    @Override
    public List<BookingDto> findAllBookingsByUserId(Integer userId) {
        String sql = "SELECT  "
                + "    b.booking_id, b.room_id, b.user_id, b.booking_date, b.booking_hours, "
                + "    r.room_name, "
                + "    u.user_name "
                + "FROM  "
                + "booking_room b "
                + "LEFT JOIN room r ON b.room_id = r.room_id "
                + "LEFT JOIN users u ON b.user_id = u.user_id "
                + "WHERE b.user_id = ?";
        
        return jdbcTemplate.query(sql, new BookingDtoRowMapper(), userId);
    }

    @Override
    public List<Booking> findBookingsByRoomAndTime(int roomId, LocalDateTime startTime, LocalDateTime endTime) {
        String sql = "SELECT * FROM booking_room WHERE room_id = ? AND booking_date < ? AND booking_date + INTERVAL booking_hours HOUR > ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Booking.class), roomId, endTime, startTime);
    }
    
    @Override
    public boolean update(Booking booking) {
        String sql = "UPDATE booking_room SET user_id = ?, room_id = ?, booking_date = ?, booking_hours = ? WHERE booking_id = ?";
        int rowcount = jdbcTemplate.update(sql, booking.getUser().getUserId(), 
                                                booking.getRoom().getRoomId(),
                                                booking.getBookingDate(),
                                                booking.getBookingHours(),
                                                booking.getBookingId());
        return rowcount > 0;
    }
    
    private static class BookingDtoRowMapper implements RowMapper<BookingDto> {
        @Override
        public BookingDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            Integer bookingId = rs.getInt("booking_id");
            Integer roomId = rs.getInt("room_id");
            Integer userId = rs.getInt("user_id");
            String bookingDate = rs.getString("booking_date");
            Integer bookingHours = rs.getInt("booking_hours");
            String roomName = rs.getString("room_name");
            String userName = rs.getString("user_name");
            
            Room room = new Room();
            room.setRoomId(roomId);
            room.setRoomName(roomName);
            
            User user = new User();
            user.setUserId(userId);
            user.setUserName(userName);
            
            BookingDto dto = new BookingDto();
            dto.setBookingId(bookingId);
            dto.setRoomId(roomId);
            dto.setUserId(userId);
            dto.setBookingDate(bookingDate);
            dto.setBookingHours(bookingHours);
            dto.setRoom(room);
            dto.setUser(user);
            return dto;
        }
    }
}