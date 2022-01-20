package com.sg.mastermind.data;

import com.sg.mastermind.data.interfaces.RoundDao;
import com.sg.mastermind.model.Game;
import com.sg.mastermind.model.Round;
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
    public List<Round> getRoundsByGameId(int gameId) {
        final String SELECT_ROUNDS_BY_GAME = "SELECT r.* FROM round r WHERE gameId = ? ";
        List<Round> rounds = jdbcTemplate.query(SELECT_ROUNDS_BY_GAME,
                new RoundMapper(), gameId);
        return rounds;
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
            statement.setInt(4, round.getGameId());
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
            Round round = new Round();
            round.setId(rs.getInt("roundId"));
            round.setTimeAttempt(rs.getTimestamp("timeAttempt").toLocalDateTime());
            round.setGuess(rs.getInt("guess"));
            round.setResult(rs.getString("result"));
            round.setGameId(rs.getInt("gameId"));
            return round;
        }
    }
}
