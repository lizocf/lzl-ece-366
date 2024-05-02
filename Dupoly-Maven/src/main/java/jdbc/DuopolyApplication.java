package jdbc;

import com.fasterxml.jackson.core.JsonProcessingException;
import game.*;
import jdbc.account_util.*;
import org.springframework.stereotype.Controller;
import webservice.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

import jdbc.game_util.GameDAO;
import jdbc.game_util.GameUtil;
import jdbc.player_util.*;
import jdbc.property_util.OwnedPropertyUtil;
import jdbc.property_util.OwnedPropertyDAO;
import jdbc.turn_util.TurnDAO;
import jdbc.turn_util.TurnUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import webservice.*;



@SpringBootApplication
@RestController
@Import({SpringBootTest.class, Account.class, Player.class, Board.class, WebSocketConfig.class, WebSocketTextController.class})
@CrossOrigin
public class DuopolyApplication {

	Board gb = new Board();

	@GetMapping("/simpleTest")
	public String simpleTest()
	{
		System.out.println("TESTING 123");
		return "d";
	}

	@PostMapping("/createNewGame")
	public GameUtil createNewGame(@RequestBody String json) throws JsonProcessingException
	{
		System.out.println(json);
		ObjectMapper objectMapper = new ObjectMapper();
		Map <String, String> inputMap = objectMapper.readValue(json, Map.class);
		DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
				"duopoly", "postgres", "password");
		GameUtil newGame = new GameUtil();
		try {
			Connection connection = dcm.getConnection();
			GameDAO gameDAO = new GameDAO(connection);
			newGame.setGameCode(inputMap.get("game_code"));

			newGame = gameDAO.createInstance(newGame);
			System.out.println(newGame);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return newGame;
	}

    // need for card descriptions
	@GetMapping("/getGameInfo/{gameCode}")
	public GameUtil getGameInfo(@PathVariable("gameCode") String gameCode)
	{

		DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
				"duopoly", "postgres", "password");
		GameUtil newGame = new GameUtil();
		try {
			Connection connection = dcm.getConnection();
			GameDAO gameDAO = new GameDAO(connection);
			newGame.setGameCode(gameCode);

			newGame = gameDAO.findById(newGame);
			System.out.println(newGame);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return newGame;
	}
	// used to check if specific property is owned already
    @GetMapping("/getOwnedProperty/{gameId}/{PropertyName}")
    public OwnedPropertyUtil getOwnedProperty(@PathVariable("gameId") int gameId,
                                      @PathVariable("PropertyName") String propertyName) {
        // System.out.println(gameId, propertyName);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
                "duopoly", "postgres", "password");

        OwnedPropertyUtil property = new OwnedPropertyUtil();
        property.setGameId(gameId);
        property.setPropertyName(propertyName);

        try {
            Connection connection = dcm.getConnection();
            OwnedPropertyDAO propertyDAO = new OwnedPropertyDAO(connection);

            property = propertyDAO.findById(property);
            System.out.println(property);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return property;
    }

    @PostMapping("/updateUpgradeable")
    public void updateUpgradeable(@RequestBody String json) throws JsonProcessingException
    {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map <String, String> inputMap = objectMapper.readValue(json, Map.class);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
                "duopoly", "postgres", "password");
        OwnedPropertyUtil owned_property = new OwnedPropertyUtil();

