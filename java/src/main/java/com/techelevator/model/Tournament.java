package com.techelevator.model;


import java.time.LocalDate;
import java.util.List;

/**CREATED TOURNAMENT MODEL CLASS
 * Getters and setters generated for each property, default constructor that accepts no args
 * and constructor that accepts all instance variables as args except for tournament Id (add that in if we need it)
 * Not sure if all these methods will be needed but we can cut stuff out later once we know for sure
 */
public class Tournament {
    private Long tournamentId;
    private String tournamentName;
    private String gameName;
    private int playerCount;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String startTime;
    private String description;
    private boolean teamTournament;
    private int creatorId;
    private List<TournamentPlayer> players;
    private List<TournamentTeam> teams;

    public List<TournamentTeam> getTeams() {
        return teams;
    }

    public void setTeams(List<TournamentTeam> teams) {
        this.teams = teams;
    }

    public List<TournamentPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(List<TournamentPlayer> players) {
        this.players = players;
    }

    public Tournament() {
    }

    public Tournament(String tournamentName, String gameName, int playerCount, boolean teamTournament) {
        this.tournamentName = tournamentName;
        this.gameName = gameName;
        this.playerCount = playerCount;
        this.teamTournament = teamTournament;
    }

    public Tournament(String tournamentName, String gameName, int playerCount, boolean teamTournament, int creatorId) {
        this.tournamentName = tournamentName;
        this.gameName = gameName;
        this.playerCount = playerCount;
        this.creatorId = creatorId;
    }

    

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isTeamTournament() {
        return teamTournament;
    }

    public void setTeamTournament(boolean teamTournament) {
        this.teamTournament = teamTournament;
    }

    public Long getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(Long tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }
}
