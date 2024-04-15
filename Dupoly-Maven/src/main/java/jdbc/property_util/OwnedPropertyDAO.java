package jdbc.property_util;

import jdbc.game_util.GameUtil;
import jdbc.player_util.PlayerUtil;
import jdbc.jdbc_util.DataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OwnedPropertyDAO extends DataAccessObject<OwnedPropertyUtil>
{


    public OwnedPropertyDAO(Connection connection) {
        super(connection);
    }

    private static final String GET_ONE = "SELECT game_id, user_id, set_name, property_name, num_hotels " +
            "FROM owned_property WHERE game_id = ? AND property_name = ?";
    
    private static final String GET_ALL = "SELECT game_id, user_id, set_name, property_name, num_hotels " +
            "FROM owned_property WHERE game_id = ? AND user_id = ?";

    private static final String INSERT = "INSERT INTO owned_property(game_id, user_id, set_name, property_name)"
            + " VALUES (?,?,?,?)";

    // need this to find property/set names from current position
    private static final String GET_NAMES = "SELECT property_name, set_name FROM full_property WHERE space = ?";

    private static final String UPDATE = "UPDATE owned_property " + "SET ? = ? " + "WHERE  game_id = ? AND user_id = ? AND property_name = ?";

    private static final String DELETE = "DELETE FROM owned_property " + " WHERE (game_id, property_name) = (?,?)";

    @Override
    public OwnedPropertyUtil findById(OwnedPropertyUtil dto) {
        OwnedPropertyUtil property = new OwnedPropertyUtil();
        try(PreparedStatement statement = this.connection.prepareStatement(GET_ONE);)
        {
            statement.setInt(1, dto.getGameId());
            statement.setString(2, dto.getPropertyName());
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                property.setGameId(rs.getInt("game_id"));
                property.setUserId(rs.getInt("user_id"));
                property.setSetName(rs.getString("set_name"));
                property.setPropertyName(rs.getString("property_name"));
                property.setNumOfHotels(rs.getInt("num_hotels"));
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return property;
    }

    // for when a user buys property
    @Override
    public OwnedPropertyUtil createInstance(OwnedPropertyUtil dto) {
        try(PreparedStatement statement = this.connection.prepareStatement(INSERT);) {
            statement.setInt(1, dto.getGameId());
            statement.setInt(2, dto.getUserId());
            statement.setString(3, dto.getSetName());
            statement.setString(4,dto.getPropertyName());
            statement.execute();
            return this.findById(dto);
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    // what else would we have to update besides num of hotels. Maybe the owner?
    // do we need to return anything
    @Override
    public void update(OwnedPropertyUtil dto) {
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE);)
        {
            statement.setString(1,"");
            statement.setInt(2,(dto.getDebtPot() + 50)); // can I get the current value then just add 50?
            statement.setInt(3,dto.getGameId());
            statement.execute();

        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }


    public void updateNumHotel(OwnedPropertyUtil dto, int num_of_hotels) {
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE);)
        {
            statement.setString(1,"num_hotels");
            statement.setInt(2,num_of_hotels); // can I get the current value then just add 50?
            statement.setInt(3,dto.getGameId());
            statement.setInt(4,dto.getUserId());
            statement.setString(5,dto.getPropertyName());
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public OwnedPropertyUtil findNames(PlayerUtil player_dto) {
       OwnedPropertyUtil property = new OwnedPropertyUtil();
        try(PreparedStatement statement = this.connection.prepareStatement(GET_NAMES);)
        {
            statement.setInt(1, player_dto.getCurrentPosition());
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                property.setPropertyName(rs.getString("property_name"));
                property.setSetName(rs.getString("set_name"));
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return property;
    }

    // added for Owned Properties table
    public OwnedPropertyUtil[] findAllOwned(OwnedPropertyUtil dto) {
       OwnedPropertyUtil[] properties = new OwnedPropertyUtil[40]; // max possible properties one can own

        try(PreparedStatement statement = this.connection.prepareStatement(GET_ALL);)
        {
            statement.setInt(1, dto.getGameId());
            statement.setInt(2, dto.getUserId());
            ResultSet rs = statement.executeQuery();
            System.out.println("game_id\t\tuser_id\t\tset_name\t\tproperty_name\t\tnum_hotels");
            int i = 0;
            while(rs.next()) {
                properties[i] = new OwnedPropertyUtil();
                properties[i].setGameId(rs.getInt("game_id"));
                properties[i].setUserId(rs.getInt("user_id"));
                properties[i].setSetName(rs.getString("set_name"));
                properties[i].setPropertyName(rs.getString("property_name"));
                properties[i].setNumOfHotels(rs.getInt("num_hotels"));
                System.out.println(properties[i]);
                ++i;
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return properties;
    }

    @Override
    public void delete(OwnedPropertyUtil dto)
    {
        try(PreparedStatement statement = this.connection.prepareStatement(DELETE);)
        {
            statement.setInt(1,dto.getGameId());
            statement.setString(2,dto.getPropertyName()); // can I get the current value then just add 50?
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // transfer property--> do a find by Id. Then you just change the owner
}
