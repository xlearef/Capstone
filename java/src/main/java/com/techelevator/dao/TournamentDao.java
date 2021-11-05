package com.techelevator.dao;

import com.techelevator.model.Tournament;

import java.util.List;

public interface TournamentDao {

    List<Tournament> getAllTournaments();

    Tournament getTournamentByName(String name);

    Tournament getTournamentById(int id);

    void newTournament(Tournament tournament, int userId);

    void updateTournament(Tournament tournament);

    List<Tournament> getTournamentsByUserId(int id);
}
