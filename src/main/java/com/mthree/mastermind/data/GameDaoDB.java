package com.mthree.mastermind.data;

import com.mthree.mastermind.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.List;

@Repository
public class GameDaoDB implements GameDao {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public GameDaoDB(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Game> getAll() {
        final String SELECT_ALL_GAMES = "SELECT * FROM game";
        return jdbcTemplate.query(SELECT_ALL_GAMES, new GameMapper());
    }

    @Override
    public Game getGameById(int id) {
        try {
            final String SELECT_GAME_BY_ID = "SELECT * FROM game WHERE gameId = ?";
            return jdbcTemplate.queryForObject(SELECT_GAME_BY_ID, new GameMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public Game addGame(Game game) {
        final String INSERT_GAME = "INSERT INTO game(answer,finished) VALUES(?,?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    INSERT_GAME,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, game.getAnswer());
            statement.setBoolean(2, false);
            return statement;

        }, keyHolder);

        game.setId(keyHolder.getKey().intValue());

        return game;
    }

    @Override
    public boolean updateGame(Game game) {
        final String UPDATE_GAME = "UPDATE game SET "
                + "finished = ? "
                + "WHERE gameId = ?;";

        return jdbcTemplate.update(UPDATE_GAME,
                game.isFinished(),
                game.getId()) > 0;

    }

    @Transactional
    @Override
    public void deleteGameById(int id) {
        final String DELETE_ROUND_BY_GAME = "DELETE FROM round WHERE gameId = ?";
        jdbcTemplate.update(DELETE_ROUND_BY_GAME, id);

        final String DELETE_GAME = "DELETE FROM game WHERE gameId = ?";
        jdbcTemplate.update(DELETE_GAME, id);

    }

    public static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game rm = new Game();
            rm.setId(rs.getInt("gameId"));
            rm.setAnswer(rs.getInt("answer"));
            rm.setFinished(rs.getBoolean("finished"));
            return rm;
        }
    }

}
