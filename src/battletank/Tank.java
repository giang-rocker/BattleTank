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
public class Tank {
    int tankID;

   
    Position position;
    int armor;
    int damange;
    int attackRange;
    int price;

     public int getTankID() {
        return tankID;
    }

    public void setTankID(int tankID) {
        this.tankID = tankID;
    }
    
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position.setPosition( position);
    }

    public int getAmor() {
        return armor;
    }

    public void setAmor(int armor) {
        this.armor = armor;
    }

    public int getDamange() {
        return damange;
    }

    public void setDamange(int damange) {
        this.damange = damange;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Tank(int tankID, Position position, int armor, int damange, int attackRange, int price) {
        this.tankID = tankID;
        this.position.setPosition(position);
        this.armor = armor;
        this.damange = damange;
        this.attackRange = attackRange;
        this.price = price;
    }

    public Tank(int armor, int damange, int attackRange) {
        this.armor = armor;
        this.damange = damange;
        this.attackRange = attackRange;
    }
    
    
    
}
