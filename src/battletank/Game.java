/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battletank;

/**
 *
 * @author Administrator
 */
public class Game {

    public static int COLUMN = 8;
    public static int ROW = 8;
    private int matchId;
    private Team teamA;
    private Team teamB;
    private Tank tanks[]; // tanks infomation of this game
    private Setting setting;
    private  Report report;

    public static void setCOLUMN(int COLUMN) {
        Game.COLUMN = COLUMN;
    }

    public static void setROW(int ROW) {
        Game.ROW = ROW;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public Setting getSetting() {
        return setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }

    public Report getReport() {
        return report;
    }

    public void setReadReport(Report report) {
        this.report = report;
    }

    public void setTeamA(Team teamA) {
        this.teamA = teamA;
    }

    public void setTeamB(Team teamB) {
        this.teamB = teamB;
    }

    public Tank[] getTanks() {
        return tanks;
    }

    public void setTanks(Tank[] tanks) {
        this.tanks = tanks;
    }

    public Game(int matchId) {
        this.teamA = new Team(0, null);
        this.teamB = new Team(0, null);
        this.matchId = matchId;
        setting = new Setting();
        report = new Report(matchId);
        tanks = new Tank[Setting.MAX_TANK];
    }

    public Game(Team teamA, Team teamB) {
        this.teamA = new Team(teamA.getMoney(), teamA.getTeamName());
        this.teamB = new Team(teamB.getMoney(), teamB.getTeamName());

    }

}
