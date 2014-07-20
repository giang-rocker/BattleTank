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
        // draw main backgdound
         g.drawImage( Asset.boardlg.getImage() , Asset.boardlg.getPosition().getX(),  Asset.boardlg.getPosition().getY(), this);
   
        
         // draw bet backgroud
        if ( game.getSetting().getGameState() == Setting.GAME_STATE.BET ) drawBet(g);
        else { // draw action background
        
     g.setColor(Color.blue);g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
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
    }
    g.finalize();
     repaint();
  
        
  }

    
    public void update (Game game){
    
        this.game = game;
        
    
    }
    
    
    
void drawBet (Graphics g) {
    int baseX = 0 ;
    int baseY = 200;
   int betIndex =  game.getSetting().getCurrentBetTurn()-1 ; 
   
    // draw Background
    g.drawImage (Asset.betBackground.getImage(), baseX,baseY,this);
    // draw Price A
    int priceA = game.getTeamA().getDecisiontBet()[betIndex].getPrice();
    
    int priceB = game.getTeamB().getDecisiontBet()[ betIndex ].getPrice();
   g.setColor(Color.decode("#ff8a00"));g.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 50));
   g.drawString  ( priceA+"",baseX+45,baseY+150  ); 
   g.drawString  (priceB +"",baseX+420,baseY+150  ); 
   
   if ( priceA>=priceB  )
   g.drawImage(Asset.check.getImage(),baseX+65,baseY+150,this  ); 
   if ( priceA<=priceB  )
   g.drawImage(Asset.check.getImage(),baseX+450,baseY+150,this  ); 
   
   
     g.setColor(Color.decode("#003e09") );g.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 40));
    // draw Tank's staticstic
       g.drawString(game. getTanks()[betIndex].getAmor()+"", baseX+245,baseY+200 );
        g.drawString(game .getTanks()[betIndex].getDamange()+"", baseX+310,baseY+200);
        g.drawString(game .getTanks()[betIndex].getAttackRange()+"", baseX+365,baseY+200 );

}
    
}
