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

    private static final String GET_ONE = "SELECT game_id, game_code,debt_pot " +
            " FROM game_meta WHERE game_code=?";

    private static final String INSERT = "INSERT INTO game_meta (game_code) " +
            " VALUES (?)";

    private static final String UPDATE = "UPDATE game_meta " + "SET ? = ? " + " WHERE ? ";
    private static final String UPDATE_DEBT = "UPDATE game_meta " + "SET debt_pot = ? WHERE game_code = ? ";
    private static final String UPDATE_JOIN = "UPDATE game_meta " + "SET joinable = ? WHERE game_code = ? ";
    private static final String UPDATE_TURN = "UPDATE game_meta " + "SET which_player_turn = ?  WHERE game_code = ? ";
    private static final String UPDATE_ROLL = "UPDATE game_meta " + "SET roll_number = ?  WHERE game_code = ? ";


    private static final String DELETE = "DELETE FROM game_meta WHERE game_id = ?";

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
//                game.setNumOfPlayers(rs.getInt("num_players"));
                game.setDebtPot(rs.getInt("debt_pot"));
//                game.setPlayerTurn(rs.getInt("which_player_turn"));
//                game.setJoinable(rs.getBoolean("joinable"));
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
            statement.setInt(2,dto.getGameId());
            statement.execute();

        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public void update_debt_pot(GameUtil dto) {
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE_DEBT);)
        {
            // statement.setString(1,"debt_pot");
            statement.setInt(1,(dto.getDebtPot() + 50)); // can I get the current value then just add 50?
            statement.setInt(2,dto.getGameId());
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
            statement.setInt(2,dto.getGameId());
            statement.execute();

        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Not sure about this one neecs to be looked into
    public void updatePlayerTurn(GameUtil dto) {
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE_TURN);)
        {
            statement.setInt(1,dto.getId()); // this needs to be an actual player id.
            statement.setInt(2,dto.getGameId());
            statement.execute();

        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void update_dice_roll(GameUtil dto) {
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE_ROLL);)
        {
            statement.setInt(1,dto.rollDice());
            statement.setString(2,dto.getGameCode());
            statement.execute();

        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // idk what to return here. Maybe it should be void.
    @Override
    public void delete(GameUtil dto) {
        try(PreparedStatement statement = this.connection.prepareStatement(DELETE);){
            statement.setInt(1,dto.getGameId());
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

