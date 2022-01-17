package com.mthree.mastermind.data;

import com.mthree.mastermind.model.Game;

import java.util.List;

public interface GameDao {
    List<Game> getAll();

    Game getGameById(int id);

    Game addGame(Game game);

    boolean updateGame(Game game);

    void deleteGameById(int id);
}
