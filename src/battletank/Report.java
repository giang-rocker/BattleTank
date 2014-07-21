/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battletank;

import battletank.geneticAlgorithm.Chromosome;
import battletank.geneticAlgorithm.Population;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class Report {

    private int matchId; // match id 
    //
    private String current_path = "";
    // HOST REPORT FILE
    private String host_path = ""; // path to host folder
    private String report_path = ""; // path to REPORT folder
    private String file_tanks;  // name of file containt tanks's information
    private String file_bets;// name of file containt teams's bet information
    private String file_status;// name of file containt status map infomation
    private String file_setting;// name of file containt status map infomation
    private int UTF;

    // TEAM REPORT FILE
    private String teamA_part = "";
    private String teamB_part = "";
    private String file_teamA_decisionBet = "";
    private String file_teamB_decisionBet = "";
    private String file_teamA_decisionPlace = "";
    private String file_teamB_decisionPlace = "";
    private String file_teamA_decisionAction = "";
    private String file_teamB_decisionAction = "";
    private String file_teamA_status = "";
    private String file_teamB_status = "";
   
    // genetic algorthm report // report population
        private String generation_path = "";
        
        
    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }
// setup path
    private void init() {
        this.current_path = Paths.get("").toAbsolutePath().toString();
        this.host_path = this.current_path + "/src/battletank/" + "Report/host/";
        this.report_path = this.current_path + "/src/battletank/" + "Report";
        
           // genetic algorithm report
        generation_path = this.report_path+"/Generation";
    }

    public Report(int matchId) {
        this.matchId = matchId;
        init();
        this.file_tanks = host_path + Integer.toString(this.matchId) + "_tanks.txt";
        this.file_bets = host_path + Integer.toString(this.matchId) + "_bets.txt";
        this.file_status = host_path + Integer.toString(this.matchId) + "_status.txt";
        this.file_setting = host_path + Integer.toString(this.matchId) + "_settings.txt";
        this.teamA_part = this.report_path + "/teamA";
        this.teamB_part = this.report_path + "/teamB";

        // teams's part
         // decision files of 2 teams
         file_teamA_decisionBet = this.teamA_part + "/" + matchId + "_decision_bet_A.txt";
         file_teamB_decisionBet = this.teamB_part + "/" + matchId + "_decision_bet_B.txt";

        file_teamA_decisionPlace = this.teamA_part + "/" + matchId + "_decision_place_A.txt";
        file_teamB_decisionPlace = this.teamB_part + "/" + matchId + "_decision_place_B.txt";
        file_teamA_decisionAction = this.teamA_part + "/" + matchId + "_decision_action_A.txt";
        file_teamB_decisionAction = this.teamB_part + "/" + matchId + "_decision_action_B.txt";
        file_teamA_status = this.teamA_part + "/" + matchId + "_status_A.txt";
        file_teamB_status = this.teamB_part + "/" + matchId + "_status_B.txt";
        
     

    }

    // HOST PART
    // Read Game setting
    public void readSetting(Game game) throws FileNotFoundException {
        FileInputStream is = new FileInputStream(this.file_setting);
        Scanner input = new Scanner(is, "UTF-8");// set utf-8
        // num Match ID
        game.getSetting().setMatchID(Integer.parseInt(input.nextLine()));
        // Name of Team A
        game.getSetting().setNameTeamA(input.nextLine());
        // Name of Team B
        game.getSetting().setNameTeamB(input.nextLine());
        // Name of TANK
        game.getSetting().setNumOfTank(Integer.parseInt(input.nextLine()));
        // Name of TANK
        game.getSetting().setDefaultMoney(Integer.parseInt(input.nextLine()));
        // set Num Of Turn
        game.getSetting().setNumOfTurn(Integer.parseInt(input.nextLine()));

    }

    // read tanks's info
    public void readReportTank(Game game) throws FileNotFoundException {
        FileInputStream is = new FileInputStream(this.file_tanks);
        Scanner input = new Scanner(is, "UTF-8");// set utf-8

        // read num Of Tank
        String line = input.nextLine();
        game.getSetting().setNumOfTank(Integer.parseInt(line));
        int numOfTank = game.getSetting().getNumOfTank();

        Tank tanks[] = new Tank[numOfTank];
        // read tanks's info
        String tempArrayString[] = new String[5];
        int armor, damage, attackRange; //  
        for (int i = 0; i < numOfTank; i++) {
            line = input.nextLine();

            tempArrayString = line.split(" ");
            // set tank value
            armor = Integer.parseInt(tempArrayString[0]); // first value is armor point
            damage = Integer.parseInt(tempArrayString[1]);// second value is damage point
            attackRange = Integer.parseInt(tempArrayString[2]);// third value is attackRange point

            tanks[i] = new Tank(damage, armor, attackRange);
        }

        game.setTanks(tanks);
    }

    public void readTeamBetDecision(Game game) throws FileNotFoundException, IOException {
        FileInputStream is = null;
        Scanner input = null;
        String getLine = "";

        // decision files of 2 teams
        
       
        // desision of each team
        DecisionBet teamADecisionBet = new DecisionBet();
        DecisionBet teamBDecisionBet = new DecisionBet();

        // get decision bet of each team
        // TEAM A
        is = new FileInputStream(file_teamA_decisionBet);
        input = new Scanner(is, "UTF-8");// set utf-8
        teamADecisionBet.setBetTurn(game.getSetting().getCurrentBetTurn());
        // ignore currentbetturn
        getLine = input.nextLine(); 
        getLine = input.nextLine();
        teamADecisionBet.setPrice(Integer.parseInt(getLine));
        game.getTeamA().addDecisionBet(teamADecisionBet);
        is.close();
        // TEAM B
        is = new FileInputStream(file_teamB_decisionBet);
        input = new Scanner(is, "UTF-8");// set utf-8
        teamBDecisionBet.setBetTurn(game.getSetting().getCurrentBetTurn());
         // ignore currentbetturn
        getLine = input.nextLine(); 
        getLine = input.nextLine();
        teamBDecisionBet.setPrice(Integer.parseInt(getLine));
        game.getTeamB().addDecisionBet(teamBDecisionBet);
        is.close();

    }

    // Read Decide of 2 teams . Update bet file for each team                     
    public void updateTeamReportBet(Game game) throws UnsupportedEncodingException, IOException {

        // input variable
        FileInputStream is = null;
        Scanner input = null;
        String getLine = "";

        // writer file
        Writer writerTeamA = null;
        Writer writerTeamB = null;
        // Update bet file for team A
        String TeamAReportBet = this.teamA_part + "/" + game.getSetting().getMatchID() + "_bets_A.txt";
        String TeamBReportBet = this.teamB_part + "/" + game.getSetting().getMatchID() + "_bets_B.txt";

        File f = new File(TeamAReportBet);
        if (f.exists() && !f.isDirectory()) {

            writerTeamA = new BufferedWriter(new FileWriter(new File(TeamAReportBet)));
            writerTeamB = new BufferedWriter(new FileWriter(new File(TeamBReportBet)));
            // write bet report team A
            // insert team Name of each team
            writerTeamA.write(game.getSetting().getNameTeamA() + "\n");
            writerTeamB.write(game.getSetting().getNameTeamB() + "\n");

            // insert Defalt money
            writerTeamA.write(game.getSetting().getDefaultMoney() + "\n");
            writerTeamB.write(game.getSetting().getDefaultMoney() + "\n");

            // insert num Of Tank
            writerTeamA.write(game.getSetting().getNumOfTank() + "\n");
            writerTeamB.write(game.getSetting().getNumOfTank() + "\n");
            // insert tanks information
            int numOfTank = game.getSetting().getNumOfTank();
            for (int i = 0; i < numOfTank; i++) {
                writerTeamA.write(game.getTanks()[i].getAmor() + " " + game.getTanks()[i].getDamange() + " " + game.getTanks()[i].getAttackRange() + "\n");
                writerTeamB.write(game.getTanks()[i].getAmor() + " " + game.getTanks()[i].getDamange() + " " + game.getTanks()[i].getAttackRange() + "\n");
            }

            // insert current bet : currentBetTurn + 1;
            writerTeamA.write(game.getSetting().getCurrentBetTurn() + 1 + "\n");
            writerTeamB.write(game.getSetting().getCurrentBetTurn() + 1 + "\n");

            // insert team Name
            writerTeamA.write(game.getSetting().getNameTeamA() + "\n");
            writerTeamB.write(game.getSetting().getNameTeamA() + "\n");
            // write history report of team A
            for (int i = 0; i < game.getSetting().getCurrentBetTurn(); i++) {
                writerTeamA.write(game.getTeamA().getDecisiontBet()[i].getPrice() + "\n");
                writerTeamB.write(game.getTeamA().getDecisiontBet()[i].getPrice() + "\n");

            }
            // insert team Name
            writerTeamA.write(game.getSetting().getNameTeamB() + "\n");
            writerTeamB.write(game.getSetting().getNameTeamB() + "\n");
            // write history report of team A
            for (int i = 0; i < game.getSetting().getCurrentBetTurn(); i++) {
                writerTeamA.write(game.getTeamB().getDecisiontBet()[i].getPrice() + "\n");
                writerTeamB.write(game.getTeamB().getDecisiontBet()[i].getPrice() + "\n");

            }
            writerTeamA.close();
            writerTeamB.close();
        } else { //  first time . Create file

            writerTeamA = new BufferedWriter(new FileWriter(new File(TeamAReportBet)));
            writerTeamB = new BufferedWriter(new FileWriter(new File(TeamBReportBet)));
            // write bet report team A
            // insert team Name of each team
            writerTeamA.write(game.getSetting().getNameTeamA() + "\n");
            writerTeamB.write(game.getSetting().getNameTeamB() + "\n");

            // insert Defalt money
            writerTeamA.write(game.getSetting().getDefaultMoney() + "\n");
            writerTeamB.write(game.getSetting().getDefaultMoney() + "\n");

            // insert num Of Tank
            writerTeamA.write(game.getSetting().getNumOfTank() + "\n");
            writerTeamB.write(game.getSetting().getNumOfTank() + "\n");
            // insert tanks information
            int numOfTank = game.getSetting().getNumOfTank();
            for (int i = 0; i < numOfTank; i++) {
                writerTeamA.write(game.getTanks()[i].getAmor() + " " + game.getTanks()[i].getDamange() + " " + game.getTanks()[i].getAttackRange() + "\n");
                writerTeamB.write(game.getTanks()[i].getAmor() + " " + game.getTanks()[i].getDamange() + " " + game.getTanks()[i].getAttackRange() + "\n");
            }

            // insert current bet : 1;
            writerTeamA.write(1 + "\n");
            writerTeamB.write(1 + "\n");
            writerTeamA.close();
            writerTeamB.close();

        }

    }

    // read team's placing tank decision
    public void readTeamPlaceDecision(Game game) {
        FileInputStream is = null;
        Scanner input = null;
        String getLine = "";

        try {
            is = new FileInputStream(new File(this.file_teamA_decisionPlace));
            input = new Scanner(is, "UTF-8");// set utf-8
            getLine = input.nextLine();

            getLine = input.nextLine(); // lay so luong xe tang
            int numOfTank = Integer.parseInt(getLine);
            String tempArrayString[] = new String[2];
            int x, y;
            for (int i = 0; i < numOfTank; i++) {
                getLine = input.nextLine();

                tempArrayString = getLine.split(" ");
                // set tank value
                x = Integer.parseInt(tempArrayString[0]); // first value is armor point
                y = Integer.parseInt(tempArrayString[1]);// second value is damage point
                game.getTeamA().addDecisionPlace(new DecisionPlace(i, new Position(x, y)));
            }

        } catch (FileNotFoundException ex) {
            // tim khogn thay file doi A = > thua

            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }

        // read file decision place of team B
        try {
            is = new FileInputStream(new File(this.file_teamB_decisionPlace));
            input = new Scanner(is, "UTF-8");// set utf-8
            getLine = input.nextLine();
            getLine = input.nextLine(); // lay so luong xe tang
            int numOfTank = Integer.parseInt(getLine);

            String tempArrayString[] = new String[2];
            int x, y;
            for (int i = 0; i < numOfTank; i++) {
                getLine = input.nextLine();

                tempArrayString = getLine.split(" ");
                // set tank value
                x = Integer.parseInt(tempArrayString[0]); // first value is X
                y = Integer.parseInt(tempArrayString[1]);// second value is  Y
                game.getTeamB().addDecisionPlace(new DecisionPlace(i, new Position(x, y)));
            }

        } catch (FileNotFoundException ex) {
            // tim khogn thay file doi A = > thua
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // read status map's info
    
    // load data from report file
    // team part    
    public void readReportStatus(Game game) {
    }

    // generate file status for each team
    public void generateReportStatus(Game game) throws IOException {
      
        
    }

    
    // update Status Report of each team
    public void updateReportStatus(Game game) throws IOException {
        
         // input variable
        FileInputStream is = null;
        Scanner input = null;
        String getLine = "";

        // writer file
        Writer writerTeamA = null;
        Writer writerTeamB = null;
        // Update bet file for team A
      
        File f = new File(this.file_teamA_status);
        if (f.exists() && !f.isDirectory()) {
            // team A
            writerTeamA = new BufferedWriter(new FileWriter(new File(file_teamA_status)));
            // write bet report team A
            // insert team Name of each team
            writerTeamA.write(game.getSetting().getNameTeamA() + "\n");
          // insert point
            writerTeamA.write(game.getTeamA().getPoint()+ "\n");
           // insert numOfTank
            int numOfTank = game.getTeamA().getNumOfTank();
            writerTeamA.write(numOfTank + "\n");
          // write tank info
            for (int i =0; i < numOfTank; i ++) {
                writerTeamA.write (  game.getTeamA().getTanks()[i].getAmor() + " " + game.getTeamA().getTanks()[i].getDamange()+" " +game.getTeamA().getTanks()[i].getAttackRange()+" ");
                 writerTeamA.write (  game.getTeamA().getTanks()[i].getPosition().getX()+" "+ game.getTeamA().getTanks()[i].getPosition().getY()+"\n" );
            }
                
            // team B
            
            // insert team Name team V
         //   writerTeamA.write(game.getSetting().getNameTeamB() + "\n");
          // insert point
            writerTeamA.write(game.getTeamB().getPoint()+ "\n");
           // insert numOfTank
              numOfTank = game.getTeamB(). getNumOfTank();
            writerTeamA.write(numOfTank + "\n");
          // write tank info
            for (int i =0; i < numOfTank; i ++) {
                writerTeamA.write (  game.getTeamB().getTanks()[i].getAmor() + " " + game.getTeamB().getTanks()[i].getDamange()+" "+ game.getTeamB().getTanks()[i].getAttackRange()+" ");
                 writerTeamA.write (  game.getTeamB().getTanks()[i].getPosition().getX()+" "+ game.getTeamB().getTanks()[i].getPosition().getY()+"\n" );
            }
            
             writerTeamA.close();
         
        }
        // TEAM B
         f = new File(this.file_teamB_status);
        if (f.exists() && !f.isDirectory()) {
            // team B
            writerTeamB = new BufferedWriter(new FileWriter(new File(file_teamB_status)));
            // write bet report team A
            // insert team Name of each team
            writerTeamB.write(game.getSetting().getNameTeamB() + "\n");
          // insert point
            writerTeamB.write(game.getTeamB().getPoint()+ "\n");
           // insert numOfTank
            int numOfTank = game.getTeamB().getNumOfTank();
            writerTeamB.write(numOfTank + "\n");
          // write tank info
            for (int i =0; i < numOfTank; i ++) {
                writerTeamB.write (  game.getTeamB().getTanks()[i].getAmor() + " " + game.getTeamB().getTanks()[i].getDamange()+" "+ game.getTeamB().getTanks()[i].getAttackRange()+" ");
                 writerTeamB.write (  game.getTeamB().getTanks()[i].getPosition().getX()+" "+ game.getTeamB().getTanks()[i].getPosition().getY()+"\n" );
            }
                
            // team A
            
            // insert team Name team A
        //    writerTeamB.write(game.getSetting().getNameTeamA() + "\n");
          // insert point
            writerTeamB.write(game.getTeamA().getPoint()+ "\n");
           // insert numOfTank
              numOfTank = game.getTeamA().getNumOfTank();
            writerTeamB.write(numOfTank + "\n");
          // write tank info
            for (int i =0; i < numOfTank; i ++) {
                writerTeamB.write (  game.getTeamA().getTanks()[i].getAmor() + " " + game.getTeamA().getTanks()[i].getDamange()+" "+  game.getTeamA().getTanks()[i].getAttackRange()+" ");
                 writerTeamB.write (  game.getTeamA().getTanks()[i].getPosition().getX()+" "+ game.getTeamA().getTanks()[i].getPosition().getY()+"\n" );
            }
            
             writerTeamB.close();
           
        // file of Team B
        
        }
        
    }
    // read teams's action decision
    public void readTeamActionDecision(Game game, String Team) {

        FileInputStream is = null;
        Scanner input = null;
        String getLine = null;

        String command;
        int sx, sy, dx, dy; // command, source coordinates, desination coordinates
        String tempArrayString[] = new String[5];
        if (Team == "A") {
            try {
                is = new FileInputStream(new File(this.file_teamA_decisionAction));
                input = new Scanner(is, "utf-8");
                getLine = input.nextLine(); // team name
                // get command
                getLine = input.nextLine();
                command = getLine;
                // get source coordinates, desination coordinates
                getLine = input.nextLine();
                tempArrayString = getLine.split(" ");
                //  source coordinates,
                sx = Integer.parseInt(tempArrayString[0]);
                sy = Integer.parseInt(tempArrayString[1]);
                //desination coordinates
                dx = Integer.parseInt(tempArrayString[2]);
                dy = Integer.parseInt(tempArrayString[3]);

                game.getTeamA().addDecisionAction(new DecisionAction(command, new Position(sx, sy), new Position(dx, dy)));

            } catch (FileNotFoundException ex) {
                // khong tim thay file

                Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {

            try {
                is = new FileInputStream(new File(this.file_teamB_decisionAction));
                input = new Scanner(is, "utf-8");
                getLine = input.nextLine(); // team name
                // get command
                getLine = input.nextLine();
                command = getLine;
                // get source coordinates, desination coordinates
                getLine = input.nextLine();
                tempArrayString = getLine.split(" ");
                //  source coordinates,
                sx = Integer.parseInt(tempArrayString[0]);
                sy = Integer.parseInt(tempArrayString[1]);
                //desination coordinates
                dx = Integer.parseInt(tempArrayString[2]);
                dy = Integer.parseInt(tempArrayString[3]);

                game.getTeamB().addDecisionAction(new DecisionAction(command, new Position(sx, sy), new Position(dx, dy)));

            } catch (FileNotFoundException ex) {
                // khong tim thay file

                Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
  
    
    // report generation statistic
    
    public void reportGeneration (Population P) throws IOException {
     FileInputStream is = null;
        Scanner input = null;
        String getLine = "";
        
        // writer file
        Writer writerPopulation = null;
          String file_generation = "";
         if (P.getGeneration()>9)
       file_generation= this.generation_path + "/" + P.getGeneration()+ "th_Generation.txt";
         else
         file_generation = this.generation_path + "/0" + P.getGeneration()+ "th_Generation.txt";
         
        File f = new File(file_generation);
        writerPopulation = new BufferedWriter(new FileWriter(new File(file_generation)));
        // header
        if (!f.exists()) f.createNewFile();
       
        writerPopulation.write(  P.getGeneration() +"th GENERATION STATISTIC \n") ;
        // write gen
        
        for (int i =0; i <P.getNumOfChromosome(); i ++) { 
              writerPopulation.write ("C"+i +":  " );
            for (int j=0; j < Chromosome.numOfGen; j ++ )
          writerPopulation.write (P.getChromosomes()[i].getGen()[j] +" " );
               writerPopulation.write ("Point : " + P.getChromosomes()[i].getPoint()+ "  " );
            writerPopulation.write ("Fitness Value : " + P.getChromosomes()[i].getFitnessValue()+ "\n" );
        }
          
        writerPopulation.write("\n BEST CHROMOSOME \n") ;      
          for (int j=0; j < Chromosome.numOfGen; j ++ )
          writerPopulation.write (P.getBestChromosome().getGen()[j] +" " );   
         writerPopulation.write ("  point : " + P.getBestChromosome().getPoint() + "\n  + FitnessValue : " + P.getBestChromosome().getFitnessValue()+ "\n" );
        
        writerPopulation.write("\n BEST CHROMOSOME OF "+   P.getGeneration() +"th GENERATION  \n") ;      
          for (int j=0; j < Chromosome.numOfGen; j ++ )
          writerPopulation.write (P.getBestChromosomeOfCurrentPopulation().getGen()[j] +" " );   
         writerPopulation.write ("  point : " + P.getBestChromosomeOfCurrentPopulation().getPoint() + "\n  + FitnessValue : " + P.getBestChromosomeOfCurrentPopulation().getFitnessValue()+ "\n" );
         
         writerPopulation.close();
    }
 
    public void writeTeamDecisionBet (Game game) {
    
        Writer writerTeamA = null;
        File f = new File(file_teamA_decisionBet);
       

            try {
                writerTeamA = new BufferedWriter(new FileWriter(new File(file_teamA_decisionBet)));

                writerTeamA.write(game.getSetting().getCurrentBetTurn() + "\n");
                writerTeamA.write(game.getTeamA().getDecisiontBet()[game.getSetting().getCurrentBetTurn()].getPrice() + "\n");
                writerTeamA.close();

            } catch (IOException ex) {
                 System.out.println("SSS");
                Logger.getLogger(BattleScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
        
       
         Writer writerTeamB = null;
          f = new File(file_teamB_decisionBet);
         

            try {
                writerTeamB = new BufferedWriter(new FileWriter(new File(file_teamB_decisionBet)));

                writerTeamB.write(game.getSetting().getCurrentBetTurn() + "\n");
                writerTeamB.write(game.getTeamB().getDecisiontBet()[game.getSetting().getCurrentBetTurn()].getPrice() + "\n");
                writerTeamB.close();

            } catch (IOException ex) {
                System.out.println("SSS");
                Logger.getLogger(BattleScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    
    }
    
}
