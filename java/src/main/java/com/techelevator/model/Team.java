package com.techelevator.model;

import java.util.List;

public class Team {
    private int teamId;
    private int creatorId;
    private String teamName;
    private int teamMemberCount;
    private List<TeamMember> teamMembers;

    //NOT SURE, TESTING TO ASSIGN A VALUE TO A TEAM
    private int tempLocalTeamId;

    public List<TeamMember> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(List<TeamMember> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public Team() {}

    public Team(int teamId, int creatorId, String teamName, int teamMemberCount) {
        this.teamId = teamId;
        this.creatorId = creatorId;
        this.teamName = teamName;
        this.teamMemberCount = teamMemberCount;
    }

    public int getTempLocalTeamId() {
        return tempLocalTeamId;
    }

    public void setTempLocalTeamId(int tempLocalTeamId) {
        this.tempLocalTeamId = tempLocalTeamId;
    }

    public int getTeamId() {
        return teamId;
    }
    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
    public int getCreatorId() {
        return creatorId;
    }
    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }
    public String getTeamName() {
        return teamName;
    }
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    public int getTeamMemberCount() {
        return teamMemberCount;
    }
    public void setTeamMemberCount(int teamMemberCount) {
        this.teamMemberCount = teamMemberCount;
    }

    
}
