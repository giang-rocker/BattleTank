/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battletank;

import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Asus
 */
public class TankTournament {
private static final int WAIT_LENGTH = 0; // 1 second
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        // TODO code application logic here
      
         final AutoGameScreen AT = new AutoGameScreen();
         
        AT.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
       
      
       
      // Thread will update the UI (via updateUI() call) about every 1 second
class UIUpdater extends Thread {
  @Override
  public void run() {
    while (true &&  AT.game.getSetting().getGameState()!=Setting.GAME_STATE.DONE  ) {
        
         AT.setVisible(true);
        AT.update();
        
      
           try {
               Thread.sleep(WAIT_LENGTH);
               AT.repaint();
           } catch (InterruptedException ex) {
               Logger.getLogger(TankTournament.class.getName()).log(Level.SEVERE, null, ex);
           }
          
       
    }
  }
}
        UIUpdater t = new UIUpdater();
t.start();
    }
   
}