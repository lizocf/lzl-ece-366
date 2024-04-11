package game;

import jdbc.game_util.GameDAO;
import jdbc.game_util.GameUtil;
import jdbc.player_util.PlayerDAO;
import jdbc.player_util.PlayerUtil;
import jdbc.property_util.OwnedPropertyDAO;
import jdbc.property_util.OwnedPropertyUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
@RestController
@CrossOrigin
public class SkullCard implements Card,Space {

    GameUtil goi = new GameUtil();
    PlayerUtil poi = new PlayerUtil();

    OwnedPropertyUtil proi = new OwnedPropertyUtil();


    String propertyName;
    Connection connection;
    String gamecode;
    int poi_userId;
    int poi_gameId;


    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public int getPoi_userId() {
        return poi_userId;
    }

    public void setPoi_userId(int poi_userId) {
        this.poi_userId = poi_userId;
    }

    public int getPoi_gameId() {
        return poi_gameId;
    }

    public void setPoi_gameId(int poi_gameId) {
        this.poi_gameId = poi_gameId;
    }



    public String getGamecode() {
        return gamecode;
    }

    public void setGamecode(String gamecode) {
        this.gamecode = gamecode;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }




    WeightedProbModel<Object> skullPB = new WeightedProbModel<>()
            .add(100, "Bankrupt")
            .add(0, "Market Crash")
            .add(0, "Based")
            .add(0, "f off")
            .add(0, "Squatters Rights")
            .add(0, "Jailed")
            .add(0, "No Go")
            .add(0, "Divorced")
            .add(0, "Swap")
            .add(0, "West Coast Best Coast")
            .add(0, "West Coast Worst Coast")
            .add(0, "Dust Bowl")
            .add(0, "Triple Skull emoji")
            .add(0, "We're So Back")
            .add(0, "Self Sabotage")
            .add(0, "What Do You Bring To The Table")
            .add(0, "Rent Stabilized")
            .add(0, "Eviction")
            .add(0, "Winner Winner Chicken Dinner");

    // public String cardName;
    Map<String, OnCard> cardMap = new HashMap<>();




