package com.sg.mastermind.controller;

import com.sg.mastermind.model.Game;
import com.sg.mastermind.model.Round;
import com.sg.mastermind.service.MasterMindService;
import com.sg.mastermind.service.exception.GameNotFoundException;
import com.sg.mastermind.service.exception.GuessNumberValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MasterMindController {

    private final MasterMindService service;

    @Autowired
    public MasterMindController(MasterMindService service) {
        this.service = service;
    }

    /*This method starts a game, generates an answer, and sets the correct status.
    It returns a 201 CREATED message as well as the created gameId.*/
    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public int createGame() {
        return service.addGame();
    }

    /*This method Makes a guess by passing the guess and gameId in as JSON.
    The program must calculate the results of the guess and mark the game finished if the guess is correct.
    It returns the Round object with the results filled in.*/
    @PostMapping("/guess")
    public Round makeGuess(@RequestBody Round round) throws GameNotFoundException, GuessNumberValidationException {
        return service.makeGuess(round);
    }

    /*This method returns a list of all games.*/
    @GetMapping("/game")
    public List<Game> getAllGames() {
        return service.getAllGames();
    }

    /*This method returns a specific game based on ID. In-progress game is not displayed.*/
    @GetMapping("/game/{gameId}")
    public Game getGameById(@PathVariable int gameId) throws GameNotFoundException {
        return service.getGameById(gameId);
    }


    /*This method returns a list of rounds for the specified game sorted by time.*/
    @GetMapping("/rounds/{gameId}")
    public List<Round> getRoundsForGame(@PathVariable int gameId) throws GameNotFoundException {
        return service.getRoundsByGameId(gameId);
    }

}
