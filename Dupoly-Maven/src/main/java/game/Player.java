package game;
import com.fasterxml.jackson.core.JsonProcessingException;
import jdbc.DatabaseConnectionManager;
import jdbc.game_util.GameDAO;
import jdbc.game_util.GameUtil;
import jdbc.player_util.PlayerDAO;
import jdbc.player_util.PlayerUtil;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import static java.lang.Math.abs;

@SpringBootApplication
@RestController
@CrossOrigin
public class Player extends Account {
    public int game_id, cash, currSpace;
    public boolean afk, current_direction;

    private int passedSpace(int prev_pos, int curr_pos, String curr_dir)
    {

        // so there are two spaces  in this game that if you pass buy they can owe you money
        // these are debt Pot and GO

        // so we need to check if prev and current are in between one of these spaces

        if(curr_dir.equals("left"))
        {
            if((prev_pos>10) && (10>curr_pos))
            {

                return 1;



            }

            if((prev_pos<0) && (0<curr_pos))
            {
                return 2;

            }

        }
        else
        {
            if((prev_pos<10) && (10<curr_pos))
            {

                return 1;

            }

            if((prev_pos>10) && (10>curr_pos))
            {
                return 2;

            }
        }
        return 0;
    }



    @GetMapping("/playerTest")
    public String playerTest()
    {
        System.out.println("PLAYER TESTING 123");
        return "Player Springboot!";
    }



