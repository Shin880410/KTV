package com.example.demo.mvc.model.dto;

import java.util.List;

public class RoomStatusDto {
    private String roomName;
    private List<TimeSlotDto> timeSlots;

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public List<TimeSlotDto> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlotDto> timeSlots) {
        this.timeSlots = timeSlots;
    }
}