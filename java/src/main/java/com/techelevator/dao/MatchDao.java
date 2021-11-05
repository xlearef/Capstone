package com.techelevator.dao;

import com.techelevator.model.Match;
import java.util.List;

public interface MatchDao {

    void postMatchResults(Match match);

    List<Match> getMatchesByTournamentId(int tournamentId);

}
