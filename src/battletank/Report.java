/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battletank;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    private String current_path= "";
    // HOST REPORT FILE
    private String host_path = ""; // path to host folder
     private String report_path = ""; // path to REPORT folder
    private String file_tanks;  // name of file containt tanks's information
    private String file_bets;// name of file containt teams's bet information
    private String file_status;// name of file containt status map infomation
    private int UTF;

    // TEAM REPORT FILE
    private String teamA_part = "";
    private String teamB_part = "";
    
    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    private void init() {
        this.current_path = Paths.get("").toAbsolutePath().toString();
        this.host_path =this.current_path+"/src/battletank/"+ "Report/host/";
        this.report_path = this.current_path+"/src/battletank/"+ "Report";
       }

    public Report(int matchId) {
        this.matchId = matchId;
        init();
        this.file_tanks = host_path + Integer.toString(this.matchId) + "_tanks.txt";
        this.file_bets = host_path + Integer.toString(this.matchId) + "_bets.txt";
        this.file_status = host_path + Integer.toString(this.matchId) + "_status.txt";
        
        this.teamA_part = this.report_path + "/teamA";
        
        this.teamB_part = this.report_path + "/teamB";
        
    }

    // HOST PART
    
    
    // Read Game setting
    
    public void readSetting ( Setting setting) {
    
    }
    
    // read tanks's info
    public void readReportTank(Game game) throws FileNotFoundException {
        FileInputStream is = new FileInputStream(this.file_tanks);
        Scanner input=new Scanner(is,"UTF-8");// set utf-8
       
        // read num Of Tank
        String line = input.nextLine();
        game.getSetting().setNumOfTank(Integer.parseInt(line));
        int numOfTank = game.getSetting().getNumOfTank();
        
        Tank tanks[] = new Tank[numOfTank];
        // read tanks's info
        String tempArrayString[] = new String[5];
        int armor,damage,attackRange ; //  
        for (int i =0; i < numOfTank; i ++) {
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

    // generate file bet for each team
    public void updateTeamReportBet (Game game, Team teamA, Team teamB ) throws UnsupportedEncodingException, IOException {
         Writer writer = null;
        // generate file for team A
       String TeamAReportBet = this.teamA_part+ "/" + game.getSetting().getMatchID()+ "_bets_A.txt";
       
        try {
            FileInputStream is = new FileInputStream(TeamAReportBet);
            Scanner input=new Scanner(is,"UTF-8");// set utf-8
            
            // readfile
        } catch (FileNotFoundException ex) {
        
             try {
                 //  first time . Create file
                 writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(TeamAReportBet), "utf-8"));
                 // insert team Name
                 writer.write(game.getSetting().getNameTeamA());
                 // insert Defalt money
                   writer.write(game.getSetting().getDefaultMoney());
                 // insert num Of Tank
                  writer.write(game.getSetting().getNumOfTank());
                 // insert tanks information
                 int numOfTank = game.getSetting().getNumOfTank();
                 for (int i =0; i < numOfTank; i ++)
                      writer.write( game.getTanks()[i].getAmor() + " " + game.getTanks()[i].getDamange() + " " + game.getTanks()[i].getAttackRange());
                  
                 // insert current bet : 1
                 writer.write("1");
             } catch (FileNotFoundException ex1) {
                 Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex1);
             }
          
            
            
        }
       
        // generate file for team B
        
    }
    
    // read team's bet decision
    public void readTeamBetDecision() {
    }

    // update bet Report of host
    public void updateReportBet() {
    }

    // read team's placing tank decision
    public void readTeamPlaceDecision() {
    }

    // read status map's info
    public void readReportStatus() {
    }
    
    // generate file status for each team
    public void updateTeamReportStatus () {}
    
    // read teams's action decision
    public void readTeamActionDecision() {
    }

    // update Status Report of host
    public void updateReportStatus() {
    }

}
