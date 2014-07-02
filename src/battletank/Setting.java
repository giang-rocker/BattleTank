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
   public static int MAX_TANK = 50;
   private int numOfTurn;
   private  int currentTurn;
   private int numOfTank;
   private int defaultMoney;
  private  int defaultPoint;

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

 
}
