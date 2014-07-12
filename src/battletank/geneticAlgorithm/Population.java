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
public class Population {
    static int rangeOfValue = 100;
    int numOfChromosome = 100; // num of individual in population
    int numOfGoodChromosome = (int) (numOfChromosome*0.8)  ; // num of individual in population
    Chromosome chromosomes[]; // population
    static int mutationProportion = 10 ; //percentage 
    Chromosome bestChromosome;
    int generation;
        
    public int getNumOfChromosome() {
        return this.numOfChromosome;
    }

    public int getNumOfGoodChromosome() {
        return numOfGoodChromosome;
    }

    public void setNumOfGoodChromosome(int numOfGoodChromosome) {
        this.numOfGoodChromosome = numOfGoodChromosome;
    }

    public void setNumOfChromosome(int numOfChromosome) {
        this.numOfChromosome = numOfChromosome;
    }

    public Chromosome[] getChromosomes() {
        return chromosomes;
    }

    public void setChromosomes(Chromosome[] chromosomes) {
        this.chromosomes = chromosomes;
    }

    public static int getMutationProportion() {
        return mutationProportion;
    }

    public static void setMutationProportion(int mutationProportion) {
        Population.mutationProportion = mutationProportion;
    }

    public Population(Chromosome[] chromosomes) {
        this.chromosomes = chromosomes;
    }

    public Chromosome getBestChromosome() {
        return bestChromosome;
    }

    public void setBestChromosome(Chromosome bestChromosome) {
        this.bestChromosome = bestChromosome;
    }

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public Population ( int numOfChromosome ) {
        this.numOfChromosome = numOfChromosome;
        this.chromosomes = new Chromosome[numOfChromosome];
        this.generation = 0;
    } 
    
   public void generatePopulation ( ) {
       Random R = new Random();
       
       for (int i =0; i < this.numOfChromosome; i++){
           chromosomes[i] = new Chromosome();
         for (int j=0; j  < Chromosome.numOfGen; j ++)
              chromosomes[i].setGen( R.nextInt(this.rangeOfValue)+1 , j);
       }
      
       
   } 
    
  // sort by fitness Value 
   public void sortByFitnessValue () {
       
       for (int i =0; i < numOfChromosome; i++)
            for (int j=i+1; j < numOfChromosome; j ++)
                   if ( chromosomes[i].getFitnessValue() < chromosomes[j].getFitnessValue() ) {
                   
                       chromosomes[i].swap ( chromosomes[j] );
                   
                   
                   } 
       
       bestChromosome = new Chromosome ( chromosomes[0] );
   }
   
   // choose good Chromosome // Parent Selection
   public void chooseGoodChromosomes () {
   }
  
   // cross Over chromosome
   public void crossOver () {
       // lai ghep 50 con tot nhat
      boolean check[] = new boolean[numOfGoodChromosome];
      
      for ( int i=0; i < numOfChromosome; i ++) check[i] = false;
      int count = 0;
      while ( count <numOfChromosome  ) {
          Random R = new Random();
          int c1, c2;
          do {
              c1 = R.nextInt(numOfGoodChromosome);
              c2 = R.nextInt(numOfGoodChromosome);
          }
          while ( check[c1] || check[c2] );
          
          chromosomes[c1].crossOver( chromosomes[c2]);
      }
   
   }
   
   // mutaion
   public void mutation() {
  Random R = new Random();
       for (int i =0; i <numOfGoodChromosome; i++)
           if ( R.nextInt(101) <  this.mutationProportion ) chromosomes[i].mutation(Population.rangeOfValue);
       
  }
   
   public void addNewChromosomeToPopulation ( ) {
     Random R = new Random();
       
       for (int i =this.numOfGoodChromosome; i < this.numOfChromosome; i++)
         for (int j=0; j  < Chromosome.numOfGen; j ++)
              chromosomes[i].setGen( R.nextInt(Population.rangeOfValue)+1 , j);
   
   }
    
   public void evolution () {
       // selection parent
       sortByFitnessValue();
      // cross over
       crossOver();
       // mutation
       mutation ();
       //
       addNewChromosomeToPopulation();
       
       this.generation ++;
   }
}
