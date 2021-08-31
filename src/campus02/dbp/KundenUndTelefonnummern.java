package campus02.dbp;

import java.sql.*;

public class KundenUndTelefonnummern {
   private String connectionString="jdbc:sqlite:C://sqlite/db/dienstag.db";


        public  void createTable() {

            // SQL statement for creating a new table
            String createTableKundeSql = "CREATE TABLE Kunden (\n"
                    + "	KDNR INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + "	Vorname varchar(20) )\n";

            String createTableKundeTelefonnummerSql = "CREATE TABLE KundenTelefonnummern (\n"
                    + "	KDId INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + "	KDNR INTEGER,\n"
                    + " Telefonnummer varchar(20), \n"
                    + " FOREIGN KEY(KDNR ) REFERENCES Kunden(KDNR))";

            String insKunde="INSERT INTO Kunde(Vorname) VALUES(?)";
            String insTelefon="INSERT INTO KundenTelefonnummern(KDNR, Telefonnummer)\n" +
                    "VALUES(?,?)";
            String pragmaForeignKey="PRAGMA foreign_keys=on;";

            String joinKundeUndTelefon="SELECT k.Vorname, t.Telefonnummer\n" +
                    "FROM Kunde k JOIN KundenTelefonnummern t\n" +
                    "ON k.KDNR = t.KDNR";


            try (Connection conn = DriverManager.getConnection(connectionString);
                 Statement stmt = conn.createStatement()) {
                stmt.execute(createTableSql);


            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }


    }

