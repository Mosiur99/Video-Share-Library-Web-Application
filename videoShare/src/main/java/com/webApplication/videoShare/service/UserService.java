package com.webApplication.videoShare.service;

import com.webApplication.videoShare.entity.User;

public interface UserService {

    User singleUserDetails(Long id);

    Long fetchUserId();

    Boolean isValidUser(String email, String password);

    void saveNewUser(String email, String username, String password);
}
