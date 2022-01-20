package com.sg.mastermind.controller;

import com.sg.mastermind.service.exception.GameNotFoundException;
import com.sg.mastermind.service.exception.GuessNumberValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GameNotFoundException.class)
    public final ResponseEntity<Error> handleGameNotFoundException(
            GameNotFoundException ex,
            WebRequest request) {

        Error err = new Error();
        err.setMessage("The game is not found for this Id!");
        return new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);
    }


    @ExceptionHandler(GuessNumberValidationException.class)
    public final ResponseEntity<Error> handleGuessNumberValidationException(
            GuessNumberValidationException ex,
            WebRequest request) {

        Error err = new Error();
        err.setMessage("The guess must be a 4-digit number with no duplicate digits!");
        return new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
