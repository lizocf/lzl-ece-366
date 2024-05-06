package jdbc;

import game.Player;
import jdbc.game_util.GameDAO;
import jdbc.game_util.GameUtil;
import jdbc.player_util.PlayerDAO;
import jdbc.player_util.PlayerUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



@SpringBootTest(classes = Player.class)
public class PlayerDAOTest
{
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet rs;

    private PlayerDAO playerDAO;
    private PlayerUtil playerUtil;
    private GameDAO gameDAO;
    private GameUtil gameUtil;

    @BeforeEach
    void setUp() throws SQLException {

        // Mock the Connection and PreparedStatement objects
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        rs = mock(ResultSet.class);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(rs);
        when(rs.getString(anyString())).thenReturn("test");
        when(rs.getInt(anyString())).thenReturn(1);


        playerDAO = new PlayerDAO(connection);
        playerUtil = new PlayerUtil();

        gameDAO = new GameDAO(connection);
        gameUtil = new GameUtil();

        playerUtil.setUserId(1);
        playerUtil.setCash(1);
        playerUtil.setCurrentPosition(1);
        playerUtil.setCurrentDirection("test");

        gameUtil.setGameCode("test");
        gameUtil.setDebtPot(1);
        gameUtil.setGameId(1);
        gameUtil.setRecent_card("test");
        gameUtil.setRecentRoll(1);


    }

    @Test
    void TestFindById() throws SQLException {
        when(rs.next()).thenReturn(true,true,true,true,true,true,true,true,true,false);

        playerDAO.findById(playerUtil);
        verify(preparedStatement).setInt(2,1);
        verify(preparedStatement).executeQuery();
        verify(rs,times(10)).next();
        assertEquals(playerUtil.getGameId(),0);

    }

    @Test
    void TestFindByUserId() throws SQLException {
        playerDAO.findByUserId(playerUtil);

        verify(preparedStatement).setInt(1,1);
        verify(preparedStatement).executeQuery();
        verify(rs,times(1)).next();
    }

    @Test
    void TestFindByGameId() throws SQLException {
        playerDAO.findByGameId(playerUtil);

        verify(preparedStatement).setInt(1,0);
        verify(preparedStatement).executeQuery();
        verify(rs,times(1)).next();

    }

    @Test
    void TestCreateInstance() throws SQLException {
        playerDAO.createInstance(playerUtil);

        verify(preparedStatement).setInt(1,0);
        verify(preparedStatement).setInt(2,1);
        verify(preparedStatement).execute();
    }

    @Test
    void testUpdateCash() throws SQLException
    {
        playerDAO.update_cash(playerUtil,100);
        verify(preparedStatement).setInt(1, 101);
    }

    @Test
    void testUpdatePositonLeft() throws SQLException
    {
        playerDAO.update_position(playerUtil,gameUtil);
        assertEquals(60,playerUtil.getCurrentPosition());

    }

    @Test
    void testUpdatePositonRight() throws SQLException
    {
        playerUtil.setCurrentDirection("right");
        playerDAO.update_position(playerUtil,gameUtil);
        assertEquals(2,playerUtil.getCurrentPosition());
    }

    @Test
    void testUpdateAFK() throws SQLException
    {
        playerDAO.update_afk(playerUtil,true);
        verify(preparedStatement).setBoolean(1, true);
    }













}
