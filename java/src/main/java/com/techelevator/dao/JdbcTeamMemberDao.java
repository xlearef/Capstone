package com.techelevator.dao;

import java.util.List;
import java.util.ArrayList;

import com.techelevator.model.Team;
import com.techelevator.model.TeamMember;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.support.rowset.SqlRowSet;

@Service
public class JdbcTeamMemberDao implements TeamMemberDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcTeamMemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TeamMember> getAllMembersOfTeam(Team team) {
        List<TeamMember> memberList = new ArrayList<TeamMember>();
        String sqlString = "SELECT * FROM team_members WHERE team_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlString, team.getTeamId());
        while (results.next()) {
            memberList.add(mapRowToTeamMember(results, team));
        }
        return memberList;
    }

    @Override
    public void joinTeam(int teamId, int userId) {
        String sqlString = "INSERT INTO team_members (team_member_id, team_id) " + "VALUES (?, ?)";
        jdbcTemplate.update(sqlString, userId, teamId);
    }

    @Override
    public void leaveTeam(int teamId, int userId) {
        String sqlString = "DELETE FROM team_members WHERE team_id = ? AND team_member_id = ?";
        jdbcTemplate.update(sqlString, teamId, userId);
    }

    private TeamMember mapRowToTeamMember(SqlRowSet results, Team team) {
        TeamMember member = new TeamMember();
        member.setPlayerId(results.getInt("team_member_id"));
        member.setTeamId(results.getInt("team_id"));
        member.setTeamName(team.getTeamName());
        String sqlString = "SELECT username FROM users WHERE user_id = ?";
        SqlRowSet uRowSet = jdbcTemplate.queryForRowSet(sqlString, member.getPlayerId());
        if (uRowSet.next()) {
            member.setUsername(uRowSet.getString("username"));
        }
        return member;
    }

}
