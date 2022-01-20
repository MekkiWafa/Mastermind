package com.sg.mastermind.service;

import com.sg.mastermind.data.interfaces.GameDao;
import com.sg.mastermind.data.interfaces.RoundDao;
import com.sg.mastermind.model.Game;
import com.sg.mastermind.model.Round;
import com.sg.mastermind.service.exception.GameNotFoundException;
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
    public void testCalculateResults1() {
        //ARRANGE
        int guess = 2635;
        int answer = 2547;

        String result = service.calculateResults(guess, answer);

        assertEquals("e:1:p:1", result);
    }

    @Test
    public void testCalculateResults2() {
        //ARRANGE
        int guess = 7894;
        int answer = 7894;
        //ACT
        String result = service.calculateResults(guess, answer);
        //ARRANGE
        assertEquals("e:4:p:0", result);
    }
}