    // The problem is I need a way to get the stuff a gameMove would be sending me
    // Sln potentially --> updateGameboard thing.
    // We need to be able to do this without the comfort of hardcoding
    // Only thing really want to be sent is the game_id
    public SkullCard()
    {
        cardMap.put("Bankrupt", new OnCard()
        {

            // drop money to zero and mark the player as dead
            @Override
            public void cardAction()
            {
                System.out.println("ruh roh raggy your bankrupt now");
                PlayerDAO playerDAO = new PlayerDAO(getConnection());
                int money = poi.getCash();
                playerDAO.update_cash(poi,money*-1);
                playerDAO.update_dead(poi);
                // poi.setDead(true);
            }
        });

        cardMap.put("Market Crash", new OnCard()
        {
            @Override
            public void cardAction()
            {
                PlayerUtil[] players = new PlayerUtil[10];
                // subtract 420 dollars from all the players
                System.out.print("Market Crash Everyone looses 420 bucks");
                // have to use getAllPlayersInGame but not sure how
                PlayerDAO playerDAO = new PlayerDAO(getConnection());
                players = playerDAO.findByGameId(poi);
                for(int i=0; i<10; i++) {
                    playerDAO.update_cash(players[i],-420);
                }
            }
        });

        cardMap.put("Based", new OnCard()
        {
            @Override
            public void cardAction()
            {
                // define what needs to happen in the sequel database
                // find the richest player. Use get all players and compare all of them. Until you find the richest and poorest one.
                // do a property transfer
                System.out.print("Skull Card 3 happens");

            }
        });

        cardMap.put("f off", new OnCard()
        {
            @Override
            public void cardAction()
            {
                // idk if we will be able to do this one
                System.out.print("Skull Card 4 happens");

            }
        });

        cardMap.put("Squatters Rights", new OnCard()
        {
            @Override
            public void cardAction()
            {
                // define what needs to happen in the sequel database
                System.out.print("Skull Card 4 happens");

            }
        });

        cardMap.put("Jailed", new OnCard()
        {
            @Override
            public void cardAction()
            {
                // define what needs to happen in the sequel database
                System.out.print("GO TO JAIL");
                PlayerDAO playerDAO = new PlayerDAO(getConnection());
                playerDAO.update_jail(poi,true);
            }
        });


        cardMap.put("No Go", new OnCard()
        {
            @Override
            public void cardAction()
            {
                // go space no longer grants money
                // need to include some sort of toggle option in the special spaces.
                System.out.print("Skull Card 4 happens");

            }
        });

        cardMap.put("AMONG US", new OnCard()
        {
            @Override
            public void cardAction()
            {
                // we never defined what this is
                System.out.print("amoooongusss :)");

            }
        });

        cardMap.put("Swap", new OnCard()
        {
            @Override
            public void cardAction()
            {
                // define what needs to happen in the sequel database
                System.out.print("Skull Card 2 happens");

            }
        });

        cardMap.put("West Coast Best Coast", new OnCard()
        {
            @Override
            public void cardAction()
            {
                // define what needs to happen in the sequel database
                System.out.print("Skull Card 3 happens");

            }
        });

        cardMap.put("West Coast Worst Coast", new OnCard()
        {
            @Override
            public void cardAction()
            {
                // define what needs to happen in the sequel database
                System.out.print("Skull Card 4 happens");

            }
        });

        cardMap.put("Dust Bowl", new OnCard()
        {
            @Override
            public void cardAction()
            {
                // define what needs to happen in the sequel database
                System.out.print("Skull Card 4 happens");

            }
        });

        cardMap.put("Triple Skull Emoji", new OnCard()
        {
            @Override
            public void cardAction()
            {
                // define what needs to happen in the sequel database
                System.out.print("Skull Card 4 happens");

            }
        });


        cardMap.put("We're So Back", new OnCard()
        {
            @Override
            public void cardAction()
            {
                // define what needs to happen in the sequel database
                System.out.print("Skull Card 4 happens");

            }
        });

        cardMap.put("Self Sabotage", new OnCard()
        {
            @Override
            public void cardAction()
            {
                // define what needs to happen in the sequel database
                System.out.print("Skull Card 4 happens");

            }
        });

        cardMap.put("What Do You Bring To The Table", new OnCard()
        {
            @Override
            public void cardAction()
            {
                // define what needs to happen in the sequel database
                System.out.print("Skull Card 4 happens");

            }
        });

        cardMap.put("Rent Stabilized", new OnCard()
        {
            @Override
            public void cardAction()
            {
                // define what needs to happen in the sequel database
                System.out.print("Skull Card 4 happens");

            }
        });


        cardMap.put("Eviction", new OnCard()
        {
            @Override
            public void cardAction()
            {
                // define what needs to happen in the sequel database
                System.out.print("Skull Card 4 happens");

            }
        });

        cardMap.put("Winner Winner Chicken Dinner", new OnCard()
        {
            @Override
            public void cardAction()
            {
                // define what needs to happen in the sequel database
                System.out.print("Skull Card 4 happens");

            }
        });
    }

    @Override
    public void onUse()
    {
        GameDAO gameDAO = new GameDAO(getConnection());
        PlayerDAO playerDAO = new PlayerDAO(getConnection());
        OwnedPropertyDAO ownedPropertyDAO = new OwnedPropertyDAO(getConnection());


        // investigate whether I use user or game Id to find owned property (conflicting statements in the DAO)
        poi.setUserId(getPoi_userId());
        poi.setGameId(getPoi_gameId());
        goi.setGameCode(getGamecode());
        proi.setUserId(getPoi_userId());
        goi = gameDAO.findById(goi);
        poi = playerDAO.findById(poi);
        proi = ownedPropertyDAO.findById(proi);
        System.out.println(goi);
        System.out.println(poi);
        String cardName = (String) skullPB.next();
        cardMap.get(cardName).cardAction();
    }


    @Override
    public void isOccupied(boolean occupied, int userId, int gameId, String gamecode, Connection connection)
    {
        setConnection(connection);
        setGamecode(gamecode);
        setPoi_gameId(gameId);
        setPoi_userId(userId);
        onUse();
    }

    // um just change isOccupied to iunclude the game code.

}
