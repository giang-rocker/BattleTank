/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battletank;

import battletank.geneticAlgorithm.Chromosome;
import battletank.geneticAlgorithm.Population;
import java.awt.Point;
import java.util.Random;

/**
 *
 * @author Asus
 */
public class AutoGame extends Game {

    Evaluation evaluation;
    int C1, C2; // current Choromosome
    Population population;
    int numOfUsedChromome;
    boolean check[];
    int currentGame =0;
    
boolean c[][] ;
    public int getC1() {
        return C1;
    }

    public void setC1(int C1) {
        this.C1 = C1;
    }

    public int getC2() {
        return C2;
    }

    public void setC2(int C2) {
        this.C2 = C2;
    }

    public Population getPopulation() {
        return population;
    }

    public void setPopulation(Population population) {
        this.population = population;
    }

    public int getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(int currentGame) {
        this.currentGame = currentGame;
    }

    public AutoGame() {
        evaluation = new Evaluation();
        population = new Population(50);
        population.generatePopulation();
        check = new boolean[population.getNumOfChromosome()];
        for (int i = 0; i < population.getNumOfChromosome(); i++) {
            check[i] = false;
        }
        numOfUsedChromome = 0;
         c = new boolean[this.getPopulation().getNumOfChromosome()][this.getPopulation().getNumOfChromosome()];
        // reset check board
       for (int i =0; i < this.getPopulation().getNumOfChromosome(); i ++)
        for (int j =0; j < this.getPopulation().getNumOfChromosome(); j ++) {
             c[i][j] = false;
        }

    }


    public void updateAutoGame() {

        if (this.getSetting().getGameState() == Setting.GAME_STATE.ACTION) {
            findBestDecisionAction();
            updateStatusBoard();
            this.getSetting().updateActionTurn();

            if (checkFinnish()) this.getSetting().setGameState( Setting.GAME_STATE.FINISH);
            

        } else if (this.getSetting().getGameState() == Setting.GAME_STATE.FINISH) {
            // end of a game
            // update point
            if (this.getTeamA().getPoint() > this.getTeamB().getPoint()) {
                this.getPopulation().getChromosomes()[C1].setFitnessValue(this.getPopulation().getChromosomes()[C1].getFitnessValue() + 3);
            } else if (this.getTeamA().getPoint() < this.getTeamB().getPoint()) {
                this.getPopulation().getChromosomes()[C2].setFitnessValue(this.getPopulation().getChromosomes()[C2].getFitnessValue() + 1);
            } else {

                this.getPopulation().getChromosomes()[C2].setFitnessValue(this.getPopulation().getChromosomes()[C2].getFitnessValue() + 1);
                this.getPopulation().getChromosomes()[C1].setFitnessValue(this.getPopulation().getChromosomes()[C1].getFitnessValue() + 1);
            }
            // find nextmatch
            
          //  this.getPopulation().sortByFitnessValue();
            
           if (! nextTournamentMatch()) this.getSetting().setGameState(Setting.GAME_STATE.DONE); 
           else createNextMatch();
            
            if (this.numOfUsedChromome != population.getNumOfChromosome()) {
          //      this.getSetting().setGameState(Setting.GAME_STATE.ACTION);
            }
           
        }
    }

