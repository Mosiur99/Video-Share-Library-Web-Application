package com.webApplication.videoShare.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

    String email;
    public ResourceNotFoundException() {
        super("error!!!");
    }

    public ResourceNotFoundException(String email) {
        super(String.format("this email %s is already registered!!!!", email));
        this.email = email;
    }
}
