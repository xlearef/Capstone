package com.techelevator.model;

public class TournamentTeam {
    private int teamId;
    private int tournamentId;
    private String teamName;
    private String tournamentName;

    
    public TournamentTeam() {}
    
    public TournamentTeam(int teamId, int tournamentId, String teamName, String tournamentName) {
        this.teamId = teamId;
        this.tournamentId = tournamentId;
        this.teamName = teamName;
        this.tournamentName = tournamentName;
    }

    public int getTeamId() {
        return teamId;
    }
    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
    public int getTournamentId() {
        return tournamentId;
    }
    public void setTournamentId(int tournamentId) {
        this.tournamentId = tournamentId;
    }
    public String getTeamName() {
        return teamName;
    }
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    public String getTournamentName() {
        return tournamentName;
    }
    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    
}
