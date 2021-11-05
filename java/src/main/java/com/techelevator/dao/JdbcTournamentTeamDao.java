package com.techelevator.dao;

import java.util.List;

import com.techelevator.model.Tournament;
import com.techelevator.model.TournamentTeam;
import org.springframework.stereotype.Service;
import java.util.ArrayList;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

@Service
public class JdbcTournamentTeamDao implements TournamentTeamDao{
    private JdbcTemplate jdbcTemplate;

    public JdbcTournamentTeamDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TournamentTeam> getAllTeamsOfTournament(Tournament tournament) {
        List<TournamentTeam> teamList = new ArrayList<TournamentTeam>();
        String sqlString = "SELECT * FROM tournament_teams WHERE tournament_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlString, tournament.getTournamentId());
        while (results.next()) {
            teamList.add(mapRowToTournamentTeam(results, tournament));
        }
        return teamList;
    }

    @Override
    public TournamentTeam getTournamentTeamByTeamId(Tournament tournament, int teamId) {
        TournamentTeam team = new TournamentTeam();
        String sqlString = "SELECT * FROM tournament_teams WHERE team_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlString, teamId);
        if (results.next()) {
            team = mapRowToTournamentTeam(results, tournament);
        }
        return team;
    }

    @Override
    public void joinTournament(int tournamentId, int teamId) {
        String sqlString = "INSERT INTO tournament_teams (team_id, tournament_id) " +
            "VALUES (?, ?)";
        jdbcTemplate.update(sqlString, teamId, tournamentId);

        
    }

    @Override
    public void leaveTournament(int tournamentId, int teamId) {
        String sqlString = "DELETE FROM tournament_teams WHERE tournament_id = ? AND team_id = ?";
        jdbcTemplate.update(sqlString, tournamentId, teamId);
    }

    public TournamentTeam mapRowToTournamentTeam(SqlRowSet results, Tournament tournament) {
        TournamentTeam newTeam = new TournamentTeam();
        newTeam.setTeamId(results.getInt("team_id"));
        newTeam.setTournamentId(results.getInt("tournament_id"));
        String sqlString = "SELECT team_name FROM teams WHERE team_id = ?";
        SqlRowSet nameRowSet = jdbcTemplate.queryForRowSet(sqlString, newTeam.getTeamId());
        if (nameRowSet.next()) {
            newTeam.setTeamName(nameRowSet.getString("team_name"));
        }
        newTeam.setTournamentName(tournament.getTournamentName());
        return newTeam;
    }
    


}
