package com.webApplication.videoShare.service;

import com.webApplication.videoShare.entity.User;
import com.webApplication.videoShare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

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
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        Long id = 0L;
        List<User> userList = userRepository.findAll();
        for(User user : userList){
            if(user.getEmail().equals(username)){
                id = user.getId();
                break;
            }
        }
        return id;
    }

    @Override
    public void validUser(String email, String password){
        List<User> userList = userRepository.findAll();
        Optional<User> user = userList.stream()
                .filter(t -> email.equals(t.getUsername()))
                .filter(t -> password.equals(t.getPassword()))
                .findFirst();
    }
}
