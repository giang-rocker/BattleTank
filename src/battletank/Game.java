/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battletank;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class Game {

    public enum GAME_STATE {

        BET, PLACE, ACTION, FINISH
    };
    public GAME_STATE gameState;
    public static int COLUMN = 8;
    public static int ROW = 8;
    private int matchId;
    private Team teamA;
    private Team teamB;
    private Tank tanks[]; // tanks infomation of this game
    private Setting setting;
    private Report report;

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

    public void setReport(Report report) {
        this.report = report;
    }

    public void setTeamA(Team teamA) {
        this.teamA = teamA;
    }

    public void setTeamB(Team teamB) {
        this.teamB = teamB;
    }

    public Team getTeamA() {
        return teamA;
    }

    public Team getTeamB() {
        return teamB;
    }

    public Tank[] getTanks() {
        return tanks;
    }

    public void setTanks(Tank[] tanks) {
        this.tanks = tanks;
    }

    public GAME_STATE getGameState() {
        return gameState;
    }

    public void setGameState(GAME_STATE gameState) {
        this.gameState = gameState;
    }

    public Game() {
        this.teamA = new Team(0, null);
        this.teamB = new Team(0, null);
        setting = new Setting();
        report = new Report(matchId);
        tanks = new Tank[Setting.MAX_TANK];
        this.gameState = GAME_STATE.BET;
    }

    public Game(int matchId) {
        this.teamA = new Team(0, null);
        this.teamB = new Team(0, null);
        this.matchId = matchId;
        setting = new Setting();
        report = new Report(matchId);
        tanks = new Tank[Setting.MAX_TANK];
         this.gameState = GAME_STATE.BET;
    }

    public Game(Team teamA, Team teamB) {
        this.teamA = new Team(teamA.getMoney(), teamA.getTeamName());
        this.teamB = new Team(teamB.getMoney(), teamB.getTeamName());

    }

    public void updateGame() {

        if (this.gameState == GAME_STATE.BET) {
            updateBetTurn();
        }
    }

    private void updateBetTurn() {
        try {
         
            this.getReport().readTeamBetDecision(this);
 
            try {
                this.getReport().updateTeamReportBet(this);
                this.getSetting().updateBetTurn();
              
            } catch (IOException ex) {
             
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (IOException ex) {
            
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
