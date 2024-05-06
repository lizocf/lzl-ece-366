package game;

import com.fasterxml.jackson.core.JsonProcessingException;
import jdbc.DatabaseConnectionManager;
import jdbc.account_util.AccountDAO;
import jdbc.account_util.AccountUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.springframework.test.util.ReflectionTestUtils;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class AccountTest {

    Connection connection;
    DatabaseConnectionManager dcm;
    AccountDAO accountDAO;
    AccountUtil accountUtil;
    Account account;

    @BeforeEach
    public void setUp() throws SQLException {

        connection = mock(Connection.class);
        accountDAO = mock(AccountDAO.class);
        accountUtil = mock(AccountUtil.class);

        dcm = mock(DatabaseConnectionManager.class);
        account = new Account();
        account.setDCM(dcm);
//        account.setUser_name(1);
//        account.setDuo_points(1);
//        account.setUser_id(1);
//        account.setNum_wins(1);
//        account.setNum_losses(1);
//        account.setElo_rating(1);

        when(accountDAO.createInstance(any())).thenReturn(accountUtil);

        }

    @Test
    public void testCreateNewAccount() throws SQLException, JsonProcessingException {

        String json = "{\"user_name\":\"test_user\", \"user_pw\":\"test_password\"}";

        account.createNewAccount(json);

        verify(accountDAO).createInstance(any());

    }
    @Test
    public void TestSetGet() {
        Account acc = new Account();
        acc.setUser_id(1);
        acc.setNum_wins(1);
        acc.setNum_losses(1);
        acc.setElo_rating(1);
        acc.setDuo_points(1);
        acc.setUser_name(1);


    }
//    @Test
//    void testFindByGameId() throws SQLException {
//        turnDAO.findByGameId(turnUtil);
//    }
//
//    @Test
//    void testCreateInstance() throws SQLException {
//        turnDAO.createInstance(turnUtil);
//
//    }
//
//    @Test
//    void testUpdate() throws SQLException {
//        turnDAO.update(turnUtil);
//
//    }
//
//    @Test
//    void testRotateUserOrder() throws SQLException {
//        turnDAO.rotateUserOrder(turnUtil);
//
//    }
//
//    @Test
//    void testDelete() throws SQLException {
//        turnDAO.delete(turnUtil);
//
//    }
//
//    @Test
//    void testDeleteUser() throws SQLException {
//        turnDAO.deleteUser(turnUtil);
//
//    }
}
