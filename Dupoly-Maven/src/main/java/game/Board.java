package game;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdbc.DatabaseConnectionManager;
import jdbc.game_util.GameDAO;
import jdbc.game_util.GameUtil;
import jdbc.player_util.PlayerDAO;
import jdbc.player_util.PlayerUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.abs;

@SpringBootApplication
@RestController
@CrossOrigin
public class Board {
    // insert stuff here



    // 59 spaces
    public Space[] gameBoard;
    public Map<Integer,String> lookup;

    public Board()
    {
        // Intiliaze the probability and its weights!
        // Hardcoded for now but maybe we eventualy put these in a yaml file
        // the format is .add(probability percentage, "card name")
        // call specialPB.next() to draw a card. This gets passed in to the SkullCard.onUSe function AHHHH THIS MIGHT WORK




        gameBoard = new Space[60];
        lookup = new HashMap<Integer,String>();


        // Better way to intialize this board using a loop but for now we will hardcode it. 

        // Intialize SPecial Spaces
        gameBoard[0] = new Special("Start");
        gameBoard[10] = new Special("Debt");
        gameBoard[20] = new Special("Vacation");
        gameBoard[30] = new Special("Reverse");
        gameBoard[40] = new Special("Eviction");
        gameBoard[50] = new Special("Jail");

        gameBoard[1] = new Property(100,100,100,100,"New York City", "New York"); //need to input property name
        gameBoard[2] = new SpecialCard(); // might need a speicial Card NAme. Also We should have the hashmaps be here not in the classes themselves. Then we pass in the map. IDK
        gameBoard[3] = new Property(100,100,100,100,"createOwnedProperty", "New York");
        gameBoard[4] = new Property(100,100,100,100,"Miami", "Florida");
        gameBoard[5] = new Property(100,100,100,100,"Tampa", "Florida");
        gameBoard[6] = new Property(100,100,100,100,"Orlando", "Florida");
        gameBoard[7] = new Property(100,100,100,100,"Boston", "New York");
        gameBoard[8] = new SkullCard();
        gameBoard[9] = new Property(100,100,100,100,"Cambridge", "Vermont");

        gameBoard[11] = new Property(100,100,100,100,"Salem", "New York"); //need to input property name
        gameBoard[12] = new Property(100,100,100,100,"Detroit", "New York"); // might need a speicial Card NAme. Also We should have the hashmaps be here not in the classes themselves. Then we pass in the map. IDK
        gameBoard[13] = new Property(100,100,100,100,"Flint", "New York");
        gameBoard[14] = new Property(100,100,100,100,"Rochester", "New York");
        gameBoard[15] = new Property(100,100,100,100,"Cleveland", "New York");
        gameBoard[16] = new SpecialCard();
        gameBoard[17] = new Property(100,100,100,100,"Cincinnati", "New York");
        gameBoard[18] = new SkullCard();
        gameBoard[19] = new Property(100,100,100,100,"Beavercreek", "New York");

        gameBoard[21] = new Property(100,100,100,100,"Stowe", "New York"); //need to input property name
        gameBoard[22] = new Property(100,100,100,100,"Burlington", "New York"); // might need a speicial Card NAme. Also We should have the hashmaps be here not in the classes themselves. Then we pass in the map. IDK
        gameBoard[23] = new Property(100,100,100,100,"Los Angeles", "New York");
        gameBoard[24] = new Property(100,100,100,100,"San Francisco", "New York");
        gameBoard[25] = new Property(100,100,100,100,"Las Vegas", "New York");
        gameBoard[26] = new SpecialCard();
        gameBoard[27] = new Property(100,100,100,100,"Henderson", "New York");
        gameBoard[28] = new SkullCard();
        gameBoard[29] = new Property(100,100,100,100,"Boulder City", "New York");

        gameBoard[31] = new Property(100,100,100,100,"San Antonio", "New York");
        gameBoard[32] = new SpecialCard(); // might need
        gameBoard[33] = new Property(100,100,100,100,"Dallas", "New York");
        gameBoard[34] = new Property(100,100,100,100,"Houston", "New York");
        gameBoard[35] = new Property(100,100,100,100,"Portland", "New York");
        gameBoard[36] = new Property(100,100,100,100,"Eugene", "New York");
        gameBoard[37] = new Property(100,100,100,100,"Oregon City", "New York");
        gameBoard[38] = new Property(100,100,100,100,"Seattle", "New York");
        gameBoard[39] = new Property(100,100,100,100,"Olympia", "New York");

        gameBoard[41] = new Property(100,100,100,100,"Vancouver", "New York");
        gameBoard[42] = new Property(100,100,100,100,"Albuquerque", "New York");
        gameBoard[43] = new Property(100,100,100,100,"Santa Fe", "New York");
        gameBoard[44] = new SkullCard();
        gameBoard[45] = new Property(100,100,100,100,"Electric Company", "New York");
        gameBoard[46] = new Property(100,100,100,100,"Water Company", "New York");
        gameBoard[47] = new Property(100,100,100,100,"JFK", "New York");
        gameBoard[48] = new Property(100,100,100,100,"DTW", "New York");
        gameBoard[49] = new Property(100,100,100,100,"CLE", "New York");

        gameBoard[51] = new Property(100,100,100,100,"LAX", "New York"); //need to input property name
        gameBoard[52] = new SpecialCard();
        gameBoard[53] = new Property(100,100,100,100,"DFW", "New York");
        gameBoard[54] = new Property(100,100,100,100,"DCA", "New York");
        gameBoard[55] = new Property(100,100,100,100,"Tax", "New York");
        gameBoard[56] = new Property(100,100,100,100,"New York City", "New York");
        gameBoard[57] = new Property(100,100,100,100,"New York City", "New York");
        gameBoard[58] = new SkullCard();
        gameBoard[59] = new Property(100,100,100,100,"New York City", "New York");


//        lookup.put(1,"New York City");
//        lookup.put(9,"Seattle");
    }
    // should be reset at the end of every player's turn

