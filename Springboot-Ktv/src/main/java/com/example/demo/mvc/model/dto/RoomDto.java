package com.example.demo.mvc.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {
	
	private Integer roomId; // 房號
	private String roomName; // 房間名稱
	private Integer roomSize;  // 房間大小
}
