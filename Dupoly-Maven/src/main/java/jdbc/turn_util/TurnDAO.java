package jdbc.turn_util;

import jdbc.game_util.GameDAO;
import jdbc.game_util.GameUtil;
import jdbc.jdbc_util.DataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.lang.Math.abs;

public class TurnDAO extends DataAccessObject<TurnUtil>
{
        public TurnDAO(Connection connection) {
            super(connection);
        }

    private static final String GET_ONE = "SELECT id, game_id, user_id FROM turn_orders WHERE game_id=? AND user_id=?;";
    private static final String GET_ORDER = "SELECT user_id FROM turn_orders WHERE game_id=? ORDER BY id;";
    private static final String INSERT = "INSERT INTO turn_orders (id, game_id, user_id) ? AS game_id, user_id FROM accounts WHERE user_id=?;";
    private static final String UPDATE_TURN = "UPDATE turn_orders SET user_id = ARRAY_APPEND(user_id[2:], user_id[1]) WHERE game_id = ?";
    private static final String DELETE = "DELETE FROM turn_orders WHERE game_id = ?";
    private static final String DELETE_USER = "UPDATE turn_orders SET user_id = ARRAY_REMOVE(user_id, ?) WHERE game_id = ?";

    @Override
    public TurnUtil findById(TurnUtil dto) {
        TurnUtil turn = new TurnUtil();
        try(PreparedStatement statement = this.connection.prepareStatement(GET_ONE);)
        {
            statement.setInt(1, dto.getGameId());
            statement.setInt(2, dto.getUserId());
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                turn.setTurnNumber(rs.getInt("id"));
                turn.setGameId(rs.getInt("game_id")); // need userId as another search parameter instead
                turn.setUserId(rs.getInt("user_id"));
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return turn;
    }

    public TurnUtil[] findByGameId(TurnUtil dto) {
        TurnUtil[] order = new TurnUtil[8];
        try(PreparedStatement statement = this.connection.prepareStatement(GET_ORDER);)
        {
            statement.setInt(1, dto.getGameId());
            ResultSet rs = statement.executeQuery();
            int i = 0;
            System.out.println("turn_order\t\tgame_id\t\tuser_id");

            while(rs.next()) {
                int id = rs.getInt("id");
                int game_id = rs.getInt("game_id");
                int user_id = rs.getInt("user_id");
                System.out.println(id + "\t\t" + game_id + "\t\t" + user_id);
                                   
                order[i] = new TurnUtil();
                order[i].setTurnNumber(id);
                order[i].setGameId(game_id);
                order[i].setUserId(user_id);
                ++i;
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        
        return order;
    }

    @Override
    public TurnUtil createInstance(TurnUtil dto) {
        try(PreparedStatement statement = this.connection.prepareStatement(INSERT);)
        {
            statement.setInt(1, dto.getGameId());
            statement.setInt(2, dto.getUserId());
            statement.execute();
            return this.findById(dto);
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(TurnUtil dto) // updateDead
    {
         try(PreparedStatement statement = this.connection.prepareStatement(UPDATE_TURN);)
        {
            // statement.setString(1,"dead");
            statement.setInt(1, dto.getUserId());
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(TurnUtil dto)
    {
        try(PreparedStatement statement = this.connection.prepareStatement(DELETE);)
        {
            statement.setInt(1,dto.getGameId());
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

//    public void delete(TurnUtil dto)
//    {
//        try(PreparedStatement statement = this.connection.prepareStatement(DELETE);)
//        {
//            statement.setInt(1,dto.getGameId());
//            statement.execute();
//        }catch (SQLException e){
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }
}
