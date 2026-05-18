package com.example.sdetapi.exception;

public class DuplicateEmployeeException extends RuntimeException {
    public DuplicateEmployeeException(String email) {
        super("Employee already exists with email: " + email);
    }
}
