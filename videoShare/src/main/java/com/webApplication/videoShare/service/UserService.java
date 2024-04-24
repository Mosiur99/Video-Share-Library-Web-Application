package com.webApplication.videoShare.service;

import com.webApplication.videoShare.entity.User;

public interface UserService {

    User singleUserDetails(Long id);

    Long fetchUserId();

    void validUser(String email, String password);
}
