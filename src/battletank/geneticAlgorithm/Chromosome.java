/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battletank.geneticAlgorithm;

import java.util.Random;

/**
 *
 * @author Administrator
 */
public class Chromosome {
    public static int numOfGen = 3;
    int gen[];
    int fitnessValue;

    public int[] getGen() {
        return gen;
    }

    public int getGen(int index) {
        return gen[index];
    }
    
    public void setGen(int[] gen) {
        this.gen = gen;
    }
    
    public void setGen(int value, int index) {
        this.gen[index] = value;
    }

    public int getFitnessValue() {
        return fitnessValue;
    }

    public void setFitnessValue(int fitnessValue) {
        this.fitnessValue = fitnessValue;
    }
    
    public Chromosome(int[] gen) {
        this.gen = gen;
    }
    
      public Chromosome(Chromosome X) {
        this.gen = X.getGen();
        this.setFitnessValue( X.getFitnessValue() );
    }
    
        public Chromosome( ) {
        this.gen = new int[numOfGen];
    }
        
        public void clone ( Chromosome X ) {
        this.gen = X.getGen();
        this.setFitnessValue( X.getFitnessValue() );
    }
    
        public void swap ( Chromosome X ) {
            
            Chromosome temp = new Chromosome();
           temp.clone(X);
            X.clone(this);
            this.clone(temp);
            
            
        }
        
        // lai ghep 2 nhiem sac the
        public void crossOver ( Chromosome X ) {
            Random R = new Random();
            int index = R.nextInt( Chromosome.numOfGen -1);
            
            Chromosome temp = new Chromosome();
            temp.clone(X);
            
         for (int i=0; i  < index; i ++)
               this.setGen( temp.getGen()[i], i);
         
         for (int i=index; i  < Chromosome.numOfGen; i ++)
               X.setGen( temp.getGen()[i], i);
            
        
        }
        
        public void mutation(int rangeOfValue){
              Random R = new Random();
            int index = R.nextInt( Chromosome.numOfGen)-1;
            
            this.setGen( R.nextInt(rangeOfValue) , index);
            
            
        }
    
}
