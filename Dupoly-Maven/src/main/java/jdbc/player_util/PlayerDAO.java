package jdbc.player_util;

import jdbc.game_util.GameDAO;
import jdbc.game_util.GameUtil;
import jdbc.jdbc_util.DataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.lang.Math.abs;

public class PlayerDAO extends DataAccessObject<PlayerUtil>
{
        public PlayerDAO(Connection connection) {
            super(connection);
        }

    private static final String GET_ONE = "SELECT game_id, user_id, user_name, cash, current_direction, current_position, previous_position, jail, afk, dead " +
            "FROM player_in_game WHERE game_id=? AND user_id=?";
    
    private static final String GET_GAME = "SELECT game_id, user_id, user_name, cash, current_direction, current_position, previous_position, jail, afk, dead " +
            "FROM player_in_game WHERE game_id=? ORDRE BY user_id";
    private static final String INSERT = "INSERT INTO player_in_game(game_id, user_id, user_name) SELECT ? AS game_id," + 
            "user_id, user_name FROM accounts WHERE user_id=?;";

    // private static final String UPDATE = "UPDATE player_in_game " + "SET ? = ? " + "WHERE ?";
    private static final String UPDATE_DEAD = "UPDATE player_in_game " + "SET dead = ? " + "WHERE user_id = ?";
    private static final String UPDATE_CASH = "UPDATE player_in_game " + "SET cash = ? " + "WHERE user_id = ?";
    private static final String UPDATE_DIR = "UPDATE player_in_game " + "SET current_direction = ? " + "WHERE user_id = ?";
    private static final String UPDATE_JAIL = "UPDATE player_in_game " + "SET jail = ? " + "WHERE user_id = ?";
    private static final String UPDATE_AFK = "UPDATE player_in_game " + "SET afk = ? " + "WHERE user_id = ?";
    private static final String UPDATE_POS_CUR = "UPDATE player_in_game " + "SET previous_position = current_position, current_position = ? " + "WHERE user_id = ?";



    private static final String UPDATE_POS_PREV = "UPDATE player_in_game " + "SET current_position = ? " + "WHERE user_id = ?";




    private static final String DELETE = "DELETE FROM player_in_game WHERE user_id = ?";

