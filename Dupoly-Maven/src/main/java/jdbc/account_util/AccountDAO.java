package jdbc.account_util;

import jdbc.jdbc_util.DataAccessObject;
import jdbc.player_util.PlayerUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO extends DataAccessObject<AccountUtil>
{
    public AccountDAO(Connection connection){
        super(connection);
    }

    private static final String GET_ONE = "SELECT user_id, user_name, user_pw, token, num_wins, num_losses, elo_rating, duo_points " +
                                          "FROM accounts WHERE user_name=(?)";

    private static final String GET_TOKEN = "SELECT user_id, user_name, user_pw, token, num_wins, num_losses, elo_rating, duo_points " +
                                          "FROM accounts WHERE token=(?)";


    private static final String INSERT = "INSERT INTO accounts (user_name, user_pw) VALUES (?, ?);"; // need to add user_pw

    // private static final String UPDATE = "UPDATE accounts SET ?=? WHERE user_id=?";
    private static final String UPDATE_WINS = "UPDATE accounts SET num_wins=num_wins+1 WHERE user_id=?";
    private static final String UPDATE_LOSS = "UPDATE accounts SET num_losses=num_losses+1 WHERE user_id=?";
    private static final String UPDATE_DP = "UPDATE accounts SET duo_points=? WHERE user_id=?";
    private static final String UPDATE_ELO = "UPDATE accounts SET elo_rating=? WHERE user_id=?";
    private static final String UPDATE_TOKEN = "UPDATE accounts SET token=? WHERE user_id=?"; 
    
    private static final String DELETE = "DELETE FROM accounts WHERE user_id = (?)";

    @Override
    public AccountUtil findById(AccountUtil dto) {
        AccountUtil account = new AccountUtil();
        try(PreparedStatement statement = this.connection.prepareStatement(GET_ONE);)
        {
            // statement.setInt(1, dto.getUserId());
            statement.setString(1, dto.getUserName());
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                account.setUserId(rs.getInt("user_id")); // need id to interface with accounts !!!!
                account.setUserName(rs.getString("user_name"));
                account.setUserPW(rs.getString("user_pw"));
                account.setToken(rs.getString("token"));
                account.setNumWins(rs.getInt("num_wins"));
                account.setNumLosses(rs.getInt("num_losses"));
                account.setEloRating(rs.getString("elo_rating")); // fixed
                account.setDuoPoints(rs.getInt("duo_points"));
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return account;
    }

    public AccountUtil findByToken(AccountUtil dto) {
        AccountUtil account = new AccountUtil();
        try(PreparedStatement statement = this.connection.prepareStatement(GET_TOKEN);)
        {
            // statement.setInt(1, dto.getUserId());
            statement.setString(1, dto.getToken());
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                account.setUserId(rs.getInt("user_id")); // need id to interface with accounts !!!!
                account.setUserName(rs.getString("user_name"));
                account.setUserPW(rs.getString("user_pw"));
                account.setToken(rs.getString("token"));
                account.setNumWins(rs.getInt("num_wins"));
                account.setNumLosses(rs.getInt("num_losses"));
                account.setEloRating(rs.getString("elo_rating")); // fixed
                account.setDuoPoints(rs.getInt("duo_points"));
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return account;
    }
    

    @Override
    public AccountUtil createInstance(AccountUtil dto) {
        try(PreparedStatement statement = this.connection.prepareStatement(INSERT);) {
            statement.setString(1, dto.getUserName());
            statement.setString(2, dto.getUserPW());
            statement.execute();
            return this.findById(dto);    // need user_id sequence
        } catch(SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void createToken(AccountUtil dto) {
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE_TOKEN);) {
            statement.setString(1, dto.getToken());
            statement.setInt(2, dto.getUserId());
            statement.execute();
            // return this.findById(dto);    // need user_id sequence
        } catch(SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    @Override
    public void update(AccountUtil dto) { // update_wins
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE_WINS);)
            {
                statement.setInt(1, dto.getUserId());
                statement.execute();
            } catch (SQLException e){
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

    public void update_loss(AccountUtil dto) { // update_loss
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE_LOSS);)
            {
                statement.setInt(1, dto.getUserId());
                statement.execute();
            } catch (SQLException e){
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

    public void update_dp(AccountUtil dto, int dp) { // update_dp
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE_DP);)
            {
                statement.setInt(1, dp); // variable dp input
                statement.setInt(2, dto.getUserId());
                statement.execute();
            } catch (SQLException e){
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

    public void update_elo(AccountUtil dto, String elo) { // update_elo
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE_ELO);)
            {
                statement.setString(1, elo); // variable elo input
                statement.setInt(2, dto.getUserId());
                statement.execute();
            } catch (SQLException e){
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

    @Override
    public void  delete(AccountUtil dto) { // delete account from table
        try(PreparedStatement statement = this.connection.prepareStatement(DELETE);)
            {
                statement.setInt(1, dto.getUserId());
                statement.execute();
            } catch (SQLException e){
                e.printStackTrace();
                throw new RuntimeException(e);
            }
    }
}

