import axios from 'axios';

export default {
    getAllTeams() {
        return axios.get('/teams');
    },
    createTeam(team) {
        return axios.post('/add-team', team);
    },
    joinTeam(teamId) {
        return axios.post(`/team/${teamId}/join-team`);
    },
    leaveTeam(teamId) {
        return axios.delete(`/team/${teamId}/leave-team`);
    },
    getTeamsByUserId() {
        return axios.get('/teams/joined');
    },
    updateTeam(team) {
        return axios.put(`/update-team`, team);
    }

}