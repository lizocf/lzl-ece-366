package jdbc;

import jdbc.game_util.GameUtil;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerUtilTest {

    @Test
    void TestAll() {
        GameUtil gametUtil = new GameUtil();

        gametUtil.setGameId(1);
        gametUtil.setGameCode("test");
        gametUtil.setPlayerTurn(1);
        gametUtil.setDebtPot(1);
        gametUtil.setNumOfPlayers(1);
        gametUtil.setNumTurns(1);
        gametUtil.setJoinable(true);
        gametUtil.setDiceRoll(1);
        gametUtil.setPurchaseable(true);
        gametUtil.setClicked(true);
        gametUtil.setHost(1);
        gametUtil.setLastPlayer(1);

        assertEquals(1, gametUtil.getGameId());
        assertEquals("test", gametUtil.getGameCode());
        assertEquals(1, gametUtil.getPlayerTurn());
        assertEquals(1, gametUtil.getDebtPot());
        assertEquals(1, gametUtil.getNumOfPlayers());
        assertEquals(1, gametUtil.getNumTurns());
        assertTrue(gametUtil.isJoinable());
        assertEquals(1, gametUtil.getDiceRoll());
        assertTrue(gametUtil.getPurchaseable());
        assertTrue(gametUtil.isClicked());
        assertEquals(1, gametUtil.getHost());
        assertEquals(1, gametUtil.getLastPlayer());
    }
}
