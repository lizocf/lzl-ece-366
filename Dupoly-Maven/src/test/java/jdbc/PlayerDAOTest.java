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
import java.sql.SQLException;



@SpringBootTest(classes = Player.class)
public class PlayerDAOTest
{
    private Connection connection;
    private PreparedStatement preparedStatement;
    private PlayerDAO playerDAO;
    private PlayerUtil playerUtil;
    private GameDAO gameDAO;
    private GameUtil gameUtil;

    @BeforeEach
    void setUp() throws SQLException {

        // Mock the Connection and PreparedStatement objects
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        playerDAO = new PlayerDAO(connection);
        playerUtil = new PlayerUtil();

        gameDAO = new GameDAO(connection);
        gameUtil = new GameUtil();

        playerUtil.setUserId(1);
        playerUtil.setCash(1000);
        playerUtil.setCurrentPosition(1);
        playerUtil.setCurrentDirection("LEFT");

        gameUtil.setGameCode("AS123");
        gameUtil.setDebtPot(100);
        gameUtil.setGameId(1);
        gameUtil.setRecent_card("Bankrupt");
        gameUtil.setRecentRoll(4);


    }

    @Test
    void testUpdateCash() throws SQLException
    {
        playerDAO.update_cash(playerUtil,100);
        verify(preparedStatement).setInt(1, 1100);
    }

    @Test
    void testUpdatePositonLeft() throws SQLException
    {
        playerDAO.update_position(playerUtil,gameUtil);
        assertEquals(57,playerUtil.getCurrentPosition());

    }

    @Test
    void testUpdatePositonRight() throws SQLException
    {
        playerUtil.setCurrentDirection("right");
        playerDAO.update_position(playerUtil,gameUtil);
        assertEquals(5,playerUtil.getCurrentPosition());
    }

    @Test
    void testUpdateAFK() throws SQLException
    {
        playerDAO.update_afk(playerUtil,true);
        verify(preparedStatement).setBoolean(1, true);
    }













}
