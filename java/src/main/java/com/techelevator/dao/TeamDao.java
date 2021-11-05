package com.techelevator.dao;

import java.util.List;
import com.techelevator.model.Team;

public interface TeamDao {
    
    List<Team> getAllTeams();

    List<Team> getTeamsByUserId(int userId);

    Team getTeamByTeamId(int teamId);

    Team newTeam(Team team, int userId);

    void updateTeam(Team team);

    List<Team> getTeamsByMemberId(int id);


}
