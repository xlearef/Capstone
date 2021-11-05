package com.techelevator.controller;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import com.techelevator.model.Bracket;
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
public class BracketController {

    @Autowired
    TeamDao teamDao;
    @Autowired
    UserDao userDao;
    @Autowired
    TeamMemberDao teamMemberDao;

    @RequestMapping(path = "/team-bracket", method = RequestMethod.POST)
    public Bracket getBracket(@RequestBody Bracket bracket) {
        // Thinking we can add table in the DB, with
        // tournament_1, tournament_2 and tournament_winner with tournament_winner being 1 or 2
        // can use this to add our "winner" styling to the page and keep track of past tournaments

        return bracket;
    }

}
