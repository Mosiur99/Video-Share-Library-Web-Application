package com.webApplication.videoShare.service;

import com.webApplication.videoShare.Entity.User;
import com.webApplication.videoShare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Override
    public boolean validUser(String username, String email, String password) {
        List<User> userList = userRepository.findAll();
        boolean isValidUser = false;
        for(User user : userList){
            if(user.getUsername().equals(username) && user.getEmail().equals(email) && user.getPassword().equals(password)){
                isValidUser = true;
                break;
            }
        }

        return isValidUser;
    }
}
