package com.techelevator.controller;
import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import com.techelevator.model.Team;
import com.techelevator.dao.UserDao;
import org.springframework.web.bind.annotation.*;
import com.techelevator.model.TeamMember;

import com.techelevator.dao.TeamDao;
import com.techelevator.dao.TeamMemberDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class TeamController {
    
    @Autowired
    TeamDao teamDao;
    @Autowired
    UserDao userDao;
    @Autowired
    TeamMemberDao teamMemberDao;

    @RequestMapping(path = "/teams", method = RequestMethod.GET) 
    public List<Team> getAllTeams() {
        List<Team> teamList = teamDao.getAllTeams();

        for (Team team : teamList) {
            List<TeamMember> memberList = teamMemberDao.getAllMembersOfTeam(team);
            team.setTeamMembers(memberList);
        }
        return teamList;
    }

    @RequestMapping(path = "/add-team", method = RequestMethod.POST) 
    public void createTeam(@RequestBody @Valid Team team, Principal principal) {
        int userId = userDao.findIdByUsername(principal.getName());
        Team newTeam = teamDao.newTeam(team, userId);
        teamMemberDao.joinTeam(newTeam.getTeamId(), userId);
    }

    @RequestMapping(path = "/update-team", method = RequestMethod.PUT)
    public boolean updateTeam(@RequestBody @Valid Team team, Principal principal) {
        if (userDao.findIdByUsername(principal.getName()) != team.getCreatorId()) {
            return false;
        } else {
            teamDao.updateTeam(team);
            return true;
        }
    }

    @RequestMapping(path = "/team/{id}/members", method = RequestMethod.GET)
    public List<TeamMember> getAllMembersOfTeam(@PathVariable int id) {
        Team team = teamDao.getTeamByTeamId(id);
        return teamMemberDao.getAllMembersOfTeam(team);
    }

    @RequestMapping(path = "/team/{id}/join-team", method = RequestMethod.POST)
    public boolean joinTeam(@PathVariable int id, Principal principal) {
        try {
            int userId = userDao.findIdByUsername(principal.getName());
            teamMemberDao.joinTeam(id, userId);
            return true;
        } catch (Exception e) {
            return false;
        }
        
    }

    @RequestMapping(path = "/team/{id}/leave-team", method = RequestMethod.DELETE)
    public void leaveTeam(@PathVariable int id, Principal principal) {
        int userId = userDao.findIdByUsername(principal.getName());
        teamMemberDao.leaveTeam(id, userId);
    }

    @RequestMapping(path="/teams/joined", method = RequestMethod.GET)
    public List<Team> getTeamsByUserId(Principal principal) {
        int userId = userDao.findIdByUsername(principal.getName());
        return teamDao.getTeamsByMemberId(userId);
    }
}
