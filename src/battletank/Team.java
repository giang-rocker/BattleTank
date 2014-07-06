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
public class Team {
    
    private String teamName;
    int money;
    Tank [] tanks; // Information of Tanks of player
    Tank[] enemyTanks ; //Information of Enemy's Tanks
    DecisionBet[] decisionBet; // information of bets of Player
    DecisionBet[] enemyDecisionBet; // information of Enemy's bets
    DecisionPlace[] decisionPlaces;  // information of Enemy's
    DecisionPlace[] enemyDecisionPlaces; 
    DecisionAction[] decisionAction; // status of map

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

    public DecisionPlace []  getDecisionPlaces() {
        return decisionPlaces;
    }

    public void setDecisionPlaces(DecisionPlace []  decisionPlaces) {
        this.decisionPlaces = decisionPlaces;
    }

    public DecisionAction [] getDecisionAction() {
        return decisionAction;
    }

    public void setDecisionAction(DecisionAction [] decisionAction) {
        this.decisionAction = decisionAction;
    }

    public Team(int money,String name) {
        this.teamName = name;
        this.money = money;
        this.decisionBet = new DecisionBet[100]; 
    }

    public Team() {
    }
    
    
    
    public void addDecisionBet (DecisionBet desisionBet) {
        this.decisionBet[desisionBet.getBetTurn()] = new DecisionBet(desisionBet.getPrice());
        
    }
    
   
    public void readDecisionBet() {}
    public void decideBet() {}
    public void decidePlaceTanks() {}
    public void readDecisionStatus(){}
    public void decideAction (){}
    
    
    
}
