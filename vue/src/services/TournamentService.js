import axios from 'axios';


export default {
    getAllTournaments(){
        return axios.get('/tournaments');
    },
    createTournament(tournament){
        return axios.post('/add-tournament', tournament);
    },
    joinTournament(tournamentId){   
        return axios.post('/tournament/'+ tournamentId + '/join-tournament');
    },
    getAllPlayersForTournaments(tournamentId){
        return axios.get('/tournament/' + tournamentId + '/players')
    },
    getCurrentUser(){
        return axios.get('/user')
    },
    leaveTournament(tournamentId){
        return axios.delete('/tournament/' + tournamentId + '/leave-tournament')
    },
    getTournamentsByUserId() {
        return axios.get('/tournaments/entered');
    },
    updateTournament(tournament) {
        return axios.put(`/update-tournament`, tournament);
    },
    joinTeamTournament(ids){
        return axios.post('/tournament/' + ids[0] + '/team-join-tournament/' + ids[1] );
    },
    getAllTeamsByTournamentId(tournamentId){
        return axios.get('/tournament/' + tournamentId + '/teams')
    },
    getUsersTeams(){
        return axios.get('/teams/joined');
    },
    generateTeamBracket(teams){
        return axios.post('/team-bracket', teams)
    },
    postMatchResults(match) {
        return axios.post(`/post-match-results`, match)
    },
    getMatchesByTournamentId(tournamentId) {
        return axios.get('/tournament/' + tournamentId + '/match-results')
    }
    
}
