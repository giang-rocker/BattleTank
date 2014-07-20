/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battletank;

import battletank.geneticAlgorithm.Chromosome;
import battletank.geneticAlgorithm.Population;
import java.awt.Point;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    int currentGame = 0;

    boolean c[][];

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
        super();
        evaluation = new Evaluation();
        population = new Population(300);
        population.generatePopulation();
        check = new boolean[population.getNumOfChromosome()];
        for (int i = 0; i < population.getNumOfChromosome(); i++) {
            check[i] = false;
        }
        numOfUsedChromome = 0;
        c = new boolean[this.getPopulation().getNumOfChromosome()][this.getPopulation().getNumOfChromosome()];
        // reset check board
        for (int i = 0; i < this.getPopulation().getNumOfChromosome(); i++) {
            for (int j = 0; j < this.getPopulation().getNumOfChromosome(); j++) {
                c[i][j] = false;
            }
        }

        currentGame = 0;

    }

    public void updateAutoGame() {
        if (this.getSetting().getGameState() == Setting.GAME_STATE.BET) {

            if (this.getSetting().getCurrentBetTurn() == this.getSetting().getNumOfTank()) {
                setTeamGoFirst();
                this.getSetting().setGameState(Setting.GAME_STATE.PLACE);
                System.out.println("DONE BET");
            } else {

                findBestDecisionBet();
                updateBetTurn();
                // update current statis
                
                if (!checkBetDicision(this.getTeamA())) {
                    this.getSetting().setGameState(Setting.GAME_STATE.FINISH);
                    this.getSetting().setWinner(this.getTeamB().getTeamName());
                }
                else if (!checkBetDicision(this.getTeamB())) {
                    this.getSetting().setGameState(Setting.GAME_STATE.FINISH);
                    this.getSetting().setWinner(this.getTeamA().getTeamName());
                }
                
            }

        } 
        if (this.getSetting().getGameState() == Setting.GAME_STATE.PLACE) {
            findBestDecisionPlace();
            this.getSetting().setGameState(Setting.GAME_STATE.ACTION);
            System.out.println("DONE PLACING");
        } 
        if (this.getSetting().getGameState() == Setting.GAME_STATE.ACTION) {
System.out.println("BE ACTTING");
            findBestDecisionAction();
            
System.out.println("DONE FINDING BEST ACTION");
            updateStatusBoard();
            
System.out.println("DONE UPDATING BOARD");
            this.getSetting().updateActionTurn();

            if (checkFinnish()) {
                this.getSetting().setGameState(Setting.GAME_STATE.FINISH);
            }

        } 
        if (this.getSetting().getGameState() == Setting.GAME_STATE.FINISH) {

            // end of a game
            // update point
            if (this.getTeamA().getPoint() > this.getTeamB().getPoint() || this.getTeamB().checkOutOfTank()) {
                this.getPopulation().getChromosomes()[C1].setPoint(this.getPopulation().getChromosomes()[C1].getPoint() + 3);
            } else if (this.getTeamA().getPoint() < this.getTeamB().getPoint() || this.getTeamA().checkOutOfTank()) {
                this.getPopulation().getChromosomes()[C2].setPoint(this.getPopulation().getChromosomes()[C2].getPoint() + 3);
            } else {

                this.getPopulation().getChromosomes()[C2].setPoint(this.getPopulation().getChromosomes()[C2].getPoint() + 1);
                this.getPopulation().getChromosomes()[C1].setPoint(this.getPopulation().getChromosomes()[C1].getPoint() + 1);
            }
            // find nextmatch

            //  this.getPopulation().sortByPoint();
            // next season
            if (!nextTournamentMatch()) {
                this.getPopulation().calculateFitnessValue();
                try {
                    this.getReport().reportGeneration(this.getPopulation());
                } catch (IOException ex) {
                    Logger.getLogger(AutoGame.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.getPopulation().evolution();
                //   System.out.println("DONE EVOLUION");
                this.nextSeason();
                

            }

            createNextMatch();

            if (this.numOfUsedChromome != population.getNumOfChromosome()) {
                //      this.getSetting().setGameState(Setting.GAME_STATE.ACTION);
            }

        }
    }

    @Override
    void updateBetTurn() {

        // update Tank for bet turn
        if (this.getTeamA().getDecisiontBet()[this.getSetting().getCurrentBetTurn()].getPrice() >= this.getTeamB().getDecisiontBet()[this.getSetting().getCurrentBetTurn()].getPrice()) {
            this.getTeamA().addTank(this.getTanks()[ this.getSetting().getCurrentBetTurn()]);
            this.getTeamA().payMoney(this.getTeamA().getDecisiontBet()[this.getSetting().getCurrentBetTurn()].getPrice());

        } 
        if (this.getTeamA().getDecisiontBet()[this.getSetting().getCurrentBetTurn()].getPrice() <= this.getTeamB().getDecisiontBet()[this.getSetting().getCurrentBetTurn()].getPrice()) {
            this.getTeamB().addTank(this.getTanks()[ this.getSetting().getCurrentBetTurn()]);
            this.getTeamB().payMoney(this.getTeamB().getDecisiontBet()[this.getSetting().getCurrentBetTurn()].getPrice());

        }
        // next turn
        this.getSetting().updateBetTurn();
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

                        Position oldPosition = new Position(this.getTeamA().getTanks()[i].getPosition());
                        if (movePosition.inBound(1, Game.COLUMN, 1, Game.ROW / 2) && this.getTeamA().findTankByPosition(movePosition) == -1) {

                            this.getTeamA().getTanks()[i].setPosition(movePosition);
                            int currentVal = evaluation.evaluate(this.getTeamA(), this.getTeamB(), population.getChromosomes()[C1], 0);
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
                            Tank oldTank = new Tank();
                            oldTank.clone(this.getTeamB().getTanks()[j]);
                            int bonusPoint = this.getTeamA().getTanks()[i].attack(this.getTeamB().getTanks()[j]);
                            int currentVal = evaluation.evaluate(this.getTeamA(), this.getTeamB(), population.getChromosomes()[C1], bonusPoint);
                            if (currentVal > maxVal) {
                                maxVal = currentVal;
                                bestDecisionAction = new DecisionAction("ATTACK", this.getTeamA().getTanks()[i].getPosition(), this.getTeamB().getTanks()[j].getPosition());

                            } // end of find best action
                            this.getTeamB().getTanks()[j].clone(oldTank);
                        }

                    } // check attack each tank of Team B

                } // check alive
            } // end of each Tank 
            this.getTeamA().addDecisionAction(bestDecisionAction);

        } // end of team A
        else if (this.getSetting().getCurrentTeamAction()=="B") { // else team B
            maxVal = -999999;
            for (int i = 0; i < this.getTeamB().getNumOfTank(); i++) {
                if (this.getTeamB().getTanks()[i].isAlive()) {
                    // check each moe
                    for (int j = 0; j < 4; j++) {
                        int x = this.getTeamB().getTanks()[i].getPosition().getX() + dx[j];
                        int y = this.getTeamB().getTanks()[i].getPosition().getY() + dy[j];
                        Position movePosition = new Position(x, y);

                        Position oldPosition = new Position(this.getTeamB().getTanks()[i].getPosition());
                        if (movePosition.inBound(1, Game.COLUMN, Game.ROW / 2 + 1, Game.ROW) && this.getTeamB().findTankByPosition(movePosition) == -1) {

                            this.getTeamB().getTanks()[i].setPosition(movePosition);
                            int currentVal = evaluation.evaluate(this.getTeamB(), this.getTeamA(), population.getChromosomes()[C2], 0);
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
                            Tank oldTank = new Tank();
                            oldTank.clone(this.getTeamA().getTanks()[j]);
                            int bonusPoint = this.getTeamB().getTanks()[i].attack(this.getTeamA().getTanks()[j]);
                            int currentVal = evaluation.evaluate(this.getTeamB(), this.getTeamA(), population.getChromosomes()[C2], bonusPoint);
                            if (currentVal > maxVal) {
                                maxVal = currentVal;
                                bestDecisionAction = new DecisionAction("ATTACK", this.getTeamB().getTanks()[i].getPosition(), this.getTeamA().getTanks()[j].getPosition());

                            } // end of find best action
                            this.getTeamA().getTanks()[j].clone(oldTank);
                        }

                    } // check attack each tank of Team B

                } // check alive
            } // end of each Tank
           
            this.getTeamB().addDecisionAction(bestDecisionAction);

        } // end of team B
         System.out.println("Current Team : " +  this.getSetting().getCurrentTeamAction());
    }
    /*
     // create next Battle Match without betting & placing
     public void createNextMatch() {
     //  random match
     Random R = new Random();
         
     this.setTeamA(new Team());
     this.setTeamB(new Team());

     int armor, damage, attackRange;
     int x, y;
     for (int i = 0; i < Setting.MAX_TANK / 2; i++) {
     armor = R.nextInt(Tank.rangeOfValue) + 1;
     damage = R.nextInt(Tank.rangeOfValue / 2) + 1;
     attackRange = R.nextInt(Tank.rangeOfValue - 2) + 1;
     do {
     x = R.nextInt(Game.COLUMN) + 1;
     y = R.nextInt(Game.ROW / 2) + 1;
     } while (this.getTeamA().findTankByPosition(new Position(x, y)) != -1);
     this.getTeamA().addTank(new Tank(armor, damage, attackRange, new Position(x, y)));
     this.getTeamB().addTank(new Tank(armor, damage, attackRange, new Position(x, Game.ROW - y + 1)));
     }

     currentGame++;
     if (R.nextBoolean()) {
     this.getSetting().setCurrentTeamAction("A");
     } else {
     this.getSetting().setCurrentTeamAction("B");
     }

     this.getSetting().setCurrentActionTurn(0);
     this.getSetting().setGameState(Setting.GAME_STATE.ACTION);

     }
     */

    public void createNextMatch() {
        //  random match
        Random R = new Random();

        this.getTeamA().resetStatistic();
        this.getTeamB().resetStatistic();

        int armor, damage, attackRange;
        int x, y;
        for (int i = 0; i < Setting.MAX_TANK; i++) {
            armor = R.nextInt(Tank.rangeOfValue) + 1;
            damage = R.nextInt(Tank.rangeOfValue / 2) + 1;
            attackRange = R.nextInt(Tank.rangeOfValue - 2) + 2;
            this.getTanks()[i] = new Tank(armor, damage, attackRange);

        }

        currentGame++;
        this.getTeamA().setMoney(this.getSetting().getDefaultMoney());
        this.getTeamB().setMoney(this.getSetting().getDefaultMoney());
        this.getSetting().setCurrentActionTurn(0);
        this.getSetting().setCurrentBetTurn(0);
        this.getSetting().setGameState(Setting.GAME_STATE.BET);
        //     System.out.println("END OF CREATE");

    }

    boolean checkFinnish() {
        if (this.getSetting().getCurrentActionTurn() == Setting.MAX_ACTION_TURN) {
            return true;
        }
        if (this.getTeamA().checkOutOfTank() || this.getTeamB().checkOutOfTank()) {
            return true;
        }

        return false;
    }

    boolean nextTournamentMatch() {
        for (int i = 0; i < this.getPopulation().getNumOfChromosome() - 1; i++) {
            for (int j = i + 1; j < this.getPopulation().getNumOfChromosome(); j++) {
                if (!c[i][j]) {
                    c[i][j] = true;
                    C1 = i;
                    C2 = j;
                    return true;
                }
            }
        }
        return false;
    }

    // reset statistic for next season ;
    void nextSeason() {

        numOfUsedChromome = 0;
        c = new boolean[this.getPopulation().getNumOfChromosome()][this.getPopulation().getNumOfChromosome()];
        // reset check board
        for (int i = 0; i < this.getPopulation().getNumOfChromosome(); i++) {
            for (int j = 0; j < this.getPopulation().getNumOfChromosome(); j++) {
                c[i][j] = false;
            }
        }
        
        this.getSetting().setGameState(Setting.GAME_STATE.ACTION);
        currentGame = 0;

    }

    // find best decision for each team
    void findBestDecisionBet() {
        
        int price = 0;
        int numOfTopTank = (this.getSetting().getNumOfTank() - this.getSetting().getCurrentBetTurn()) / 2 + this.getSetting().getNumOfTank() % 2;

        // evaluate value of each tank
        int valueA[] = new int[this.getSetting().getNumOfTank()];
        int valueB[] = new int[this.getSetting().getNumOfTank()];

        for (int i = 0; i < this.getSetting().getCurrentBetTurn(); i++) {
            valueA[i] = 0;
            valueB[i] = 0;
        }

        for (int i = this.getSetting().getCurrentBetTurn(); i < this.getSetting().getNumOfTank(); i++) {
            valueA[i] = evaluation.evaluate(this.getTanks()[i], this.getPopulation().getChromosomes()[C1]);
            valueB[i] = evaluation.evaluate(this.getTanks()[i], this.getPopulation().getChromosomes()[C2]);
        }

        // sort value
        for (int i = 0; i < this.getSetting().getNumOfTank(); i++) {
            for (int j = i + 1; j < this.getSetting().getNumOfTank(); j++) {
                if (valueA[i] < valueA[j]) {
                    int temp = valueA[i];
                    valueA[i] = valueA[j];
                    valueA[j] = temp;

                }
                if (valueB[i] < valueB[j]) {
                    int temp = valueB[i];
                    valueB[i] = valueB[j];
                    valueB[j] = temp;

                }
            }
        }

        // set price
        int sumValueA = 0, sumValueB = 0;

        for (int i = 0; i < numOfTopTank; i++) {
            sumValueA += valueA[i];
            sumValueB += valueB[i];
        }

        float unitPriceA = this.getTeamA().getMoney() / (float) (sumValueA);
        float unitPriceB = this.getTeamB().getMoney() / (float) (sumValueB);

        DecisionBet DA = new DecisionBet();
        DA.setPrice(Math.min(this.getTeamA().getMoney(), (int) (unitPriceA * evaluation.evaluate(this.getTanks()[this.getSetting().getCurrentBetTurn()], this.getPopulation().getChromosomes()[C1]))));
        DA.setBetTurn(this.getSetting().getCurrentBetTurn());
        this.getTeamA().addDecisionBet(DA);

        DecisionBet DB = new DecisionBet();
        DB.setPrice(Math.min(this.getTeamB().getMoney(), (int) (unitPriceB * evaluation.evaluate(this.getTanks()[this.getSetting().getCurrentBetTurn()], this.getPopulation().getChromosomes()[C2]))));
        DB.setBetTurn(this.getSetting().getCurrentBetTurn());
        this.getTeamB().addDecisionBet(DB);

    }

    void findBestDecisionPlace() {
        // team A
        int x = 0, y = 0; // x : 1-8; y : 1-4
        for (int i = 0; i < this.getTeamA().getNumOfTank(); i++) {
            x = Math.max(1, Game.COLUMN - this.getTeamA().getTanks()[i].getAttackRange());
            y = Math.min(Game.ROW / 2, Math.max(1, Game.ROW - this.getTeamA().getTanks()[i].getAttackRange() - 1));

            boolean isPlaced = false;

            for (int j = 0; j < 8 && !isPlaced; j++) {
                if (this.getTeamA().findTankByPosition(new Position(x, y)) == -1) {
                    this.getTeamA().getTanks()[i].setPosition(new Position(x, y));
                    isPlaced = true;
                }
                x++;
                if (x == Game.COLUMN + 1) {
                    x = 1;
                }
            }
            x = Math.max(1, Game.COLUMN - this.getTeamA().getTanks()[i].getAttackRange()); 
            while (!isPlaced) { // trong truong hop qua xui khong dat duoc tren truc X @@
                  x++;
                if (x == Game.COLUMN + 1) {
                    x = 1;
                }
                 // tra ve vi tri X ban dau
                for (int j = 0; j < 4 && !isPlaced; j++) {
                    if (this.getTeamA().findTankByPosition(new Position(x, y)) == -1) {
                        this.getTeamA().getTanks()[i].setPosition(new Position(x, y));
                        isPlaced = true;
                    }
                    y--;
                    if (y == (0)) {
                        y = Game.ROW/2;
                    }
                }

            } // DONE // gần như ko thể xảy ra trường hợp không thể đặt được ở cả trục X và Y. FACK !!!
            
            
        } // end of place tank A

        // place tanks's of Team B
        for (int i = 0; i < this.getTeamB().getNumOfTank(); i++) {
            x = Math.max(1, Game.COLUMN - this.getTeamB().getTanks()[i].getAttackRange());
            y = Math.max(Game.ROW / 2 + 1, (Game.ROW) - Math.max(1, Game.ROW - this.getTeamB().getTanks()[i].getAttackRange() - 1) + 1);

            boolean isPlaced = false;

            for (int j = 0; j < 8 && !isPlaced; j++) {
                if (this.getTeamB().findTankByPosition(new Position(x, y)) == -1) {
                    this.getTeamB().getTanks()[i].setPosition(new Position(x, y));
                    isPlaced = true;
                }
                x++;
                if (x == Game.COLUMN + 1) {
                    x = 1;
                }
            }
     x = Math.max(1, Game.COLUMN - this.getTeamB().getTanks()[i].getAttackRange());// tra ve vi tri X ban dau
           
            while (!isPlaced) { // trong truong hop qua xui khong dat duoc tren truc X @@
                 x++;
                if (x == Game.COLUMN + 1) {
                    x = 1;
                }
                for (int j = 0; j < 4 && !isPlaced; j++) {
                    if (this.getTeamB().findTankByPosition(new Position(x, y)) == -1) {
                        this.getTeamB().getTanks()[i].setPosition(new Position(x, y));
                        isPlaced = true;
                    }
                    y++;
                    if (y == (Game.ROW  +1)) {
                        y = Game.ROW/2+1;
                    }
                }

            } // DONE // gần như ko thể xảy ra trường hợp không thể đặt được ở cả trục X và Y. FACK !!!

        } // end of team B

    }
}
