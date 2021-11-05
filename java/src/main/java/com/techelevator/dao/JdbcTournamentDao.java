package com.techelevator.dao;

import com.techelevator.model.Tournament;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class JdbcTournamentDao implements TournamentDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTournamentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Tournament> getAllTournaments() {
        List<Tournament> tournamentList = new ArrayList<Tournament>();
        String sqlString = "SELECT * FROM tournaments";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlString);
        while (results.next()) {
            tournamentList.add(mapRowToTournament(results));
        }
        return tournamentList;
    }

    @Override
    public Tournament getTournamentByName(String name) {
        Tournament tournament = new Tournament();
        String sqlString = "SELECT * FROM tournaments WHERE tournament_name = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlString, name);
        if (results.next()) {
            tournament = mapRowToTournament(results);
        }
        return tournament;
    }

    @Override
    public Tournament getTournamentById(int id) {
        Tournament tournament = new Tournament();
        String sqlString = "SELECT * FROM tournaments WHERE tournament_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlString, id);
        if (results.next()) {
            tournament = mapRowToTournament(results);
        }
        return tournament;
    }

    @Override
    public void newTournament(Tournament tournament, int userId) {
        String sqlString = "INSERT INTO tournaments (tournament_name, game_name, player_count, "
                + "date_from, date_to, start_time, description, is_team_tournament, creator_id) "
                + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlString, tournament.getTournamentName(), tournament.getGameName(),
                tournament.getPlayerCount(), tournament.getDateFrom(), tournament.getDateTo(),
                tournament.getStartTime(), tournament.getDescription(), tournament.isTeamTournament(), userId);
    }

    @Override
    public void updateTournament(Tournament tournament) {
        String sqlString = "UPDATE tournaments SET tournament_name = ?, game_name = ?, player_count = ?, date_from = ?, "
                + "date_to = ?, start_time = ?, description = ?, is_team_tournament = ? WHERE tournament_id = ?";
        jdbcTemplate.update(sqlString, tournament.getTournamentName(), tournament.getGameName(),
                tournament.getPlayerCount(), tournament.getDateFrom(), tournament.getDateTo(),
                tournament.getStartTime(), tournament.getDescription(), tournament.isTeamTournament(),
                tournament.getTournamentId());
    }

    @Override
    public List<Tournament> getTournamentsByUserId(int id) {
        List<Tournament> tournamentList = new ArrayList<>();
        String tPlayerString = "SELECT * FROM tournaments JOIN tournament_players ON tournaments.tournament_id = tournament_players.tournament_id WHERE player_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(tPlayerString, id);
        while (results.next()) {
            tournamentList.add(mapRowToTournament(results));
        }
        return tournamentList;
    }

    private Tournament mapRowToTournament(SqlRowSet results) {
        Tournament tournament = new Tournament();
        tournament.setTournamentId(results.getLong("tournament_id"));
        tournament.setTournamentName(results.getString("tournament_name"));
        tournament.setGameName(results.getString("game_name"));
        tournament.setDescription(results.getString("description"));
        tournament.setPlayerCount(results.getInt("player_count"));
        try {
            tournament.setDateFrom(LocalDate.parse(results.getString("date_from")));
        } catch (NullPointerException e) {

        }
        try {
            tournament.setDateTo(LocalDate.parse(results.getString("date_to")));
        } catch (NullPointerException e) {

        }
        tournament.setStartTime(results.getString("start_time"));
        tournament.setTeamTournament(results.getBoolean("is_team_tournament"));
        tournament.setCreatorId(results.getInt("creator_id"));
        return tournament;
    }
}
