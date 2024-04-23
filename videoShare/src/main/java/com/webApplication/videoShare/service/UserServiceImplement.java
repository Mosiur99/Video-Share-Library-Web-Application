package com.webApplication.videoShare.service;

import com.webApplication.videoShare.entity.User;
import com.webApplication.videoShare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImplement implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User singleUserDetails(Long id) {
        List<User> userList = userRepository.findAll();
        for(User user : userList){
            if(user.getId().equals(id)){
                return user;
            }
        }
        return null;
    }

    @Override
    public Long fetchUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Long id = 0L;
        List<User> userList = userRepository.findAll();
        for(User user : userList){
            if(user.getUsername().equals(username)){
                id = user.getId();
                break;
            }
        }
        return id;
    }

    @Override
    public boolean validUser(String username, String password) {
        List<User> userList = userRepository.findAll();
        for(User user : userList){
            if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }
}
