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
    Chromosome bestChromosomeOfCurrentPopulation;
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

    public Chromosome getBestChromosomeOfCurrentPopulation() {
        return bestChromosomeOfCurrentPopulation;
    }

    public void setBestChromosomeOfCurrentPopulation(Chromosome bestChromosomeOfCurrentPopulation) {
        this.bestChromosomeOfCurrentPopulation = bestChromosomeOfCurrentPopulation;
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
       bestChromosomeOfCurrentPopulation = new Chromosome();
   } 
   
   // clone population
   void clone (Population P) {
       for (int i =0; i < numOfChromosome; i++)
           this.getChromosomes()[i].clone(P.getChromosomes()[i]);
   }
   
   // Reproduction
   void preproduction ( ) {
      
     int roulette []=  new int[100] ; // roulette
     int sumpoint = 0;
     
     // sum fitness value
     for (int i =0; i < numOfGoodChromosome; i ++)
         sumpoint+= chromosomes[i].getPoint();
     
     // insert to roulette
     int index = 0;
     
     for (int i =0; i < numOfGoodChromosome  ; i ++) {
         int len = (chromosomes[i].getPoint()*100)/ sumpoint;
          for (int j=0; j < len  ; j++)
             roulette[index+j] = i;
         index+=len;     
     }
     // insert the last
     for (int i =index; i < 100; i ++)roulette[i]= 0;
      
     // choose chromosome by spinning the roulette
     Population middlePopulation = new Population( numOfGoodChromosome );
     Random R = new Random ();
     for (int i =0; i < numOfGoodChromosome; i++) {
            index = R.nextInt(1000000000);
        index%=100;
        middlePopulation.getChromosomes()[i] = new Chromosome();
        middlePopulation.getChromosomes()[i].clone( chromosomes[roulette[index] ] );
       if (roulette[index] ==0) System.out.println("0");
   
      }
    
     for (int i =0; i < numOfGoodChromosome; i ++)
         this.chromosomes[i].clone(middlePopulation.getChromosomes()[i]);
     
     // add new chromosomes to the population
       addNewChromosomeToPopulation(numOfGoodChromosome);
       /*
        System.out.println ("TEMPORATION" );
       for (int i =0; i <this.getNumOfChromosome(); i ++) { 
              System.out.println ("C"+i +":  " );
            for (int j=0; j < Chromosome.numOfGen; j ++ )
          System.out.print (this.getChromosomes()[i].getGen()[j] +" " );
            System.out.println ("Point : " + this.getChromosomes()[i].getPoint()+ "  " );
            System.out.println ("Fitness Value : " + this.getChromosomes()[i].getFitnessValue()+ "\n" );
        }
               */
       
   }
    
  // sort by fitness Value 
   public void sortByPoint () {
       
       for (int i =0; i < numOfChromosome-1; i++)
            for (int j=i+1; j < numOfChromosome; j ++)
                   if ( chromosomes[i].getPoint() < chromosomes[j].getPoint() ) {
                   
                       chromosomes[i].swap ( chromosomes[j] );
                 
                   
                   } 
       
     
       
       if (chromosomes[0] .getPoint()  > bestChromosome.getPoint()){
       bestChromosome = new Chromosome (  );
       bestChromosome.clone(chromosomes[0]);
       bestGeneration = this.getGeneration();
       }
       
       bestChromosomeOfCurrentPopulation = new Chromosome();
       bestChromosomeOfCurrentPopulation.clone(chromosomes[0]);
   }
   
   // choose good Chromosome // Parent Selection
   public void chooseGoodChromosomes () {
   }
  
   // cross Over chromosome
   public void crossOver () {
       // lai ghep 50 con tot nhat
      boolean check[] = new boolean[numOfChromosome];
      
      for ( int i=0; i < numOfChromosome; i ++) check[i] = false;
      int count = 0;
      while ( count <numOfChromosome  ) {
          Random R = new Random();
          int c1, c2;
          do {
              c1 = R.nextInt(numOfChromosome);
              c2 = R.nextInt(numOfChromosome);
          }
          while ( check[c1] || check[c2]  || c1==c2);
          
          check[c1]= true; check[c2]= true;
         
            
            int index = R.nextInt( Chromosome.numOfGen-1 )+1;
            
            Chromosome temp = new Chromosome();
            temp.clone(this.getChromosomes()[c1]);
            
         for (int i=0; i  < index; i ++)
               this.getChromosomes()[c1].setGen( temp.getGen()[i], i);
         
         for (int i=0; i  < index; i ++)
               this.getChromosomes()[c2].setGen( this.getChromosomes()[c1].getGen()[i], i);
            
          
          count+=2;
      }
   
   }
   
   // mutaion
   public void mutation() {
  Random R = new Random();
       for (int i =0; i <numOfChromosome; i++)
           if ( R.nextInt(100) <  this.mutationProportion ) {chromosomes[i].mutation(Population.rangeOfValue); System.out.println("DETECTED MUTATION");}
       
  }
   
   public void addNewChromosomeToPopulation (int numOfGoodChromosome ) {
     Random R = new Random();
       
       for (int i = numOfGoodChromosome; i < this.numOfChromosome; i++)
         for (int j=0; j  < Chromosome.numOfGen; j ++)
              this.chromosomes[i].setGen( R.nextInt(Population.rangeOfValue)+1 , j);
       
       
      
   }
   
    
   public void evolution () {
       
       // selection good chromosome
       sortByPoint();   
       // select parent
       preproduction ( );

      // cross over
       crossOver(); 
       // mutation
       mutation ();
       //
       
       this.generation ++;
       
       // reset fitness value
       for (int i =0; i < numOfChromosome ; i ++)
           chromosomes[i].setPoint(0);
       
   }
   
   public void calculateFitnessValue () {
       int sumPoint = 0;
       for (int i =0;i <numOfChromosome; i++)
           sumPoint+=this.getChromosomes()[i].getPoint();
       
        for (int i =0;i <numOfChromosome; i++)
            this.getChromosomes()[i].setFitnessValue(  ((float)(this.getChromosomes()[i].getPoint()) /sumPoint));
   }
   
}
