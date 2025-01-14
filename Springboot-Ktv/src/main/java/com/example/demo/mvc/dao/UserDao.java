package com.example.demo.mvc.dao;

import java.util.List;

import com.example.demo.mvc.model.po.User;

public interface UserDao {
    User findById(int userId);
    User findByEmail(String email);
    List<User> findAll();
    boolean create(User user);
}