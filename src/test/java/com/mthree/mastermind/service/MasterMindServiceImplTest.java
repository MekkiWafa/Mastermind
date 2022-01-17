package com.mthree.mastermind.service;

import com.mthree.mastermind.data.GameDao;
import com.mthree.mastermind.data.RoundDao;
import com.mthree.mastermind.model.Game;
import com.mthree.mastermind.model.Round;
import com.mthree.mastermind.service.exception.GameNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MasterMindServiceImplTest {
    @Autowired
    GameDao gameDao;

    @Autowired
    RoundDao roundDao;

    @Autowired
    MasterMindService service;


    @Before
    public void setUp() throws Exception {
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
    public void tearDown() throws Exception {
    }

    @Test
    public void getFinishedGameById() throws GameNotFoundException {
        //ARRANGE
        // Create our  game
        Game game = new Game();
        game.setAnswer(1254);
        game.setFinished(false);

        gameDao.addGame(game);
        Game unsolvedGame = service.getFinishedGameById(game.getId());


        assertNull("This is unsolved game! It should be null.", unsolvedGame);

        //ARRANGE
        // update our  game
        game.setFinished(true);
        gameDao.updateGame(game);
        Game solvedGame = service.getFinishedGameById(game.getId());

        assertNotNull("This is solved game! It should be not null.", solvedGame);
    }
}