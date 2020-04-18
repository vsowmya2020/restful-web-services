package com.vsowmya.rest.webservices.restfulwebservices.users;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_MODIFIED)
public class UserSaveException extends RuntimeException {
    public UserSaveException(String message) {
        super(message);

    }

}
