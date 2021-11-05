package com.techelevator.model;

public class TeamMember {
    private int playerId;
    private int teamId;
    private String username;
    private String teamName;

    public TeamMember() {}
    
    public TeamMember(int playerId, int teamId, String username, String teamName) {
        this.playerId = playerId;
        this.teamId = teamId;
        this.username = username;
        this.teamName = teamName;
    }
    
    public int getPlayerId() {
        return playerId;
    }
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
    public int getTeamId() {
        return teamId;
    }
    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getTeamName() {
        return teamName;
    }
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }



}
