package jdbc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jdbc.game_util.GameDAO;
import jdbc.game_util.GameUtil;
import jdbc.player_util.PlayerUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameDAOTest {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private GameDAO gameDAO;
    private GameUtil gameUtil;
    private PlayerUtil playerUtil;

    @BeforeEach
    void setUp() throws SQLException {
        // Mock the Connection and PreparedStatement objects
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        gameDAO = new GameDAO(connection);
        gameUtil = new GameUtil();

        gameUtil.setGameCode("AS123");
        gameUtil.setDebtPot(100);
        gameUtil.setGameId(1);
        gameUtil.setRecent_card("Bankrupt");

    }

    @Test
    void testUpdate() throws SQLException {
        gameDAO.update(gameUtil);

        verify(preparedStatement,times(1)).execute();
        verify(preparedStatement).setInt(1,150);
    }

    @Test
    void testUpdateDebtPot_ResetTrue() throws SQLException {
        gameDAO.update_debt_pot(gameUtil, true);

        verify(preparedStatement, times(1)).execute();

        verify(preparedStatement).setInt(1, 0);

        verify(preparedStatement).setString(2, "AS123");
    }

    @Test
    void testUpdateDebtPot_ResetFalse() throws SQLException {
        gameDAO.update_debt_pot(gameUtil, false);

        verify(preparedStatement, times(1)).execute();

        verify(preparedStatement).setInt(1, 150);

        verify(preparedStatement).setString(2, "AS123");
    }

    @Test
    void testUpdate_joinable() throws SQLException {
        gameDAO.update_joinable(gameUtil);

        verify(preparedStatement, times(1)).execute();
        verify(preparedStatement).setBoolean(1, false);
        verify(preparedStatement).setString(2, "AS123");
    }

    @Test
    void testUpdatePlayerTurn() throws SQLException {
        playerUtil = new PlayerUtil();
        playerUtil.setUserId(1);
        playerUtil.setCash(1000);
        playerUtil.setCurrentPosition(1);
        playerUtil.setCurrentDirection("LEFT");
        gameDAO.updatePlayerTurn(playerUtil, gameUtil);

        verify(preparedStatement, times(1)).execute();
        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).setString(2, "AS123");

    }

    @Test
    void testDiceRoll() throws SQLException {
        int val = gameUtil.rollDice(); // rolls a dice should be between 1 and 12
        assertTrue(val >= 1 && val <= 12);
    }

    @Test
    void testUpdateDiceRoll() throws SQLException {
        gameDAO.update_dice_roll(gameUtil);
        assertTrue(gameUtil.getRecentRoll() >= 1 && gameUtil.getRecentRoll() <= 12);
    }








}
