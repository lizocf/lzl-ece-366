package game;


import jdbc.game_util.GameDAO;
import jdbc.game_util.GameUtil;
import jdbc.player_util.PlayerDAO;
import jdbc.player_util.PlayerUtil;
import jdbc.property_util.OwnedPropertyDAO;
import jdbc.property_util.OwnedPropertyUtil;

import java.sql.Connection;
import java.util.Scanner;
class Special implements Space
{
    boolean inJail = false;
    public String spaceName;
    public Special(String name) { spaceName = name; }

    private void startSpace(PlayerDAO playerDAO,PlayerUtil player)
    {
        // put 300 dollars in the users account for landing directly on jail
        System.out.println("You've received 300 dollars for landing on START");
        playerDAO.update_cash(player,300);
    }

    private  void debtSpace(PlayerDAO playerDAO,PlayerUtil player,GameDAO gameDAO,GameUtil game)
    {
        System.out.println("BANGPOTT!!! You received all the debt money");
        playerDAO.update_cash(player,game.getDebtPot());

        // find a way to reset the debt pot
        gameDAO.update_debt_pot(game,true);

        // reset the debt pot
    }

    private void vactionSpace()
    {
        System.out.println("Welcome to paradise");
        // literally does nothing
    }

    private void reverseSpace(PlayerDAO playerDAO,PlayerUtil player)
    {
        System.out.println("Turn Around (Every now and then I get a little bit hungry)");
        // modify direction in the player database
        String curr_dir = player.getCurrentDirection();
        if(curr_dir.equals("LEFT"))
        {
            playerDAO.update_direction(player,"RIGHT");
        }
        else
        {
            playerDAO.update_direction(player,"LEFT");
        }
    }

    private  void evictionSpace(OwnedPropertyDAO propertyDAO, OwnedPropertyUtil owned_property)
    {
        System.out.println("Sorry Bud you loosing a random house");
        // modify number of hotels. Decrement it by 1.
        OwnedPropertyUtil[] properties = new OwnedPropertyUtil[40];
        // cannot invoke because null
        properties = propertyDAO.findAllOwned(owned_property);
        if (properties.length > 1)
        {
            propertyDAO.delete(properties[0]);
        }
    }

    private void jailSpace()
    {

            System.out.println("JAILLED");
//        if(inJail)
//        {
//            Scanner scanner = new Scanner(System.in);
//            System.out.print("Would you like to pay bail[Yes/No]: ");
//            String s = scanner.nextLine();
//
//            if (s.equals("Yes"))
//            {
//                System.out.println("OUT ON BAIL");
//            }
//        }

    }

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
        owned_property.setUserId(userId);
        owned_property.setGameId(gameId);

        //Switch case that executes member functions
        switch (spaceName)
        {

            case "Start":
                startSpace(playerDAO,player);
                break;

            case "Debt":
                debtSpace(playerDAO,player,gameDAO,game);
                break;

            case "Vacation":
                vactionSpace();
                break;

            case "Reverse":
                reverseSpace(playerDAO,player);
                break;

            case "Eviction":
                evictionSpace(propertyDAO,owned_property);
                break;

            case "Jail":
                jailSpace();
                break;
        }
    }
}




