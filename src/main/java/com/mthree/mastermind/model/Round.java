/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.mastermind.model;

import com.mthree.mastermind.dto.RoundResult;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Wafa Mekki
 */
public class Round {
    private int id;
    private LocalDateTime timeAttempt;
    private int guess;
    private String result;
    private Game game;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getTimeAttempt() {
        return timeAttempt;
    }

    public void setTimeAttempt(LocalDateTime time) {
        this.timeAttempt = time;
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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public RoundResult asDto() {
        return new RoundResult(id, timeAttempt, guess, result);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Round round = (Round) o;
        return id == round.id &&
                guess == round.guess &&
                Objects.equals(timeAttempt, round.timeAttempt) &&
                Objects.equals(result, round.result) &&
                Objects.equals(game, round.game);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timeAttempt, guess, result, game);
    }
}
