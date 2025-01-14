<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>KTV預約系統</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
<link rel="stylesheet" href="./css/buttons.css">
<style>
body {
	background-image: url('./images/bg.png');
	background-size: cover;
	background-position: flex-start;
	background-repeat: no-repeat;
	font-family: Arial, sans-serif;
}

.wrapper {
	display: flex;
	flex-direction: column;
	align-items: flex-start;
	max-width: 750px;
	margin: auto;
	padding: 20px;
	border-radius: 8px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	text-align: center;
}

header {
	max-width: 900px;
	max-height: 200px;
	margin: auto;
	padding: 100px;
	text-align: center;
	margin-bottom: 50px;
	background-image: url('./images/bg-2.png');
	background-size: cover;
	background-position: center;
	background-repeat: no-repeat;
}

h1 {
	font-size: 64px;
	text-align: center;
	color: #FFF;
}

h2 {
	margin-top: 30px;
	color: #fff;
}

ul {
	list-style-type: none;
	padding: 0;
	text-align: left;
	margin-top: 20px;
}

li {
	margin-bottom: 10px;
}

.pure-button {
	background-color: #3498db;
	color: #ffffff;
	text-decoration: none;
	padding: 10px 20px;
	border-radius: 5px;
	transition: background-color 0.3s ease;
}

.pure-button:hover {
	background-color: #2980b9;
}

/* 添加紅色按鈕的樣式 */
.button-error {
	background-color: #d9534f;
}
/* 添加紅色按鈕的互動效果 */
.button-error:hover {
	background-color: #c9302c;
}

.button-container {
    padding: 5px;
    border-radius: 8px; /* 根據需要可調整 */
    margin-top: -20px; /* 根據需要可調整，可用來覆蓋 header 的底部邊距 */
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 根據需要可調整 */
    align-items: center; /* 新增這行，使子元素靠左對齊 */
}

.pure-button, .button-error {
	width: 100%; /* 或根據你的需求調整 */
	max-width: 350px; /* 防止按鈕過寬 */
	margin-top: 10px; /* 按鈕之間的間距 */
}

.login-button {
	background-color: #3498db; /* 設定次要按鈕的背景顏色 */
	color: #ffffff; /* 按鈕文字顏色設為白色 */
}

.register-button {
	background-color: #2ecc71; /* 設定成功按鈕的背景顏色 */
	color: #ffffff; /* 按鈕文字顏色設為白色 */
}

</style>
</head>
<body>
	<div class="wrapper">
		<header>
			<h1>KTV預約系統</h1>
		</header>
		<div class="button-container">
			<h2>功能選項</h2>

			<a href="/login" class="pure-button pure-button-secondary login-button">登入</a> 
			<a href="/register" class="pure-button pure-button-success register-button">註冊</a>
		</div>
	</div>
</body>
</html>
