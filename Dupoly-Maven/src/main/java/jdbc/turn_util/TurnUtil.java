package jdbc.turn_util;

import jdbc.jdbc_util.DataTransferObject;

public class TurnUtil implements DataTransferObject {
    private int gameId;
    private int userId;
    private int turnNumber;

    public int getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public int getId() {
        return gameId; // Assuming gameId is used as the primary key
    }

    @Override
    public String toString() {
        return "TurnUtil{" +
                "turnNumber=" + turnNumber +
                "gameId=" + gameId +
                ", userId=" + userId +
                '}';
    }
}