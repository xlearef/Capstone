package com.techelevator.controller;

import com.techelevator.dao.TeamDao;
import com.techelevator.dao.TournamentDao;
import com.techelevator.dao.TournamentPlayerDao;
import com.techelevator.dao.TournamentTeamDao;
import com.techelevator.dao.MatchDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.User;
import com.techelevator.model.Match;
import com.techelevator.model.Tournament;
import com.techelevator.model.TournamentPlayer;
import com.techelevator.model.TournamentTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class TournamentController {

    @Autowired
    TournamentDao tournamentDao;
    @Autowired
    UserDao userDao;
    @Autowired
    TournamentPlayerDao tournamentPlayerDao;
    @Autowired
    TournamentTeamDao tournamentTeamDao;
    @Autowired
    TeamDao teamDao;
    @Autowired
    MatchDao matchDao;

    @PreAuthorize("permitAll()")
    @RequestMapping(path = "/tournaments", method = RequestMethod.GET)
    public List<Tournament> getAllTournaments() {
        List<Tournament> allTournaments = tournamentDao.getAllTournaments();

        for (Tournament tournament : allTournaments) {
            List<TournamentPlayer> players = tournamentPlayerDao.getAllPlayersOfTournament(tournament);
            List<TournamentTeam> teams = tournamentTeamDao.getAllTeamsOfTournament(tournament);
            tournament.setPlayers(players);
            tournament.setTeams(teams);
        }
        return allTournaments;
    }

    @RequestMapping(path = "/add-tournament", method = RequestMethod.POST)
    public void createNewTournament(@RequestBody @Valid Tournament tournament, Principal principal) {
        tournamentDao.newTournament(tournament, userDao.findIdByUsername(principal.getName()));
    }

    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public User getCurrentlyLoggedInUser(Principal principal) {
        return userDao.findByUsername(principal.getName());
    }

    @RequestMapping(path = "/update-tournament", method = RequestMethod.PUT)
    public boolean updateTournament(@RequestBody @Valid Tournament tournament, Principal principal) {
        int userId = userDao.findIdByUsername(principal.getName());
        int creatorId = tournament.getCreatorId();
        if (userId != creatorId) {
            return false;
        } else {
            tournamentDao.updateTournament(tournament);
            return true;
        }
    }

    @RequestMapping(path = "/tournament/{id}/players", method = RequestMethod.GET)
    public List<TournamentPlayer> getAllPlayersByTournamentId(@PathVariable int id) {
        Tournament tournament = tournamentDao.getTournamentById(id);
        return tournamentPlayerDao.getAllPlayersOfTournament(tournament);
    }

    @RequestMapping(path = "/tournament/{id}/join-tournament", method = RequestMethod.POST)
    public boolean joinTournament(@PathVariable int id, Principal principal) {
        try {
            int userId = userDao.findIdByUsername(principal.getName());
            tournamentPlayerDao.joinTournament(id, userId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @RequestMapping(path = "/tournament/{id}/leave-tournament", method = RequestMethod.DELETE)
    public boolean leaveTournament(@PathVariable int id, Principal principal) {
        try {
            int userId = userDao.findIdByUsername(principal.getName());
            tournamentPlayerDao.leaveTournament(id, userId);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @RequestMapping(path = "/tournament/{id}/teams", method = RequestMethod.GET)
    public List<TournamentTeam> getAllTeamsByTournamentId(@PathVariable int id) {
        Tournament tournament = tournamentDao.getTournamentById(id);
        return tournamentTeamDao.getAllTeamsOfTournament(tournament);
    }

    // returns boolean for error handling client side -- if a tournament is not a team
    // tournament a team cannot join it
    @RequestMapping(path = "/tournament/{tournamentId}/team-join-tournament/{teamId}", method = RequestMethod.POST)
    public boolean joinTeamTournament(@PathVariable int tournamentId, @PathVariable int teamId) {
        Tournament tournament = tournamentDao.getTournamentById(tournamentId);
        if (tournament.isTeamTournament()) {
            tournamentTeamDao.joinTournament(tournamentId, teamId);
            return true;
        } else {
            return false;
        }

    }

    // Returns boolean for error handling client side -- if a team is not already
    // entered in the tournament they cannot leave it
    @RequestMapping(path = "/tournament/{tournamentId}/team-leave-tournament", method = RequestMethod.DELETE)
    public boolean leaveTeamTournament(@PathVariable int tournamentId, @RequestBody int teamId) {
        Tournament tournament = tournamentDao.getTournamentById(tournamentId);
        List<TournamentTeam> teamList = tournamentTeamDao.getAllTeamsOfTournament(tournament);
        for (TournamentTeam team : teamList) {
            if (team.getTeamId() == teamId) {
                tournamentTeamDao.leaveTournament(tournamentId, teamId);
                return true;
            }
        }
        return false;
    }

    @RequestMapping(path = "/tournaments/entered", method = RequestMethod.GET)
    public List<Tournament> getTournamentsByUserId(Principal principal) {
        int userId = userDao.findIdByUsername(principal.getName());
        return tournamentDao.getTournamentsByUserId(userId);
    }

    @RequestMapping(path = "/tournament/{tournamentId}/match-results", method = RequestMethod.GET)
    public List<Match> getMatchesByTournamentId(@PathVariable int tournamentId) {
        return matchDao.getMatchesByTournamentId(tournamentId);
    }

    @RequestMapping(path = "/post-match-results", method = RequestMethod.POST)
    public boolean postMatchResults(@RequestBody @Valid Match match, Principal principal) {
        Tournament tournament = tournamentDao.getTournamentById(match.getTournamentId());
        System.out.println(match.isTeamMatch());
        int userId = userDao.findIdByUsername(principal.getName());
        if (tournament.getCreatorId() != userId) {
            return false;
        } else {
            matchDao.postMatchResults(match);
            return true;
        }
    }
}
