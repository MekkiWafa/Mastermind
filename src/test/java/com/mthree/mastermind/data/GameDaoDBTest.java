/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.mastermind.data;

import com.mthree.mastermind.model.Game;
import com.mthree.mastermind.model.Round;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ASUS
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GameDaoDBTest {
    @Autowired
    GameDao gameDao;

    @Autowired
    RoundDao roundDao;

    public GameDaoDBTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        List<Game> games = gameDao.getAll();
        for (Game game : games) {
            gameDao.deleteGameById(game.getId());
        }

        List<Round> rounds = roundDao.getAll();
        for (Round round : rounds) {
            roundDao.deleteRoundById(round.getId());
        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetAllGames() {
        //ARRANGE
        // Create our first game
        Game game1 = new Game();
        game1.setAnswer(1254);


        // Create our second game
        Game game2 = new Game();
        game2.setAnswer(5874);

        //ACT
        // Add both our games to the DAO
        gameDao.addGame(game1);
        gameDao.addGame(game2);
        // Retrieve the list of all games within the DAO
        List<Game> games = gameDao.getAll();

        //ASSERT
        // First check the general contents of the list
        assertNotNull("The list of games must not null", games);
        assertEquals(2, games.size());
        // Then the specifics
        assertTrue(games.contains(game1));
        assertTrue(games.contains(game2));
    }

    @Test
    public void testAddAndGetGame() {
        Game game = new Game();
        game.setAnswer(1234);


        Game gameAdded = gameDao.addGame(game);

        Game fromDao = gameDao.getGameById(gameAdded.getId());

        assertEquals(gameAdded, fromDao);
    }

    @Test
    public void testUpdateGame() {
        //ARRANGE
        Game game = new Game();
        game.setAnswer(1234);
        //ACT
        game = gameDao.addGame(game);
        Game fromDao = gameDao.getGameById(game.getId());
        //ASSERT
        assertEquals(game, fromDao);
        //ACT change local copy
        game.setFinished(true);
        gameDao.updateGame(game);
        //ASSERT
        assertNotEquals(game, fromDao);
        //ACT
        fromDao = gameDao.getGameById(game.getId());
        //ASSERT
        assertEquals(game, fromDao);
    }

    @Test
    public void testDeleteGameById() {
        //ARRANGE
        // Create our game
        Game game = new Game();
        game.setAnswer(1254);
        game = gameDao.addGame(game);

        // Create our round
        Round round = new Round();
        round.setTimeAttempt(LocalDateTime.now());
        round.setGuess(1254);
        round.setResult("e:4:p:0");
        round.setGame(game);
        round = roundDao.addRound(round);

        //ACT
        Game fromDao = gameDao.getGameById(game.getId());
        assertEquals(game, fromDao);

        gameDao.deleteGameById(game.getId());
        fromDao = gameDao.getGameById(game.getId());
        assertNull(fromDao);


    }
}
