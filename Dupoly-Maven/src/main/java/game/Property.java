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
        if ((!(owned_property.getPropertyName() == null)) && (game.getPlayerTurn() != owned_property.getUserId()))
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

        if((owned_property.getPropertyName() == null) && (player.getCash() > baseCost))
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

// //Logic to determine if they have set?
// //Determine if houses are built or not and how many(how many is in the database)

// // For each property I want to make a new DAO?