        try{
            Connection connection = dcm.getConnection();
            OwnedPropertyDAO ownedPropertyDAO = new OwnedPropertyDAO(connection);

            owned_property.setPropertyName(inputMap.get("property_name"));
            owned_property.setGameId(Integer.valueOf(inputMap.get("game_id")));
            owned_property.setUpgradeable(Boolean.valueOf(inputMap.get("upgradeable")));
            ownedPropertyDAO.updateUpgradeable(owned_property);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

	// get name of property via current position
    @GetMapping("/getNames/{gameId}/{userId}")
    public OwnedPropertyUtil getNames(@PathVariable("gameId") int gameId,
                                      @PathVariable("userId") int userId)
    {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
                "duopoly", "postgres", "password");

        OwnedPropertyUtil property = new OwnedPropertyUtil();
        PlayerUtil player = new PlayerUtil();
        player.setUserId(userId);
        player.setGameId(gameId);

        try {
            Connection connection = dcm.getConnection();
            OwnedPropertyDAO propertyDAO = new OwnedPropertyDAO(connection);
            PlayerDAO playerDAO = new PlayerDAO(connection);

            player = playerDAO.findById(player);

            property = propertyDAO.findNames(player);
            System.out.println(property);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return property;
    }

	// get name of property via ANY position
    @GetMapping("/getNamesByPos/{pos}")
    public OwnedPropertyUtil getNamesByPos(@PathVariable("pos") int pos)
    {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
                "duopoly", "postgres", "password");

        OwnedPropertyUtil property = new OwnedPropertyUtil();

        try {
            Connection connection = dcm.getConnection();
            OwnedPropertyDAO propertyDAO = new OwnedPropertyDAO(connection);

            property = propertyDAO.findNamesByPos(pos);
            System.out.println(property);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return property;
    }

    @PostMapping("/upgradeProperty")
    public void upgradeProperty(@RequestBody String json) throws JsonProcessingException
    {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map <String, String> inputMap = objectMapper.readValue(json, Map.class);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
                "duopoly", "postgres", "password");
        OwnedPropertyUtil property = new OwnedPropertyUtil();
        OwnedPropertyUtil[] properties = new OwnedPropertyUtil[40];

        // we need to get the property they are interested in updating. [.]
        // Then we get all for the associated user of that property. [.]
        // if its equal to one of the two guys set target to 2
        // Then we just check if its equal to that props set name.

        int target = 3;
        int owned = 0;

        try{
            Connection connection = dcm.getConnection();
            OwnedPropertyDAO ownedPropertyDAO = new OwnedPropertyDAO(connection);

            property.setPropertyName(inputMap.get("property_name"));
            property.setUserId(Integer.valueOf(inputMap.get("user_id")));
            property.setGameId(Integer.valueOf(inputMap.get("game_id")));

            // property.setUpgradeable(Boolean.valueOf(inputMap.get("upgradeable")));
            property = ownedPropertyDAO.findById(property);
            properties = ownedPropertyDAO.findAllOwned(property);

            if (property.getSetName().equals("New York") || property.getSetName().equals("California") || property.getSetName().equals("Vermont") || property.getSetName().equals("New Mexico") )
            {
                target = 2;
            }

            for(OwnedPropertyUtil p : properties)
            {
                if(p.getSetName().equals(property.getSetName()))
                {
                    owned++;
                    if(owned == target+1) // plus 1 b/c it will count the property itself
                    {
                        ownedPropertyDAO.updateNumHotel(property,1);
                        owned = 0;
                    }
                }
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

	// get all properties owned by a player
    @GetMapping("/getAllOwnedProperties/{gameId}/{userId}")
    public OwnedPropertyUtil[] getAllOwnedProperties(@PathVariable("gameId") int gameId, @PathVariable("userId") int userId) {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
                "duopoly", "postgres", "password");
        OwnedPropertyUtil property = new OwnedPropertyUtil();
        OwnedPropertyUtil[] properties = new OwnedPropertyUtil[40];
        property.setGameId(gameId);
		property.setUserId(userId);
		
        try {
            Connection connection = dcm.getConnection();
            OwnedPropertyDAO propertyDAO = new OwnedPropertyDAO(connection);

            properties = propertyDAO.findAllOwned(property);
            System.out.println(properties);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return properties;
    }

    @PostMapping("/playerTrade")
    public void playerTrade(@RequestBody String json) throws JsonProcessingException
    {

        // System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map <String, String> inputMap = objectMapper.readValue(json, Map.class);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
                "duopoly", "postgres", "password");

        try
        {
            Connection connection = dcm.getConnection();

            // so in order to do a trade we need to first grab two players and the properties they own
            // next we need to a way to know if that property is trade-able
            // then we grab all the trade-able properties and just swap them between the two players

            OwnedPropertyDAO ownedPropertyDAO = new OwnedPropertyDAO(connection);

            OwnedPropertyUtil[] trader_properties = new OwnedPropertyUtil[40];
            OwnedPropertyUtil[] tradey_proerties = new OwnedPropertyUtil[40];

            OwnedPropertyUtil trader_prop = new OwnedPropertyUtil();
            trader_prop.setGameId(Integer.valueOf(inputMap.get("game_id1")));
            trader_prop.setUserId(Integer.valueOf(inputMap.get("user_id1")));

            OwnedPropertyUtil tradey_prop = new OwnedPropertyUtil();
            tradey_prop.setGameId(Integer.valueOf(inputMap.get("game_id2")));
            tradey_prop.setUserId(Integer.valueOf(inputMap.get("user_id2")));

            tradey_proerties = ownedPropertyDAO.findAllOwned(tradey_prop);
            trader_properties = ownedPropertyDAO.findAllOwned(trader_prop);

            // we have all the properties now we need to swap the ones marked as traded

            // loop through one of the props
//            for(int j = 0; j < trader_properties.length; j++)
//            {
//                //
//            }


        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

////// TURN ORDER COMMANDS //////

    // getUserTurnOrder
    @GetMapping("/getTurnOrder/{gameId}/{userId}")
    public TurnUtil getUserTurnOrder(@PathVariable("gameId") int gameId, @PathVariable("userId") int userId) {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
                "duopoly", "postgres", "password");

        TurnUtil turn = new TurnUtil();
        turn.setGameId(gameId);
        turn.setUserId(userId);

        try {
            Connection connection = dcm.getConnection();
            TurnDAO turnDAO = new TurnDAO(connection);

            turn = turnDAO.findById(turn);
            System.out.println(turn);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return turn;
    }

    // getGameTurnOrder
    @GetMapping("/getGameTurnOrder/{gameId}")
    public TurnUtil[] getGameTurnOrder(@PathVariable("gameId") int gameId) {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
                "duopoly", "postgres", "password");

        TurnUtil turn = new TurnUtil();
        turn.setGameId(gameId);

        TurnUtil[] order = new TurnUtil[8];

        try {
            Connection connection = dcm.getConnection();
            TurnDAO turnDAO = new TurnDAO(connection);

            order = turnDAO.findByGameId(turn);
            System.out.println(order);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return order;
    }


    // addUserToTurnOrder
    @PostMapping("/addUserToTurnOrder")
    public void addUserToTurnOrder(@RequestBody String json) throws JsonProcessingException
    {
        // System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map <String, String> inputMap = objectMapper.readValue(json, Map.class);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
                "duopoly", "postgres", "password");
        TurnUtil turn = new TurnUtil();

        try{
            Connection connection = dcm.getConnection();
            TurnDAO turnDAO = new TurnDAO(connection);

            turn.setGameId(Integer.valueOf(inputMap.get("game_id")));
            turn.setUserId(Integer.valueOf(inputMap.get("user_id")));
            
            turnDAO.createInstance(turn);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    // deleteGameOrder
    @PostMapping("/deleteGameOrder")
    public void deleteGameOrder(@RequestBody String json) throws JsonProcessingException
    {
        // System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map <String, String> inputMap = objectMapper.readValue(json, Map.class);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
                "duopoly", "postgres", "password");
        TurnUtil turn = new TurnUtil();

        try{
            Connection connection = dcm.getConnection();
            TurnDAO turnDAO = new TurnDAO(connection);

            turn.setGameId(Integer.valueOf(inputMap.get("game_id")));
            
            turnDAO.delete(turn);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/updateTurnOrder")
    public void updateTurnOrder(@RequestBody String json) throws JsonProcessingException
    {
        // System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map <String, String> inputMap = objectMapper.readValue(json, Map.class);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
                "duopoly", "postgres", "password");
        TurnUtil turn = new TurnUtil();

        try{
            Connection connection = dcm.getConnection();
            TurnDAO turnDAO = new TurnDAO(connection);

            turn.setGameId(Integer.valueOf(inputMap.get("game_id")));
            turnDAO.rotateUserOrder(turn);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    // deleteUserOrder
    @PostMapping("/deleteUserOrder")
    public void deleteUserOrder(@RequestBody String json) throws JsonProcessingException
    {
        // System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map <String, String> inputMap = objectMapper.readValue(json, Map.class);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
                "duopoly", "postgres", "password");
        TurnUtil turn = new TurnUtil();

        try{
            Connection connection = dcm.getConnection();
            TurnDAO turnDAO = new TurnDAO(connection);

            turn.setGameId(Integer.valueOf(inputMap.get("game_id")));
            turn.setUserId(Integer.valueOf(inputMap.get("user_id")));

            turnDAO.deleteUser(turn);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }


//////// gameMove ////////////

	@PostMapping("/gameMove")
	public void createOwnedProperty(@RequestBody String json) throws JsonProcessingException
	{
		// System.out.println(json);
		ObjectMapper objectMapper = new ObjectMapper();
		Map <String, String> inputMap = objectMapper.readValue(json, Map.class);
		DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
				"duopoly", "postgres", "password");

		try
		{
			Connection connection = dcm.getConnection();
			gb.gameBoard[Integer.valueOf(inputMap.get("space"))].isOccupied(true,Integer.valueOf(inputMap.get("user_id")),Integer.valueOf(inputMap.get("game_id")), inputMap.get("game_code"), connection);
			// does game id need to be switched to game_code???
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) { SpringApplication.run(DuopolyApplication.class, args); }



}

// Need to find a way to do this curr space stuff in springboot
// Issue: This pulls a lot from player stuff and dice stuff.
// Idea: we get the current roll from game doa and add that to something in the player DAO
//

// Literally This stuff working in Springboot:
//
//             if(currPlayerTurn.current_direction)
//            {
//                currPlayerTurn.currSpace = (diceroll + currPlayerTurn.currSpace) % 60;
//            }
//            else
//            {
//                currPlayerTurn.currSpace = currPlayerTurn.currSpace > diceroll ? (currPlayerTurn.currSpace-diceroll) : (60 - abs(diceroll - currPlayerTurn.currSpace));
//            }
