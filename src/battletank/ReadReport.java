/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battletank;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 *
 * @author Administrator
 */
public class ReadReport {

    private int matchId; // match id 
    //
    private String current_path= "";
    // HOST REPORT FILE
    private String host_path = ""; // path to host folder
    private String file_tanks;  // name of file containt tanks's information
    private String file_bets;// name of file containt teams's bet information
    private String file_status;// name of file containt status map infomation
    private int UTF;

    // TEAM REPORT FILE
    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    private void init() {
        this.current_path = Paths.get("").toAbsolutePath().toString();
        this.host_path =this.current_path+"/src/battletank/"+ "Report/host/";

    }

    public ReadReport(int matchId) {
        this.matchId = matchId;
        init();
        this.file_tanks = host_path + Integer.toString(this.matchId) + "_tanks.txt";
        this.file_bets = host_path + Integer.toString(this.matchId) + "_bets.txt";
        this.file_status = host_path + Integer.toString(this.matchId) + "_status.txt";
    }

    // HOST PART
    // read tanks's info
    public void readReportTank(Tank[] tanks, int numOfTank) throws FileNotFoundException {
        FileInputStream is = new FileInputStream(this.file_tanks);
        Scanner input=new Scanner(is,"UTF-8");// set utf-8
       
        // read num Of Tank
        String line = input.nextLine();
        numOfTank  = Integer.parseInt(line);
        tanks = new Tank[numOfTank];
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

    // read teams's action decision
    public void readTeamActionDecision() {
    }

    // update Status Report of host
    public void updateReportStatus() {
    }

}
