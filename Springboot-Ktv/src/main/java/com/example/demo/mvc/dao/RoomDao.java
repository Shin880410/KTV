package com.example.demo.mvc.dao;

import java.util.List;
import java.util.Optional;

import com.example.demo.mvc.model.po.Room;


public interface RoomDao {
	List<Room> findALL(); // 查詢所有房間
	Optional<Room> findById(Integer roomId);
}
