/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battletank;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public Game() {
        this.teamA = new Team(0, null);
        this.teamB = new Team(0, null);
        setting = new Setting();
        report = new Report(matchId);
        tanks = new Tank[Setting.MAX_TANK];
        setting.setGameState(Setting.GAME_STATE.BET);

    }

    public Game(int matchId) {

        this.matchId = matchId;
        report = new Report(matchId);
        setting = new Setting();
        try {
            // set setting
            report.readSetting(this);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TestForm.class.getName()).log(Level.SEVERE, null, ex);
        }

        tanks = new Tank[Setting.MAX_TANK];
        this.teamA = new Team(setting.getDefaultMoney(), setting.getNameTeamA());
        this.teamB = new Team(setting.getDefaultMoney(), setting.getNameTeamB());

        setting.setGameState(Setting.GAME_STATE.BET);
    }

    public Game(Team teamA, Team teamB) {
        this.teamA = new Team(teamA.getMoney(), teamA.getTeamName());
        this.teamB = new Team(teamB.getMoney(), teamB.getTeamName());

    }
// Update Game by game state

    public void updateGame() {

        if (this.getSetting().getGameState() == Setting.GAME_STATE.BET) {
            if (this.getSetting().getCurrentBetTurn() == this.getSetting().getNumOfTank()) {
                setTeamGoFirst () ;
                this.getSetting().setGameState(Setting.GAME_STATE.PLACE);
            } else {
                updateBetTurn();
                if (!checkBetDicision(teamA)) {
                    this.getSetting().setGameState(Setting.GAME_STATE.FINISH);
                    this.getSetting().setWinner(this.getTeamB().getTeamName());
                }
                if (!checkBetDicision(teamB)) {
                    this.getSetting().setGameState(Setting.GAME_STATE.FINISH);
                    this.getSetting().setWinner(this.getTeamA().getTeamName());
                }
            }

        }

        if (this.getSetting().getGameState() == Setting.GAME_STATE.PLACE) {
            updatePlace();
            this.getSetting().setGameState(Setting.GAME_STATE.ACTION);
        //    if (this.getSetting().getCurrentBetTurn() == this.getSetting().getNumOfTank())  this.getSetting().setGameState(Setting.GAME_STATE.PLACE);
            // generate Status file

        } else if (this.getSetting().getGameState() == Setting.GAME_STATE.ACTION) {
            updateAction();
            //    if (this.getSetting().getCurrentBetTurn() == this.getSetting().getNumOfTank())  this.getSetting().setGameState(Setting.GAME_STATE.PLACE);
            // update status board    
        }

    }
