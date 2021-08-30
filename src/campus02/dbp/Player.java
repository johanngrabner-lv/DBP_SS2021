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

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    private double points;

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

    public ArrayList<LovedGames> getMyLovedGames() {
        return myLovedGames;
    }

    public ArrayList<Game> getMyLovedGamesOOP() {
        return myLovedGamesOOP;
    }

    public void setMyLovedGamesOOP(ArrayList<Game> myLovedGamesOOP) {
        this.myLovedGamesOOP = myLovedGamesOOP;
    }

    private ArrayList<Game> myLovedGamesOOP;


    ArrayList<LovedGames> myLovedGames;

    public void fillLovedGames() {
        if (myLovedGames==null)
            myLovedGames =new ArrayList<LovedGames>();
        else
            myLovedGames.clear();

        String sqlSelect = "SELECT * FROM LovedGames WHERE PlayerId = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = conn.prepareStatement(sqlSelect)) {
            preparedStatement.setInt(1, PlayerId);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                LovedGames g =new LovedGames();
                g.setLovedGamesId(rs.getInt("LovedGamesId"));
                g.setGameId(rs.getInt("GameId"));
                g.setPlayerId(rs.getInt("PlayerId"));
              //  g.setPlayDate(rs.getDate("PlayDate"));

                myLovedGames.add(g);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    public void fillLovedGamesOOP() {
        if (myLovedGamesOOP==null)
            myLovedGamesOOP =new ArrayList<Game>();
        else
            myLovedGamesOOP.clear();

        DBHelperTyped dbHelper=new DBHelperTyped();
        String sqlSelect = "SELECT * FROM LovedGames WHERE PlayerId = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = conn.prepareStatement(sqlSelect)) {
            preparedStatement.setInt(1, PlayerId);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Game g = dbHelper.getGameById(rs.getInt("GameId"));
                myLovedGamesOOP.add(g);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    public void addLovedGame(Game g){
        String insSQL = "INSERT INTO LovedGames(LovedGamesId,PlayerID, GameId) VALUES(?,?,?)";
        //pStmt.setInt(1, getPlayerId());
        //pStmt.setInt(2, g.getGameId());
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = conn.prepareStatement(insSQL)) {
            preparedStatement.setInt(1, 10);
            preparedStatement.setInt(2, PlayerId);
            preparedStatement.setInt(3, g.getGameId());
            int affectedRows = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void setMyLovedGames(ArrayList<LovedGames> myLovedGames) {
        this.myLovedGames = myLovedGames;
    }
}
