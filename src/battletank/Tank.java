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
public class Tank {
    
    public static int rangeOfValue = 8;
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
    
    public Tank (Tank t ) {
       this.position = new Position(t.getPosition());
        this.armor = t.armor;
        this.damange = t.damange;
        this.attackRange = t.attackRange;
    }

    public Tank(int armor, int damange, int attackRange) {
        this.position = new Position(0,0);
        this.armor = armor;
        this.damange = damange;
        this.attackRange = attackRange;
    }
    
    public Tank(int armor, int damange, int attackRange, Position P) {
        this.position = new Position(P);
        this.armor = armor;
        this.damange = damange;
        this.attackRange = attackRange;
    }
    public void attack ( Tank enemy ) {
    
        enemy.setAmor( Math.max(0, enemy.getAmor()-this.getDamange()) );
    
    }
    
    public boolean isAlive () {
    return this.armor >0;
    }
    
  public boolean checkInAttackRange (Tank enemy) {
      boolean f= false;
      int manhattan = Math.abs( enemy.getPosition().getX() - this.getPosition().getX() ) +  Math.abs( enemy.getPosition().getY() - this.getPosition().getY() );
      
        return this.getAttackRange() >= manhattan;
      
  }

  
}