// update BET

    void updateBetTurn() {
        try {

            this.getReport().readTeamBetDecision(this);

            try {
                this.getReport().updateTeamReportBet(this);
                // reduce money of eachteam
                this.getTeamA().payMoney(this.getTeamA().getDecisiontBet()[this.getSetting().getCurrentBetTurn()].getPrice());
                this.getTeamB().payMoney(this.getTeamB().getDecisiontBet()[this.getSetting().getCurrentBetTurn()].getPrice());

                // update Tank for bet turn
                if (this.getTeamA().getDecisiontBet()[this.getSetting().getCurrentBetTurn()].getPrice() > this.getTeamB().getDecisiontBet()[this.getSetting().getCurrentBetTurn()].getPrice()) {
                    this.getTeamA().addTank(this.getTanks()[ this.getSetting().getCurrentBetTurn() ]);
                } else if (this.getTeamA().getDecisiontBet()[this.getSetting().getCurrentBetTurn()].getPrice() < this.getTeamB().getDecisiontBet()[this.getSetting().getCurrentBetTurn()].getPrice()) {
                    this.getTeamB().addTank(this.getTanks()[ this.getSetting().getCurrentBetTurn() ]);
                } else {
                    this.getTeamA().addTank(this.getTanks()[ this.getSetting().getCurrentBetTurn() ]);
                    this.getTeamB().addTank(this.getTanks()[ this.getSetting().getCurrentBetTurn() ]);
                }
                // next turn
                this.getSetting().updateBetTurn();

            } catch (IOException ex) {

                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (IOException ex) {

            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // update Place
    private void updatePlace() {
        // read place decision
        this.getReport().readTeamPlaceDecision(this);

        // load teams's decision place
        int numOfTank = this.getTeamA().getNumOfTank();
        for (int i = 0; i < numOfTank; i++) {
            this.getTeamA().getTanks()[i].setPosition(this.getTeamA().getDecisionPlace()[i].getPosition());
        }

        numOfTank = this.getTeamB().getNumOfTank();
        for (int i = 0; i < numOfTank; i++) {
            this.getTeamB().getTanks()[i].setPosition(this.getTeamB().getDecisionPlace()[i].getPosition());
        }

        // check illegal decision
       

    }
    // set team go first
    void setTeamGoFirst () {
    
     // set team go first
        if (this.getTeamA().getMoney() > this.getTeamB().getMoney()) {
            this.getSetting().setCurrentTeamAction("A");
        } else if (this.getTeamA().getMoney() < this.getTeamB().getMoney()) {
            this.getSetting().setCurrentTeamAction("B");
        } else 
            {
            Random R = new Random();
            boolean f = R.nextBoolean();
              if (f)   this.getSetting().setCurrentTeamAction("A");
              else  this.getSetting().setCurrentTeamAction("B");
        }
         
    }

    // update Action
    private void updateAction() {
        // read action decision
        this.getReport().readTeamActionDecision(this, this.getSetting().getCurrentTeamAction());

        // check illegal decision
        // update status map
        this.updateStatusBoard();

        try {
            //update report
            this.getReport().updateReportStatus(this);
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }

        // update current team action
        this.getSetting().updateActionTurn();

    }

    // update status of game board 
    void updateStatusBoard() {
        if (this.getSetting().getCurrentTeamAction() == "A") {
            
            String command = this.getTeamA().getDecisionAction().getCommand();
             // find source
            int selectedTank = this.getTeamA().findTankByPosition(this.getTeamA().getDecisionAction().source);

            if ((command.equals("ATTACK"))) {
                // find taget
                int enemyTarget = this.getTeamB().findTankByPosition(this.getTeamA().getDecisionAction().getDestination());
                // increase point
                this.getTeamA().setPoint(this.getTeamA().getPoint()+ Math.min(this.getTeamA().getTanks()[selectedTank].getDamange(), this.getTeamB().getTanks()[enemyTarget].getAmor()));
                // update tank status
                this.getTeamA().getTanks()[selectedTank].attack(this.getTeamB().getTanks()[enemyTarget]);
            } else if ((command.equals("MOVE"))) {
                this.getTeamA().getTanks()[selectedTank].setPosition(this.getTeamA().getDecisionAction().getDestination());
             }
         } else { // team B
            String command = this.getTeamB().getDecisionAction().getCommand();
         // find source
            int selectedTank = this.getTeamB().findTankByPosition(this.getTeamB().getDecisionAction().source);
            if((command.equals("ATTACK"))) {
                // find taget
                int enemyTarget = this.getTeamA().findTankByPosition(this.getTeamB().getDecisionAction().getDestination());
                // increase point
                this.getTeamB().setPoint(this.getTeamB().getPoint()+Math.min(this.getTeamB().getTanks()[selectedTank].getDamange(), this.getTeamA().getTanks()[enemyTarget].getAmor()));
                // update tank status
                this.getTeamB().getTanks()[selectedTank].attack(this.getTeamA().getTanks()[enemyTarget]);
            } else if((command.equals("MOVE"))){
                this.getTeamB().getTanks()[selectedTank].setPosition(this.getTeamB().getDecisionAction().getDestination());
            }
        }

    }

    // check the correctness of current betting decision
    boolean checkBetDicision(Team teamX) {
        if (teamX.getMoney() < 0) {
            return false;
        }
        return true;
    }

    //  check the correctness of current placing decision
    void checkPlaceDicision() {

    }

    // check the correctness of current action decision
    void checkActionDicision() {
       // check source

       // check destination
        // check range
    }
    // finish game at Action turn
public boolean checkFinnish() {
        if (this.getSetting().getCurrentActionTurn() == Setting.MAX_ACTION_TURN) {
            return true;
        }
        if (this.getTeamA().checkOutOfTank() || this.getTeamB().checkOutOfTank()) {
            return true;
        }

        return false;
    }
     
}
