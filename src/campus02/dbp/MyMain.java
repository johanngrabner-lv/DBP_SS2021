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
        db.readAllGames("Donnerstag.db");

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

}
