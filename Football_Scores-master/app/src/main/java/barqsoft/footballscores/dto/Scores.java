package barqsoft.footballscores.dto;

/**
 * Created by E01090 on 3/14/2016.
 */
public class Scores {
    private String teamA;
    private String teamB;
    private String teamAScore;
    private String teamBScore;

    public Scores(String teamA, String teamB, String scoreA, String scoreB){
        this.teamA = teamA;
        this.teamB = teamB;
        this.teamAScore = scoreA;
        this.teamBScore = scoreB;
    }

    public String getTeamA() {
        return teamA;
    }

    public void setTeamA(String teamA) {
        this.teamA = teamA;
    }

    public String getTeamB() {
        return teamB;
    }

    public void setTeamB(String teamB) {
        this.teamB = teamB;
    }

    public String getTeamAScore() {
        return teamAScore;
    }

    public void setTeamAScore(String teamAScore) {
        this.teamAScore = teamAScore;
    }

    public String getTeamBScore() {
        return teamBScore;
    }

    public void setTeamBScore(String teamBScore) {
        this.teamBScore = teamBScore;
    }
}
