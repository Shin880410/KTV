package com.example.demo.mvc.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.mvc.model.po.User;

@Repository
public class UserDaoImpl implements UserDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User findById(int userId) {
        String sql = "SELECT user_id, user_name, gender, email, password, salt, permissions FROM users WHERE user_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), userId);
        } catch (Exception e) {
            logger.error("Error finding user by ID", e);
            return null;
        }
    }

    @Override
    public User findByEmail(String email) {
        String sql = "SELECT user_id, user_name, gender, email, password, salt, permissions FROM users WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), email);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("No user found with email: {}", email);
            return null;
        } catch (Exception e) {
            logger.error("Error finding user by email", e);
            return null;
        }
    }

    

    @Override
    public List<User> findAll() {
        String sql = "SELECT user_id, user_name, gender, email, password, salt, permissions FROM users";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
        } catch (Exception e) {
            logger.error("Error finding all users", e);
            return null;
        }
    }

    @Override
    public boolean create(User user) {
        String sql = "INSERT INTO users (user_name, gender, email, password, phone, salt, permissions) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            return jdbcTemplate.update(sql, user.getUserName(), user.getGender(), user.getEmail(), user.getPassword(), user.getPhone(), user.getSalt(), user.getPermissions()) > 0;
        } catch (Exception e) {
            logger.error("Error creating user", e);
            return false;
        }
    }
}