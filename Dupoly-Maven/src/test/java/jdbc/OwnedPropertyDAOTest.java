package jdbc;

import jdbc.player_util.PlayerUtil;
import jdbc.property_util.OwnedPropertyDAO;
import jdbc.property_util.OwnedPropertyUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class OwnedPropertyDAOTest {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private OwnedPropertyDAO ownedPropertyDAO;
    private OwnedPropertyUtil ownedPropertyUtil;
    private ResultSet rs;

    private PlayerUtil playerUtil;

    @BeforeEach
    void setUp() throws SQLException {

        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        rs = mock(ResultSet.class);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(rs);


        ownedPropertyDAO = new OwnedPropertyDAO(connection);
        ownedPropertyUtil = new OwnedPropertyUtil();
        ownedPropertyUtil.setUserId(1);
        ownedPropertyUtil.setPropertyName("test");
        ownedPropertyUtil.setGameId(1);
        ownedPropertyUtil.setDebtPot(1);
        ownedPropertyUtil.setUpgradeable(true);
        ownedPropertyUtil.setSetName("test");
        ownedPropertyUtil.setNumOfHotels(1);
        ownedPropertyUtil.setTradeable(true);


    }

    @Test
    void testFindById() throws SQLException {

        when(rs.next()).thenReturn(true,true,true,true,true,false);

        ownedPropertyDAO.findById(ownedPropertyUtil);

        verify(rs,times(6)).next();
        verify(preparedStatement).executeQuery();
    }

    @Test
    void TestCreateInstance() throws SQLException {
        ownedPropertyDAO.createInstance(ownedPropertyUtil);

        verify(preparedStatement,times(2)).setInt(1,1);
        verify(preparedStatement).setInt(2,1);
        verify(preparedStatement).setString(3,"test");
        verify(preparedStatement).setString(4,"test");
        verify(preparedStatement).execute();
    }

    @Test
    void TestUpdateNumHotel() throws SQLException {
        ownedPropertyDAO.updateNumHotel(ownedPropertyUtil,1);

        verify(preparedStatement).setString(1,"num_hotels");
        verify(preparedStatement).setInt(2,1);
        verify(preparedStatement).setInt(3,1);
        verify(preparedStatement).setInt(4,1);
        verify(preparedStatement).execute();
    }

    @Test
    void TestUpdateUpgradeable() throws SQLException {
        ownedPropertyDAO.updateUpgradeable(ownedPropertyUtil);

        verify(preparedStatement).setBoolean(1,true);
        verify(preparedStatement).setInt(2,1);
        verify(preparedStatement).setInt(3,1);
        verify(preparedStatement).setString(4,"test");
        verify(preparedStatement).execute();
    }

    @Test
    void TestUpdateTradeable() throws SQLException {
        ownedPropertyDAO.updateTradeable(ownedPropertyUtil);

        verify(preparedStatement).setBoolean(1,true);
        verify(preparedStatement).setInt(2,1);
        verify(preparedStatement).setInt(3,1);
        verify(preparedStatement).setString(4,"test");
        verify(preparedStatement).execute();
    }

    @Test
    void TestFindNames() throws SQLException {
        playerUtil = mock(PlayerUtil.class);
        playerUtil.setCurrentPosition(1);
        when(rs.next()).thenReturn(true,false);

        ownedPropertyDAO.findNames(playerUtil);

        verify(playerUtil).getCurrentPosition();

        verify(rs,times(2)).next();
        verify(preparedStatement).setInt(1,0);
    }

    @Test
    void TestFindNamesByPos() throws SQLException {
        int space = 1;

        ownedPropertyDAO.findNamesByPos(space);

        verify(preparedStatement).setInt(1,1);
        verify(rs,times(1)).next();
        verify(preparedStatement).executeQuery();
    }

    @Test
    void TestFindAllOwned() throws SQLException {

        when(rs.next()).thenReturn(true,true,true,true,true,true,false);

        ownedPropertyDAO.findAllOwned(ownedPropertyUtil);

        verify(preparedStatement).setInt(1,1);
        verify(preparedStatement).setInt(2,1);
        verify(preparedStatement).executeQuery();
        verify(rs,times(7)).next();
    }

    @Test
    void TestDelete() throws SQLException {
        ownedPropertyDAO.delete(ownedPropertyUtil);

        verify(preparedStatement).setInt(1,1);
        verify(preparedStatement).setString(2,"test");
        verify(preparedStatement).execute();

    }

}