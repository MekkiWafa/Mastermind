package com.mthree.mastermind.data;

import com.mthree.mastermind.model.Game;
import com.mthree.mastermind.model.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class RoundDaoDB implements RoundDao {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public RoundDaoDB(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Round> getRoundsByGame(Game game) {
        final String SELECT_ROUNDS_BY_GAME = "SELECT r.* FROM round r WHERE gameId = ? ";
        List<Round> rounds = jdbcTemplate.query(SELECT_ROUNDS_BY_GAME,
                new RoundMapper(), game.getId());

        addGameToRounds(rounds);
        return rounds;
    }

    private void addGameToRounds(List<Round> rounds) {
        for (Round round : rounds) {
            round.setGame(getGameForRound(round));
        }
    }

    private Game getGameForRound(Round round) {
        final String SELECT_GAME_FOR_ROUND = "SELECT g.* FROM game g "
                + "JOIN round r ON g.gameId = r.gameId WHERE r.roundId = ?";
        return jdbcTemplate.queryForObject(SELECT_GAME_FOR_ROUND, new GameDaoDB.GameMapper(),
                round.getId());
    }

    @Override
    public Round addRound(Round round) {
        final String INSERT_ROUND = "INSERT INTO round(timeAttempt, guess, result, gameId) VALUES(?,?,?,?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    INSERT_ROUND,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setObject(1, Timestamp.valueOf(round.getTimeAttempt()));
            statement.setInt(2, round.getGuess());
            statement.setString(3, round.getResult());
            statement.setInt(4, round.getGame().getId());
            return statement;

        }, keyHolder);

        round.setId(keyHolder.getKey().intValue());
        return round;
    }

    @Override
    public void deleteRoundById(int id) {
        final String DELETE_ROUND = "DELETE FROM round WHERE roundId = ?";
        jdbcTemplate.update(DELETE_ROUND, id);
    }

    @Override
    public List<Round> getAll() {
        final String SELECT_ALL_ROUNDS = "SELECT * FROM round";
        return jdbcTemplate.query(SELECT_ALL_ROUNDS, new RoundMapper());
    }

    public static final class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round rm = new Round();
            rm.setId(rs.getInt("roundId"));
            rm.setTimeAttempt(rs.getTimestamp("timeAttempt").toLocalDateTime());
            rm.setGuess(rs.getInt("guess"));
            rm.setResult(rs.getString("result"));
            return rm;
        }
    }
}