    @Override
    public PlayerUtil findById(PlayerUtil dto) {
        PlayerUtil player = new PlayerUtil();
        try(PreparedStatement statement = this.connection.prepareStatement(GET_ONE);)
        {
            statement.setInt(1, dto.getGameId());
            statement.setInt(2, dto.getUserId());
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                player.setGameId(rs.getInt("game_id"));
                player.setUserId(rs.getInt("user_id")); // need userId as another search parameter instead
                player.setUserName(rs.getString("user_name"));
                player.setCash(rs.getInt("cash"));
                player.setCurrentDirection(rs.getString("current_direction"));
                player.setCurrentPosition(rs.getInt("current_position"));
                player.setPreviousPosition(rs.getInt("previous_position"));
                player.setJail(rs.getBoolean("jail"));
                player.setAfk(rs.getBoolean("afk"));
                player.setDead(rs.getBoolean("dead"));
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return player;
    }

    public PlayerUtil[] findByGameId(PlayerUtil dto) {
        PlayerUtil[] players = new PlayerUtil[8];
        try(PreparedStatement statement = this.connection.prepareStatement(GET_GAME);)
        {
            statement.setInt(1, dto.getGameId());
            ResultSet rs = statement.executeQuery();
            int i = 0;
            System.out.println("game_id\t\tuser_id\t\tuser_name\t\tcash\t\tcurrent_direction\t\tcurrent_position\t\tjail\t\tafk\t\tdead");

            while(rs.next()) {
                int game_id = rs.getInt("game_id");
                int user_id = rs.getInt("user_id");
                String user_name = rs.getString("user_name");
                int cash = rs.getInt("cash");
                String cur_dir = rs.getString("current_direction");
                int cur_pos = rs.getInt("current_position");
                int prev_pos = rs.getInt("previous_position");
                boolean jail = rs.getBoolean("jail");
                boolean afk = rs.getBoolean("afk");
                boolean dead = rs.getBoolean("dead");
                System.out.println(game_id + "\t\t" + user_id + "\t\t" + user_name + "\t\t" + cash + 
                                    "\t\t" + cur_dir + "\t\t" + cur_pos + "\t\t" + jail + "\t\t" + afk + "\t\t" + dead);
                                   
                players[i] = new PlayerUtil();
                players[i].setGameId(game_id);
                players[i].setUserId(user_id);
                players[i].setUserName(user_name);
                players[i].setCash(cash);
                players[i].setCurrentDirection(cur_dir);
                players[i].setCurrentPosition(cur_pos);
                players[i].setPreviousPosition(prev_pos);
                players[i].setJail(jail);
                players[i].setAfk(afk);
                players[i].setDead(dead);
                ++i;
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        
        return players;
    }

    @Override
    public PlayerUtil createInstance(PlayerUtil dto) {
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
    public void update(PlayerUtil dto) // updateDead
    {
         try(PreparedStatement statement = this.connection.prepareStatement(UPDATE_DEAD);)
        {
            // statement.setString(1,"dead");
            statement.setBoolean(1, 1==1);
            statement.setInt(2,dto.getUserId());
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void update_dead(PlayerUtil dto) // updateDead
    {
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE_DEAD);)
        {
            // statement.setString(1,"dead");
            statement.setBoolean(1, true);
            statement.setInt(2,dto.getUserId());
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void update_cash(PlayerUtil dto, int newAmount) 
    {
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE_CASH);)
        {
            // statement.setString(1,"cash");
            statement.setInt(1,(dto.getCash() + newAmount)); // can I get the current value then just add 50?
            // statement.setInt(3,dto.getGameId());
            statement.setInt(2,dto.getUserId());
            statement.execute();

        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void update_direction(PlayerUtil dto, String direction)
    {
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE_DIR);)
        {
            // statement.setString(1,"current_direction");
            statement.setString(1, direction);
            // statement.setInt(3,dto.getGameId());
            statement.setInt(2,dto.getUserId());
            statement.execute();

        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void update_jail(PlayerUtil dto, boolean yes)
    {
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE_JAIL);)
        {
            // statement.setString(1,"jail");
            statement.setBoolean(1, yes);
            // statement.setInt(3,dto.getGameId());
            statement.setInt(2,dto.getUserId());
            statement.execute();

        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void update_afk(PlayerUtil dto, boolean yes)
    {
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE_AFK);)
        {
            // statement.setString(1,"afk");
            statement.setBoolean(1, yes);
            // statement.setInt(3,dto.getGameId());
            statement.setInt(2,dto.getUserId());
            statement.execute();

        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public boolean passedGo(int prev_pos, int curr_pos, String curr_dir)
    {
        if(curr_dir.equals("right"))
        {
            if(((48<=prev_pos) && (prev_pos<=59)) && ((1<=curr_pos) && (curr_pos<=11)))
            {
                return true;

            }
        }
        else
        {
            if(((48<=curr_pos) && (curr_pos<=59)) && ((1<=prev_pos) && (prev_pos<=11)))
            {
                return true;
            }
        }
        return false;
    }

    public boolean passedDebtPot(int prev_pos, int curr_pos, String curr_dir)
    {
        if(curr_dir.equals("right"))
        {
            if( ((prev_pos==59) || ((0<=prev_pos) && (prev_pos<=9)) ) && ((11<=curr_pos) && (curr_pos<=23)))
            {
                return true;
            }
        }
        else
        {
            if(((curr_pos==59) || ((0<=curr_pos) && (curr_pos<=9)) ) && ((11<=prev_pos) && (prev_pos<=23)))
            {
                return true;
            }
        }
        return false;
    }
    public void update_position(PlayerUtil dto, GameUtil game_dto) // update_position
    {
         try(PreparedStatement statement = this.connection.prepareStatement(UPDATE_POS_CUR);)
        {
            // statement.setString(1,"current_position");
            dto.setPreviousPosition(dto.getCurrentPosition());
            if(dto.getCurrentDirection().equals("right"))  // Check if its suppose to be right or left lol
            {

                int new_pos = ((dto.getCurrentPosition() + game_dto.getRecentRoll()) % 60);
                dto.setCurrentPosition(new_pos);


            }
            else
            {
                if (dto.getCurrentPosition() > game_dto.getRecentRoll()) {
                    dto.setCurrentPosition(dto.getCurrentPosition() - game_dto.getRecentRoll());
                }
                else
                {
                    dto.setCurrentPosition(60 - abs(game_dto.getRecentRoll() - dto.getCurrentPosition()));
                }
                // currPlayerTurn.currSpace = currPlayerTurn.currSpace > diceroll ? (currPlayerTurn.currSpace-diceroll) : (60 - abs(diceroll - currPlayerTurn.currSpace));

            }
            statement.setInt(1, dto.getCurrentPosition());
            statement.setInt(2,dto.getUserId());
            statement.execute();

            if(passedGo(dto.getPreviousPosition(),dto.getCurrentPosition(),dto.getCurrentDirection()))
            {
                update_cash(dto,200);
                System.out.println("UPDATE CASH");
            }


        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void delete(PlayerUtil dto)
    {
        try(PreparedStatement statement = this.connection.prepareStatement(DELETE);)
        {
            statement.setInt(1,dto.getUserId());
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
