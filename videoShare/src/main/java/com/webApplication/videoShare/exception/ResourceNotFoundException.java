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
        super(String.format("this %s email is already registered!!!!", email));
        this.email = email;
    }

    public ResourceNotFoundException(Long userId, String url) {
        super(String.format("this %s video is already uploaded other's user!!!!", url));
        this.url = url;
        this.userId = userId;
    }
}
