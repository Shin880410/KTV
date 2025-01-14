<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>預約房間</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<style>
body {
	margin-top: 30px;
}

.container {
	display: flex;
	justify-content: space-between;
}

.left, .right {
	width: 48%;
}

.time-slots {
	display: inline-block;
	width: 80px height: 40px;
	margin: 5px;
	text-align: center;
	line-height: 40px;
	border-radius: 10px;
	cursor: pointer;
}

.time-slot.available {
	background-color: #28a745;
	color: white;
}

.time-slot.unavailable {
	background-color: #6c757d;
	color: white;
	cursor: not-allowed;
}

.time-slot:hover {
	background-color: #218838;
}

.room {
	margin-bottom: 20px;
}

.room h4 {
	margin: 0;
	padding: 5px;
	background-color: #f8f9fa;
	border-radius: 10px 10px 0 0;
}

.time-slots {
	display: flex;
	flex-wrap: wrap;
	background-color: #ffffff;
	padding: 10px;
	border-radius: 0 0 10px 10px;
}

.time-slots .time-slot {
	flex: 1 0 14%; /* 每行顯示六個方框 */
	margin: 5px;
}

@media ( max-width : 768px) {
	.time-slots .time-slot {
		flex: 1 0 30%; /* 視窗縮小時每行顯示三個方框 */
	}
}

@media ( max-width : 480px) {
	.time-slots .time-slot {
		flex: 1 0 45%; /* 視窗更小時每行顯示兩個方框 */
	}
}

.btn-check:checked + .btn-outline-primary {
    background-color: #28a745;
    color: #000;
    border-color: #28a745;
}

