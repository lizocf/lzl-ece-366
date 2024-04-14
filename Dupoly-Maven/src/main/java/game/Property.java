package game;

import java.sql.Connection;
import java.util.Scanner;

import jdbc.game_util.GameDAO;
import jdbc.game_util.GameUtil;
import jdbc.property_util.*;
import jdbc.player_util.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

// @SpringBootApplication
// @RestController
// @CrossOrigin
class Property implements Space {
    public int baseCost,buildCost,baseRent,buildRent;
    public boolean purchased;
    public String propertyName, setName;
    // OwnedPropertyDAO propertyDAO;

    public Property(int costPrice, int buildingCostPrice, int rentPrice, int hotelRentPrice,String propname,String setname)
    {
        // need super constructor to initialize stuff


        baseCost = costPrice;
        buildCost = buildingCostPrice;
        baseRent = rentPrice;
        buildRent = hotelRentPrice;
        propertyName = propname;
        setName = setname;
        // everything should get the same collection so its all in the database
        // OwnedPropertyDAO propertyDAO = new OwnedPropertyDAO(connection);
    }

//    public void setPropertyDAO(Connection connection) {
//
//        propertyDAO = new OwnedPropertyDAO(connection);
//    }

    // need to pass in the gameId.
    // How are we passing in propery and set names. Do we change the function?
    // connection needs to be passed.


    @Override
    public void isOccupied(boolean occupied, int userId, int gameId, String gamecode, Connection connection)
    {
        OwnedPropertyDAO propertyDAO = new OwnedPropertyDAO(connection);
        GameDAO gameDAO = new GameDAO(connection);
        PlayerDAO playerDAO = new PlayerDAO(connection);

        GameUtil game = new GameUtil();
        game.setGameCode(gamecode);
        game = gameDAO.findById(game);

        PlayerUtil player = new PlayerUtil();
        player.setUserId(userId);
        player.setGameId(gameId);
        player = playerDAO.findById(player);

        // how can we tell if the property is owned.
        OwnedPropertyUtil owned_property = new OwnedPropertyUtil();
        owned_property.setGameId(gameId);
        owned_property.setPropertyName(propertyName);

        // someone owns this and it is not you
        if ((!(owned_property.getPropertyName() == null)) & (game.getPlayerTurn() != owned_property.getUserId()))
        {

            System.out.println("You have payed rent");
            owned_property = propertyDAO.findById(owned_property);
            playerDAO.update_cash(player,-baseRent * (owned_property.getNumOfHotels() + 1));

            PlayerDAO playerDAO2 = new PlayerDAO(connection);
            // this needs to be fixed up
            PlayerUtil tenant = new PlayerUtil();
            tenant.setUserId(owned_property.getUserId());
            tenant.setGameId(gameId);
            tenant = playerDAO2.findById(tenant);
            playerDAO.update_cash(tenant,baseRent * (owned_property.getNumOfHotels() + 1));
            System.out.println(owned_property.getUserId());
        }

        if((owned_property.getPropertyName() == null) & (player.getCash() > baseCost))
        {
            // the property can be purchased and they choose to purchase it
            if (game.getPurchaseable()) {
                // update database (user has purchased a valid property)
                System.out.println("Property Purchased");
                playerDAO.update_cash(player, -baseCost);
                OwnedPropertyUtil property = new OwnedPropertyUtil();
                // set the other stuff
                property.setPropertyName(propertyName);
                property.setNumOfHotels(0);
                property.setSetName(setName);
                property.setUserId(userId);
                property.setGameId(gameId);
                property = propertyDAO.createInstance(property);
            }
        }
    }
}




// package game;

// import java.sql.Connection;
// import java.util.Scanner;
// import com.fasterxml.jackson.core.JsonProcessingException;
// import jdbc.DatabaseConnectionManager;
// import jdbc.property_util.OwnedPropertyDAO;
// import jdbc.property_util.OwnedPropertyUtil;
// import jdbc.player_util.PlayerDAO;
// import jdbc.player_util.PlayerUtil;

// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.context.annotation.Import;
// import org.springframework.web.bind.annotation.*;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import java.sql.Connection;
// import java.sql.SQLException;
// import java.util.Map;


// @SpringBootApplication
// @RestController
// @CrossOrigin
// class Property implements Space {
//     public int baseCost,buildCost,baseRent,buildRent;
//     public boolean purchased;

//     // public String propertyName, setName;

//     public int gameId;
//     public int userId;
//     public String setName;
//     public String propertyName;
//     public int numOfHotels;

//     public int debtPot;
//     // OwnedPropertyDAO propertyDAO;

//     public Property(int costPrice, int buildingCostPrice, int rentPrice, int hotelRentPrice,String propname,String setname)
//     {
//         baseCost = costPrice;
//         buildCost = buildingCostPrice;
//         baseRent = rentPrice;
//         buildRent = hotelRentPrice;
//         propertyName = propname;
//         setName = setname;
//         // everything should get the same collection so its all in the database
//         // OwnedPropertyDAO propertyDAO = new OwnedPropertyDAO(connection);
//     }