    @PostMapping("/updateRoll")
    public void updateRoll(@RequestBody String json) throws JsonProcessingException
    {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map <String, String> inputMap = objectMapper.readValue(json, Map.class);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
                "duopoly", "postgres", "password");
        GameUtil game = new GameUtil(); //needs to be changed to whatever has game meta
        try {
            Connection connection = dcm.getConnection();
            GameDAO gameDAO = new GameDAO(connection);

            //------
            game.setGameCode((inputMap.get("game_code")));
            game = gameDAO.findById(game);
            //------------

            gameDAO.update_dice_roll(game);
            System.out.println(game);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/updateJoinable")
    public void updateJoinable(@RequestBody String json) throws JsonProcessingException
    {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map <String, String> inputMap = objectMapper.readValue(json, Map.class);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
                "duopoly", "postgres", "password");
        GameUtil game = new GameUtil(); //needs to be changed to whatever has game meta
        try {
            Connection connection = dcm.getConnection();
            GameDAO gameDAO = new GameDAO(connection);

            //------
            game.setGameCode((inputMap.get("game_code")));
            game = gameDAO.findById(game);
            //------------

            gameDAO.update_joinable(game);
            System.out.println(game);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/updateDebtPot")
    public void updateDebtPot(@RequestBody String json) throws JsonProcessingException
    {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map <String, String> inputMap = objectMapper.readValue(json, Map.class);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
                "duopoly", "postgres", "password");
        GameUtil game = new GameUtil(); //needs to be changed to whatever has game meta
        try {
            Connection connection = dcm.getConnection();
            GameDAO gameDAO = new GameDAO(connection);

            //------
            game.setGameCode((inputMap.get("game_code")));
            game = gameDAO.findById(game);
            //------------

            gameDAO.update_debt_pot(game);
            System.out.println(game);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    private boolean passedSpace(int prevSpaceNum, int currSpaceNum, int specialSpaceNum)
    {
        return abs(currSpaceNum - prevSpaceNum) > specialSpaceNum;
    }
}

// playing a game. Hard Code No game loop. Instatiate Player classes
