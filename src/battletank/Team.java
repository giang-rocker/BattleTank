/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battletank;

import battletank.geneticAlgorithm.Chromosome;

/**
 *
 * @author Administrator
 */
public class Team {
    
    private String teamName;
    int money;
    int point;
    int numOfTank;
    Tank [] tanks; // Information of Tanks of player
    DecisionBet[] decisionBet; // information of bets of Player
    DecisionBet[] enemyDecisionBet; // information of Enemy's bets
    DecisionPlace[] decisionPlace;  // information of Enemy's
    DecisionPlace[] enemyDecisionPlace; 
    DecisionAction decisionAction; // status of map

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String Name) {
        this.teamName = Name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Tank[] getTanks() {
        return tanks;
    }

    public void setTanks(Tank[] tanks) {
        this.tanks = tanks;
    }

    public DecisionBet[]  getDecisiontBet() {
        return decisionBet;
    }

    public void setDecisionBet(DecisionBet[]  decisionBet) {
        this.decisionBet = decisionBet;
    }

    public DecisionPlace []  getDecisionPlace() {
        return decisionPlace;
    }

    public void setDecisionPlace(DecisionPlace []  decisionPlaces) {
        this.decisionPlace = decisionPlaces;
    }

    public DecisionAction getDecisionAction() {
        return decisionAction;
    }

    public void setDecisionAction(DecisionAction decisionAction) {
        this.decisionAction = decisionAction;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getNumOfTank() {
        return numOfTank;
    }

    public void setNumOfTank(int numOfTank) {
        this.numOfTank = numOfTank;
    }

    public Team(int money,String name) {
        this.teamName = name;
        this.money = money;
        this.numOfTank= 0;
        this.point=0;
        this.decisionBet = new DecisionBet[100]; 
         this.decisionPlace = new DecisionPlace[100]; 
          this.decisionAction = new DecisionAction();
         this.tanks = new Tank[100];
    }

    public Team() {
        this.numOfTank= 0;
        this.point=0;
        this.decisionBet = new DecisionBet[100]; 
         this.decisionPlace = new DecisionPlace[100]; 
          this.decisionAction = new DecisionAction();
         this.tanks = new Tank[100];
    }
    
    
    
    public void addDecisionBet (DecisionBet desisionBet) {
        this.decisionBet[desisionBet.getBetTurn()] = new DecisionBet(desisionBet.getPrice());
        
    }
    
     public void addDecisionPlace (DecisionPlace decisionplace) {
        this.decisionPlace[decisionplace.getPlaceTurn()] = new DecisionPlace(decisionplace );
        
    }
     
      public void addDecisionAction (DecisionAction decisionaction) {
        this.decisionAction = new DecisionAction (decisionaction );
        
    }
      
      public void addTank (Tank T) {
          this.tanks[numOfTank] = new Tank(T);
          this.numOfTank++;
      }
   
    public void readDecisionBet() {}
    public void decideBet() {}
    public void decidePlaceTanks() {}
    public void readDecisionStatus(){}
    public void decideAction (){}

    void payMoney(int price) {
      this.money -=price;
              }
    
   public int findTankByPosition (Position X) {
       for (int i =0; i < this.numOfTank; i ++)
           if ( this.getTanks()[i].getPosition().compare(X) ) return i;
   
       return -1;
   } 

    boolean checkOutOfTank() {
        for (int i =0; i < numOfTank; i ++ )
            if (this.getTanks()[i].getAmor()>0) return false;
        return true;
    }
    
   
   
}
