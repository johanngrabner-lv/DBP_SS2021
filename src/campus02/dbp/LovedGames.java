package campus02.dbp;

import java.util.Date;

public class LovedGames {
    public int getLovedGamesId() {
        return LovedGamesId;
    }

    @Override
    public String toString() {
        return "LovedGames{" +
                "LovedGamesId=" + LovedGamesId +
                ", PlayerId=" + PlayerId +
                ", GameId=" + GameId +
                ", PlayDate=" + PlayDate +
                '}';
    }

    public void setLovedGamesId(int lovedGamesId) {
        LovedGamesId = lovedGamesId;
    }

    public int getPlayerId() {
        return PlayerId;
    }

    public void setPlayerId(int playerId) {
        PlayerId = playerId;
    }

    public int getGameId() {
        return GameId;
    }

    public void setGameId(int gameId) {
        GameId = gameId;
    }

    public Date getPlayDate() {
        return PlayDate;
    }

    public void setPlayDate(Date playDate) {
        PlayDate = playDate;
    }

    private int LovedGamesId;
    private int PlayerId;
    private int GameId;
    private Date PlayDate;

}
