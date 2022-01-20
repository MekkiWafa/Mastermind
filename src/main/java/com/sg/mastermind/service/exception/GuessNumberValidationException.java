package com.sg.mastermind.service.exception;

public class GuessNumberValidationException extends Exception {

    public GuessNumberValidationException(String message) {
        super(message);
    }

    public GuessNumberValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
