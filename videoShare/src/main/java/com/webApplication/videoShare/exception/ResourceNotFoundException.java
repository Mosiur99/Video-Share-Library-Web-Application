package com.webApplication.videoShare.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("error!!!");
    }
}
