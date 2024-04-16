package game;
import game.Player;
import jdbc.player_util.PlayerDAO;
import jdbc.player_util.PlayerUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ProbabilityTest
{

    @Test
    void testWeightedProbModel()
    {
        WeightedProbModel<Object> model = new WeightedProbModel<>()
                .add(100, "Bankrupt")
                .add(0, "Market Crash")
                .add(0, "Based")
                .add(0, "f off")
                .add(0, "Squatters Rights")
                .add(0, "Jailed")
                .add(0, "No Go")
                .add(0, "Divorced")
                .add(0, "Swap")
                .add(0, "West Coast Best Coast")
                .add(0, "West Coast Worst Coast")
                .add(0, "Dust Bowl")
                .add(0, "Triple Skull emoji")  //kinda broke the game
                .add(0, "We're So Back")
                .add(0, "Self Sabotage")
                .add(0, "What Do You Bring To The Table") //untested
                .add(0, "Rent Stabilized")
                .add(0, "Eviction")
                .add(0, "Winner Winner Chicken Dinner");

        String cardName = (String) model.next();
        String expected_result = "Bankrupt";
        assertEquals(cardName, expected_result);

    }
    // this test also shows special cards work since they are virtually the same






}
