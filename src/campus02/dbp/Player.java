package campus02.dbp;

import java.sql.*;
import java.util.ArrayList;

public class Player {

    private String url = "jdbc:sqlite:C://sqlite/db/donnerstag.db";
    public Player(){

    }
    public Player(int playerId, String firstname, String lastname, String nickname) {
        PlayerId = playerId;
        Firstname = firstname;
        Lastname = lastname;
        Nickname = nickname;
    }

    @Override
    public String toString() {
        return "Player{" +
                "PlayerId=" + PlayerId +
                ", Firstname='" + Firstname + '\'' +
                ", Lastname='" + Lastname + '\'' +
                ", Nickname='" + Nickname + '\'' +
                '}';
    }

    public int getPlayerId() {
        return PlayerId;
    }

    public void setPlayerId(int playerId) {
        PlayerId = playerId;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    private int PlayerId;
    private String Firstname;
    private String Lastname;
    private String Nickname;

    ArrayList<LovedGames> myLovedGames;

    public ArrayList<LovedGames> getMyLovedGames() {
        if (myLovedGames==null)
            myLovedGames =new ArrayList<LovedGames>();
        else
            myLovedGames.clear();

        String sqlSelect = "SELECT * FROM LovedGames WHERE PlayerId = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = conn.prepareStatement(sqlSelect)) {
            preparedStatement.setInt(1, PlayerId);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                LovedGames g =new LovedGames();
                g.setLovedGamesId(rs.getInt("LovedGamesId"));
                g.setGameId(rs.getInt("GameId"));
                g.setPlayerId(rs.getInt("PlayerId"));
                g.setPlayDate(rs.getDate("PlayDate"));

                myLovedGames.add(g);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return myLovedGames;
    }

    public void setMyLovedGames(ArrayList<LovedGames> myLovedGames) {
        this.myLovedGames = myLovedGames;
    }
}
