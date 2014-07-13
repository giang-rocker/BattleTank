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
    int numOfChromosome ; // num of individual in population
    int numOfGoodChromosome = (int) (numOfChromosome*0.8)  ; // num of individual in population
    Chromosome chromosomes[]; // population
    static int mutationProportion = 10 ; //percentage 
    Chromosome bestChromosome;
    int bestGeneration;
    int generation;

    public int getBestGeneration() {
        return bestGeneration;
    }

    public void setBestGeneration(int bestGeneration) {
        this.bestGeneration = bestGeneration;
    }
        
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
        this.numOfGoodChromosome =(int)( this.numOfChromosome * 0.8);
        this.generation = 0;
    } 
    
   public void generatePopulation ( ) {
       Random R = new Random();
       
       for (int i =0; i < this.numOfChromosome; i++){
           chromosomes[i] = new Chromosome();
         for (int j=0; j  < Chromosome.numOfGen; j ++)
              chromosomes[i].setGen( R.nextInt(this.rangeOfValue)+1 , j);
       }
      
       bestChromosome = new Chromosome();
   } 
   
   // clone population
   void clone (Population P) {
       for (int i =0; i < numOfChromosome; i++)
           this.getChromosomes()[i].clone(P.getChromosomes()[i]);
   }
   
   // Reproduction
   void preproduction (int numOfGoodChromosome) {
     int roulette []=  new int[1000] ; // roulette
     int sumFitnessValue = 0;
     
     // sum fitness value
     for (int i =0; i < numOfGoodChromosome; i ++)
         sumFitnessValue+= chromosomes[i].getFitnessValue();
     
     // insert to roulette
     int index = 0;
     for (int i =0; i < numOfGoodChromosome && index<1000; i ++) {
         int len = (chromosomes[i].getFitnessValue()*1000)/ sumFitnessValue;
         for (int j=0; j < len && (index+j)<1000; j++)
             roulette[index+j] = i;
         index+=len;     
     }
     // insert the last
     for (int i =index; i < 1000; i ++)roulette[i]= 0;
     
   
     // choose chromosome by spinning the roulette
     Population middlePopulation = new Population( numOfGoodChromosome );
     Random R = new Random ();
     for (int i =0; i < numOfGoodChromosome; i++) {
            index = R.nextInt(100000);
        index%=1000;
        middlePopulation.getChromosomes()[i] = new Chromosome();
        middlePopulation.getChromosomes()[i].clone( chromosomes[roulette[index] ] );
   
      }
    
     for (int i =0; i < numOfGoodChromosome; i ++)
         this.chromosomes[i].clone(middlePopulation.getChromosomes()[i]);
     
     // add new chromosomes to the population
       addNewChromosomeToPopulation(numOfGoodChromosome);
   }
    
  // sort by fitness Value 
   public void sortByFitnessValue () {
       
       for (int i =0; i < numOfChromosome; i++)
            for (int j=i+1; j < numOfChromosome; j ++)
                   if ( chromosomes[i].getFitnessValue() < chromosomes[j].getFitnessValue() ) {
                   
                       chromosomes[i].swap ( chromosomes[j] );
                 
                   
                   } 
       
     
       
       if (chromosomes[0] .getFitnessValue()  > bestChromosome.getFitnessValue()){
       bestChromosome = new Chromosome ( chromosomes[0] );
       bestGeneration = this.getGeneration();
       }
   }
   
   // choose good Chromosome // Parent Selection
   public void chooseGoodChromosomes () {
   }
  
   // cross Over chromosome
   public void crossOver () {
       // lai ghep 50 con tot nhat
      boolean check[] = new boolean[numOfGoodChromosome];
      
      for ( int i=0; i < numOfGoodChromosome; i ++) check[i] = false;
      int count = 0;
      while ( count <numOfGoodChromosome  ) {
          Random R = new Random();
          int c1, c2;
          do {
              c1 = R.nextInt(numOfGoodChromosome);
              c2 = R.nextInt(numOfGoodChromosome);
          }
          while ( check[c1] || check[c2]  || c1==c2);
          
          check[c1]= true; check[c2]= true;
          chromosomes[c1].crossOver( chromosomes[c2]);
          count+=2;
      }
   
   }
   
   // mutaion
   public void mutation() {
  Random R = new Random();
       for (int i =0; i <numOfGoodChromosome; i++)
           if ( R.nextInt(100) <  this.mutationProportion ) chromosomes[i].mutation(Population.rangeOfValue);
       
  }
   
   public void addNewChromosomeToPopulation (int numOfGoodChromosome ) {
     Random R = new Random();
       
       for (int i =this.numOfGoodChromosome; i < this.numOfChromosome; i++)
         for (int j=0; j  < Chromosome.numOfGen; j ++)
              chromosomes[i].setGen( R.nextInt(Population.rangeOfValue)+1 , j);
       
       
      
   }
   
    
   public void evolution () {
       
       // selection good chromosome
       sortByFitnessValue(); 
       // select parent
       preproduction (numOfChromosome);
      // cross over
       crossOver(); 
       // mutation
       mutation ();
       //
       
       this.generation ++;
       
       // reset fitness value
       for (int i =0; i < numOfChromosome ; i ++)
           chromosomes[i].setFitnessValue(0);
      
   }
}
