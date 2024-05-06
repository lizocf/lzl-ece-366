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
import java.util.Arrays;

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
        PlayerDAO playerDAO_2 = new PlayerDAO(connection);

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

        // // tax spaces
        String tax[] = {"Tax_4", "Tax_26", "Tax_34", "Tax_56"};

        // if property is a tax space
    // Check if the property is a tax space
        if (Arrays.asList(tax).contains(owned_property.getPropertyName())) {
            System.out.println("You have paid tax");
            playerDAO.update_cash(player, -baseRent);
        } else {
        // Check property ownership
        if (owned_property.getUserId() != 0 && game.getPlayerTurn() != owned_property.getUserId()) {
                System.out.println("You have paid rent");
                playerDAO.update_cash(player, -baseRent * (owned_property.getNumOfHotels() + 1));

                // Update owner's cash
                PlayerUtil tenant = new PlayerUtil();
                tenant.setUserId(owned_property.getUserId());
                tenant.setGameId(gameId);
                tenant = playerDAO_2.findById(tenant);
                playerDAO_2.update_cash(tenant, baseRent * (owned_property.getNumOfHotels() + 1));
            
            } else if (owned_property.getUserId() == 0 && player.getCash() > baseCost) {
                // The property is unowned and can be purchased
                System.out.println("Property Purchased");
                playerDAO.update_cash(player, -baseCost);

                // Create a new owned property instance
                OwnedPropertyUtil property = new OwnedPropertyUtil();
                // Set other properties
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
// //Logic to determine if they have set?
// //Determine if houses are built or not and how many(how many is in the database)

// // For each property I want to make a new DAO?