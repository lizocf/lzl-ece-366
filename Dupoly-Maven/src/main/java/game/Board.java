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

        gameBoard[1] = new Property(20,10,20,60,"Burlington", "Vermont"); //need to input property name
        gameBoard[2] = new SpecialCard();
        gameBoard[3] = new Property(20,10,20,50,"Stowe", "Vermont");
        gameBoard[4] = new Property(100,100,100,100,"Tax_4", "Florida");
        gameBoard[5] = new Property(100,100,100,100,"CLE", "Airports");
        gameBoard[6] = new Property(60,30,160,300,"Beavercreek", "Ohio");
        gameBoard[7] = new Property(80,35,180,350,"Cincinnati", "Ohio");
        gameBoard[8] = new SkullCard();
        gameBoard[9] = new Property(100,40,200,400,"Cleveland", "Ohio");

        gameBoard[11] = new Property(60,30,160,300,"Rochester", "Massachusetts");
        gameBoard[12] = new Property(100,100,100,400,"Electric Company", "Utility");
        gameBoard[13] = new Property(80,35,180,350,"Flint", "Michigan");
        gameBoard[14] = new Property(100,40,200,400,"Detroit", "Michigan");
        gameBoard[15] = new Property(100,100,100,100,"DTW", "Airports");
        gameBoard[16] = new SpecialCard();
        gameBoard[17] = new Property(60,30,160,300,"Salem", "Massachusetts");
        gameBoard[18] = new Property(80,35,180,350,"Cambridge", "Massachusetts");
        gameBoard[19] = new Property(100,40,200,400,"Boston", "Massachusetts");

        gameBoard[21] = new Property(150,50,220,440,"Orlando", "Florida"); //need to input property name
        gameBoard[22] = new SpecialCard();
        gameBoard[23] = new Property(150,50,220,440,"Tampa", "Florida");
        gameBoard[24] = new Property(160,60,250,500,"Miami", "Florida");
        gameBoard[25] = new Property(100,100,100,100,"JFK", "Airports");
        gameBoard[26] = new Property(100,100,100,100,"Tax_26", "New York");
        gameBoard[27] = new Property(180,80,350,450,"Poughkeepskie", "New York");
        gameBoard[28] = new SkullCard();
        gameBoard[29] = new Property(200,100,400,500,"New York City", "New York");

        gameBoard[31] = new Property(200,100,400,500,"Los Angeles", "California");
        gameBoard[32] = new SpecialCard();
        gameBoard[33] = new Property(180,80,350,450,"San Francisco", "California");
        gameBoard[34] = new Property(100,100,100,100,"Tax_34", "New York");
        gameBoard[35] = new Property(100,100,100,100,"DTW", "Airports");
        gameBoard[36] = new Property(200,100,400,500,"Las Vegas", "Nevada");
        gameBoard[37] = new Property(160,60,180,350,"Henderson", "Nevada");
        gameBoard[38] = new SkullCard();
        gameBoard[39] = new Property(150,50,220,440,"Boulder City", "Nevada");

        gameBoard[41] = new Property(100,40,200,400,"San Antonio", "Texas");
        gameBoard[42] = new Property(80,35,180,350,"Dallas", "Texas");
        gameBoard[43] = new Property(60,30,160,300,"Houston", "Texas");
        gameBoard[44] = new SkullCard();
        gameBoard[45] =  new Property(100,100,100,100,"DFW", "Airports");
        gameBoard[46] = new Property(100,40,200,400,"Portland", "Oregon");
        gameBoard[47] = new Property(80,35,180,350,"Eugene", "Oregon");
        gameBoard[48] = new Property(100,100,100,400,"Water Company", "Utility");
        gameBoard[49] = new Property(60,30,160,300,"Oregon City", "Oregon");

        gameBoard[51] = new Property(100,40,200,400,"Seattle", "Washington"); //need to input property name
        gameBoard[52] = new SpecialCard();
        gameBoard[53] = new Property(80,35,180,350,"Olympia", "Washington");
        gameBoard[54] = new Property(60,30,160,300,"Vancouver", "Washington");
        gameBoard[55] = new Property(100,100,100,100,"DCA", "Airports");
        gameBoard[56] = new Property(100,100,100,100,"Tax_56", "New York");
        gameBoard[57] = new Property(20,10,20,60,"Albuquerque", "New Mexico");
        gameBoard[58] = new SkullCard();
        gameBoard[59] = new Property(20,10,20,50,"Santa Fe", "New Mexico");
    }

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

    @PostMapping("/updatePlayerTurn")
    public void updatePlayerTurn(@RequestBody String json) throws JsonProcessingException
    {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map <String, String> inputMap = objectMapper.readValue(json, Map.class);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
                "duopoly", "postgres", "password");
        GameUtil game = new GameUtil(); //needs to be changed to whatever has game meta
        PlayerUtil player = new PlayerUtil();

        try {
            Connection connection = dcm.getConnection();
            GameDAO gameDAO = new GameDAO(connection);

            //------
            player.setUserId(Integer.valueOf(inputMap.get("user_id")));
            game.setGameCode((inputMap.get("game_code")));
            //------------

            gameDAO.updatePlayerTurn(player, game);
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

            gameDAO.update_debt_pot(game,false);
            System.out.println(game);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }

    }

    @PostMapping("/updateNumTurns")
    public void updateNumTurns(@RequestBody String json) throws JsonProcessingException
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
            game.setNumTurns(Integer.valueOf(inputMap.get("num_turns")));
            //------------

            gameDAO.update_num_turns(game);
            System.out.println(game);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }

    }

    
    @PostMapping("/updatePurchaseable")
    public void updatePurchaseable(@RequestBody String json) throws JsonProcessingException
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

            game.setPurchaseable(Boolean.valueOf(inputMap.get("purchase")));
            gameDAO.update_purchaseable(game);
            System.out.println(game);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/updateHost")
    public void updateHost(@RequestBody String json) throws JsonProcessingException
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
            game.setHost(Integer.valueOf(inputMap.get("host")));
            //------------

            gameDAO.updateHost(game);
            System.out.println(game);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }


//    @PostMapping("/updateCardName")
//    public void updateCardName(@RequestBody String json) throws JsonProcessingException
//    {
//        System.out.println(json);
//        ObjectMapper objectMapper = new ObjectMapper();
//        Map <String, String> inputMap = objectMapper.readValue(json, Map.class);
//        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
//                "duopoly", "postgres", "password");
//        GameUtil game = new GameUtil(); //needs to be changed to whatever has game meta
//        try {
//            Connection connection = dcm.getConnection();
//            GameDAO gameDAO = new GameDAO(connection);
//
//            //------
//            game.setGameCode((inputMap.get("game_code")));
//            game = gameDAO.findById(game);
//            //------------
//
//            game.setPurchaseable(Boolean.valueOf(inputMap.get("purchase")));
//            System.out.println(game);
//        }
//        catch(SQLException e) {
//            e.printStackTrace();
//        }
//    }


}

// playing a game. Hard Code No game loop. Instatiate Player classes
