package com.techelevator.dao;

import java.util.List;
import com.techelevator.model.TournamentTeam;
import com.techelevator.model.Tournament;

public interface TournamentTeamDao {
    
    List<TournamentTeam> getAllTeamsOfTournament(Tournament tournament);

    TournamentTeam getTournamentTeamByTeamId(Tournament tournament, int teamId);

    void joinTournament(int tournamentId, int teamId);

    void leaveTournament(int tournamentId, int teamId);
}
