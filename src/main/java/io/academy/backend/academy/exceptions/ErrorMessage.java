package io.academy.backend.academy.exceptions;

import org.springframework.http.HttpStatus;

public class ErrorMessage {
    private final String message;
    private HttpStatus status;
    public ErrorMessage(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
