package com.example.demo.mvc.service;

import java.util.List;

import com.example.demo.mvc.model.po.Room;

public interface RoomService {
    List<Room> findAllRooms();
    Room findRoomById(Integer roomId);
}