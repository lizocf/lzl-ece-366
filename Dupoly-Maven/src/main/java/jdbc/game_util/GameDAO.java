package jdbc.game_util;
import jdbc.jdbc_util.DataAccessObject;
import jdbc.player_util.PlayerUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class GameDAO extends DataAccessObject<GameUtil>
{

    public GameDAO(Connection connection) {
        super(connection);
    }

    // TODO: add purchaseable boolean to findById
    private static final String GET_ONE = "SELECT game_id, game_code,  debt_pot, roll_number, purchaseable, which_player_turn, num_turns, recent_card, host, last_player " +
            " FROM game_meta WHERE game_code=?";

    private static final String INSERT = "INSERT INTO game_meta (game_code) " +
            " VALUES (?)";

    private static final String UPDATE = "UPDATE game_meta " + "SET ? = ? " + " WHERE ? ";
    private static final String UPDATE_DEBT = "UPDATE game_meta " + "SET debt_pot = ? WHERE game_code = ? ";
    private static final String UPDATE_JOIN = "UPDATE game_meta " + "SET joinable = ? WHERE game_code = ? ";
    private static final String UPDATE_TURN = "UPDATE game_meta " + "SET which_player_turn = ?  WHERE game_code = ? ";
    private static final String UPDATE_NUM_TURNS = "UPDATE game_meta " + "SET num_turns = ?  WHERE game_code = ? ";
    private static final String UPDATE_ROLL = "UPDATE game_meta " + "SET roll_number = ?  WHERE game_code = ? ";
    private static final String UPDATE_PURCHASEABLE = "UPDATE game_meta " + "SET purchaseable = ? WHERE game_code = ? ";
    private static final String UPDATE_RECENT_CARD = "UPDATE game_meta " + "SET recent_card = ? WHERE game_code = ? ";
    private static final String UPDATE_HOST = "UPDATE game_meta " + "SET host = ? WHERE game_code = ? ";
    private static final String UPDATE_LAST_PLAYER = "UPDATE game_meta " + "SET last_player = ? WHERE game_code = ? ";
    private static final String UPDATE_NUM_PLAYERS = "UPDATE game_meta " + "SET num_players = ? WHERE game_code = ? ";

    // private static final String DELETE = "DELETE FROM game_meta WHERE game_id = ?";
    private static final String DELETE = "DELETE FROM game_meta WHERE game_code = ?";

    @Override
    public GameUtil findById(GameUtil dto) {
        GameUtil game = new GameUtil();
        try(PreparedStatement statement = this.connection.prepareStatement(GET_ONE);)
        {

            statement.setString(1, dto.getGameCode());
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                game.setGameId(rs.getInt("game_id"));
                game.setGameCode(rs.getString("game_code"));
                game.setNumOfPlayers(rs.getInt("num_players"));
                game.setDebtPot(rs.getInt("debt_pot"));
                game.setRecentRoll(rs.getInt("roll_number"));
                game.setPurchaseable(rs.getBoolean("purchaseable"));
                game.setPlayerTurn(rs.getInt("which_player_turn"));
                game.setNumTurns(rs.getInt("num_turns"));
                game.setRecent_card(rs.getString("recent_card"));
                game.setHost(rs.getInt("host"));
                game.setLastPlayer(rs.getInt("last_player"));
            }

        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return game;
    }



    @Override
    public GameUtil createInstance(GameUtil dto) {
        try(PreparedStatement statement = this.connection.prepareStatement(INSERT);)
        {
            //statement.setInt(1, dto.getGameId());
            statement.setString(1, dto.getGameCode());
            statement.execute();
            return this.findById(dto);

        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    //what else in a game updates besides the joinability and debtPot?
    @Override
    public void update(GameUtil dto) {
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE);)
        {
            //ResultSet rs = statement.executeQuery();
            //what about ?
            statement.setInt(1,(dto.getDebtPot() + 50)); // can I get the current value then just add 50?
            statement.setString(2,dto.getGameCode());
            statement.execute();

        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void updateHost(GameUtil dto) {
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE_HOST);)
        {
            //ResultSet rs = statement.executeQuery();
            //what about ?
            statement.setInt(1,dto.getHost());
            statement.setString(2,dto.getGameCode());
            statement.execute();

        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void updateNumofPlayers(GameUtil dto) {
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE_NUM_PLAYERS);)
        {
            //ResultSet rs = statement.executeQuery();
            //what about ?
            statement.setInt(1,dto.getNumOfPlayers()); // can I get the current value then just add 50?
            statement.setString(2,dto.getGameCode());
            statement.execute();

        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void updateLastPlayer(GameUtil dto) {
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE_LAST_PLAYER);)
        {
            //ResultSet rs = statement.executeQuery();
            //what about ?
            statement.setInt(1,dto.getLastPlayer()); // can I get the current value then just add 50?
            statement.setString(2,dto.getGameCode());
            statement.execute();

        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void update_debt_pot(GameUtil dto,boolean reset) {
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE_DEBT);)
        {
            // statement.setString(1,"debt_pot");
            if (reset)
            {
                statement.setInt(1,0);
            }
            else
            {
                statement.setInt(1,(dto.getDebtPot() + 50));
            }

            statement.setString(2,dto.getGameCode());
            statement.execute();

        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void update_joinable(GameUtil dto) {
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE_JOIN);)
        {
            statement.setBoolean(1,false);
            statement.setString(2,dto.getGameCode());
            statement.execute();

        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    // Not sure about this one neecs to be looked into
    public void updatePlayerTurn(PlayerUtil player, GameUtil dto) {
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE_TURN);)
        {
            statement.setInt(1,player.getUserId()); // this needs to be an actual player id.
            statement.setString(2,dto.getGameCode());
            statement.execute();

        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void update_num_turns(GameUtil dto) {
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE_NUM_TURNS);)
        {
            statement.setInt(1,dto.getNumTurns()); // this needs to be an actual player id.
            statement.setString(2,dto.getGameCode());
            statement.execute();

        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public void update_dice_roll(GameUtil dto) {
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE_ROLL);)
        {

            dto.setRecentRoll(dto.rollDice());

            dto.setRecentRoll(dto.getRecentRoll());
            statement.setInt(1,dto.getRecentRoll());
            statement.setString(2,dto.getGameCode());
            statement.execute();

        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public void update_purchaseable(GameUtil dto) {
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE_PURCHASEABLE);)
        {
            statement.setBoolean(1,dto.getPurchaseable());
            statement.setString(2,dto.getGameCode());
            statement.execute();

        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void update_recent_card(GameUtil dto, String card_name) {
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE_RECENT_CARD);)
        {
            statement.setString(1,card_name);
            statement.setString(2,dto.getGameCode());
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(GameUtil dto) {
        try(PreparedStatement statement = this.connection.prepareStatement(DELETE);){
            statement.setString(1,dto.getGameCode());
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