.btn-outline-success:hover {
    background-color: #218838;
    color: white;
    border-color: #28a745;
}
</style>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
	<div class="container">
		<div class="left">
			<h1>預約包廂</h1>
            <form id="reserveForm" action="/booking/addOrUpdate" method="post">
                <input type="hidden" id="userId" name="userId" value="${userId}" />
                <input type="hidden" id="bookingId" name="bookingId" value="" />
                
                <label for="date">日期：</label> 
                <input type="text" id="date" name="date" class="form-control" placeholder="請選擇日期" required autocomplete="off"><br>
                
                <label for="roomId">包廂選擇：</label>
				<div id="roomId" class="mb-3">
				    <c:forEach var="room" items="${rooms}">
				        <input type="radio" class="btn-check" name="roomId" id="room-${room.roomId}" value="${room.roomId}" required>
				        <label class="btn btn-outline-success room-btn" for="room-${room.roomId}">${room.roomName}</label>
				    </c:forEach>
				</div>
                
                <label for="time">時間：</label> 
                <div id="time" class="mb-3">
                    <input type="radio" class="btn-check" name="time" id=time-09:00 value="09:00" required>
				    <label class="btn btn-outline-success time-btn" for="time-09:00">09:00</label>
                    <input type="radio" class="btn-check" name="time" id="time-10:00" value="10:00" required>
			        <label class="btn btn-outline-success time-btn" for="time-10:00">10:00</label>
			        <input type="radio" class="btn-check" name="time" id="time-11:00" value="11:00" required>
			        <label class="btn btn-outline-success time-btn" for="time-11:00">11:00</label>
			        <input type="radio" class="btn-check" name="time" id="time-12:00" value="12:00" required>
			        <label class="btn btn-outline-success time-btn" for="time-12:00">12:00</label>
			        <input type="radio" class="btn-check" name="time" id="time-13:00" value="13:00" required>
			        <label class="btn btn-outline-success time-btn" for="time-13:00">13:00</label>
			        <input type="radio" class="btn-check" name="time" id="time-14:00" value="14:00" required>
			        <label class="btn btn-outline-success time-btn" for="time-14:00">14:00</label><br>
			        <input type="radio" class="btn-check" name="time" id="time-15:00" value="15:00" required>
			        <label class="btn btn-outline-success time-btn" for="time-15:00">15:00</label>
			        <input type="radio" class="btn-check" name="time" id="time-16:00" value="16:00" required>
			        <label class="btn btn-outline-success time-btn" for="time-16:00">16:00</label>
			        <input type="radio" class="btn-check" name="time" id="time-17:00" value="17:00" required>
			        <label class="btn btn-outline-success time-btn" for="time-17:00">17:00</label>
			        <input type="radio" class="btn-check" name="time" id="time-18:00" value="18:00" required>
			        <label class="btn btn-outline-success time-btn" for="time-18:00">18:00</label>
			        <input type="radio" class="btn-check" name="time" id="time-19:00" value="19:00" required>
			        <label class="btn btn-outline-success time-btn" for="time-19:00">19:00</label>
			        <input type="radio" class="btn-check" name="time" id="time-20:00" value="20:00" required>
			        <label class="btn btn-outline-success time-btn" for="time-20:00">20:00</label>
			    </div>
				
				<label for="bookingHours">預約時長：</label>
				<div id="bookingHours" class="mb-3">
				    <input type="radio" class="btn-check" name="bookingHours" id="hours-3" value="3" required>
				    <label class="btn btn-outline-success booking-hours-btn" for="hours-3">3 小時</label>
				    <input type="radio" class="btn-check" name="bookingHours" id="hours-4" value="4" required>
				    <label class="btn btn-outline-success booking-hours-btn" for="hours-4">4 小時</label>
				    <input type="radio" class="btn-check" name="bookingHours" id="hours-5" value="5" required>
				    <label class="btn btn-outline-success booking-hours-btn" for="hours-5">5 小時</label>
				</div><br>

				<button type="submit" class="btn btn-primary">預約包廂</button>
				<button type="button" class="btn btn-secondary"	onclick="window.location='/index';">返回首頁</button>
			</form><br><br>

			<h2>我的預約</h2>
			<div class="pure-form">
					<table class="pure-table pure-table-bordered" width="100%">
						<thead>
							<tr>
								<th>包廂名稱</th>
								<th>預約日期</th>
								<th>預約時長</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id="reservationsTableBody">
							<c:forEach items="${bookingDtos}" var="booking">
								<tr>
									<td>${booking.room.roomName}</td>
									<td>${booking.bookingDate}</td>
									<td>${booking.bookingHours}小時</td>
									<td>
                                        <button type="button" onclick="editReservation(${booking.bookingId})" class="btn btn-warning">修改資料</button>
										<form id="cancelForm-${booking.bookingId}" action="/booking/cancel" method="post" style="display: inline;">
										    <input type="hidden" name="bookingId" value="${booking.bookingId}" />
										    <button type="button" class="btn btn-danger" onclick="confirmCancel(${booking.bookingId})">取消預約</button>
										</form>
                                    </td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				
			</div>
		</div>

		<div class="right">
			<h2>預約狀況</h2>
			<div id="roomStatus">
				<input type="text" id="statusDate" class="form-control"
					placeholder="請選擇日期" required autocomplete="off"><br>
				<div id="rooms">
					<!-- 根據房間數量動態生成 -->
					<c:forEach var="room" items="${rooms}">
						<div class="room">
							<h4>包廂：${room.roomName}</h4>
							<div class="time-slots">
								<c:forEach var="timeSlot" items="${timeSlots}">
									<div class="time-slot ${timeSlot.status}"
										data-time="${timeSlot.time}">${timeSlot.time}</div>
								</c:forEach>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>

	<script>
	function confirmCancel(bookingId) {
        Swal.fire({
            title: '確定要取消預約嗎?',
            text: "這個操作無法撤銷!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '是的, 取消預約!',
            cancelButtonText: '取消'
        }).then((result) => {
            if (result.isConfirmed) {
                document.getElementById(`cancelForm-${bookingId}`).submit();
            }
        });
    }
    // 更新房間的預約狀況
    function updateRoomStatus(date) {
        $.get('${pageContext.request.contextPath}/booking/getRoomStatus', { date: date }, function(data) {
            $('#rooms').empty();
            data.forEach(room => {
                var roomDiv = $('<div class="room"></div>');
                var roomName = $('<h4></h4>').text(room.roomName);
                var timeSlotsDiv = $('<div class="time-slots"></div>');
                
                room.timeSlots.forEach(timeSlot => {
                    var timeSlotDiv = $('<div class="time-slot"></div>')
                        .addClass(timeSlot.status)
                        .text(timeSlot.time)
                        .attr('data-time', timeSlot.time);
                        
                    timeSlotsDiv.append(timeSlotDiv);
                });
                
                roomDiv.append(roomName).append(timeSlotsDiv);
                $('#rooms').append(roomDiv);
            });
        }).fail(function() {
            Swal.fire({
                icon: 'error',
                title: '錯誤',
                text: '無法獲取房間狀況'
            });
        });
    }
           
      function editReservation(bookingId) {
          $.get('${pageContext.request.contextPath}/booking/findById', { bookingId: bookingId }, function(data) {
              $('#bookingId').val(data.bookingId);
              var bookingDate = data.bookingDate.split('T')[0];
              $('#date').val(bookingDate);
              $("#statusDate").datepicker("setDate", bookingDate);
		updateRoomStatus(bookingDate);
		$('input[name="roomId"][value="' + data.room.roomId + '"]').prop('checked', true);
		$('input[name="bookingHours"][value="' + data.bookingHours + '"]').prop('checked', true);
		$('input[name="time"][value="' + data.bookingDate.split('T')[1].substring(0, 5) + '"]').prop('checked', true);
          }).fail(function() {
              Swal.fire({
                  icon: 'error',
                  title: '錯誤',
                  text: '無法獲取預約信息'
              });
          });
      }

        $(document).ready(function() {
            // 顯示操作結果
            var message = "${message}";
            if (message) {
                Swal.fire({
                    icon: 'info',
                    title: '訊息',
                    text: message
                });
            }
            
            // 初始化日期選擇器
            $("#date, #statusDate").datepicker({
                dateFormat: "yy-mm-dd",
                minDate: 0, // 今天以後的日期
                maxDate: "+1M", // 最多選擇30天後的日期
                onSelect: function(dateText) {
                    // 當選擇日期時，更新房間的預約狀況
                    if (this.id === 'date') {
                        $("#statusDate").datepicker("setDate", dateText);
                        updateRoomStatus(dateText);
                    } else {
                        updateRoomStatus(dateText);
                    }
                }
            });
            
            // 設置初始日期為今天
            var today = new Date().toISOString().split('T')[0];
            $("#date, #statusDate").datepicker("setDate", today);
            updateRoomStatus(today);

            
            // 預約表單提交後的行為
            $('#reserveForm').on('submit', function(event) {
                event.preventDefault();
                var form = $(this);
                $.post(form.attr('action'), form.serialize(), function(response) {
                    Swal.fire({
                    	icon: response.state ? 'success' : 'error',
                        title: response.state ? '成功' : '錯誤',
                        text: response.message
                    }).then(() => {
                    	if (response.state) {
	                        var selectedDate = $('#date').val();
	                        // 更新右邊的日期選擇器並更新預約狀況
	                        $("#statusDate").datepicker("setDate", selectedDate);
	                        updateRoomStatus(selectedDate);
	                        updateMyReservations();
                    	}
                    });
                }).fail(function() {
                    Swal.fire({
                        icon: 'error',
                        title: '錯誤',
                        text: '預約失敗'
                    });
                });
            });
            
            function updateMyReservations() {
                $.get('${pageContext.request.contextPath}/booking/myReservations', { userId: $('#userId').val() }, function(data) {
                    var reservationsTableBody = $('#reservationsTableBody');
                    reservationsTableBody.empty();
                    data.sort((a, b) => new Date(a.bookingDate) - new Date(b.bookingDate));
                    data.forEach(reservation => {
                        var bookingDateTime = new Date(reservation.bookingDate);
                        var now = new Date();
                        var threeHoursBefore = new Date(bookingDateTime.getTime() - 3 * 60 * 60 * 1000);

                        // 隱藏已經超過時間的預約
                        if (now < threeHoursBefore) {
                            var row = $('<tr></tr>');
                            row.append($('<td></td>').text(reservation.room.roomName));
                            row.append($('<td></td>').text(reservation.bookingDate));
                            row.append($('<td></td>').text(reservation.bookingHours + ' 小時'));
                            var actions = $('<td></td>');

                            if (now < bookingDateTime) {
                                actions.append('<button type="button" onclick="editReservation(' + reservation.bookingId + ')" class="btn btn-warning">修改資料</button>');
                                var cancelForm = $('<form id="cancelForm-' + reservation.bookingId + '" action="/booking/cancel" method="post" style="display:inline;">' +
                                    '<input type="hidden" name="bookingId" value="' + reservation.bookingId + '" />' +
                                    '<button type="button" class="btn btn-danger" onclick="confirmCancel(' + reservation.bookingId + ')">取消預約</button>' +
                                    '</form>');
                                actions.append(cancelForm);
                            }

                            row.append(actions);
                            reservationsTableBody.append(row);
                        }
                    });

                    // 重新綁定取消按鈕的事件處理程序
                    $('button.btn-danger').off('click').on('click', function() {
                        var bookingId = $(this).closest('form').find('input[name="bookingId"]').val();
                        confirmCancel(bookingId);
                    });
                }).fail(function() {
                    Swal.fire({
                        icon: 'error',
                        title: '錯誤',
                        text: '無法獲取預約列表'
                    });
                });
            }
        });
    </script>
</body>
</html>