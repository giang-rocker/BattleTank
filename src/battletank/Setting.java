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
public class Setting {

    public static enum GAME_STATE {

        BET, PLACE, ACTION, FINISH
    };

 

    public GAME_STATE gameState;

    public static int MAX_TANK = 14;
    private int numOfTurn;
    private int currentTurn;

    private int currentBetTurn;

    private int numOfTank;
    private int defaultMoney;
    private int defaultPoint;
    private int MatchID;
    private String nameTeamA;
    private String nameTeamB;
    private String currentTeamAction;
    private String Winner;
    
    public String getNameTeamA() {
        return nameTeamA;
    }

    public void setNameTeamA(String nameTeamA) {
        this.nameTeamA = nameTeamA;
    }

    public String getNameTeamB() {
        return nameTeamB;
    }

    public void setNameTeamB(String nameTeamB) {
        this.nameTeamB = nameTeamB;
    }

    public int getMatchID() {
        return MatchID;
    }

    public void setMatchID(int MatchID) {
        this.MatchID = MatchID;
    }

    public int getNumOfTurn() {
        return numOfTurn;
    }

    public void setNumOfTurn(int numOfTurn) {
        this.numOfTurn = numOfTurn;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
    }

    public int getNumOfTank() {
        return numOfTank;
    }

    public void setNumOfTank(int numOfTank) {
        this.numOfTank = numOfTank;
    }

    public int getDefaultMoney() {
        return defaultMoney;
    }

    public void setDefaultMoney(int defaultMoney) {
        this.defaultMoney = defaultMoney;
    }

    public int getDefaultPoint() {
        return defaultPoint;
    }

    public void setDefaultPoint(int defaultPoint) {
        this.defaultPoint = defaultPoint;
    }

    public static int getMAX_TANK() {
        return MAX_TANK;
    }

    public static void setMAX_TANK(int MAX_TANK) {
        Setting.MAX_TANK = MAX_TANK;
    }

    public int getCurrentBetTurn() {
        return currentBetTurn;
    }

    public void setCurrentBetTurn(int currentBetTurn) {
        this.currentBetTurn = currentBetTurn;
    }

    public GAME_STATE getGameState() {
        return gameState;
    }

    public void setGameState(GAME_STATE gameState) {
        this.gameState = gameState;
    }

    public String getCurrentTeamAction() {
        return currentTeamAction;
    }

    public void setCurrentTeamAction(String currentTeamAction) {
        this.currentTeamAction = currentTeamAction;
    }

    public String getWinner() {
        return Winner;
    }

    public void setWinner(String Winner) {
        this.Winner = Winner;
    }

    
    
    public Setting(int numOfTurn, int currentTurn, int currentBetTurn, int numOfTank, int defaultMoney, int defaultPoint, int MatchID, String nameTeamA, String nameTeamB) {
        this.numOfTurn = numOfTurn;
        this.currentTurn = currentTurn;
        this.currentBetTurn = currentBetTurn;
        this.numOfTank = numOfTank;
        this.defaultMoney = defaultMoney;
        this.defaultPoint = defaultPoint;
        this.MatchID = MatchID;
        this.nameTeamA = nameTeamA;
        this.nameTeamB = nameTeamB;
    }

    public Setting() {
        this.setCurrentBetTurn(0);
    }

    public void updateTurn() {

    }

    void updateBetTurn() {
        this.currentBetTurn++;

    }

    void updateActionTurn() {
        if (this.getCurrentTeamAction() == "A") {
            this.setCurrentTeamAction("B");
        } else {
            this.setCurrentTeamAction("A");
        }
    }
}
