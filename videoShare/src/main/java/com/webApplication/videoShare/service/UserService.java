package com.webApplication.videoShare.service;

import com.webApplication.videoShare.Entity.User;
import com.webApplication.videoShare.Entity.Video;

public interface UserService {
    public User singleUserDetails(Long id);

    public Long fetchUserId();
}
