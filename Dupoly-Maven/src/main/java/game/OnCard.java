package game;


import jdbc.game_util.GameDAO;

import java.sql.Connection;

public interface OnCard {


    // I think this function needs to intake a gameDAO or a connection or something.
    void cardAction(); // function that defines the action for when the card is drawn

}