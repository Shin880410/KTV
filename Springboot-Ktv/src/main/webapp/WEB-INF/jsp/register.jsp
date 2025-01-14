<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<html lang="zh-TW">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>會員註冊</title>
	<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
	<link rel="stylesheet" href="./css/layout.css">
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
	<div class="container mt-5">
		<h2>會員註冊</h2>
		<form class="needs-validation" novalidate method="post"
			action="/register">
			<div class="mb-3">
				<label for="userName" class="form-label">姓名：</label> <input
					type="text" class="form-control" name="userName" required>
				<div class="invalid-feedback">請輸入姓名</div>
			</div>

			<div class="mb-3">
				<label class="form-label">性別</label>
				<div>
					<input type="radio" class="btn-check" name="gender" id="genderMale"	value="男" required> 
						<label class="btn btn-outline-primary" for="genderMale">男</label>
						<input type="radio" class="btn-check" name="gender" id="genderFemale" value="女" required>
						<label class="btn btn-outline-primary" for="genderFemale">女</label>
					<div class="invalid-feedback">請選擇性別</div>
				</div>
			</div>

			<div class="mb-3">
				<label for="email" class="form-label">信箱：</label>
				<input type="email"	class="form-control" name="email" required>
				<div class="invalid-feedback">請輸入有效的信箱</div>
			</div>
			
			<div class="mb-3">
                <label for="password" class="form-label">密碼：</label>
                <input type="password" class="form-control" name="password" id="password" pattern=".{8,}" required>
                <div class="invalid-feedback">密碼不可為空白，並至少8個字</div>
            </div>
            
            <div class="mb-3">
                <label for="confirmPassword" class="form-label">確認密碼：</label>
                <input type="password" class="form-control" id="confirmPassword" pattern=".{8,}" required>
                <div class="invalid-feedback">密碼不相符，請再次輸入密碼</div>
            </div>
			
			<div class="mb-3">
				<label for="phone" class="form-label">手機號碼：</label>
				<input type="text" id="phone" name="phone" pattern="\d{10}" required>
				<div class="invalid-feedback">請輸入有效的手機號碼</div>
			</div>
			

			<button type="submit" class="btn btn-primary">註冊</button>
			<button type="button" class="btn btn-secondary"	onclick="window.location='/index';">返回</button>
		</form>
	</div>

	<script	src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"></script>
	<script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	<script>
        // Bootstrap 5 表單驗證
        (function() {
            'use strict';
            var forms = document.querySelectorAll('.needs-validation');
            Array.prototype.slice.call(forms).forEach(function(form) {
                form.addEventListener('submit', function(event) {
                    if (!form.checkValidity()) {
                        event.preventDefault();
                        event.stopPropagation();
                    } else {
	                    // 檢查密碼和確認密碼是否相符
	                    var password = document.getElementById('password').value;
	                    var confirmPassword = document.getElementById('confirmPassword').value;
	                    if(password !== confirmPassword) {
		                    event.preventDefault();
		                    event.stopPropagation();		                    
		                    document.getElementById('confirmPassword').setCustomValidity('密碼不相符');
	                        document.getElementById('confirmPassword').reportValidity();
	                    } else {
	                        document.getElementById('confirmPassword').setCustomValidity('');
	                    }
                    }
                    form.classList.add('was-validated');
                }, false);
            });
        
     // 即時驗證密碼和確認密碼是否匹配
        document.getElementById('confirmPassword').addEventListener('input', function() {
            var password = document.getElementById('password').value;
            var confirmPassword = document.getElementById('confirmPassword').value;
            if (password !== confirmPassword) {
                document.getElementById('confirmPassword').setCustomValidity('密碼不相符');
            } else {
                document.getElementById('confirmPassword').setCustomValidity('');
            }
        });
    })();
        
	window.addEventListener('DOMContentLoaded', (event) => {
		var errorMessage = document.getElementById('error-message').textContent;
	    var successMessage = document.getElementById('success-message').textContent;
	
	    if (errorMessage) {
	        Swal.fire('註冊失敗', errorMessage, 'error');
	    }
	
	    if (successMessage) {
	        Swal.fire('註冊成功', successMessage, 'success').then((result) => {
	            if (result.isConfirmed) {
	                window.location.href = '/index';
	            }
	        });
	    }
	});
	</script>
	<div id="error-message" style="display: none;"><c:out value="${errorMessage}" /></div>
    <div id="success-message" style="display: none;"><c:out value="${successMessage}" /></div>
</body>
</html>