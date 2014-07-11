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
public class Evaluation {
    public int evaluate (Team player, Team enemy, Chromosome C) {
       int value = 0;
       // team A value
       int valueA = 0;
        for (int i =0; i < player.getNumOfTank(); i++ )
            valueA+= evaluate( player.getTanks()[i] , C);
       // team B value
       int valueB = 0;
         for (int i =0; i < enemy.getNumOfTank(); i++ )
            valueB+= evaluate( enemy.getTanks()[i] , C);
         
       // team A Tank inCheck value
       int valueInCheckA = 0;
       // team B tank incheckValue
       int valueInCheckB = 0;
       
        for (int i =0; i < player.getNumOfTank(); i ++)
             for (int j =0; j < enemy.numOfTank; j++) {
             if ( player.getTanks()[i].checkInAttackRange( enemy.getTanks()[j] ) ) valueInCheckA+= evaluate(enemy.getTanks()[i], C);
              if ( enemy.getTanks()[i].checkInAttackRange( player.getTanks()[j] ) ) valueInCheckB+= evaluate(player.getTanks()[i], C);
             
             }
       
       
  
       
       value = valueA - valueB + valueInCheckB - valueInCheckA;
      // System.out.println("value "  + value);
       return value;
   }
    
      public int evaluate (Tank T, Chromosome C) {
       return ( C.getGen(0)*T.getAmor() + C.getGen(1)*T.getDamange() + C.getGen(2)*T.getAttackRange()); 
    }
}
