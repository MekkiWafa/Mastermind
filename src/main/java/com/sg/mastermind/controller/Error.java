package com.sg.mastermind.controller;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Error {

    private LocalDateTime timestamp = LocalDateTime.now();
    private String message;

}