    void findBestDecisionAction() {
        DecisionAction bestDecisionAction = new DecisionAction();
        int dx[] = {0, 0, -1, 1};
        int dy[] = {1, -1, 0, 0};

        int maxVal = -999999;

        if (this.getSetting().getCurrentTeamAction() == "A") {

            maxVal = -999999;
            for (int i = 0; i < this.getTeamA().getNumOfTank(); i++) {
                if (this.getTeamA().getTanks()[i].isAlive()) {
                     // check each move
                    for (int j = 0; j < 4; j++) {
                        int x = this.getTeamA().getTanks()[i].getPosition().getX() + dx[j];
                        int y = this.getTeamA().getTanks()[i].getPosition().getY() + dy[j];
                        Position movePosition = new Position(x, y);

                     
                        Position oldPosition  = new Position ( this.getTeamA().getTanks()[i].getPosition() );
                        if (movePosition.inBound(1,Game.COLUMN,1, Game.ROW/2) && this.getTeamA().findTankByPosition(movePosition)==-1 ) {

                            this.getTeamA().getTanks()[i].setPosition(movePosition);
                            int currentVal = evaluation.evaluate(this.getTeamA(), this.getTeamB(), population.getChromosomes()[C1]);
                            if (currentVal > maxVal) {
                                maxVal = currentVal;
                                bestDecisionAction = new DecisionAction("MOVE", oldPosition, movePosition);
                           } // end of find best action
                        } // end of check in bound
                        this.getTeamA().getTanks()[i].setPosition(oldPosition); // reset defaultvalue
                    } // end of 4 direction

                    //check attack
                    for (int j = 0; j < this.getTeamB().getNumOfTank(); j++) {
                        if (this.getTeamB().getTanks()[j].isAlive() && (this.getTeamA().getTanks()[i].checkInAttackRange(this.getTeamB().getTanks()[j]))) {
                            Tank oldTank = new Tank(this.getTeamB().getTanks()[j]);
                            this.getTeamA().getTanks()[i].attack(this.getTeamB().getTanks()[j]);
                            int currentVal = evaluation.evaluate(this.getTeamA(), this.getTeamB(), population.getChromosomes()[C1]);
                            if (currentVal > maxVal) {
                                maxVal = currentVal;
                                bestDecisionAction = new DecisionAction("ATTACK", this.getTeamA().getTanks()[i].getPosition(), this.getTeamB().getTanks()[j].getPosition());

                            } // end of find best action
                            this.getTeamB().getTanks()[j] = new Tank(oldTank);
                        }

                    } // check attack each tank of Team B

                } // check alive
            } // end of each Tank

            this.getTeamA().addDecisionAction(bestDecisionAction);
            


        } // end of team A
        else { // else team B

            maxVal = -999999;
            for (int i = 0; i < this.getTeamB().getNumOfTank(); i++) {
                if (this.getTeamB().getTanks()[i].isAlive()) {
                    // check each moe
                    for (int j = 0; j < 4; j++) {
                        int x = this.getTeamB().getTanks()[i].getPosition().getX() + dx[j];
                        int y = this.getTeamB().getTanks()[i].getPosition().getY() + dy[j];
                        Position movePosition = new Position(x, y);

                        Position oldPosition  = new Position ( this.getTeamB().getTanks()[i].getPosition() );
                        if (movePosition.inBound(1,Game.COLUMN,Game.ROW/2+1, Game.ROW)&& this.getTeamB().findTankByPosition(movePosition)==-1) {

                            this.getTeamB().getTanks()[i].setPosition(movePosition);
                            int currentVal = evaluation.evaluate(this.getTeamB(), this.getTeamA(), population.getChromosomes()[C2]);
                            if (currentVal > maxVal) {
                                maxVal = currentVal;
                                bestDecisionAction = new DecisionAction("MOVE", oldPosition, movePosition);
                               } // end of find best action
                        } // end of check in bound
                        this.getTeamB().getTanks()[i].setPosition(oldPosition); // reset defaultvalue
                    } // end of 4 direction

                    //check attack
                    for (int j = 0; j < this.getTeamA().getNumOfTank(); j++) {
                        if (this.getTeamA().getTanks()[j].isAlive() && (this.getTeamB().getTanks()[i].checkInAttackRange(this.getTeamA().getTanks()[j]))) {
                            Tank oldTank = new Tank(this.getTeamA().getTanks()[j]);
                            this.getTeamB().getTanks()[i].attack(this.getTeamA().getTanks()[j]);
                            int currentVal = evaluation.evaluate(this.getTeamB(), this.getTeamA(), population.getChromosomes()[C2]);
                            if (currentVal > maxVal) {
                                maxVal = currentVal;
                                bestDecisionAction = new DecisionAction("ATTACK", this.getTeamB().getTanks()[i].getPosition(), this.getTeamA().getTanks()[j].getPosition());

                            } // end of find best action
                            this.getTeamA().getTanks()[j] = new Tank(oldTank);
                        }

                    } // check attack each tank of Team B

                } // check alive
            } // end of each Tank

            this.getTeamB().addDecisionAction(bestDecisionAction);
           

        } // end of team B

    }
    
    public void createNextMatch() {
    //  random match
        Random R = new Random();
/* 
        do {
            C1 = R.nextInt(population.getNumOfChromosome());
            C2 = R.nextInt(population.getNumOfChromosome());
        } while (check[C1] || check[C2] || C1== C2);

        check[C1] = true;
        check[C2] = true;
        */
        this.setTeamA(new Team());
        this.setTeamB(new Team());

        int armor, damage, attackRange;
        int x, y;
        for (int i = 0; i < Setting.MAX_TANK / 2; i++) {
            armor = R.nextInt(Tank.rangeOfValue) + 1;
            damage = R.nextInt(Tank.rangeOfValue/2) + 1;
            attackRange = R.nextInt(Tank.rangeOfValue) + 1;
            do {
                x = R.nextInt(Game.COLUMN) + 1;
                y = R.nextInt(Game.ROW / 2) + 1;
            } while (this.getTeamA().findTankByPosition(new Position(x, y)) != -1);
            this.getTeamA().addTank(new Tank(armor, damage, attackRange, new Position(x, y)));
            this.getTeamB().addTank(new Tank(armor, damage, attackRange, new Position(x, Game.ROW - y + 1)));
        }

        numOfUsedChromome += 2;
        currentGame++;
        this.getSetting().setCurrentTeamAction("A");
        this.getSetting().setCurrentActionTurn(0);
        this.getSetting().setGameState(Setting.GAME_STATE.ACTION);

    }
    
    boolean  checkFinnish() {
        if ( this.getSetting().getCurrentActionTurn() == Setting.MAX_ACTION_TURN ) return true;
       if ( this.getTeamA().checkOutOfTank () || this.getTeamB().checkOutOfTank() ) return true;
        
    return false;
    }
    
    boolean nextTournamentMatch () {
    for (int i =0; i < this.getPopulation().getNumOfChromosome(); i ++)
        for (int j =i+1; j < this.getPopulation().getNumOfChromosome(); j ++) {
            if (!c[i][j]) { c[i][j] = true; C1=i;C2=j; return true; }
        }
    return false;
    }
}
