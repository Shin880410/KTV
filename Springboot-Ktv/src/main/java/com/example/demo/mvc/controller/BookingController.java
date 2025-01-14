package com.example.demo.mvc.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.demo.mvc.model.dto.BookingDto;
import com.example.demo.mvc.model.dto.RoomStatusDto;
import com.example.demo.mvc.model.po.Booking;
import com.example.demo.mvc.model.po.Room;
import com.example.demo.mvc.model.po.User;
import com.example.demo.mvc.model.response.ApiResponse;
import com.example.demo.mvc.service.BookingService;
import com.example.demo.mvc.service.RoomService;
import com.example.demo.mvc.service.UserService;

@Controller
@RequestMapping("/booking")
public class BookingController {
    
    @Autowired
    private BookingService bookingService;
    
    @Autowired
    private RoomService roomService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/reserve")
    public String reservePage(@ModelAttribute Booking bookingRoom,
                              @ModelAttribute Room room,
                              @SessionAttribute("userId") Integer userId,
                              Model model) {
        List<Room> rooms = roomService.findAllRooms();
        List<BookingDto> bookingDtos = bookingService.findAllBookingsByUserId(userId);
        model.addAttribute("rooms", rooms); // 給表單下拉選單用
        model.addAttribute("userId", userId); // 傳遞 userId
        model.addAttribute("bookingDtos", bookingDtos); // 給列表用
        return "reservePage"; // 確保這個名字與你的 JSP 頁面名稱一致
    }
    
    @PostMapping("/addOrUpdate")
    @ResponseBody
    public ApiResponse<String> addOrUpdate(@ModelAttribute Booking bookingRoom, @RequestParam("userId") Integer userId, @RequestParam("roomId") Integer roomId,
                    @RequestParam("date") String date, @RequestParam("time") String time, @RequestParam(value = "bookingId", required = false) Integer bookingId) {
        try {
            // 合併日期和時間
            String bookingDateTimeStr = date + " " + time;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime bookingDateTime = LocalDateTime.parse(bookingDateTimeStr, formatter);

            // 日期驗證
            if (bookingDateTime.isBefore(LocalDateTime.now())) {
                return new ApiResponse<>(false, "不能預約比今天還早的日期", null);
            }
            
            // 計算結束時間
            LocalDateTime endTime = bookingDateTime.plusHours(bookingRoom.getBookingHours());
            
            // 檢查時間是否重疊
            if (!bookingService.isTimeSlotAvailable(bookingId != null ? bookingId : -1, roomId, bookingDateTime, endTime)) {
                return new ApiResponse<>(false, "該時段已被預約", null);
            }

            // 設置 bookingDate
            bookingRoom.setBookingDate(bookingDateTime);

            User user = userService.getUserById(userId); // 確保獲取到正確的 User 對象
            Room room = roomService.findRoomById(roomId); // 確保獲取到正確的 Room 對象
            bookingRoom.setUser(user); // 設置 userId
            bookingRoom.setRoom(room); // 設置 roomId

            // 更新預約
            if (bookingId != null) {
                bookingRoom.setBookingId(bookingId); // 刪除舊的預約記錄
            }

            Integer rowcount = bookingService.addOrUpdateBooking(bookingRoom);
            String message = (bookingId != null ? "更新" : "新增") + "預約" + ((rowcount == 1) ? "成功" : "失敗");
            return new ApiResponse<>(true, message, null);
        } catch (Exception e) {
            String message = (bookingId != null ? "更新" : "新增") + "預約錯誤:";
            if (e.getMessage().contains("unique_room_id_and_booking_date")) {
                message += "該房間當日已經有人預訂"; 
            } else {
                message += e.getMessage();
            }
            return new ApiResponse<>(false, message, null);
        }
    }
    
    @GetMapping("/findAll")
    public String findAll(Model model) {
        List<BookingDto> bookingDtos = bookingService.findAllBookings();
        model.addAttribute("bookingDtos", bookingDtos); // 給列表用
        return "reservePage"; // 確保這個名字與你的 JSP 頁面名稱一致
    }
    
    @PostMapping("/cancel")
    public String cancel(@RequestParam("bookingId") Integer bookingId, @SessionAttribute("userId") Integer userId, Model model) {
        BookingDto booking = bookingService.findBookingById(bookingId);
        LocalDateTime bookingDateTime = LocalDateTime.parse(booking.getBookingDate(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        LocalDateTime now = LocalDateTime.now();
        if (bookingDateTime.minusHours(3).isBefore(now)) {
            model.addAttribute("message", "預約日期前三個小時不能再做取消");
            return reloadReservePage(model, userId);
        }
        int rowcount = bookingService.cancelBooking(bookingId);
        String message = "取消" + ((rowcount == 1) ? "成功" : "失敗");
        model.addAttribute("message", message);
        return "redirect:/booking/reserve"; // 確保這個名字與你的 JSP 頁面名稱一致
    }

    @GetMapping("/findById")
    @ResponseBody
    public BookingDto findBookingById(@RequestParam("bookingId") Integer bookingId) {
        BookingDto booking = bookingService.findBookingById(bookingId);
        return booking;
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<BookingDto> getUserReservations(@RequestParam("userId") Integer userId) {
        List<BookingDto> reservations = bookingService.findAllBookingsByUserId(userId);
        reservations.sort(Comparator.comparing(BookingDto::getBookingDate));
        return reservations;
    }

    private String reloadReservePage(Model model, Integer userId) {
        List<Room> rooms = roomService.findAllRooms();
        List<BookingDto> bookingDtos = bookingService.findAllBookingsByUserId(userId);
        
        // 過濾過期的預約
        bookingDtos.removeIf(booking -> 
            LocalDateTime.parse(booking.getBookingDate(), DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                .isBefore(LocalDateTime.now())
        );
        
        model.addAttribute("rooms", rooms); // 給表單下拉選單用
        model.addAttribute("userId", userId); // 傳遞 userId
        model.addAttribute("bookingDtos", bookingDtos); // 給列表用
        
        return "reservePage"; // 確保這個名字與你的 JSP 頁面名稱一致
    }
    
    @GetMapping("/getRoomStatus")
    @ResponseBody
    public List<RoomStatusDto> getRoomStatus(@RequestParam("date") String date) {
        LocalDate localDate = LocalDate.parse(date);
        return roomService.findAllRooms().stream()
                .map(room -> {
                    RoomStatusDto roomStatus = new RoomStatusDto();
                    roomStatus.setRoomName(room.getRoomName());
                    roomStatus.setTimeSlots(bookingService.getTimeSlotsForRoom(room.getRoomId(), localDate));
                    return roomStatus;
                })
                .collect(Collectors.toList());
    }
    
    @GetMapping("/myReservations")
    @ResponseBody
    public List<BookingDto> getMyReservations(@RequestParam("userId") Integer userId) {
        return bookingService.findAllBookingsByUserId(userId);
    }
}