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
public class DecisionBet {
    int betTurn;
    int price;

    public int getBetTurn() {
        return betTurn;
    }

    public void setBetTurn(int betTurn) {
        this.betTurn = betTurn;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public DecisionBet(int price) {
        this.price = price;
    }

    public DecisionBet(int betTurn, int price) {
        this.betTurn = betTurn;
        this.price = price;
    }

    public DecisionBet() {
    }
        
    
}
