package com.techelevator.dao;

import com.techelevator.model.Team;
import com.techelevator.model.TeamMember;
import java.util.List;

public interface TeamMemberDao {
    
    List<TeamMember> getAllMembersOfTeam(Team team);

    void joinTeam(int teamId, int userId);

    void leaveTeam(int teamId, int userId);
}
