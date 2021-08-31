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



            try (Connection conn = DriverManager.getConnection(connectionString);
                 Statement stmtCreateKunde = conn.createStatement();
                 Statement stmtCreateTelefon = conn.createStatement();

                 ) {
                stmtCreateKunde.execute(createTableKundeSql);
                stmtCreateTelefon.execute(createTableKundeTelefonnummerSql);


            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    public  void insertValues() {


        String insKunde="INSERT INTO Kunden(Vorname) VALUES(?)";
        String insTelefon="INSERT INTO KundenTelefonnummern(KDNR, Telefonnummer)\n" +
                "VALUES(?,?)";
        String pragmaForeignKey="PRAGMA foreign_keys=on;";

        String joinKundeUndTelefon="SELECT k.Vorname, t.Telefonnummer\n" +
                "FROM Kunden k JOIN KundenTelefonnummern t\n" +
                "ON k.KDNR = t.KDNR";


        try (Connection conn = DriverManager.getConnection(connectionString);

             Statement stmtPragma = conn.createStatement();
             Statement stmtSelectMitJoin = conn.createStatement();
             PreparedStatement pStmtInsKunden = conn.prepareStatement(insKunde);
             PreparedStatement pStmtInsTelefon = conn.prepareStatement(insTelefon);

        ) {

            stmtPragma.execute(pragmaForeignKey);

            String vorname="Stefie";
            pStmtInsKunden.setString(1,vorname);
           // pStmtInsKunden.executeUpdate();

            //Erster Parameter mit KDNR 1 befüllen
            pStmtInsTelefon.setInt(1,2);
            //Zweiter Parameter mit Telefonnummer 0316
            pStmtInsTelefon.setString(2,"0316");
           // pStmtInsTelefon.executeUpdate();

            pStmtInsTelefon.setInt(1,9);
            pStmtInsTelefon.setString(2,"0650");
            //pStmtInsTelefon.executeUpdate();

            ResultSet rs = stmtSelectMitJoin.executeQuery(joinKundeUndTelefon);

            while(rs.next()){
                System.out.println("Vorname: " + rs.getString("Vorname") + " Telefon: "
                        + rs.getString("Telefonnummer"));
            }




        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



}

