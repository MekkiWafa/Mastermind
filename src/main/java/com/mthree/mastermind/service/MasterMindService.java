package com.mthree.mastermind.service;

import com.mthree.mastermind.model.Game;
import com.mthree.mastermind.model.Round;
import com.mthree.mastermind.service.exception.GameNotFoundException;
import com.mthree.mastermind.service.exception.GuessNumberValidationException;

import java.util.List;

public interface MasterMindService {

    List<Game> getAllFinishedGames();

    Game getFinishedGameById(int id) throws GameNotFoundException;

    List<Round> getRoundsForGame(int gameId) throws GameNotFoundException;

    int addGame();

    Round makeGuess(int gameId, int guess) throws GameNotFoundException, GuessNumberValidationException;
}
