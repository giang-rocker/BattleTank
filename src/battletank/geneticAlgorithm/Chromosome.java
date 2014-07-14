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
    public static int numOfGen = 4;
    int gen[];
    int point;
    float fitnessValue ;

    public float getFitnessValue() {
        return fitnessValue;
    }

    public void setFitnessValue(float fitnessValue) {
        this.fitnessValue = fitnessValue;
    }

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

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
    
    public Chromosome(int[] gen) {
        this.gen = gen;
    }
    
      public Chromosome(Chromosome X) {
        this.gen = X.getGen();
        this.setPoint( X.getPoint() );
        this.setFitnessValue(X.getFitnessValue());
    }
    
        public Chromosome( ) {
        this.gen = new int[numOfGen];
        this.point = 0;
        this.fitnessValue=0;
    }
        
        public void clone ( Chromosome X ) {
     for (int i=0; i <numOfGen; i++)
            this.getGen()[i] = X.getGen()[i];
        this.setPoint( X.getPoint() );
         this.setFitnessValue(X.getFitnessValue());
    }
    
        public void swap ( Chromosome X ) {
            
            Chromosome temp = new Chromosome();
           temp.clone(X);
            X.clone(this);
            this.clone(temp);
            
            
        }
        
        // lai ghep 2 nhiem sac the
        public void crossOver ( Chromosome X ) {
          
        
        }
        
        public void mutation(int rangeOfValue){
              Random R = new Random();
            int index = R.nextInt( Chromosome.numOfGen);
            
            this.setGen( R.nextInt(rangeOfValue)+1 , index);
            
            
        }
    
}
