<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %> 
<!DOCTYPE html>
<html lang="zh-TW">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>登入頁面</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
 	 .form-control {
	  width: 350px;
    }
    .captcha-refresh img {
      width: 30px;
      height: 30px;
    }
    .captcha-refresh {
      border: none;
      background: none;
      padding: 0;
    }
    .captcha-refresh:focus, .captcha-refresh:active {
      background: none;
      box-shadow: none;
    }
    .captcha-container {
      display: flex;
      align-items: center;
    }
    .captcha-input {
      width: 150px;
    }
    .login-container {
	    display: flex;
	    justify-content: center;
	    align-items: center;
	    height: 70vh;
	    padding-top: 50px;
    }
  </style>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
  <div class="container login-container">
    <div class="row justify-content-center">
      <div class="col-md-6">
        <h2 class="text-center">登入</h2>
    <form id="loginForm" class="needs-validation" novalidate action="/processLogin" method="post">
      <div class="mb-3">
        <label for="email" class="form-label">信箱</label>
        <input type="email" class="form-control" id="email" name="email" required>
        <div class="invalid-feedback">請輸入信箱</div>
      </div>
      
      <div class="mb-3">
        <label for="password" class="form-label">密碼</label>
        <input type="password" class="form-control" id="password" name="password" required>
        <div class="invalid-feedback">請輸入密碼</div>
      </div>
     

      <div class="mb-3">
		  <label for="captcha" class="form-label">驗證碼 (不分大小寫)</label>
		  <div class="captcha-container">
		    <input type="text" class="form-control captcha-input" id="captcha" name="captcha" maxlength="6" required>
		    <img src="/captcha" id="captchaImage" alt="Captcha" class="captcha-image">
		    <button type="button" class="btn btn-outline-secondary captcha-refresh" id="refreshCaptcha">
			    <img src="/images/refresh.png" alt="Refresh">
		    </button>
		  </div>
		  <div class="invalid-feedback">請輸入驗證碼</div>
	  </div>
      
      <button type="submit" class="btn btn-primary">登入</button>
      <button type="button" class="btn btn-secondary" onclick="window.location='/index';">返回</button>
      <a href="#" class="btn btn-link">忘記密碼</a>
    </form>
  </div>

  <script>
    $(document).ready(function() {
      $('#loginForm').on('submit', function(event) {
        if (!this.checkValidity()) {
          event.preventDefault();
          event.stopPropagation();
        }
        $(this).addClass('was-validated');
      });
      
   	  // 刷新驗證碼
      $('#refreshCaptcha').on('click', function() {
        $('#captchaImage').attr('src', '/captcha?' + new Date().getTime());
      });

  	  // 檢查是否有錯誤訊息
      <c:if test="${not empty errorMessage}">
        Swal.fire({
          icon: 'error',
          title: '錯誤',
          text: '${errorMessage}'
        });
      </c:if>
    });
  </script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>