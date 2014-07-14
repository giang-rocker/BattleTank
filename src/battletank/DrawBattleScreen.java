/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battletank;

import java.awt.Color;
import java.awt.Font;
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
     g.setColor(Color.blue);g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
    g.drawImage( Asset.boardlg.getImage() , Asset.boardlg.getPosition().getX(),  Asset.boardlg.getPosition().getY(), this);
      try{   
        // read teams's decision place
      for (int i =0; i < game.getTeamA().getNumOfTank(); i ++){
            if (game.getTeamA().getTanks()[i].isAlive()){
                int baseX=(game.getTeamA().getTanks()[i].getPosition().getX()-1) *CELL_UNIT;
                int baseY = (game.getTeamA().getTanks()[i].getPosition().getY()-1) *CELL_UNIT;
        g.drawImage( Asset.tankA.getImage() ,baseX ,baseY, this);
           
        if (game.getTeamA().getTanks()[i].wasAttacked)
         g.drawImage( Asset.fire.getImage() , baseX,baseY, this);
        
        g.drawImage(Asset.statistic.getImage(),baseX+CELL_UNIT*2/3, baseY , this);
      // draw Statistic
      
        g.drawString(game.getTeamA().getTanks()[i].getAmor()+"", baseX+CELL_UNIT*2/3+15, baseY+18 );
        g.drawString(game.getTeamA().getTanks()[i].getDamange()+"", baseX+CELL_UNIT*2/3+43, baseY+18 );
        g.drawString(game.getTeamA().getTanks()[i].getAttackRange()+"", baseX+CELL_UNIT*2/3+65, baseY+18 );
            }
            else // dead
                   g.drawImage( Asset.dead.getImage() ,  (game.getTeamA().getTanks()[i].getPosition().getX()-1) *CELL_UNIT, (game.getTeamA().getTanks()[i].getPosition().getY()-1) *CELL_UNIT  , this);
        }
      // team B
     
            for (int i =0; i < game.getTeamB().getNumOfTank(); i ++){
            if(game.getTeamB().getTanks()[i].isAlive() ){
                  int baseX=(game.getTeamB().getTanks()[i].getPosition().getX()-1) *CELL_UNIT;
                int baseY = (game.getTeamB().getTanks()[i].getPosition().getY()-1) *CELL_UNIT;
             g.drawImage( Asset.tankB.getImage()  ,baseX ,baseY, this); 
             if (game.getTeamB().getTanks()[i].wasAttacked) // draw Statcked
              g.drawImage( Asset.fire.getImage()  ,baseX ,baseY, this); 
             
                 // draw Statistic
              g.drawImage(Asset.statistic.getImage() ,baseX+CELL_UNIT*2/3 ,baseY, this); 
          
              g.drawString(game.getTeamB().getTanks()[i].getAmor()+"", baseX+CELL_UNIT*2/3+15, baseY+18 );
              g.drawString(game.getTeamB().getTanks()[i].getDamange()+"", baseX+CELL_UNIT*2/3+43, baseY+18 );
              g.drawString(game.getTeamB().getTanks()[i].getAttackRange()+"", baseX+CELL_UNIT*2/3+65, baseY+18 );
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
