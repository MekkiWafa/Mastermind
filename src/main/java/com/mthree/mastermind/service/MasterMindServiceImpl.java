/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.mastermind.service;

import com.mthree.mastermind.data.GameDao;
import com.mthree.mastermind.data.RoundDao;
import com.mthree.mastermind.model.Game;
import com.mthree.mastermind.model.Round;
import com.mthree.mastermind.service.exception.GameNotFoundException;
import com.mthree.mastermind.service.exception.GuessNumberValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author ASUS
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

    public List<Game> getAllFinishedGames() {
        List<Game> gameList = gameDao.getAll();
        List<Game> gameList1 = new ArrayList<>();
        gameList.forEach(game -> {
            if (!game.isFinished())
                game.setAnswer(0);
            gameList1.add(game);
        });
        return gameList1;
        //return gameList.stream().filter(Game::isFinished).collect(Collectors.toList());
    }

    public Game getFinishedGameById(int id) throws GameNotFoundException {
        Game game = gameDao.getGameById(id);
        if (game == null) {
            throw new GameNotFoundException("Game ID not found");
        }
        if (game.isFinished())
            return game;
        else
            return null;
    }

    public List<Round> getRoundsForGame(int gameId) throws GameNotFoundException {
        Game game = gameDao.getGameById(gameId);
        if (game == null) {
            throw new GameNotFoundException("Game ID not found");
        }
        if (game != null) {
            return roundDao.getRoundsByGame(game).stream()
                    .sorted((o1, o2) -> o1.getTimeAttempt().compareTo(o2.getTimeAttempt()))
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public Round makeGuess(int gameId, int guess) throws GameNotFoundException, GuessNumberValidationException {
        //get game object
        Game playedGame = gameDao.getGameById(gameId);
        if (playedGame == null) {
            throw new GameNotFoundException("Game ID not found");
        }
        if (String.valueOf(guess).length() != 4 || hasDuplicateNumbers(guess)) {
            throw new GuessNumberValidationException("The guess must be a 4-digit number with no duplicate digits");
        }
        //get game answer
        int answer = playedGame.getAnswer();
        //calculate results
        String result = calculateResults(answer, guess);
        //create and add round
        Round playedRound = new Round();
        playedRound.setTimeAttempt(LocalDateTime.now());
        playedRound.setGuess(guess);
        playedRound.setResult(result);
        playedRound.setGame(playedGame);
        Round addedRound = roundDao.addRound(playedRound);
        //update game status
        if (answer == guess) {
            playedGame.setFinished(true);
            gameDao.updateGame(playedGame);
        }
        return addedRound;
    }

    private String calculateResults(int answer, int guess) {
        int bulls = 0;
        int cows = 0;
        String guessStr = Integer.toString(guess);
        String answerStr = Integer.toString(answer);

        for (int i = 0; i < guessStr.length(); i++) {
            if (Character.compare(guessStr.charAt(i), answerStr.charAt(i)) == 0) {
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


