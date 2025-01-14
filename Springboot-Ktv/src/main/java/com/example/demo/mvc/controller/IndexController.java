package com.example.demo.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.mvc.CaptchaUtil;
import com.example.demo.mvc.model.po.User;
import com.example.demo.mvc.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class IndexController {
	
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

	private final UserService userService;
	
	// 注入UserService
	public IndexController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/index")
	public String showIndexPage(HttpServletRequest req) {
		HttpSession session = req.getSession();
		Integer userId = (Integer) session.getAttribute("userId");
		
		if (userId != null) {
			// 登入後的頁面
			return "index";
		} else {
			// 未登入時頁面
			return "index2";
		}
	}

	// 顯示註冊畫面
	@GetMapping("/register")
	public String registerPage() {
		return "register"; // 返回 register.jsp
	}

	// 處理註冊請求
	@PostMapping("/register")
	public String registerUser(User newUser, RedirectAttributes red, HttpServletRequest req) {
		// 檢查信箱是否已存在
		boolean emailExists = userService.isEmailValid(newUser.getEmail());
		if (emailExists) {
			// 信箱已存在，顯示錯誤訊息
			red.addFlashAttribute("errorMessage", "信箱已被註冊，請重新輸入。");
			return "redirect:/register"; // 重導回註冊頁面
		}
		
		// 確保 permissions 欄位不為空
		newUser.setPermissions(0); // 默認為普通用戶(0)
		
		// 格式化電話號碼
		String formattedPhoneNumber = formatPhoneNumber(newUser.getPhone());
		newUser.setPhone(formattedPhoneNumber);
		
		
		boolean isUserAdded = userService.addUser(newUser);
		if (isUserAdded) {
			// 註冊成功，重新導向，將用戶狀態設為true，並導向登入後主頁
			req.getSession().setAttribute("isUserLoggedIn", true);
			red.addFlashAttribute("successMessage", "註冊成功!");
			return "redirect:/index";
		} else {
			// 註冊失敗顯示錯誤訊息
			red.addFlashAttribute("errorMessage", "用戶名可能已存在或其他錯誤，請重試。");
			return "redirect:/register"; // 重導回註冊頁面
		}
	}
	
	private String formatPhoneNumber(String phoneNumber) {
	    return phoneNumber.replaceFirst("(\\d{4})(\\d{3})(\\d{3})", "$1-$2-$3");
	}
	
	// 顯示登入畫面
    @GetMapping("/login")
    public String loginPage(HttpServletRequest req) {
        logger.info("Showing login page");
        req.setAttribute("isLoginPage", true);
        return "login"; // 返回 login.jsp
    }
    
    // 生成驗證碼圖片
    @GetMapping("/captcha")
    public void getCaptcha(HttpServletResponse response, HttpSession session) {
        response.setContentType("image/jpeg");
        String captcha = CaptchaUtil.generateCaptcha(response);
        session.setAttribute("captcha", captcha);
    }
	
 // 處理登入請求
    @PostMapping("/processLogin")
    public String loginUser(User user, RedirectAttributes red, HttpServletRequest req) {
        logger.info("Processing login for email: {}", user.getEmail());

        HttpSession session = req.getSession();
        String captcha = (String) session.getAttribute("captcha");
        String userCaptcha = req.getParameter("captcha");

        // 驗證驗證碼
        if (captcha == null || !captcha.equalsIgnoreCase(userCaptcha)) {
            logger.warn("Incorrect captcha for email: {}", user.getEmail());
            red.addFlashAttribute("errorMessage", "驗證碼錯誤");
            return "redirect:/login"; // 重導回登入頁面
        }

        if (!userService.isEmailValid(user.getEmail())) {
            logger.warn("Email does not exist: {}", user.getEmail());
            red.addFlashAttribute("errorMessage", "該信箱不存在");
            return "redirect:/login"; // 重導回登入頁面
        }

        if (!userService.isPasswordValid(user.getEmail(), user.getPassword())) {
            logger.warn("Incorrect password for email: {}", user.getEmail());
            red.addFlashAttribute("errorMessage", "密碼錯誤");
            return "redirect:/login"; // 重導回登入頁面
        }

        Integer userId = userService.loginUser(user);
        if (userId != null) {
            logger.info("User logged in successfully with userId: {}", userId);
            // 登入成功跳轉
            session.setAttribute("userId", userId);
            return "redirect:/index";
        } else {
            logger.warn("Failed to login user: {}", user.getEmail());
            red.addFlashAttribute("errorMessage", "登入失敗，請重試。");
            return "redirect:/login"; // 重導回登入頁面
        }
    }
    
	// 處理登出請求
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
	    HttpSession session = request.getSession(false);
	    if (session != null) {
	        session.invalidate();
	    }
	    return "redirect:/index"; // 登出後重導到首頁
	}	
}