//     @GetMapping("/getOwnedProperty/{gameId}/{PropertyName}")
//     public OwnedPropertyUtil getOwnedProperty(@PathVariable("gameId") int gameId,
//                                       @PathVariable("PropertyName") String propertyName) {
//         // System.out.println(gameId, propertyName);
//         DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
//                 "duopoly", "postgres", "password");

//         OwnedPropertyUtil property = new OwnedPropertyUtil();
//         property.setGameId(gameId);
//         property.setPropertyName(propertyName);

//         try {
//             Connection connection = dcm.getConnection();
//             OwnedPropertyDAO propertyDAO = new OwnedPropertyDAO(connection);

//             property = propertyDAO.findById(property);
//             System.out.println(property);
//         }
//         catch(SQLException e) {
//             e.printStackTrace();
//         }
//         return property;
//     }

//     @PostMapping("/getNames")
//     public OwnedPropertyUtil getOwnedProperty(@RequestBody String json) throws JsonProcessingException
//     {
//         System.out.println(json);
//         ObjectMapper objectMapper = new ObjectMapper();
//         Map<String, String> inputMap = objectMapper.readValue(json, Map.class);
//         DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
//                 "duopoly", "postgres", "password");

//         OwnedPropertyUtil property = new OwnedPropertyUtil();
//         PlayerUtil player = new PlayerUtil();

//         try {
//             Connection connection = dcm.getConnection();
//             OwnedPropertyDAO propertyDAO = new OwnedPropertyDAO(connection);
//             PlayerDAO playerDAO = new PlayerDAO(connection);

//             player.setUserId(Integer.valueOf(inputMap.get("user_id")));
//             player.setGameId(Integer.valueOf(inputMap.get("game_id")));
//             player = playerDAO.findById(player);

//             property = propertyDAO.findNames(player);
//             System.out.println(property);
//         }
//         catch(SQLException e) {
//             e.printStackTrace();
//         }
//         return property;
//     }





// //    public void setPropertyDAO(Connection connection) {
// //
// //        propertyDAO = new OwnedPropertyDAO(connection);
// //    }

//     // need to pass in the gameId.
//     // How are we passing in propery and set names. Do we change the function?
//     // connection needs to be passed.


//     @Override
//     public void isOccupied(boolean occupied, int userId, int gameId, Connection connection)
//     {
//         OwnedPropertyDAO propertyDAO = new OwnedPropertyDAO(connection);
//         if (occupied & !purchased)
//         {
//             //ask user if they want to purchase
//             Scanner scanner = new Scanner(System.in);
//             System.out.print("Would you like to purchase[Yes/No]: ");
//             String s = scanner.nextLine();

//             if (s.equals("Yes"))
//             {

//                 // update database (user has purchased a valid property)
//                 System.out.print("Property Purchased");
//                 OwnedPropertyUtil property = new OwnedPropertyUtil();
//                 // set the other stuff
//                 property.setPropertyName(propertyName);
//                 property.setNumOfHotels(0);
//                 property.setSetName(setName);
//                 property.setUserId(userId);
//                 property.setGameId(gameId);
//                 property = propertyDAO.createInstance(property);

//             }
//         }


//         if (occupied & purchased)
//         {
//             // update database (user has lost money)
//             System.out.print("You have payed rent");
//             // we need to get a player by the id finder
//             // call the update for that player

//             // PlayerDAO playerDAO = new PlayerDAO(connection);
//             // PlayerUtil player = playerDAO.findById();
//             // these need to be actual values
//             // playerDAO.updateCash(player,baseCost,1); // we need to return a player I guess.

//             // how do I link the util to the other things?

//             // we pass this into a player update cash function? whcih leads us to

//         }

//     }

//     public int getDebtPot() {
//         return debtPot;
//     }

//     public void setDebtPot(int debtPot) {
//         this.debtPot = debtPot;
//     }

//     public int getGameId() {
//         return gameId;
//     }

//     public void setGameId(int gameId) {
//         this.gameId = gameId;
//     }

//     public int getUserId() {
//         return userId;
//     }

//     public void setUserId(int userId) {
//         this.userId = userId;
//     }

//     public String getSetName() {
//         return setName;
//     }

//     public void setSetName(String setName) {
//         this.setName = setName;
//     }

//     public String getPropertyName() {
//         return propertyName;
//     }

//     public void setPropertyName(String propertyName) {
//         this.propertyName = propertyName;
//     }

//     public int getNumOfHotels() {
//         return numOfHotels;
//     }

//     public void setNumOfHotels(int numOfHotels) {
//         this.numOfHotels = numOfHotels;
//     }
// }





// //Logic to determine if they have set?
// //Determine if houses are built or not and how many(how many is in the database)

// // For each property I want to make a new DAO?