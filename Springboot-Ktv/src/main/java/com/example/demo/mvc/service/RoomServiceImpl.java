package com.example.demo.mvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.mvc.dao.RoomDao;
import com.example.demo.mvc.model.po.Room;

@Service
public class RoomServiceImpl implements RoomService {

	@Autowired
	@Qualifier("roomDaoImpl")
	private RoomDao roomDao;

	@Override
	public List<Room> findAllRooms() {
		return roomDao.findALL();
	}

	@Override
	public Room findRoomById(Integer roomId) {
		return roomDao.findById(roomId).orElse(null);
	}
}