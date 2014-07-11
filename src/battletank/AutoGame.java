/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battletank;

import battletank.geneticAlgorithm.Chromosome;
import battletank.geneticAlgorithm.Population;
import java.util.Random;
 

/**
 *
 * @author Asus
 */
public class AutoGame extends Game {

    Evaluation evaluation;
    Chromosome C1, C2; // current Choromosome
    Population population ;
    int numOfUsedChromome;
    boolean check[];
    public Chromosome getC1() {
        return C1;
    }

    public void setC1(Chromosome C1) {
        this.C1 = C1;
    }

    public Chromosome getC2() {
        return C2;
    }

    public void setC2(Chromosome C2) {
        this.C2 = C2;
    }

    public Population getPopulation() {
        return population;
    }

    public void setPopulation(Population population) {
        this.population = population;
    }

    public AutoGame() {
        population = new Population(100);
        population.generatePopulation();
        check = new boolean[ population.getNumOfChromosome() ];
        for (int i=0; i <  population.getNumOfChromosome(); i ++)
            check[i] = false;
    numOfUsedChromome = 0;
        
    }
    
    
    
    public void generateGame(Chromosome C1, Chromosome C2) {
        Random R = new Random();
        int armor, damage, attackRange;
        int x, y;
        for (int i = 0; i < Setting.MAX_TANK / 2; i++) {
            armor = R.nextInt(Tank.rangeOfValue) + 1;
            damage = R.nextInt(Tank.rangeOfValue) + 1;
            attackRange = R.nextInt(Tank.rangeOfValue) + 1;

            x = R.nextInt(Game.COLUMN) + 1;
            y = R.nextInt(Game.ROW / 2) + 1;

            this.getTeamA().addTank(new Tank(armor, damage, attackRange, new Position(x, y)));
            this.getTeamB().addTank(new Tank(armor, damage, attackRange, new Position(x, Game.ROW - y)));
        }
        this.C1.clone(C1);
        this.C2.clone(C2);
        numOfUsedChromome+=2;
        
    }

    @Override
    public void updateGame() {
        
        if (this.getSetting().getGameState() == Setting.GAME_STATE.ACTION) {
        findBestDecisionAction();
        }
        else if (this.getSetting().getGameState() == Setting.GAME_STATE.FINISH) {
            // write report
            // find nextmatch
            createNextMatch();
            if (this.numOfUsedChromome != population.getNumOfChromosome())
            this.getSetting().setGameState(Setting.GAME_STATE.ACTION);
        }
    }

    DecisionAction findBestDecisionAction() {
        DecisionAction bestDecisionAction = new DecisionAction();
        int dx[] = {0, 0, -1, 1};
        int dy[] = {1, -1, 0, 0};

        Team TA = new Team();
        TA.setTanks(this.getTeamA().getTanks());
        Team TB = new Team();
        TB.setTanks(this.getTeamB().getTanks());

        if (this.getSetting().getCurrentTeamAction() == "A") {
            int maxVal = -999999;
            for (int i = 0; i < TA.getNumOfTank(); i++) {
                if (TA.getTanks()[i].isAlive()) {
                    // check each moe
                    for (int j = 0; j < 4; j++) {
                        int x = TA.getTanks()[i].getPosition().getX() + dx[j];
                        int y = TA.getTanks()[i].getPosition().getY() + dy[j];
                        Position movePosition = new Position(x, y);
                        if (movePosition.inBound(Game.COLUMN, Game.COLUMN)) {
                            TA.getTanks()[i].setPosition(movePosition);
                            int currentVal = evaluation.evaluate(TA, TB, C1);
                            if (currentVal > maxVal) {
                                maxVal = currentVal;
                                bestDecisionAction = new DecisionAction("MOVE", this.getTeamA().getTanks()[i].getPosition(), movePosition);

                            } // end of find best action
                        } // end of check in bound
                    } // end of 4 direction
                    
                    TA.setTanks( this.getTeamA().getTanks() ); // reset defaultvalue
                    //check attack
                    for (int j =0; j < this.getTeamB().getNumOfTank(); j++) {
                        if ( TB.getTanks()[j].isAlive() &&  ( TA.getTanks()[i].checkInAttackRange(TB.getTanks()[j])  )  ) {
                             TA.getTanks()[i].attack(TB.getTanks()[j]);
                             int currentVal = evaluation.evaluate(TA, TB, C1);
                            if (currentVal > maxVal) {
                                maxVal = currentVal;
                                bestDecisionAction = new DecisionAction("ATTACK", TA.getTanks()[i].getPosition(), TB.getTanks()[j].getPosition());

                            } // end of find best action
                             
                             }
                        
                        } // check attack each tank of Team B
                    
              
                } // check alive
                TA.setTanks( this.getTeamA().getTanks() ); // reset defaultvalue
                TB.setTanks( this.getTeamB().getTanks() ); // reset defaultvalue
            } // end of each Tank
            
           this.getTeamA().addDecisionAction(bestDecisionAction);
            
        } // end of team A
        else {
         int maxVal = -999999;
            for (int i = 0; i < TB.getNumOfTank(); i++) {
                if (TB.getTanks()[i].isAlive()) {
                    // check each moe
                    for (int j = 0; j < 4; j++) {
                        int x = TB.getTanks()[i].getPosition().getX() + dx[j];
                        int y = TB.getTanks()[i].getPosition().getY() + dy[j];
                        Position movePosition = new Position(x, y);
                        if (movePosition.inBound(Game.COLUMN, Game.COLUMN)) {
                            TB.getTanks()[i].setPosition(movePosition);
                            int currentVal = evaluation.evaluate(TB, TA, C2);
                            if (currentVal > maxVal) {
                                maxVal = currentVal;
                                bestDecisionAction = new DecisionAction("MOVE", this.getTeamB().getTanks()[i].getPosition(), movePosition);

                            } // end of find best action
                        } // end of check in bound
                    } // end of 4 direction
                    
                    TB.setTanks( this.getTeamB().getTanks() ); // reset defaultvalue
                    //check attack
                    for (int j =0; j < this.getTeamA().getNumOfTank(); j++) {
                        if ( TA.getTanks()[j].isAlive() &&  ( TB.getTanks()[i].checkInAttackRange(TA.getTanks()[j])  )  ) {
                             TB.getTanks()[i].attack(TA.getTanks()[j]);
                             int currentVal = evaluation.evaluate(TB, TA, C2);
                            if (currentVal > maxVal) {
                                maxVal = currentVal;
                                bestDecisionAction = new DecisionAction("ATTACK", TB.getTanks()[i].getPosition(), TA.getTanks()[j].getPosition());

                            } // end of find best action
                             
                             }
                        
                        } // check attack each tank of Team B
                    
              
                } // check alive
                TA.setTanks( this.getTeamA().getTanks() ); // reset defaultvalue
                TB.setTanks( this.getTeamB().getTanks() ); // reset defaultvalue
            } // end of each Tank B

        this.getTeamB().addDecisionAction(bestDecisionAction);
        } // end of if tank B
        return bestDecisionAction;
    }
    
    public void createNextMatch() {
    Random R=  new Random ();
    int C1,C2;
    
    do {
        C1 = R.nextInt(population.getNumOfChromosome());
        C2 = R.nextInt(population.getNumOfChromosome());
    }
    while  ( !check[C1] || !check[C2] );

    generateGame( population.getChromosomes()[C1] , population.getChromosomes()[C2]);
}
}
