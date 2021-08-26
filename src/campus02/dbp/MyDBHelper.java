package campus02.dbp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
}
