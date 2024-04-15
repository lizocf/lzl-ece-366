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
public class SpecialCard implements Card,Space {

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

    WeightedProbModel<Object> specialPB = new WeightedProbModel<>()
            .add(0, "Force Trade")
            .add(100, "The Federal Reserve")
            .add(0, "King of the City")
            .add(0, "California Love")
            .add(0, "Truth or Consequences")
            .add(0, "Cringe")
            .add(0, "Get Out of Jail Free")
            .add(0, "Reverse")
            .add(0, "Debt Collector")
            .add(0, "Surprise Party") // causes null pointer issues
            .add(0, "Run it back")
            .add(0, "Lottery")
            .add(0, "Vaycay")
            .add(0, "Housing Lottery")
            .add(0, "GO TO")
            .add(0, "Mercy")
            .add(0, "Skull Town")
            .add(0, "FREE AS AIR AND WATER")
            .add(0, "Among US");
    // public String cardName;
    Map<String, OnCard> cardMap = new HashMap<>();

    public SpecialCard()
    {
        cardMap.put("Force Trade", new OnCard()
        {

            // drop money to zero and mark the player as dead
            @Override
            public void cardAction()
            {
                System.out.println("ruh roh raggy your bankrupt now");
            }
        });

        cardMap.put("The Federal Reserve", new OnCard()
        {
            @Override
            public void cardAction()
            {
                System.out.print("Um here have some money");
                PlayerDAO playerDAO = new PlayerDAO(getConnection());
                playerDAO.update_cash(poi,500);
                GameDAO gameDAO = new GameDAO(getConnection());
                gameDAO.update_recent_card(goi,"The Federal Reserve");


            }
        });

        cardMap.put("King of the City", new OnCard()
        {
            @Override
            public void cardAction()
            {
                System.out.print("New York");
            }
        });

        cardMap.put("California Love", new OnCard()
        {
            @Override
            public void cardAction()
            {
                System.out.print("Skull Card 4 happens");

            }
        });

        cardMap.put("Truth or Consequences", new OnCard()
        {
            @Override
            public void cardAction()
            {
                // define what needs to happen in the sequel database
                System.out.print("Skull Card 4 happens");

            }
        });

        cardMap.put("Cringe", new OnCard()
        {
            @Override
            public void cardAction()
            {
                // define what needs to happen in the sequel database
                System.out.print("Cringe");
            }
        });


        cardMap.put("Get Out of Jail Free", new OnCard()
        {
            @Override
            public void cardAction()
            {
                // go space no longer grants money
                // need to include some sort of toggle option in the special spaces.
                System.out.print("Skull Card 4 happens");

            }
        });

        cardMap.put("Reverse", new OnCard()
        {
            @Override
            public void cardAction()
            {
                PlayerDAO playerDAO = new PlayerDAO(getConnection());

                String curr_dir = poi.getCurrentDirection();
                if(curr_dir.equals("LEFT"))
                {
                    playerDAO.update_direction(poi,"RIGHT");
                }
                else
                {
                    playerDAO.update_direction(poi,"LEFT");
                }
                // we never defined what this is

                GameDAO gameDAO = new GameDAO(getConnection());
                gameDAO.update_recent_card(goi,"Reverse");

            }
        });

        cardMap.put("Debt Collector", new OnCard()
        {
            @Override
            public void cardAction()
            {
                // define what needs to happen in the sequel database
                System.out.print("Skull Card 2 happens");
                // GameDAO gameDAO = new GameDAO(getConnection());
                PlayerDAO playerDAO = new PlayerDAO(getConnection());
                int pot = goi.getDebtPot();
                playerDAO.update_cash(poi,pot);
                GameDAO gameDAO = new GameDAO(getConnection());
                gameDAO.update_recent_card(goi,"Debt Collector");
            }
        });

        cardMap.put("Surprise Party", new OnCard()
        {
            @Override
            public void cardAction()
            {
                // define what needs to happen in the sequel database
                System.out.print("Skull Card 3 happens");
                String cardName = (String) specialPB.next();
                cardMap.get(cardName).cardAction();
                GameDAO gameDAO = new GameDAO(getConnection());
                gameDAO.update_recent_card(goi,"Surprise Party");

            }
        });

        cardMap.put("Run it back", new OnCard()
        {
            @Override
            public void cardAction()
            {
                // define what needs to happen in the sequel database
                System.out.print("Skull Card 4 happens");

            }
        });

        cardMap.put("Lottery", new OnCard()
        {
            @Override
            public void cardAction()
            {
                // define what needs to happen in the sequel database
                System.out.print("Skull Card 4 happens");
                PlayerDAO playerDAO = new PlayerDAO(getConnection());
                playerDAO.update_cash(poi,100000);
                GameDAO gameDAO = new GameDAO(getConnection());
                gameDAO.update_recent_card(goi,"Lottery");


            }
        });

        cardMap.put("Vaycay", new OnCard()
        {
            @Override
            public void cardAction()
            {
                // define what needs to happen in the sequel database
                System.out.print("Skull Card 4 happens");

            }
        });


        cardMap.put("Housing Lottery", new OnCard()
        {
            @Override
            public void cardAction()
            {
                // define what needs to happen in the sequel database
                System.out.print("Skull Card 4 happens");

            }
        });

        cardMap.put("GO TO", new OnCard()
        {
            @Override
            public void cardAction()
            {
                // define what needs to happen in the sequel database
                System.out.print("Skull Card 4 happens");
            }
        });

        cardMap.put("Mercy", new OnCard()
        {
            @Override
            public void cardAction()
            {
                PlayerUtil[] players = new PlayerUtil[10];
                // define what needs to happen in the sequel database
                System.out.print("Skull Card 4 happens");
                PlayerDAO playerDAO = new PlayerDAO(getConnection());
                players = playerDAO.findByGameId(poi);
                for(int i=0; i<10; i++) {
                    playerDAO.update_cash(players[i],1000);
                }
                GameDAO gameDAO = new GameDAO(getConnection());
                gameDAO.update_recent_card(goi,"Mercy");

            }
        });

        cardMap.put("Skull Town", new OnCard()
        {
            @Override
            public void cardAction()
            {
                // define what needs to happen in the sequel database
                System.out.print("Skull Card 4 happens");

            }
        });


        cardMap.put("FREE AS AIR AND WATER", new OnCard()
        {
            @Override
            public void cardAction()
            {
                // define what needs to happen in the sequel database
                System.out.print("Skull Card 4 happens");

            }
        });

        cardMap.put("Among US", new OnCard()
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
    public void onUse() {

        GameDAO gameDAO = new GameDAO(getConnection());
        PlayerDAO playerDAO = new PlayerDAO(getConnection());
        OwnedPropertyDAO ownedPropertyDAO = new OwnedPropertyDAO(getConnection());


        // investigate whether I use user or game Id to find owned property (conflicting statements in the DAO)
        poi.setUserId(getPoi_userId());
        poi.setGameId(getPoi_gameId());
        goi.setGameCode(getGamecode());
        proi.setUserId(getPoi_userId());
        proi.setGameId(getPoi_gameId());
        goi = gameDAO.findById(goi);
        poi = playerDAO.findById(poi);
        proi = ownedPropertyDAO.findById(proi);
        System.out.println(goi);
        System.out.println(poi);
        String cardName = (String) specialPB.next();
        cardMap.get(cardName).cardAction();
    }

    @Override
    public void isOccupied(boolean occupied, int userId, int gameId, String gamecode,  Connection connection)
    {
        setConnection(connection);
        setGamecode(gamecode);
        setPoi_gameId(gameId);
        setPoi_userId(userId);
        onUse();
    }
}
