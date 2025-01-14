package com.example.demo.mvc.service;

import java.util.List;

import com.example.demo.mvc.model.po.User;

public interface UserService {
    Integer loginUser(User user);
    boolean addUser(User user);
    User getUserById(int userId);
    List<User> findAllUsers();
    boolean isEmailValid(String email);
    boolean isPasswordValid(String email, String password);
    String generateSalt();
    String hashPassword(String password, String salt);
}