BEGIN TRANSACTION;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS tournaments;
DROP TABLE IF EXISTS tournament_players;
DROP SEQUENCE IF EXISTS seq_user_id;
DROP SEQUENCE IF EXISTS seq_tournament_id;
CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;
CREATE SEQUENCE seq_tournament_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;
CREATE SEQUENCE seq_team_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;
CREATE TABLE users (
  user_id int DEFAULT nextval('seq_user_id'::regclass) NOT NULL,
  username varchar(50) NOT NULL,
  password_hash varchar(200) NOT NULL,
  role varchar(50) NOT NULL,
  CONSTRAINT PK_user PRIMARY KEY (user_id)
);
CREATE TABLE tournaments (
        tournament_id int DEFAULT nextval('seq_tournament_id' ::regclass) NOT NULL,
        tournament_name varchar(200) NOT NULL,
        game_name varchar(200) NOT NULL,
        player_count int,
        date_from date,
        date_to date,
        start_time varchar(10),
        description varchar(500),
        is_team_tournament boolean NOT NULL,
        creator_id int NOT NULL,
        CONSTRAINT FK_tournament_creator FOREIGN KEY(creator_id) REFERENCES users(user_id),
        CONSTRAINT PK_tournament PRIMARY KEY (tournament_id)
);
CREATE TABLE tournament_players(
        player_id int NOT NULL,
        tournament_id int NOT NULL,
        CONSTRAINT PK_tournament_player PRIMARY KEY(player_id, tournament_id),
        CONSTRAINT FK_tournament_player_player FOREIGN KEY(player_id) REFERENCES users(user_id),
        CONSTRAINT FK_tournament_player_tournament FOREIGN KEY(tournament_id) REFERENCES tournaments(tournament_id)
);
CREATE TABLE teams(
        team_id int DEFAULT nextval('seq_team_id' ::regclass) NOT NULL,
        creator_id int NOT NULL,
        team_name varchar(200) NOT NULL,
        team_member_count int,
        CONSTRAINT PK_team PRIMARY KEY (team_id),
        CONSTRAINT FK_team_creator FOREIGN KEY(creator_id) REFERENCES users(user_id)
);
CREATE TABLE team_members(
        team_member_id int NOT NULL,
        team_id int NOT NULL,
        CONSTRAINT PK_team_member PRIMARY KEY(team_member_id, team_id),
        CONSTRAINT FK_team_member_user FOREIGN KEY(team_member_id) REFERENCES users(user_id),
        CONSTRAINT FK_team_member_team FOREIGN KEY(team_id) REFERENCES teams(team_id)
);
CREATE TABLE tournament_teams(
        team_id int NOT NULL,
        tournament_id int NOT NULL,
        CONSTRAINT PK_tournament_team PRIMARY KEY(team_id, tournament_id),
        CONSTRAINT FK_tournament_team_team FOREIGN KEY(team_id) REFERENCES teams(team_id),
        CONSTRAINT FK_tournament_team_tournament FOREIGN KEY(tournament_id) REFERENCES tournaments(tournament_id)
);
CREATE TABLE match_results(
        match_id serial NOT NULL,
        tournament_id int NOT NULL,
        winner_id int NOT NULL,
        is_team_match boolean NOT NULL,
        CONSTRAINT PK_match_result PRIMARY KEY(match_id),
        CONSTRAINT FK_match_result_tournament FOREIGN KEY(tournament_id) REFERENCES tournaments(tournament_id)
);
INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');
COMMIT TRANSACTION;
















