package jdbc.game_util;

import jdbc.jdbc_util.DataTransferObject;

public class GameUtil implements DataTransferObject
{
    private int gameId;

    private String gameCode;
    private int playerTurn;
    private int debtPot;
    private int numOfPlayers;
    private int numTurns;
    private boolean joinable;
    private int diceRoll;
    private boolean purchaseable;
    private int host;
    private int lastPlayer;

    public int getLastPlayer() {
        return lastPlayer;
    }

    public void setLastPlayer(int lastPlayer) {
        this.lastPlayer = lastPlayer;
    }

    public int getHost() {
        return host;
    }

    public void setHost(int host) {
        this.host = host;
    }


    public String getRecent_card() {
        return recent_card;
    }

    public void setRecent_card(String recent_card) {
        this.recent_card = recent_card;
    }

    public String recent_card;

    public int getRecentRoll() {
        return recentRoll;
    }

    public void setRecentRoll(int recentRoll) {
        this.recentRoll = recentRoll;
    }

    private int recentRoll;



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

    public int getNumTurns() {
        return numTurns;
    }

    public void setNumTurns(int numTurns) {
        this.numTurns = numTurns;
    }

    public boolean isJoinable() {
        return joinable;
    }

    public void setJoinable(boolean joinable) {
        this.joinable = joinable;
    }

    public boolean getPurchaseable() {
        return purchaseable;
    }

    public void setPurchaseable(boolean purchaseable) {
        this.purchaseable = purchaseable;
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