    @GetMapping("/getPlayerInGame/{gameId}/{userId}")
    public PlayerUtil getPlayerInGame(@PathVariable("gameId") int gameId,
                                      @PathVariable("userId") int userId) {
        System.out.println(gameId);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
                "duopoly", "postgres", "password");
        PlayerUtil player = new PlayerUtil();
        player.setGameId(gameId);
        player.setUserId(userId);
        try {
            Connection connection = dcm.getConnection();
            PlayerDAO playerDAO = new PlayerDAO(connection);

            player = playerDAO.findById(player);
            System.out.println(player);
        }
        catch(SQLException e) {
            e.printStackTrace();
            
        }
        return player;
    }

    @PostMapping("/createPlayerInGame")
    public PlayerUtil createPlayerInGame(@RequestBody String json) throws JsonProcessingException
    {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> inputMap = objectMapper.readValue(json, Map.class);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
                "duopoly", "postgres", "password");
        PlayerUtil newPlayer = new PlayerUtil();

        try {
            Connection connection = dcm.getConnection();
            PlayerDAO playerDAO = new PlayerDAO(connection);
            //
            newPlayer.setUserId(Integer.valueOf(inputMap.get("user_id")));
            newPlayer.setGameId(Integer.valueOf(inputMap.get("game_id")));
            // The next 2 lines cause build error
            //player1.setUserId(inputMap.get("user_id"));
            //player1.setGameId(inputMap.get("game_id"));

            newPlayer = playerDAO.createInstance(newPlayer);
            System.out.println(newPlayer);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return newPlayer;
    }

    @PostMapping("/updateDead")
    public PlayerUtil updateDead(@RequestBody String json) throws JsonProcessingException
    {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map <String, String> inputMap = objectMapper.readValue(json, Map.class);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
                "duopoly", "postgres", "password");
        PlayerUtil player = new PlayerUtil();

        try {
            Connection connection = dcm.getConnection();
            PlayerDAO playerDAO = new PlayerDAO(connection);
            player.setUserId(Integer.valueOf(inputMap.get("user_id")));
            player.setGameId(Integer.valueOf(inputMap.get("game_id")));
            player = playerDAO.findById(player);
            playerDAO.update(player);
            System.out.println(player);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return player;
    }

    @PostMapping("/updateCash")
    public PlayerUtil updateCash(@RequestBody String json) throws JsonProcessingException
    {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map <String, String> inputMap = objectMapper.readValue(json, Map.class);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
                "duopoly", "postgres", "password");
        PlayerUtil player = new PlayerUtil();

        try {
            Connection connection = dcm.getConnection();
            PlayerDAO playerDAO = new PlayerDAO(connection);
            player.setUserId(Integer.valueOf(inputMap.get("user_id")));
            player.setGameId(Integer.valueOf(inputMap.get("game_id")));
            player = playerDAO.findById(player);
            playerDAO.update_cash(player, Integer.valueOf(inputMap.get("profit")));
            System.out.println(player);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return player;
    }

    @PostMapping("/updateJail")
    public PlayerUtil updateJail(@RequestBody String json) throws JsonProcessingException
    {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map <String, String> inputMap = objectMapper.readValue(json, Map.class);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
                "duopoly", "postgres", "password");
        PlayerUtil player = new PlayerUtil();

        try {
            Connection connection = dcm.getConnection();
            PlayerDAO playerDAO = new PlayerDAO(connection);
            player.setUserId(Integer.valueOf(inputMap.get("user_id")));
            player.setGameId(Integer.valueOf(inputMap.get("game_id")));
            player = playerDAO.findById(player);
            playerDAO.update_jail(player, Boolean.valueOf(inputMap.get("jail")));
            System.out.println(player);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return player;
    }

    @PostMapping("/updateAfk")
    public PlayerUtil updateAfk(@RequestBody String json) throws JsonProcessingException
    {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map <String, String> inputMap = objectMapper.readValue(json, Map.class);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
                "duopoly", "postgres", "password");
        PlayerUtil player = new PlayerUtil();

        try {
            Connection connection = dcm.getConnection();
            PlayerDAO playerDAO = new PlayerDAO(connection);
            player.setUserId(Integer.valueOf(inputMap.get("user_id")));
            player.setGameId(Integer.valueOf(inputMap.get("game_id")));
            player = playerDAO.findById(player);
            playerDAO.update_afk(player, Boolean.valueOf(inputMap.get("afk")));
            System.out.println(player);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return player;
    }

    @PostMapping("/updatePos")
    public PlayerUtil updatePos(@RequestBody String json) throws JsonProcessingException
    {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map <String, String> inputMap = objectMapper.readValue(json, Map.class);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
                "duopoly", "postgres", "password");
        PlayerUtil player = new PlayerUtil();
        GameUtil game = new GameUtil();

        try {
            Connection connection = dcm.getConnection();
            PlayerDAO playerDAO = new PlayerDAO(connection);
            GameDAO gameDAO = new GameDAO(connection);
            game.setGameCode((inputMap.get("game_code")));

            player.setUserId(Integer.valueOf(inputMap.get("user_id")));
            player.setGameId(Integer.valueOf(inputMap.get("game_id")));
            player = playerDAO.findById(player);
            game = gameDAO.findById(game);


            // needs to take in something extra, maybe a game DAO
            // can change the id to the code but is that the best way to proceed
            // why is it by code and not by id?
            playerDAO.update_position(player, game);

            boolean p = playerDAO.passedDebtPot(player.getPreviousPosition(),player.getCurrentPosition(),player.getCurrentDirection());

            if(p)
            {
                gameDAO.update_debt_pot(game,false);
            }





            System.out.println(player);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return player;
    }

    @PostMapping("/updateDir")
    public PlayerUtil updateDir(@RequestBody String json) throws JsonProcessingException
    {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map <String, String> inputMap = objectMapper.readValue(json, Map.class);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
                "duopoly", "postgres", "password");
        PlayerUtil player = new PlayerUtil();

        try {
            Connection connection = dcm.getConnection();
            PlayerDAO playerDAO = new PlayerDAO(connection);
            player.setUserId(Integer.valueOf(inputMap.get("user_id")));
            player.setGameId(Integer.valueOf(inputMap.get("game_id")));
            player = playerDAO.findById(player);
            playerDAO.update_direction(player, inputMap.get("direction"));
            System.out.println(player);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return player;
    }

    @GetMapping("/getAllPlayersInGame/{gameId}")
    public PlayerUtil[] getAllPlayersInGame(@PathVariable("gameId") int gameId) {
        System.out.println(gameId);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
                "duopoly", "postgres", "password");
        PlayerUtil player1 = new PlayerUtil();
        PlayerUtil[] players = new PlayerUtil[10];
        player1.setGameId(gameId);
        try {
            Connection connection = dcm.getConnection();
            PlayerDAO playerDAO = new PlayerDAO(connection);

            players = playerDAO.findByGameId(player1);
            System.out.println(players);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return players;
    }

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash)
    {
        this.cash = cash;
    }

    public int getCurrSpace() {
        return currSpace;
    }

    public void setCurrSpace(int currSpace) {
        this.currSpace = currSpace;
    }

    public boolean isAfk() {
        return afk;
    }

    public void setAfk(boolean afk) {
        this.afk = afk;
    }

    public boolean isCurrent_direction() {
        return current_direction;
    }

    public void setCurrent_direction(boolean current_direction) {
        this.current_direction = current_direction;
    }


    //    public Player(int user_id, int num_wins, int num_losses, int elo_rating, int duo_points, String[] user_name, String[] password) {
//        super(user_id, num_wins, num_losses, elo_rating, duo_points, user_name, password);
//    }

    // setters and getters
}