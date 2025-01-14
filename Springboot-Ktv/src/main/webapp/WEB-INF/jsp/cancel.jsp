<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>取消預約</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="./css/layout.css">
</head>

<body>
	<div class="container">
		<h1>取消預約</h1>

		<form id="cancelForm">
			<br> <label for="date">日期：</label> <input type="text" id="date"
				name="date" placeholder="請選擇日期" required><br> <br>
			<br> <br>

			<button type="submit">取消預約</button>
		</form>
		<div class="back-btn">
			<button onclick="goBack()">返回上一頁</button>
		</div>

	</div>
	<script>
		$(function() {
			$("#date").datepicker(); // 將 #date 元素轉換為日期選擇器
		});
		function goBack() {
			window.history.back();
		}
	</script>
</body>
</html>
