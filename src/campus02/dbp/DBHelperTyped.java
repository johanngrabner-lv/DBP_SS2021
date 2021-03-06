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

    public int updateGame(Game g){

        int affectedRows=0;
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
            affectedRows= stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  affectedRows;
    }

    public int incrementMaxLevelByXForGenreY(int incrementValue, String genre){
        int affectedRows = 0;
        String updateSQL="";
        updateSQL = "UPDATE Game SET ";
        updateSQL += " MaxLevel = MaxLevel + ? ";
        updateSQL += " WHERE GameGenre = ? ";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(updateSQL)) {
            stmt.setInt(1,incrementValue);
            stmt.setString(2,genre);

            affectedRows = stmt.executeUpdate();
            return affectedRows;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return affectedRows;
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

    //ERROR
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

    public Player addPlayer(Player p){
        //1. Connection aufbauen
        //2. Prepared Statement "holen"
        //3. Parameter bef??llen
        //4. An DB "schicken" -- executeUpdate
        //5. die neu vergebenen ID einlesen
        String insertSQL="";
        insertSQL = "INSERT INTO Player(Firstname, Lastname, NickName) ";
        insertSQL += " Values(?,?,?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(insertSQL)) {
            stmt.setString(1,p.getFirstname());
            stmt.setString(2,p.getLastname());
            stmt.setString(3,p.getNickname());
            int affectedRows=0;
            affectedRows = stmt.executeUpdate();


            String sqlText = "SELECT last_insert_rowid() as rowid;";
            PreparedStatement stmtAutoincrement = conn.prepareStatement(sqlText);
            ResultSet rs = stmtAutoincrement.executeQuery();
            rs.next();
            int autoincrementValue=rs.getInt("rowid");
            p.setPlayerId(autoincrementValue);
            stmtAutoincrement.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        //TODO - fill the PlayerI
       // Fehler - muss in der gleichen Connection abgefragt werden
        // int newPlayerId = getLastInsertRowid();
        //p.setPlayerId(newPlayerId);
        return p;
    }
    //PlayerId -- WHERE
    public void updatePlayer(Player p){
        String updateSQL="";
        updateSQL = "UPDATE Player SET ";
        updateSQL += " Firstname = ?, ";
        updateSQL += " Lastname = ?, ";
        updateSQL += " Nickname = ? ";
        updateSQL += " WHERE PlayerId = ? ";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(updateSQL)) {
            stmt.setString(1,p.getFirstname());
            stmt.setString(2,p.getLastname());
            stmt.setString(3,p.getNickname());
            stmt.setInt(4,p.getPlayerId());
            int affectedRows=0;
            affectedRows = stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Player getPlayerById(int playerId){
        Player p =new Player();
        String getGameById = "SELECT * FROM Player WHERE PlayerId = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = conn.prepareStatement(getGameById)) {
            preparedStatement.setInt(1, playerId);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
              p.setPlayerId(rs.getInt("PlayerId"));
              p.setFirstname(rs.getString("Firstname"));
              p.setLastname(rs.getString("Lastname"));
               p.setNickname(rs.getString("NickName"));
               p.setPoints(rs.getDouble("Points"));
            } else
            {

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return p;
    }

    public Player getPlayerWithMostLovedGames(){
        Player p =new Player();
        String selectSQL="SELECT PlayerId, Count(*)\n" +
                "FROM LovedGames\n" +
                "GROUP BY PlayerId\n" +
                "ORDER BY COUNT(*) DESC";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = conn.prepareStatement(selectSQL)) {
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
               int playerId= rs.getInt("PlayerId");
               p= getPlayerById(playerId);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return p;

    }

    public int joinLovedGame(Game g, Player p, int l) { //int lovedGames?
        String selectSQL = "";
        boolean lastReadWasNull;
        int rank=0;
        selectSQL = "SELECT p.Nickname, l.Rank, g.GameName, l.Rank " +
                "FROM LovedGames AS l " +
                "INNER JOIN Player AS p " +
                "ON p.PlayerID = l.PlayerID " +
                "JOIN Game g " +
                "ON g.GameID = l.GameID";
        selectSQL += " WHERE g.GameID=? ";
        selectSQL += " AND p.PlayerID=? ";
        //selectSQL += " AND LovedGamesID=? ";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(selectSQL)) {
            stmt.setInt(1, g.getGameId());
            stmt.setInt(2, p.getPlayerId());
            //stmt.setInt(3, l.getLovedGamesID());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                rank=rs.getInt("Rank");

                lastReadWasNull = rs.wasNull();
                System.out.println(lastReadWasNull);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return rank;
    }

    public void printMetdataData()
    {
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Player")) {
            ResultSet rs = stmt.executeQuery();

            ResultSetMetaData  meta = rs.getMetaData();
            int numerics = 0;

            for ( int i = 1; i <= meta.getColumnCount(); i++ )
            {
                System.out.printf( "%-20s %-20s%n", meta.getColumnLabel( i ),
                        meta.getColumnTypeName( i ) );

                if ( meta.isSigned( i ) )
                    numerics++;
            }

            System.out.println();
            System.out.println( "Spalten: " + meta.getColumnCount() +
                    ", Numerisch: " + numerics );

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    public  void addPointsToPlayerAndInitialize() {

        // SQL statement for creating a new table
        String createTableSql = "ALTER TABLE Player\n" +
                "ADD COLUMN POINTS decimal;";

        String updateAllPlayers = "UPDATE Player SET POINTS = 100";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSql);
            stmt.executeUpdate(updateAllPlayers);


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void transferPoints(int playerAId, int playerBId, double points){

        int affectedRows=0;
        String updateSQL1="";
        updateSQL1 = "UPDATE Player SET ";
        updateSQL1 += " Points = Points - ? ";
        updateSQL1 += " WHERE PlayerId = ? ";

        String updateSQL2="";
        updateSQL2 = "UPDATE Player SET ";
        updateSQL2 += " Points = Points + ? ";
        updateSQL2 += " WHERE PlayerId = ? ";

        String selectPlayer1 = "SELECT * FROM Player WHERE PlayerId=?";


        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt1 = conn.prepareStatement(updateSQL1);
             PreparedStatement stmt2 = conn.prepareStatement(updateSQL2);
             PreparedStatement stmtSelectPlayer = conn.prepareStatement(selectPlayer1);
             ) {
            stmt1.setInt(2, playerAId);
            stmt1.setDouble(1, points);

            stmt2.setInt(2, playerBId);
            stmt2.setDouble(1, points);

            stmtSelectPlayer.setInt(1,playerAId);

            conn.setAutoCommit(false);
            stmt1.executeUpdate();
            stmt2.executeUpdate();

           // Player p1 = getPlayerById(playerAId);
            ResultSet rs = stmtSelectPlayer.executeQuery();
            rs.next();
            double newPoints = rs.getDouble("Points");



            if (newPoints>=0){
                conn.commit();
            } else {
                conn.rollback();
            }

           // conn.commit();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


}
