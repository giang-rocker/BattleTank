/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battletank;

import java.awt.Graphics;
import java.io.IOException;
import javax.swing.JComponent;
import javax.swing.JFrame;
/*
 *
 * @author Administrator
 */
public class Draw  extends JComponent{
    Game game;
    
    public Draw (Game game) {
    this.game = game;
    }
    
    public void paint(Graphics g) {
     
    g.drawImage( Asset.board.getImage() , Asset.board.getPosition().getX(),  Asset.board.getPosition().getY(), this);
    
    int i = 0 ; 
        
        // read teams's decision place
        while ( game.getTeamA().getDecisionPlace()[i]!=null ) {
        g.drawImage( Asset.tankA.getImage() , (game.getTeamA().getDecisionPlace()[i].getPosition().getX()-1) *37,( game.getTeamA().getDecisionPlace()[i].getPosition().getY()-1) *37, this);
            i++;
        }
     i = 0 ; 
        
        // read teams's decision place
        while ( game.getTeamB().getDecisionPlace()[i]!=null ) {
        g.drawImage( Asset.tankB.getImage() ,  (game.getTeamB().getDecisionPlace()[i].getPosition().getX()-1) *37, (game.getTeamB().getDecisionPlace()[i].getPosition().getY()-1) *37, this);
            i++;
        }
    
    g.finalize();
     repaint();
  }

    
    public void update (Game game){
    
        this.game = game;
        
    
    }
    
}
