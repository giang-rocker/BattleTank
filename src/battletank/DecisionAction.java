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
public class DecisionAction {
    String command;
    Position source;
    Position destination;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Position getSource() {
        return source;
    }

    public void setSource(Position source) {
        this.source = source;
    }

    public Position getDestination() {
        return destination;
    }

    public void setDestination(Position destination) {
        this.destination = destination;
    }

    public DecisionAction(String command, Position source, Position destination) {
        this.command = command;
        this.source= new Position( source);
        this.destination = new Position(destination);
    }
    
    public DecisionAction(DecisionAction decisionaction) {
        this.command = decisionaction.getCommand();
        this.source = new Position( decisionaction.getSource()  );
        this.destination = new Position(decisionaction.getDestination());
    }
    
    public DecisionAction() {
    }
    
    
    
    
}
