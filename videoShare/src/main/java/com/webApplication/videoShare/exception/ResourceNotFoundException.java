package com.webApplication.videoShare.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

    String email;
    String url;
    Long userId;
    public ResourceNotFoundException() {
        super("error!!!");
    }

    public ResourceNotFoundException(String email) {
        super(String.format("this email %s is already registered!!!!", email));
        this.email = email;
    }

    public ResourceNotFoundException(Long userId, String url) {
        super(String.format("this video %s is already uploaded this user which user id is %d or uploaded by other's user!!!!", url, userId));
        this.url = url;
        this.userId = userId;
    }
}
