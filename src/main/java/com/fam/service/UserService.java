package com.fam.service;

import java.util.List;

import com.fam.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    public void saveUser(User user);
    public List<Object> isUserPresent(User user);
	public User getUserById(Long userId);
    public User getUserByEmail(String email);
    UserDetails getCurrentUser(HttpSession session);
}