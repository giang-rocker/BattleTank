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
public class DecisionPlace {
    int placeTurn;
    Position position;

    public int getPlaceTurn() {
        return placeTurn;
    }

    public void setPlaceTurn(int placeTurn) {
        this.placeTurn = placeTurn;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public DecisionPlace(int placeTurn, Position position) {
        this.placeTurn = placeTurn;
        this.position = position;
    }

    public DecisionPlace( DecisionPlace d ) {
        this.setPlaceTurn( d.getPlaceTurn() );
        this.setPosition( d.getPosition() );
    }
   
}
