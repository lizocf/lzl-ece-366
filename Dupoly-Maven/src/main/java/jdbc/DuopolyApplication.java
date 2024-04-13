package jdbc;

import com.fasterxml.jackson.core.JsonProcessingException;
import game.*;
import jdbc.account_util.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

import jdbc.game_util.GameDAO;
import jdbc.game_util.GameUtil;
import jdbc.player_util.*;
import jdbc.property_util.OwnedPropertyUtil;
import jdbc.property_util.OwnedPropertyDAO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;






@SpringBootApplication
@RestController
@Import({SpringBootTest.class, Account.class, Player.class, Board.class})
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
