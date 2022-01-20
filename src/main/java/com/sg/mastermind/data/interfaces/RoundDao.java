package com.sg.mastermind.data.interfaces;


import com.sg.mastermind.model.Round;
import java.util.List;

public interface RoundDao {
    List<Round> getRoundsByGameId(int gameId);

    Round addRound(Round round);

    void deleteRoundById(int id);

    List<Round> getAll();
}
