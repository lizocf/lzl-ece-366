package jdbc;

import jdbc.account_util.AccountUtil;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountUtilTest {

    @Test
    void TestAll(){
        AccountUtil accountUtil = new AccountUtil();
        accountUtil.setPassword("test");
        accountUtil.setNumWins(1);
        accountUtil.setNumLosses(1);
        accountUtil.setDuoPoints(1);
        accountUtil.setEloRating("test");
        accountUtil.setEloRating("test");
        accountUtil.setUserName("test");
        accountUtil.setUserPW("test");
        accountUtil.setToken("test");
        accountUtil.setUserId(1);

        assertEquals(1,accountUtil.getNumWins());
        assertEquals(1,accountUtil.getNumLosses());
        assertEquals(1,accountUtil.getDuoPoints());
        assertEquals("test",accountUtil.getEloRating());
        assertEquals("test",accountUtil.getEloRating());
        assertEquals("test",accountUtil.getUserName());
        assertEquals("test",accountUtil.getUserPW());
        assertEquals("test",accountUtil.getToken());
        assertEquals("test",accountUtil.getPassword());
        assertEquals(1,accountUtil.getUserId());

    }
}
