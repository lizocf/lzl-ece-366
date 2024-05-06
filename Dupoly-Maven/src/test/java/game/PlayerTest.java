package game;

import jdbc.player_util.PlayerUtil;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerTest {

    @Test
    void TestAll(){
        PlayerUtil playerUtil = new PlayerUtil();
        playerUtil.setUserId(1);
        playerUtil.setGameId(1);
        playerUtil.setCash(1);
        playerUtil.setCurrentPosition(1);
        playerUtil.setPreviousPosition(1);

        assertEquals(playerUtil.getUserId(), 1);
        assertEquals(playerUtil.getGameId(), 1);
        assertEquals(playerUtil.getCash(), 1);
        assertEquals(playerUtil.getCurrentPosition(), 1);
        assertEquals(playerUtil.getPreviousPosition(), 1);

    }
}
