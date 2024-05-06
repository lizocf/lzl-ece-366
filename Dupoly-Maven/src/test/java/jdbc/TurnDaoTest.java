package jdbc;

import jdbc.player_util.PlayerDAO;
import jdbc.player_util.PlayerUtil;
import jdbc.turn_util.TurnDAO;
import jdbc.turn_util.TurnUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TurnDaoTest {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet rs;
    private TurnDAO turnDAO;
    private TurnUtil turnUtil;

    @BeforeEach
    void setUp() throws SQLException {

        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        rs = mock(ResultSet.class);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery(anyString())).thenReturn(rs);

        when(rs.next()).thenReturn(true, true, true, true, true, true);
        when(rs.getString(anyString())).thenReturn("test");
        when(rs.getInt(anyString())).thenReturn(1);


        turnDAO = new TurnDAO(connection);
        turnUtil = new TurnUtil();
        turnUtil.setUserId(1);
        turnUtil.setGameId(1);
        turnUtil.setTurnNumber(1);

    }

    @Test
    void testFindById() throws SQLException {

        turnDAO.findById(turnUtil);

        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).setInt(1,1);
        verify(preparedStatement).setInt(2,1);
        verify(preparedStatement).executeQuery();
        verify(rs,times(2)).next();

    }

    @Test
    void testFindByGameId() throws SQLException {
        turnDAO.findByGameId(turnUtil);

        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).setInt(1,1);
        verify(preparedStatement).execute();
        verify(rs,times(2)).next();
    }

    @Test
    void testCreateInstance() throws SQLException {
        turnDAO.createInstance(turnUtil);

        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).setInt(1,1);
        verify(preparedStatement).setInt(2,1);
        verify(preparedStatement).execute();
        verify(rs).next();


    }

    @Test
    void testUpdate() throws SQLException {
        turnDAO.update(turnUtil);

        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).setInt(1,1);
        verify(preparedStatement).setInt(2,1);

    }

    @Test
    @Disabled
    void testRotateUserOrder() throws SQLException {
        turnDAO.rotateUserOrder(turnUtil);



    }

    @Test
    void testDelete() throws SQLException {
        turnDAO.delete(turnUtil);

        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).setInt(1,1);
        verify(preparedStatement).execute();

    }

    @Test
    void testDeleteUser() throws SQLException {
        turnDAO.deleteUser(turnUtil);

        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).setInt(1,1);
        verify(preparedStatement).setInt(2,1);
        verify(preparedStatement).execute();

    }


}
