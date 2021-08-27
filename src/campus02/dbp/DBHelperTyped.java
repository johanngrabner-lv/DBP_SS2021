package campus02.dbp;

import java.sql.*;
import java.util.ArrayList;

public class DBHelperTyped {

    private String url = "jdbc:sqlite:C://sqlite/db/donnerstag.db";


    public void addGame(Game g){

        String insertSQL="";
        insertSQL = "INSERT INTO Game(GameName, GameGenre, MaxLevel) ";
        insertSQL += " Values(?,?,?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(insertSQL)) {
            stmt.setString(1,g.getGameName());
            stmt.setString(2,g.getGameGenre());
            stmt.setInt(3,g.getMaxLevel());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateGame(Game g){

        String updateSQL="";
        updateSQL = "UPDATE Game SET ";
        updateSQL += " GameName=?, ";
        updateSQL += " GameGenre=?, ";
        updateSQL += " MaxLevel=? ";
        updateSQL += " WHERE GameId=? ";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(updateSQL)) {
            stmt.setString(1,g.getGameName());
            stmt.setString(2,g.getGameGenre());
            stmt.setInt(3,g.getMaxLevel());
            stmt.setInt(4,g.getGameId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addGameWithAutoincrementValue(Game g){

        String insertSQL="";
        insertSQL = "INSERT INTO Game(GameName, GameGenre, MaxLevel) ";
        insertSQL += " Values(?,?,?)";


        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(insertSQL)) {
            stmt.setString(1,g.getGameName());
            stmt.setString(2,g.getGameGenre());
            stmt.setInt(3,g.getMaxLevel());
            stmt.executeUpdate();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        g.setGameId(getLastInsertRowid());
    }

    public ArrayList<Game> getAllGamesByGenre(String genre){
        ArrayList<Game> meineSpiele =new ArrayList<Game>();
        String getGameById = "SELECT * FROM Game WHERE GameGenre = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = conn.prepareStatement(getGameById)) {
            preparedStatement.setString(1, genre);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                int gameId=rs.getInt("GameId");
                Game g = getGameById(gameId);
                meineSpiele.add(g);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

            return  meineSpiele;
        }



    public Game getGameById(int gameId){
        Game g =new Game();

        String getGameById = "SELECT * FROM Game WHERE GameId = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = conn.prepareStatement(getGameById)) {
            preparedStatement.setInt(1, gameId);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
               g.setGameId(gameId);
               g.setGameName(rs.getString("GameName"));
                g.setGameGenre(rs.getString("GameGenre"));
                g.setMaxLevel(rs.getInt("MaxLevel"));
            } else
            {
                g.setGameName("not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return g;

    }

    public int getLastInsertRowid()
    {
        int lastId=0;
        String sqlText = "SELECT last_insert_rowid() as rowid;";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sqlText))
        {

        ResultSet rs = null;

            rs = stmt.executeQuery(sqlText);
            rs.next();
            lastId=rs.getInt("rowid");
            rs.close();
            stmt.close();
            //con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  lastId;
    }
}
