package com.example.demo.mvc.service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mvc.WebKeyUtil;
import com.example.demo.mvc.dao.UserDao;
import com.example.demo.mvc.model.po.User;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }
    
    @Override
    public Integer loginUser(User user) {
        User foundUser = userDao.findByEmail(user.getEmail());
        if (foundUser != null && isPasswordValid(user.getEmail(), user.getPassword())) {
        	return foundUser.getUserId();        	
        }
        return null;
    }

    @Override
    public boolean addUser(User user) {
        if (isEmailValid(user.getEmail())) {
        	return false;
        }
        if (user.getPermissions() == null) {
            user.setPermissions(0); // 預設為 0
        }
        String salt = generateSalt();
		String hashedPassword = hashPassword(user.getPassword(), salt);
		user.setSalt(salt);
		user.setPassword(hashedPassword);
        return userDao.create(user);
    }

    @Override
    public User getUserById(int userId) {
        return userDao.findById(userId);
    }

    @Override
    public List<User> findAllUsers() {
        return userDao.findAll();
    }
    
    @Override
    public boolean isEmailValid(String email) {
        return userDao.findByEmail(email) != null;
    }

    @Override
    public boolean isPasswordValid(String email, String password) {
        User user = userDao.findByEmail(email);
        if (user == null) {
        	return false;
        }
        String hashedPassword = hashPassword(password, user.getSalt());
        return user.getPassword().equals(hashedPassword);
    }
    
    @Override
    public String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    @Override
    public String hashPassword(String password, String salt) {
        String saltedPassword = password + salt;
        return WebKeyUtil.md5(saltedPassword);
    }
}