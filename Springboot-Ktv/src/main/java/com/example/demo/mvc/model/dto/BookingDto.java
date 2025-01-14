package com.example.demo.mvc.model.dto;

import com.example.demo.mvc.model.po.Room;
import com.example.demo.mvc.model.po.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    
    private Integer bookingId; // 預約編號
    private Integer roomId; // 房號
    private Integer userId; // 會員編號
    private String bookingDate; // 預約日期
    private Integer bookingHours; // 預約時長
    private Room room; // 包廂
    private User user; // 使用者

    // 添加設置方法
    public void setRoom(Room room) {
        this.room = room;
    }

    public void setUser(User user) {
        this.user = user;
    }
}