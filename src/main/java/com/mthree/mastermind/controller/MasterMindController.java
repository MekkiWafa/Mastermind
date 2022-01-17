package com.mthree.mastermind.controller;

import com.mthree.mastermind.dto.RoundGame;
import com.mthree.mastermind.dto.RoundResult;
import com.mthree.mastermind.model.Game;
import com.mthree.mastermind.model.Round;
import com.mthree.mastermind.service.MasterMindService;
import com.mthree.mastermind.service.exception.GameNotFoundException;
import com.mthree.mastermind.service.exception.GuessNumberValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MasterMindController {

    private final MasterMindService service;

    @Autowired
    public MasterMindController(MasterMindService service) {
        this.service = service;
    }

    /*This method returns a list of all games. In-progress games are not displayed.*/
    @GetMapping("/game")
    public List<Game> getAllFinishedGames() {
        return service.getAllFinishedGames();
    }

    /*This method returns a specific game based on ID. In-progress game is not displayed.*/
    @GetMapping("/game/{gameId}")
    public ResponseEntity getFinishedGameById(@PathVariable int gameId) throws GameNotFoundException {
        Game result = service.getFinishedGameById(gameId);
        if (result == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

    /*This method returns a list of rounds for the specified game sorted by time.*/
    @GetMapping("/rounds/{gameId}")
    public List<RoundResult> getRoundsForGame(@PathVariable int gameId) throws GameNotFoundException {
        List<Round> roundList = service.getRoundsForGame(gameId);
        List<RoundResult> roundResultList = new ArrayList<>();
        for (Round round : roundList) {
            roundResultList.add(round.asDto());
        }
        return roundResultList;
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
    public RoundResult makeGuess(@RequestBody RoundGame roundGame) throws GameNotFoundException, GuessNumberValidationException {
        Round round = service.makeGuess(roundGame.getGameId(), roundGame.getGuess());
        return round.asDto();
    }

}
