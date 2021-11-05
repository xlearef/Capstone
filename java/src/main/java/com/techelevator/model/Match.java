package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Match {
    private long matchId;
    private int tournamentId;
    private int winnerId;
    private String winnerName;
    @JsonProperty
    private boolean isTeamMatch;

    public boolean isTeamMatch() {
        return isTeamMatch;
    }

    public void setTeamMatch(boolean isTeamMatch) {
        this.isTeamMatch = isTeamMatch;
    }

    public Match() {}

    public Match(int tournamentId, int winnerId, String winnerName) {
        this.tournamentId = tournamentId;
        this.winnerId = winnerId;
        this.winnerName = winnerName;
    }


    public String getWinnerName() {
        return winnerName;
    }
    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    } 
    public long getMatchId() {
        return matchId;
    }
    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }
    public int getTournamentId() {
        return tournamentId;
    }
    public void setTournamentId(int tournamentId) {
        this.tournamentId = tournamentId;
    }
    public int getWinnerId() {
        return winnerId;
    }
    public void setWinnerId(int winnerId) {
        this.winnerId = winnerId;
    }


    
}
