package campus02.dbp;

import java.sql.*;

public class MyMain {
    public static void main(String[] args){
      /*  System.out.print("Hello Campus02");
        String databasename="Donnerstag.db";
        createNewDatabase(databasename);
        connect(databasename);
        createNewTable(databasename);
        */

        MyDBHelper db=new MyDBHelper();
       // db.createGameTable("Donnerstag.db");
        //db.readAllGames("Donnerstag.db");
  //      db.readGamesFilteredByGenre("Action");
//        db.readGamesFilteredByMaxLevel(120);
        //db.readGamesOrderBy("MaxLevel");
       // db.createPlayerTable();
     //   db.readAllPlayers();
       // db.readAllPlayersOrderedBy("Firstname");
     //   db.insertGamePrepared("GamePrep","Action",700);
//        db.readGamesFilteredByGenrePrepared("Action");

        DBHelperTyped dbHelper =new DBHelperTyped();

      /*  Game g=new Game();
        g.setGameName("Game Typed");
        g.setGameGenre("Action");
        g.setMaxLevel(500);;
        dbHelper.addGame(g);


        Game gameFound = dbHelper.getGameById(1);
        System.out.println(gameFound);

        System.out.println(dbHelper.getAllGamesByGenre("Action"));
        */

        /*
        Game g=new Game();
        g.setGameName("Game Typed Autoincrement");
        g.setGameGenre("Action");
        g.setMaxLevel(500);;
        dbHelper.addGameWithAutoincrementValue(g);
        System.out.println(g);
        */


        /*
        Game g1 = dbHelper.getGameById(1);
        g1.setGameName("Geänderter Name");
        g1.setMaxLevel(700);
        g1.setGameGenre("BoardGame");
        g1.setGameId(0);
        int shouldBeOne = dbHelper.updateGame(g1);
        if (shouldBeOne==0){
            System.out.println("Game was not found");
        }
        if (shouldBeOne>1){
            System.out.println("More than one game with same id");
        }

        if (shouldBeOne==1){
            System.out.println("Game was succesfully updated");
        }



        int rowsAffected = dbHelper.incrementMaxLevelByXForGenreY(50,"Action");
*/
        /*
        Player newPlayer =
                new Player(0,"Luisa","De Franco","LF");

        Player pInserted = dbHelper.addPlayer(newPlayer);
        dbHelper.updatePlayer(newPlayer);

       // Player foundPlayer = dbHelper.getPlayerById(2);

        Player p2 = dbHelper.getPlayerById(2);
        ArrayList<LovedGames> gamesForPlayer2 = p2.getMyLovedGames();
        */

        /*
        Player p10 = dbHelper.getPlayerById(10);
        Game g4 = dbHelper.getGameById(4);

        p10.addLovedGame(g4);

        p10.fillLovedGames();
        System.out.println(p10.getMyLovedGames());
        p10.fillLovedGamesOOP();
        System.out.println(p10.getMyLovedGamesOOP());
*/
        Player most = dbHelper.getPlayerWithMostLovedGames();

        Game g1 = new Game();
        g1.setGameId(4);

        Player pZehn=new Player();
        pZehn.setPlayerId(10);

       // int rank = dbHelper.joinLovedGame(g1, pZehn,0);

        dbHelper.printMetdataData();


    }

    public static void createNewDatabase(String fileName) {

        //1. Create Folder c:/sqlite/db"
        String url = "jdbc:sqlite:C:/sqlite/db/" + fileName;

        //FILE - Project Structure
        //Libraries "+" -- sqlite-jdbc-3.30.1.jar
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void connect(String filename) {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:C:/sqlite/db/" + filename;
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void createNewTable(String fileName ) {
        // SQLite connection string
        String url = "jdbc:sqlite:C://sqlite/db/" +fileName;

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS warehouses (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + "	capacity real\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);

            String insStatement="INSERT INTO warehouses(id,name,capacity)";
            insStatement  = insStatement + " Values(1,'test',100)";

            stmt.execute(insStatement);


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /* Agenda für Montag -
    Wiederholung und offenen Fragen
    - Transactions
    * Metadata
    * DAL - Data Access Layer - Pattern
    * Null-Values, JOINS Aggregation
     */
    /*Agenda für Dienstag - Prüfungsvorbereitungsbeispiel
    Agenda Mittwoch -Auflöung
     */

    /* Beispiel-Aufgaben
        LovedGames -- Create Table, addLovedGames, updateLovedGames, deleteLovedGames

        Car --> 4 Wheels, Engine, Seats
        Player --- List of LovedGames and a List of PlayedGames
        Aggregation / Komposition, Aggregate / Composite
        ArrayList<LovedGames> lg = p1.getLovedGames();
        SELECT * FROM LovedGames WHERE PlayerID = ?
        ArrayList<GameHistory> gh = p1.getGameHistory();
        Player pHighestNumberPlayedGames = dbHelper.getPlayerHighestNumberPlayedGames();
     */

/*CREATE TABLE LovedGames (
    LovedGamesId INTEGER PRIMARY KEY,
    PlayerId     INTEGER REFERENCES Player (PlayerId),
    GameId       INTEGER REFERENCES Game (GameId),
    PlayDate     DATE
);

Aggregation in SQL
SELECT p.Firstname, l.* FROM Player p
JOIN LovedGames l
ON p.PlayerId=l.PlayerId
 */



}
