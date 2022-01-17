package com.mthree.mastermind.dto;

import java.time.LocalDateTime;

public class RoundResult {
    private int id;
    private LocalDateTime timeAttempt;
    private int guess;
    private String result;

    public RoundResult(int id, LocalDateTime timeAttempt, int guess, String result) {
        this.id = id;
        this.timeAttempt = timeAttempt;
        this.guess = guess;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getTimeAttempt() {
        return timeAttempt;
    }

    public void setTimeAttempt(LocalDateTime timeAttempt) {
        this.timeAttempt = timeAttempt;
    }

    public int getGuess() {
        return guess;
    }

    public void setGuess(int guess) {
        this.guess = guess;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
