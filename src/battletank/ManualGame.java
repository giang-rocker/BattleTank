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
 * @author Asus
 */
public class ManualGame extends Game {

    @Override
    public void updateGame() {
        // Betting
        if (this.getSetting().getGameState() == Setting.GAME_STATE.BET) {
            try {
                // read Decision Bet of team A
                
                // read Decision Bet of Team B
                this.getReport().readTeamBetDecision(this);
                // update Bet data
                // update bet file for team A
                this.getReport().updateTeamReportBet(this);
                // update bet file for team B
                
                // check illegal
                
                // check end of bet
                if (this.getSetting().getCurrentBetTurn() == this.getSetting().getNumOfTank()) {
                    this.getSetting().setGameState(Setting.GAME_STATE.PLACE);
                }
            } // placing
            catch (IOException ex) {
                Logger.getLogger(ManualGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if (this.getSetting().getGameState() == Setting.GAME_STATE.PLACE) {
            try {
                             
                // read decision placing of team A & B
                this.getReport().readTeamPlaceDecision(this);
                
                // generate file status for each team
                this.getReport().updateReportStatus(this);
            } catch (IOException ex) {
                Logger.getLogger(ManualGame.class.getName()).log(Level.SEVERE, null, ex);
            }
            // check illegal
            this.getSetting().setGameState(Setting.GAME_STATE.ACTION);

        }        else if (this.getSetting().getGameState() == Setting.GAME_STATE.ACTION) {

            try {
                if (this.getSetting().getCurrentTeamAction() == "A") {
                    // read file decision action of team A OR
                    this.getReport().readTeamActionDecision(this, "A");
                } else {
                     // read file decision action of team B
                    this.getReport().readTeamActionDecision(this, "B");
                }
                
                // update status data
                this.updateStatusBoard();
                this.getReport().updateReportStatus(this);
                
                // update action turn
                this.getSetting().updateActionTurn();
                
                // check illegal
                
                // chekc finnish
                if (this.checkFinnish()) {
                    if (this.getTeamA().getPoint() > this.getTeamB().getPoint() || this.getTeamB().checkOutOfTank()) this.getSetting().setWinner("TEAM A WIN");
                    if (this.getTeamB().getPoint() > this.getTeamA().getPoint() || this.getTeamA().checkOutOfTank()) this.getSetting().setWinner("TEAM B WIN");
                    
                    if ( this.getTeamB().getPoint() == this.getTeamA().getPoint() ) this.getSetting().setWinner("DRAW !!");
                    
                    this.getSetting().setGameState(Setting.GAME_STATE.FINISH);
                }} catch (IOException ex) {
                Logger.getLogger(ManualGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
        }
        else if (this.getSetting().getGameState() == Setting.GAME_STATE.FINISH) {
              

        }
    }

}
