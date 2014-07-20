/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battletank;

/**
 *
 * @author Asus
 */
public class ManualGame extends Game {
    
    @Override
    public void updateGame () {
    // Betting
        if (this.getSetting().getGameState()==Setting.GAME_STATE.BET) {
            // read Decision Bet of team A
            
            // read Decision Bet of Team B
            
            // update Bet data
            
            // update bet file for team A
            
            // update bet file for team B
        }
    // placing
        else if (this.getSetting().getGameState()==Setting.GAME_STATE.PLACE) {
            // execute file deciding placing of team A
            
            // execute file deciding placing of team B
            
            // read decision placing of team A
            
            // read decision placing of team B
            
            // update decision placing of two teams
            
            // generate file status for each team
        
        }
    // actioning
        else if (this.getSetting().getGameState()==Setting.GAME_STATE) {
            // execute file deciding action of team A
            
            // execute file deciding action of team B
            
            // read file decision action of team A
            
            // read file decision action of team B
            
            // update status data
            
            // update file status for each team
            
        
        }
    }
    
}
