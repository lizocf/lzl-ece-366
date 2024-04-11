package game;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;


public class SpecialCard implements Card,Space {

    WeightedProbModel<Object> specialPB = new WeightedProbModel<>()
            .add(40, "Card 1")
            .add(35, "Card 2")
            .add(25, "Card 3")
            .add(25, "Card 4");
    // public String cardName;
    Map<String, OnCard> cardMap = new HashMap<>();

    public SpecialCard()
    {
        cardMap.put("CardName 1", new OnCard()
        {
            @Override
            public void cardAction()
            {
                // define what needs to happen in the sequel database
                System.out.print("Special Card 1 happens");

            }
        });

        cardMap.put("CardName 2", new OnCard()
        {
            @Override
            public void cardAction()
            {
                // define what needs to happen in the sequel database
                System.out.print("Special Card 2 happens");

            }
        });

        cardMap.put("CardName 3", new OnCard()
        {
            @Override
            public void cardAction()
            {
                // define what needs to happen in the sequel database
                System.out.print("Special Card 3 happens");

            }
        });

        cardMap.put("CardName 4", new OnCard()
        {
            @Override
            public void cardAction()
            {
                // define what needs to happen in the sequel database
                System.out.print("Special Card 4 happens");
            }
        });
    }

    @Override
    public void onUse() {
        String cardName = (String) specialPB.next();
        cardMap.get(cardName).cardAction();
    }

    @Override
    public void isOccupied(boolean occupied, int userId, int gameId, String gamecode,  Connection connection)
    {
        onUse();
    }
}
