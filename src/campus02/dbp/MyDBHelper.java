package campus02.dbp;

import java.sql.*;

public class MyDBHelper {

    public  void createGameTable(String fileName ) {
        // SQLite connection string
        String url = "jdbc:sqlite:C://sqlite/db/" +fileName;

        // SQL statement for creating a new table
        String sql = "CREATE TABLE Game (\n"
                + "	GameId INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "	GameName VARCHAR(255),\n"
                + "	GameGenre VARCHAR(255), \n"
                + "	MaxLevel INTEGER \n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);

            String insStatement="INSERT INTO Game(GameName,GameGenre,MaxLevel )";
            insStatement  = insStatement + " Values('Solitaire','Action',100)";

            stmt.execute(insStatement);


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public  void readAllGames(String fileName ) {
        // SQLite connection string
        String url = "jdbc:sqlite:C://sqlite/db/" +fileName;

        // SQL statement for creating a new table
        String sql = "SELECT * FROM GAME;";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

           ResultSet rs = stmt.executeQuery(sql);

           /*
           rs.next();

           String gameName= rs.getString(2);
           gameName = rs.getString("GameName");
            System.out.println(gameName);

           while(rs.next()){
               gameName = rs.getString("GameName");
               System.out.println(gameName);
           }*/


            while(rs.next()){
                String gameName= rs.getString(2);
                gameName = rs.getString("GameName");
                gameName = rs.getString("GameName");
                System.out.println(gameName);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void readGamesFilteredByGenre(String genre){
            System.out.println("readGamesFilteredByGenre");
            String sql="SELECT * FROM GAME WHERE GameGenre ='" + genre + "'";
        helpReadSQLStatment(sql);
    }

    public void readGamesFilteredByMaxLevel(int maxLevel){
        System.out.println("readGamesFilteredByMaxLevel");
        String sql="SELECT * FROM GAME WHERE Maxlevel <" + maxLevel ;
        helpReadSQLStatment(sql);
    }

    public void readGamesOrderBy(String orderColumn){
        System.out.println("readGamesOrderBy");
        String sql="SELECT * FROM GAME ORDER BY " + orderColumn ;
        helpReadSQLStatment(sql);
    }

    private void helpReadSQLStatment(String sql){
        System.out.println("\nSQLStatement: " + sql + "\n");
        String url = "jdbc:sqlite:C://sqlite/db/donnerstag.db" ;
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                String gameName= rs.getString(2);
                gameName = rs.getString("GameName");
                gameName = rs.getString("GameName");
                System.out.println(gameName + " MaxLevel: " + rs.getInt(("MaxLevel")));
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
