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
        String sql = "SELECT MaxLevel, GameGenre, GameId, GameName FROM GAME;";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

           ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                String gameName= "";
                gameName = rs.getString(2);
                gameName = rs.getString("GameName");

                int maxLevel = rs.getInt("MaxLevel");

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

    public void readGamesFilteredByGenrePrepared(String genre){
        System.out.println("readGamesFilteredByGenre");
        String sql="SELECT * FROM GAME WHERE GameGenre = ? ";
        helpReadSQLStatmentPrepared(sql, genre);
    }

    private void helpReadSQLStatmentPrepared(String sql, String genre){
        System.out.println("\nSQLStatement Prepared: " + sql + "\n");
        String url = "jdbc:sqlite:C://sqlite/db/donnerstag.db" ;
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1,genre);
            ResultSet rs = stmt.executeQuery();
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

    //Aufgabe bis 12:00 Uhr
    //Auflösung 13:00 Uhr
    public void createPlayerTable(){
        // SQL statement for creating a new table
        String sql = "CREATE TABLE Player (\n"
                + "	PlayerId INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "	Firstname VARCHAR(255),\n"
                + "	Lastname VARCHAR(255), \n"
                + "	NickName Varchar(255) \n"
                + ");";

        createTable(sql);
    }

    public void readAllPlayers(){
        String sqlSelect = "SELECT * FROM PLAYER;";
        displayPlayer(sqlSelect);
    }
    public void readAllPlayersOrderedBy(String orderColumn){
        String sqlSelect = "SELECT * FROM PLAYER ORDER BY " + orderColumn + " DESC";
        displayPlayer(sqlSelect);
    }

    private  void createTable(String createTableSQLText ) {

        String url = "jdbc:sqlite:C://sqlite/db/donnerstag.db" ;

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            stmt.execute(createTableSQLText);


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void displayPlayer(String sql){
        System.out.println("\nSQLStatement: " + sql + "\n");
        String url = "jdbc:sqlite:C://sqlite/db/donnerstag.db" ;
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){

                System.out.println(rs.getString(("Firstname")) + " Nickname: " + rs.getString(("Nickname")));
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public  void insertGamePrepared(String gameName, String gameGenre, int maxLevel){

        String url = "jdbc:sqlite:C://sqlite/db/donnerstag.db" ;
        String insertSQL="";
        insertSQL = "INSERT INTO Game(GameName, GameGenre, MaxLevel) ";
        insertSQL += " Values(?,?,?)";
        System.out.println("\nSQLStatement Prepared: " + insertSQL + "\n");

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(insertSQL)) {
            stmt.setString(1,gameName);
            stmt.setString(2,gameGenre);
            stmt.setInt(3,maxLevel);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
