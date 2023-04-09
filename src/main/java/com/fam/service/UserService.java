package com.fam.service;

import java.util.List;

import com.fam.model.User;

public interface UserService {
    public void saveUser(User user);
    public List<Object> isUserPresent(User user);
	public User getUserById(Long userId);
    public User getUserByEmail(String email);
}