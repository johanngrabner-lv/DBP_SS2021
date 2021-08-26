package campus02.dbp;

public class Game {


    public int getGameId() {
        return GameId;
    }

    public void setGameId(int gameId) {
        GameId = gameId;
    }

    private int GameId;

    public String getGameName() {
        return GameName;
    }

    public void setGameName(String gameName) {
        GameName = gameName;
    }

    private String GameName;

    @Override
    public String toString() {
        return "Game{" +
                "GameId=" + GameId +
                ", GameName='" + GameName + '\'' +
                ", GameGenre='" + GameGenre + '\'' +
                ", MaxLevel=" + MaxLevel +
                '}';
    }

    public String getGameGenre() {
        return GameGenre;
    }

    public void setGameGenre(String gameGenre) {
        GameGenre = gameGenre;
    }

    private String GameGenre;

    public int getMaxLevel() {
        return MaxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        MaxLevel = maxLevel;
    }

    private int MaxLevel;
}
