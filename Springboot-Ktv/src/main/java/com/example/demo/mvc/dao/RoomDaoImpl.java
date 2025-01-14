package com.example.demo.mvc.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.mvc.model.po.Room;

@Repository
public class RoomDaoImpl implements RoomDao {

    private final JdbcTemplate jdbcTemplate;
    
    public RoomDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Room> findALL() {
        String sql = "SELECT room_id, room_name, room_size FROM room ORDER BY room_id";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Room.class));
    }
    
    @Override
    public Optional<Room> findById(Integer roomId) {
        String sql = "SELECT room_id, room_name, room_size FROM room WHERE room_id = ?";
        Room room = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Room.class), roomId);
        return Optional.ofNullable(room);
    }
}