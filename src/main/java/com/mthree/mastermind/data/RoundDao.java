package com.mthree.mastermind.data;

import com.mthree.mastermind.model.Game;
import com.mthree.mastermind.model.Round;

import java.util.List;

public interface RoundDao {
    List<Round> getRoundsByGame(Game game);

    Round addRound(Round round);

    void deleteRoundById(int id);

    List<Round> getAll();
}
