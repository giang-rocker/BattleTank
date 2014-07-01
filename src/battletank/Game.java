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

    private Team teamA;
    private Team teamB;
    private int currentBetTurn;
    private int currentActionTurn;

    public static void setCOLUMN(int COLUMN) {
        Game.COLUMN = COLUMN;
    }

    public static void setROW(int ROW) {
        Game.ROW = ROW;
    }

    public void setTeamA(Team teamA) {
        this.teamA = teamA;
    }

    public void setTeamB(Team teamB) {
        this.teamB = teamB;
    }

    public int getCurrentBetTurn() {
        return currentBetTurn;
    }

    public void setCurrentBetTurn(int currentBetTurn) {
        this.currentBetTurn = currentBetTurn;
    }

    public int getCurrentActionTurn() {
        return currentActionTurn;
    }

    public void setCurrentActionTurn(int currentActionTurn) {
        this.currentActionTurn = currentActionTurn;
    }

    public Game() {
        this.teamA = new Team(0, null);
        this.teamB = new Team(0, null);
        this.currentActionTurn = 0;
        this.currentBetTurn = 0;
    }

    public Game(Team teamA, Team teamB) {
        this.teamA = new Team(teamA.getMoney(), teamA.getTeamName());
        this.teamB = new Team(teamB.getMoney(), teamB.getTeamName());
        this.currentActionTurn = 0;
        this.currentBetTurn = 0;
    }

   
    
}
