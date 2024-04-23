package com.webApplication.videoShare.service;

import com.webApplication.videoShare.entity.User;

public interface UserService {

    User singleUserDetails(Long id);

    Long fetchUserId();

    boolean validUser(String username, String password);
}
