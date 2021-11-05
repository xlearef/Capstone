package com.techelevator.dao;

import com.techelevator.model.Tournament;
import com.techelevator.model.TournamentPlayer;


import java.util.List;

public interface TournamentPlayerDao {

    List<TournamentPlayer> getAllPlayersOfTournament(Tournament tournament);

    TournamentPlayer getPlayerByPlayerId(int playerId, int tournamentId);

    void joinTournament(int tournamentId, int userId);

    void leaveTournament(int tournamentId, int userId);
}
