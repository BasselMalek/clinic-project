package com.clinic.project.frameworks.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
          super(message);
    }
}
