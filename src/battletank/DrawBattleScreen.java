/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battletank;

import java.awt.Graphics;
import javax.swing.JComponent;

/**
 *
 * @author Administrator
 */
public class DrawBattleScreen  extends JComponent{
    Game game;
    
    public static int CELL_UNIT = 75;
    
    public DrawBattleScreen (Game game) {
    this.game = game;
    }
    
    public void paint(Graphics g) {
     
    g.drawImage( Asset.boardlg.getImage() , Asset.boardlg.getPosition().getX(),  Asset.boardlg.getPosition().getY(), this);
      try{   
        // read teams's decision place
      for (int i =0; i < game.getTeamA().getNumOfTank(); i ++){
            if (game.getTeamA().getTanks()[i].isAlive()){
        g.drawImage( Asset.tankA.getImage() , (game.getTeamA().getTanks()[i].getPosition().getX()-1) *CELL_UNIT,( game.getTeamA().getTanks()[i].getPosition().getY()-1) *CELL_UNIT, this);
           
        if (game.getTeamA().getTanks()[i].wasAttacked)
         g.drawImage( Asset.fire.getImage() , (game.getTeamA().getTanks()[i].getPosition().getX()-1) *CELL_UNIT,( game.getTeamA().getTanks()[i].getPosition().getY()-1) *CELL_UNIT, this);
       
            }
            else // dead
                   g.drawImage( Asset.dead.getImage() ,  (game.getTeamA().getTanks()[i].getPosition().getX()-1) *CELL_UNIT, (game.getTeamA().getTanks()[i].getPosition().getY()-1) *CELL_UNIT, this);
        }
      // team B
     
            for (int i =0; i < game.getTeamB().getNumOfTank(); i ++){
            if(game.getTeamB().getTanks()[i].isAlive() ){
             g.drawImage( Asset.tankB.getImage() ,  (game.getTeamB().getTanks()[i].getPosition().getX()-1) *CELL_UNIT, (game.getTeamB().getTanks()[i].getPosition().getY()-1) *CELL_UNIT, this);
              if (game.getTeamB().getTanks()[i].wasAttacked)
              g.drawImage( Asset.fire.getImage() , (game.getTeamB().getTanks()[i].getPosition().getX()-1) *CELL_UNIT,( game.getTeamB().getTanks()[i].getPosition().getY()-1) *CELL_UNIT, this);
       
            }
            else //dead
                   g.drawImage( Asset.dead.getImage() ,  (game.getTeamB().getTanks()[i].getPosition().getX()-1) *CELL_UNIT, (game.getTeamB().getTanks()[i].getPosition().getY()-1) *CELL_UNIT, this);
        }
      }
      catch(Exception ex) {}
    
    g.finalize();
     repaint();
  
  }

    
    public void update (Game game){
    
        this.game = game;
        
    
    }
    
    
    
    
}
