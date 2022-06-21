package com.splitwise.service;

import com.splitwise.models.User;

import java.util.UUID;

public class UserService {

    public User createUser(String name,String email,String phoneNo,String id) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPhone(phoneNo);
        user.setId(id);
        return user;
    }
}
