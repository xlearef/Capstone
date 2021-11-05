package com.techelevator.dao;

import com.techelevator.model.Tournament;
import com.techelevator.model.TournamentPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JdbcTournamentPlayerDao implements TournamentPlayerDao {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    TournamentDao tournamentDao;

    public JdbcTournamentPlayerDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TournamentPlayer> getAllPlayersOfTournament(Tournament tournament) {
        List<TournamentPlayer> playerList = new ArrayList<TournamentPlayer>();
        String sqlString = "SELECT * FROM tournament_players WHERE tournament_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlString, tournament.getTournamentId());
        while (results.next()) {
            playerList.add(mapRowToTournamentPlayer(results, tournament));
        }
        return playerList;
    }

    @Override
    public TournamentPlayer getPlayerByPlayerId(int playerId, int tournamentId) {
        TournamentPlayer player = new TournamentPlayer();
        Tournament tournament = tournamentDao.getTournamentById(tournamentId);
        String sql = "SELECT * FROM tournament_players WHERE player_id = ? AND tournament_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, playerId, tournamentId);
        if (results.next()) {
            player = mapRowToTournamentPlayer(results, tournament);
        }
        return player;
    }

    @Override
    public void joinTournament(int tournamentId, int userId) {
        String sqlString = "INSERT INTO tournament_players (player_id, tournament_id) " + "VALUES (?, ?)";
        jdbcTemplate.update(sqlString, userId, tournamentId);
    }

    @Override
    public void leaveTournament(int tournamentId, int userId) {
        String sqlString = "DELETE FROM tournament_players WHERE tournament_id = ? AND player_id = ?";
        jdbcTemplate.update(sqlString, tournamentId, userId);
    }

    private TournamentPlayer mapRowToTournamentPlayer(SqlRowSet results, Tournament tournament) {
        TournamentPlayer player = new TournamentPlayer();
        player.setPlayerId(results.getInt("player_id"));
        player.setTournamentId(results.getInt("tournament_id"));
        player.setTournamentName(tournament.getTournamentName());
        String sqlString = "SELECT username FROM users WHERE user_id = ?";
        SqlRowSet usernameRowSet = jdbcTemplate.queryForRowSet(sqlString, player.getPlayerId());
        if (usernameRowSet.next()) {
            player.setUserName(usernameRowSet.getString("username"));
        }
        return player;
    }
}
