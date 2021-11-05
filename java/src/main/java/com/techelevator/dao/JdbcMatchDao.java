package com.techelevator.dao;

import com.techelevator.model.Match;
import com.techelevator.model.Team;
import com.techelevator.model.TournamentPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class JdbcMatchDao implements MatchDao{
    JdbcTemplate jdbcTemplate;
    @Autowired
    TeamDao teamDao;
    @Autowired
    TournamentPlayerDao tournamentPlayerDao;

    public JdbcMatchDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    @Override
    public List<Match> getMatchesByTournamentId(int tournamentId) {
        List<Match> matchList = new ArrayList<>();
        String sql = "SELECT * FROM match_results WHERE tournament_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, tournamentId);
        while (results.next()) {
            matchList.add(mapRowToMatch(results));
        }
        return matchList;
    }

    @Override
    public void postMatchResults(Match match) {
        String sql = "INSERT INTO match_results (tournament_id, winner_id, is_team_match) " +
        "VALUES (?, ?, ?)";
        System.out.println(match.isTeamMatch());
        jdbcTemplate.update(sql, match.getTournamentId(), match.getWinnerId(), match.isTeamMatch());
    }

    private Match mapRowToMatch(SqlRowSet results) {
        Match match = new Match();
        match.setMatchId(results.getLong("match_id"));
        match.setTournamentId(results.getInt("tournament_id"));
        match.setWinnerId(results.getInt("winner_id"));
        match.setTeamMatch(results.getBoolean("is_team_match"));
//        System.out.println(results.getBoolean("is_team_match"));
        if (match.isTeamMatch()) {
            Team team = teamDao.getTeamByTeamId(match.getWinnerId());
            match.setWinnerName(team.getTeamName());
//            System.out.println("Team: " + match.getWinnerName());
        } else {
            TournamentPlayer player = tournamentPlayerDao.getPlayerByPlayerId(match.getWinnerId(), match.getTournamentId());
            match.setWinnerName(player.getUserName());
//            System.out.println("Player: " + match.getWinnerName());
        }
        return match;
    }
    

    
}
