package jdbc.player_util;

import jdbc.jdbc_util.DataTransferObject;

public class PlayerUtil implements DataTransferObject {
    @Override
    public int getId() {
        return userId;
    }

    private int userId, gameId, cash, currentPosition;
    private boolean jail, afk, dead;
    private String userName, currentDirection;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public boolean isJail() {
        return jail;
    }

    public void setJail(boolean jail) {
        this.jail = jail;
    }

    public boolean isAfk() {
        return afk;
    }

    public void setAfk(boolean afk) {
        this.afk = afk;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public String getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(String currentDirection) {
        this.currentDirection = currentDirection;
    }
}