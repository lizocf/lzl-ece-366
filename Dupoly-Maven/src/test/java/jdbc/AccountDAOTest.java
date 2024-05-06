package jdbc;

import jdbc.account_util.AccountDAO;
import jdbc.account_util.AccountUtil;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AccountDAOTest {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private AccountDAO accountDAO;
    private AccountUtil accountUtil;
    private ResultSet rs;

    @BeforeEach
    void setUp() throws SQLException {
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery(anyString())).thenReturn(rs);

        rs = mock(ResultSet.class);
        when(rs.next()).thenReturn(true, true, true, true, true, true);
        when(rs.getString(anyString())).thenReturn("test");
        when(rs.getInt(anyString())).thenReturn(1);


        accountDAO = new AccountDAO(connection);
        accountUtil = new AccountUtil();

        accountUtil.setUserId(1);
        accountUtil.setUserName("test");
        accountUtil.setUserPW("test");
        accountUtil.setToken("test");
        accountUtil.setNumWins(1);
        accountUtil.setNumLosses(1);
        accountUtil.setEloRating("test");
        accountUtil.setDuoPoints(1);

        rs = preparedStatement.executeQuery();
//        while(rs.next())
//        rs.getInt("user_id");
//        rs.getString("user_name");
//        rs.getString("user_pw");
//        rs.getString("token");
//        rs.getInt("num_wins");
//        rs.getInt("num_losses");
//        rs.getString("elo_rating");
//        rs.getInt("duo_points");

    }

    @Test
    @Disabled
    void testFindById() throws SQLException {
        accountDAO.findById(accountUtil);

        verify(rs,times(7)).next();
        verify(rs).getString(anyString());


    }

//    @Test
//    void testFindByToken() throws SQLException {
//        accountDAO.findByToken(accountUtil);
//
//        verify(preparedStatement.executeQuery(anyString()));
//
//    }
//
//    @Test
//    void testCreateInstance() throws SQLException {
//        accountDAO.createInstance(accountUtil);
//        verify(preparedStatement.executeQuery(anyString()));
//
//    }


    @Test
    void testCreateToken() throws SQLException {
        accountDAO.createToken(accountUtil);

        verify(preparedStatement, times(1)).execute();
        verify(preparedStatement).setString(1, "test");
        verify(preparedStatement).setInt(2, 1);

    }

    @Test
    void testUpdate() throws SQLException {
        accountDAO.update(accountUtil);

        verify(preparedStatement,times(1)).execute();
        verify(preparedStatement).setInt(1,1);
    }

    @Test
    void testUpdate_loss() throws SQLException {
        accountDAO.update_loss(accountUtil);

        verify(preparedStatement,times(1)).execute();
        verify(preparedStatement).setInt(1,1);
    }

    @Test
    void testUpdate_dp() throws SQLException {
        accountDAO.update_dp(accountUtil, 1);

        verify(preparedStatement).setInt(2,1);
    }

// Removed feature
//    @Test
//    void testUpdate_elo() throws SQLException {
//        accountDAO.update_elo(accountUtil, "Plat");
//    }

    @Test
    void testDelete() throws SQLException {
        accountDAO.delete(accountUtil);
        verify(preparedStatement,times(1)).execute();
        verify(preparedStatement).setInt(1,1);
    }
}
