/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battletank;

/**
 *
 * @author Administrator
 */
public class Team {
    
    private String teamName;
    int money;
    Tank [] tanks; // Information of Tanks of player
    Tank[] enemyTanks ; //Information of Enemy's Tanks
     ReportBet[] reportBet; // information of bets of Player
    ReportBet[] enemyReportBet; // information of Enemy's bets
    ReportPlace[] reportPlaces;  // information of Enemy's
    ReportPlace[] enemyReportPlaces; 
    ReportStatus [] reportStatus; // status of map

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String Name) {
        this.teamName = Name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Tank[] getTanks() {
        return tanks;
    }

    public void setTanks(Tank[] tanks) {
        this.tanks = tanks;
    }

    public ReportBet[] getReportBet() {
        return reportBet;
    }

    public void setReportBet(ReportBet[] reportBet) {
        this.reportBet = reportBet;
    }

    public ReportPlace[] getReportPlaces() {
        return reportPlaces;
    }

    public void setReportPlaces(ReportPlace[] reportPlaces) {
        this.reportPlaces = reportPlaces;
    }

    public ReportStatus[] getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(ReportStatus[] reportStatus) {
        this.reportStatus = reportStatus;
    }

    public Team(int money,String name) {
        this.teamName = name;
        this.money = money;
    }
    
     
    public void readReportBet() {}
    public void decideBet() {}
    public void decidePlaceTanks() {}
    public void readReportStatus(){}
    public void decideAction (){}
    
    
    
}
