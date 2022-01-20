/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mastermind.service;

import com.sg.mastermind.data.interfaces.GameDao;
import com.sg.mastermind.data.interfaces.RoundDao;
import com.sg.mastermind.model.Game;
import com.sg.mastermind.model.Round;
import com.sg.mastermind.service.exception.GameNotFoundException;
import com.sg.mastermind.service.exception.GuessNumberValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author Wafa Mekki
 */
@Service
public class MasterMindServiceImpl implements MasterMindService {
    private GameDao gameDao;
    private RoundDao roundDao;

    @Autowired
    public MasterMindServiceImpl(GameDao gameDao, RoundDao roundDao) {
        this.gameDao = gameDao;
        this.roundDao = roundDao;
    }

    public List<Game> getAllGames() {
        List<Game> gameList = gameDao.getAll();
        gameList.forEach(game -> {
            if (!game.isFinished())
                game.setAnswer(0);
        });
        return gameList;
    }

    public Game getGameById(int id) throws GameNotFoundException {
        Game game = gameDao.getGameById(id);
        if (game == null) {
            throw new GameNotFoundException("Game ID not found");
        }
        if (!game.isFinished())
            game.setAnswer(0);

        return game;
    }

    public List<Round> getRoundsByGameId(int gameId) throws GameNotFoundException {
        Game game = gameDao.getGameById(gameId);
        if (game == null) {
            throw new GameNotFoundException("Game ID not found");
        } else {
            return roundDao.getRoundsByGameId(game.getId()).stream()
                    .sorted((o1, o2) -> o1.getTimeAttempt().compareTo(o2.getTimeAttempt()))
                    .collect(Collectors.toList());
        }
    }

    public Round makeGuess(Round round) throws GameNotFoundException, GuessNumberValidationException {
        //get game object
        Game playedGame = gameDao.getGameById(round.getGameId());
        if (playedGame == null) {
            throw new GameNotFoundException("Game ID not found");
        }

        int guess = round.getGuess();
        if (String.valueOf(guess).length() != 4 || hasDuplicateNumbers(guess)) {
            throw new GuessNumberValidationException("The guess must be a 4-digit number with no duplicate digits");
        }
        //get game answer
        int answer = playedGame.getAnswer();

        //calculate results
        String result = calculateResults(answer, guess);
        //create and add round

        round.setTimeAttempt(LocalDateTime.now());
        round.setResult(result);

        //update game status
        if (answer == guess) {
            playedGame.setFinished(true);
            gameDao.updateGame(playedGame);
        }
        return roundDao.addRound(round);
    }

    public String calculateResults(int answer, int guess) {
        int bulls = 0;
        int cows = 0;
        String guessStr = Integer.toString(guess);
        String answerStr = Integer.toString(answer);

        for (int i = 0; i < guessStr.length(); i++) {
            if (guessStr.charAt(i) == answerStr.charAt(i)) {
                bulls++;
            } else if (answerStr.contains(String.valueOf(guessStr.charAt(i)))) {
                cows++;
            }
        }
        return "e:" + bulls + ":p:" + cows;
    }

    public int addGame() {
        int answer = generateAnswer();
        Game game = new Game();
        game.setAnswer(answer);
        return gameDao.addGame(game).getId();
    }

    private int generateAnswer() {
        int answer;
        Random gen = new Random();
        do {
            answer = gen.nextInt(9000) + 1000;
        }
        while (hasDuplicateNumbers(answer));
        return answer;
    }

    private boolean hasDuplicateNumbers(int number) {
        boolean[] digs = new boolean[10];
        while (number > 0) {
            if (digs[number % 10]) return true;
            digs[number % 10] = true;
            number /= 10;
        }
        return false;
    }


}


