package com.techelevator.model;

public class TournamentPlayer {
    private int playerId;
    private int tournamentId;
    private String userName;
    private String tournamentName;

    public TournamentPlayer() {
    }

    public TournamentPlayer(int playerId, int tournamentId) {
        this.playerId = playerId;
        this.tournamentId = tournamentId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(int tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }

    public String getTournamentName() { return tournamentName; }

    public void setTournamentName(String tournamentName) { this.tournamentName = tournamentName; }
}
