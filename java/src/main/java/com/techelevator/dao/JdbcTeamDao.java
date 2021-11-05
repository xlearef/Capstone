package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;

import com.techelevator.model.Team;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

@Service
public class JdbcTeamDao implements TeamDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcTeamDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Team> getAllTeams() {
        List<Team> teams = new ArrayList<Team>();
        String sqlString = "SELECT * FROM teams";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlString);
        while (results.next()) {
            teams.add(mapRowToTeam(results));
        }
        return teams;
    }

    @Override
    public List<Team> getTeamsByUserId(int userId) {
        List<Team> teamList = new ArrayList<>();
        String sqlString = "SELECT * FROM teams WHERE creator_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlString, userId);
        while (results.next()) {
            teamList.add(mapRowToTeam(results));
        }
        return teamList;
    }

    @Override
    public Team getTeamByTeamId(int teamId) {
        Team team = new Team();
        String sqlString = "SELECT * FROM teams WHERE team_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlString, teamId);
        if (results.next()) {
            team = mapRowToTeam(results);
        }
        return team;
    }

    @Override
    public Team newTeam(Team team, int userId) {
        String sqlString = "INSERT INTO teams (creator_id, team_name, team_member_count) VALUES (?, ?, ?)";
        jdbcTemplate.update(sqlString, userId, team.getTeamName(), team.getTeamMemberCount());
        Team newTeam = new Team();
        String selectString = "SELECT * FROM teams WHERE team_name = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(selectString, team.getTeamName());
        if (results.next()) {
            newTeam = mapRowToTeam(results);   
        }
        return newTeam;
    }

    @Override
    public void updateTeam(Team team) {
        String sqlString = "UPDATE teams SET team_name = ?, team_member_count = ? WHERE team_id = ?";
        jdbcTemplate.update(sqlString, team.getTeamName(), team.getTeamMemberCount(), team.getTeamId());

    }

    @Override
    public List<Team> getTeamsByMemberId(int id) {
        List<Team> teamList = new ArrayList<>();
        String sqlString = "SELECT * FROM teams JOIN team_members ON teams.team_id = team_members.team_id WHERE team_member_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlString, id);
        while (results.next()) {
            teamList.add(mapRowToTeam(results));
        }
        return teamList;
    }

    public Team mapRowToTeam(SqlRowSet results) {
        Team team = new Team();
        team.setTeamId(results.getInt("team_id"));
        team.setCreatorId(results.getInt("creator_id"));
        team.setTeamMemberCount(results.getInt("team_member_count"));
        team.setTeamName(results.getString("team_name"));

        return team;
    }

}
