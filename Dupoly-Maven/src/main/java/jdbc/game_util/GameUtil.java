package jdbc.game_util;

import jdbc.jdbc_util.DataTransferObject;

public class GameUtil implements DataTransferObject
{
    private int gameId;

    private String gameCode;
    private int playerTurn;
    private int debtPot;
    private int numOfPlayers;
    private boolean joinable;

    private int diceRoll;

    private int doublesCounter = 0;

    //make a function here called DO DICE ROLL. THEN STORE IT

    public int rollDice()
    {
        int die1 = (int) (6*Math.random()+1);
        int die2 = (int) (6*Math.random()+1);

        if(die1 == die2) {doublesCounter++;}

        return die1 + die2;
    }

    public int getDiceRoll() {
        return diceRoll;
    }

    public void setDiceRoll(int diceRoll) {

        this.diceRoll = diceRoll;
    }

    public int getId() {
        return gameId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }

    public int getDebtPot() {
        return debtPot;
    }

    public void setDebtPot(int debtPot) {
        this.debtPot = debtPot;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public void setNumOfPlayers(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }

    public boolean isJoinable() {
        return joinable;
    }

    public void setJoinable(boolean joinable) {
        this.joinable = joinable;
    }

    @Override
    public String toString() {
        return "GameUtil{" +
                "gameId=" + gameId +
                ", gameCode='" + gameCode + '\'' +
                ", playerTurn=" + playerTurn +
                ", debtPot=" + debtPot +
                ", numOfPlayers=" + numOfPlayers +
                '}';
    }
}
