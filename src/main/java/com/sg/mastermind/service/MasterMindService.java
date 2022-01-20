package com.sg.mastermind.service;

import com.sg.mastermind.model.Game;
import com.sg.mastermind.model.Round;
import com.sg.mastermind.service.exception.GameNotFoundException;
import com.sg.mastermind.service.exception.GuessNumberValidationException;

import java.util.List;

public interface MasterMindService {

    List<Game> getAllGames();

    Game getGameById(int id) throws GameNotFoundException;

    List<Round> getRoundsByGameId(int gameId) throws GameNotFoundException;

    int addGame();

    Round makeGuess(Round round) throws GameNotFoundException, GuessNumberValidationException;

    String calculateResults(int answer, int guess);
}
